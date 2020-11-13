package com.bilgidoku.rom.shared.cmds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.CodeEditor;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.RunZone;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.code.CodeStat;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.render.Css;
import com.bilgidoku.rom.shared.xml.Doc;
import com.bilgidoku.rom.shared.xml.Elem;
import com.bilgidoku.rom.shared.xml.Text;

public class TextCommand extends Command {

	public TextCommand() {
		super("c:text");
	}

	@Override
	public Tag getDef() {
		Att strlen = new AttImpl("strlen", false, Att.INT, emptylist, 1, "");
		Att threedots = new AttImpl("threedots", false, Att.BOOL, emptylist, 1, "");
		// Att text = new AttImpl("text", false, Att.STRING, emptylist, 1, "");

		Att iseditable = new AttImpl("edtbl", false, Att.BOOL, emptylist, 1, "");

		Att iswiki = new AttImpl("wiki", false, Att.BOOL, emptylist, 1, "");
		Att symbl = new AttImpl("symbl", false, Att.STRING, emptylist, 1, "");
		Att datatable = new AttImpl("tbl", false, Att.STRING, new String[] { "rom.resources", "site.info",
				"site.writings", "site.contents" }, 1, "");
		Att datacolumn = new AttImpl("clmn", false, Att.STRING, new String[] { "title", "summary", "body", "spot",
				"headertext", "site_footer" }, 1, "");
		// Att datauri = new AttImpl("uri", false, Att.STRING, emptylist, 1,
		// "");
		// Att datalng = new AttImpl("lng", false, Att.BOOL, emptylist, 1, "");

		// Att conttext = new AttImpl("conttext", false, Att.STRING, new
		// String[] { "title", "user", "date" }, 1, "");
		// Att wiki = new AttImpl("wiki", false, Att.OBJECT, emptylist, 1, "");
		// Att contwiki = new AttImpl("contwiki", false, Att.STRING, new
		// String[] { "summary", "body", "spot", "head",
		// "foot" }, 1, "");

		return new TagImpl("c:text", false, "cmd", new Att[] { strlen, threedots, iseditable, iswiki, symbl, datatable,
				datacolumn }, emptylist, null, "");
	}

