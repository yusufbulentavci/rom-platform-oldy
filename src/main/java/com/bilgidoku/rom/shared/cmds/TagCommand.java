package com.bilgidoku.rom.shared.cmds;

import java.util.Map;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.code.AjaxForm;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.render.Css;
import com.bilgidoku.rom.shared.xml.Doc;
import com.bilgidoku.rom.shared.xml.Elem;

public class TagCommand extends Command {
	public TagCommand() {
		super(null);
	}

	@Override
	public Tag getDef() {
		return null;
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		preview(curElem, rcs);
	}

	public static void preview(final Elem curElem, final RenderCallState rcs) throws RunException {
		String waitTag = rcs.getCurTag();
		Elem newNode = Doc.appendTag(curElem, waitTag);

		boolean inForm = false;
		if (waitTag.equals("form")) {
			rcs.form = new AjaxForm();
			inForm = true;
		}

		String mytext = rcs.evalCodeText();
		if (mytext != null) {
			newNode.setNodeValue(mytext);
		}

		widgetCommon(newNode, rcs);
		rcs.walkChildren(newNode);

		if (inForm)
			rcs.endForm(newNode);
	}
	//
	// public static void widgetCommon(final RunZone rz, Elem newNode, final
	// RenderCallState rcs) throws RunException {
	// Map<String, Map<String, String>> style = rcs.evalCodeStyle();
	// // boolean floats=style.containsKey("float");
	// if (style != null) {
	// if(rcs.isBox() && rcs.getBw()<900){
	//
	// }
	//
	// String clsta = rcs.addStyleClass(style);
	// if (clsta.startsWith(Css.ROM_TAG_STYLE)) {
	// newNode.appendForAttribute("class", clsta);
	// } else {
	// newNode.setAttribute("style", clsta);
	// }
	// }
	//
	// Map<String, String> ats = rcs.evalCodeAts();
	// if (ats != null) {
	// for (String key : ats.keySet()) {
	// String value = ats.get(key);
	// if (key.equals("class")) {
	// newNode.appendForAttribute("class", value);
	// } else {
	// newNode.setAttribute(key, value);
	// }
	// }
	// }
	// }

	public static void widgetCommon(Elem newNode, final RenderCallState rcs) throws RunException {

		try{
		Map<String, Map<String, String>> style = rcs.evalCodeStyle();
		if (style != null) {
			if (rcs.cs().box) {
				Map<String, String> ds = null;
				try {
					ds = style.get("defaultstyle");
					if(rcs.cs().overrideTop!=null){
						ds.put("top", rcs.cs().overrideTop+"px");
						ds.put("left", rcs.cs().overrideLeft+"px");
					}
				} catch (Exception e) {
					Sistem.errln(style);
				}

				if (ds != null) {
					String zind = ds.get("z-index");
					if (zind == null) {
						int ezind = rcs.estimateZindex();
						ds.put("z-index", "" + ezind);
					}
				}

			}
			String clsta = rcs.addStyleClass(style);
			if (clsta.startsWith(Css.ROM_TAG_STYLE)) {
				newNode.appendForAttribute("class", clsta);
			} else {
				newNode.setAttribute("style", clsta);
			}
		}

		Map<String, String> ats = rcs.evalCodeAts();
		if (ats != null) {
			for (String key : ats.keySet()) {
				String value = ats.get(key);
				if (key.equals("class")) {
					newNode.appendForAttribute("class", value);
				} else {
					newNode.setAttribute(key, value);
				}
			}
		}
		}catch(Exception e){
			Sistem.printStackTrace(e, "widgetCommon");
		}
	}

}