	protected void execute(Elem cElem, RenderCallState rcs) throws RunException {
		String txt = rcs.evalCodeText();
		if (txt != null) {
			boolean inChtml=false;
			// render by Html editor's
			Elem par = cElem.getParent();
			if(par!=null){
				String cls=par.getAttribute("class");
				if(cls!=null){
					inChtml=cls.indexOf("chtml")>=0;
				}
			}
			if(!inChtml){
				cElem.appendForAttribute("class", "chtml");
			}
			Integer strlen = rcs.getParamInt("strlen", true, null);
			if (strlen != null && txt.length() > strlen) {
				String threeDots = rcs.getParamStr("threedots", true, null);
				txt = truncate(txt, strlen, threeDots);
			}
			add(cElem, txt);
			return;
		}

		boolean isWiki = rcs.getParamBool("wiki", true, true);

		String symbl = rcs.getParamStr("symbl", false, null);
		String clmn = rcs.getParamStr("clmn", false, null);

		
		// Elem node = Doc.appendTag(cElem, "span");

		if (isWiki) {
			// render body, footer
			rcs.resetEdit();
			Elem node = cElem;
			JSONObject rawWikiCode = rcs.scopeLangedWiki(symbl, clmn);
			
			Code wikiCode = rcs.newWikiCode(rawWikiCode);
			CodeStat pcs=rcs.getParentCs();
			
			
			if(pcs==null)
				throw new RunException("TextCommand does not have parent div");
			
			Code attachTo = pcs.getCode();
			
			CodeEditor ce=Portable.one.codeEditor();
			if (ce!=null && rcs.getParamBool("edtbl", true, true)) {
				String nid=node.ensureId();
				
				String tbl = rcs.getParamStr("tbl", false, null);
				String uri = rcs.scopeText(symbl, "uri");
				String lngcode = rcs.getLang(symbl);
				
//				if(tbl.equals("site.info")){
//					node.appendForAttributeStyle("width:300px");
//				}
//				node.setAttribute("style", "display:inline-block");

				ce.addBoxer(rcs.getBoxHolderFeedback(), nid, lngcode, uri, tbl,  symbl, clmn, wikiCode);
			}

			
			
			if(rawWikiCode==null){
				Integer height=attachTo.ensureHeight();
				wikiCode.setPx("height", height);
			}else{
				Integer height=wikiCode.ensureHeight();
				
				if (wikiCode.children != null) {
//					if(wikiCode.animation!=null){
//						String clsta = rcs.addStyleClassForWidgetContainer(wikiCode.animation, wikiCode.getStyle());
//						if (clsta.startsWith(Css.ROM_TAG_STYLE)) {
//							node.appendForAttribute("class", clsta);
//						} else {
//							node.setAttribute("style", clsta);
//						}
//					}

					
					for (Code it : wikiCode.children) {
						it.setStyleByType("defaultstyle", "overflow", "auto");
					}
					height=rcs.injectBoxer(wikiCode.children, node, height);
					
				}
				
				
				attachTo.setPx("height", height);
				attachTo.replaceStyleText(node);
			}
			
			
		} else {
			// render summary
			Elem node = Doc.appendGroupingNode(cElem);
			String c = rcs.scopeLangedText(symbl, clmn);
			if (c == null) {
				// if(editing){
				// add(node,"edit?");
				// }
				return;
			}
			c = rcs.evaluateText(c);
			Integer strlen = rcs.getParamInt("strlen", true, null);
			if (strlen != null && c.length() > strlen) {
				String threeDots = rcs.getParamStr("threedots", true, null);
				c = truncate(c, strlen, threeDots);
			}
			add(node, c);
		}

		//
		// String myStr = rcs.getParamStr("contwiki", true, null);
		// JSONObject codes=null;
		// if (myStr != null) {
		// if (myStr.equals("summary")) {
		// codes = rcs.getWikiSummary();
		// } else if (myStr.equals("body")) {
		// codes = rcs.getWikiBody();
		// } else if (myStr.equals("spot")) {
		// codes = rcs.getWikiSpot();
		// } else if (myStr.equals("head")) {
		// codes = rcs.getWikiHeaderText();
		// } else if (myStr.equals("foot")) {
		// codes = rcs.getWikiFooterText();
		// }
		// wiki(rz, curElem, strlen, threeDots, codes);
		// return;
		// }
		//
		//
		// // codes = rcs.evalJSONObject("wiki", true, null);
		// // if (myStr != null) {
		// // wiki(rz, curElem, strlen, threeDots, codes);
		// // return;
		// // }
		//
		// myStr=rcs.evalCodeText();
		// if (myStr != null) {
		// myStr = truncate(myStr, strlen, threeDots);
		// add(curElem, myStr);
		// return;
		// }
		//
		// myStr = rcs.getParamStr("conttext", true, null);
		// if (myStr != null) {
		// if (myStr.equals("title")) {
		// myStr = rcs.getTextTitle();
		// add(curElem, myStr);
		// }
		// return;
		// }

	}

	// private void wiki(final RunZone rz, final Elem curElem, Integer strlen,
	// String threeDots, JSONObject codes) throws RunException {
	// // if (myStr == null || myStr.length() == 0)
	// // return;
	// // myStr = truncate(myStr, strlen, threeDots);
	// // Elem Elem = WikiRunner.renderXHTML(myStr, rz);
	// // if (Elem != null)
	// // curElem.appendChild(nod);
	// if(codes==null || codes.size()==0){
	// return;
	// }
	// rcs.newWikiCode(curElem, codes);
	// }

	private String truncate(String htmlStr, Integer strlen, String threeDots) {
		if (strlen == null)
			return htmlStr;

		if (htmlStr.length() <= strlen) {
			return htmlStr;
			// pad with space
			// StringBuffer pad = new StringBuffer();
			// for (int i = htmlStr.length(); i < strlen; i++) {
			// pad.append("&nbsp;");
			// }
			// return htmlStr + pad;
		}

		String str = htmlStr.substring(0, strlen);
		if (!str.substring(str.length() - 1).equals(" ")) {
			// find first space char
			int firstSpace = htmlStr.indexOf(" ", strlen);
			if (firstSpace > 0)
				str = htmlStr.substring(0, firstSpace);
		}
		if (threeDots != null) {
			return str + threeDots;
		} else {
			return str;
		}

		// if (threeDots != null) {
		// return htmlStr.substring(0, strlen - threeDots.length()) + threeDots;
		// } else {
		// return htmlStr.substring(0, strlen);
		// }
	}

	private void add(Elem currentNode, String htmlStr) {
		Text textNode = new Text(htmlStr);
		currentNode.appendChild(textNode);
	}

}
