package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.expr.MyLiteral;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.xml.Elem;

class ArrOrObj {

	private JSONArray values;
	private String[] keys;

	public ArrOrObj(JSONArray arr) {
		this.values = arr;

	}

	public ArrOrObj(JSONObject obj) throws RunException {
		values = new JSONArray();
		if (obj == null) {
			return;
		}
		keys = new String[obj.keySet().size()];
		int i = 0;
		for (String it : obj.keySet()) {
			values.set(i, obj.get(it));
			keys[i] = it;
			++i;
		}
	}

	public boolean isReady() {
		return values != null;
	}

	public int size() {
		return values.size();
	}

	public JSONValue get(int itemNo) throws RunException {
		return values.get(itemNo);
	}

	public String key(int itemNo) throws RunException {
		if (keys == null)
			return null;
		return keys[itemNo];
	}
}

public class ForeachCommand extends Command {


	public static final String[] Routines = { "item", "pagebefore", "pagein", "pageout", "pageafter",
			"rowbefore", "rowin", "rowout", "rowafter", "firstnavbefore", "firstnavin", "firstnavout", "firstnavafter",
			"firstnavactive", "firstnavpassive", "secondnavbefore", "secondnavin", "secondnavout", "secondnavafter",
			"secondnavactive", "secondnavpassive" };

	private static final int ITEM_OBJ = 0;
	private static final int PAGE_BEFORE = 1;
	private static final int PAGE_IN = 2;
	private static final int PAGE_OUT = 3;
	private static final int PAGE_AFTER = 4;
	private static final int ROW_BEFORE = 5;
	private static final int ROW_IN = 6;
	private static final int ROW_OUT = 7;
	private static final int ROW_AFTER = 8;
	private static final int FIRST_NAV_BEFORE = 9;
	private static final int FIRST_NAV_IN = 10;
	private static final int FIRST_NAV_OUT = 11;
	private static final int FIRST_NAV_AFTER = 12;
	private static final int FIRST_NAV_ACTIVE = 13;
	private static final int FIRST_NAV_PASSIVE = 14;
	private static final int SECOND_NAV_BEFORE = 15;
	private static final int SECOND_NAV_IN = 16;
	private static final int SECOND_NAV_OUT = 17;
	private static final int SECOND_NAV_AFTER = 18;
	private static final int SECOND_NAV_ACTIVE = 19;
	private static final int SECOND_NAV_PASSIVE = 20;

	public ForeachCommand() {
		super("c:foreach");
	}

	private Elem createDivNode(String cls) {
		return createClassedNode("div", cls);
	}

	private Elem createClassedNode(String tag, String cls) {
		Elem ret = new Elem(tag);
		ret.setAttribute("class", cls);
		return ret;
	}

	@Override
	public Tag getDef() {
		Att[] ats=new Att[Routines.length+6];
		ats[0] = new AttImpl("array", false, Att.STRING, emptylist, 1, "");
		ats[1] = new AttImpl("var", false, Att.STRING, emptylist, 1, "");
		ats[2] = new AttImpl("styleprefix", false, Att.STRING, emptylist, 1, "");

		ats[3] = new AttImpl("maxpage", false, Att.STRING, emptylist, 1, "");
		ats[4] = new AttImpl("maxrow", false, Att.STRING, emptylist, 1, "");
		ats[5] = new AttImpl("maxcol", false, Att.STRING, emptylist, 1, "");
		
		for (int i=0;i<Routines.length;i++) {
			ats[6+i]=new AttImpl(Routines[i], true, Att.STRING, emptylist, 1, "").routine();
		}

		return new TagImpl("c:foreach", false, "cmd", ats,
				emptylist, null, "");
	}

	@Override
	public void execute(Elem curElem, final RenderCallState rcs) throws RunException {

		String var = rcs.getParamStr("var", true, "");

		// String array = rcs.getParam("array", false, null);
		MyLiteral array = rcs.evaluateLiteral("array", false, null);
		ArrOrObj ja;
		if (array == null || array.getNull()) {
			ja = new ArrOrObj(new JSONArray());
		} else if (array.isArray()) {
			ja = new ArrOrObj(array.getArray());
		} else {
			ja = new ArrOrObj(array.getObj());
		}

		int maxPage = rcs.evaluateInt("maxpage", true, 25);
		int maxRow = rcs.evaluateInt("maxrow", true, 100);
		int maxCol = rcs.evaluateInt("maxcol", true, 20);

		// Logger logger = Logger.getLogger("FOREACH");

		Integer pageId = rcs.getScopeInt("_fe_pageid" + var, true, null);
		if (pageId == null || pageId < 0) {
			// logger.log(Level.SEVERE,
			// "?????????????????????pageid NOT FOUND:");
			pageId = 0;
			rcs.setVariable("_fe_pageid" + var, pageId);
		}

		// logger.log(Level.SEVERE, "?????????????????????pageid:" + pageId);

		String styleprefix = rcs.evaluateParam("styleprefix", true, "");

		String injects[]=new String[Routines.length];
//		Map<String, String> injects = rcs.getInjects();
		for (int i=0; i<Routines.length; i++) {
			String rtn = rcs.getParamStr(Routines[i], true, null);
			injects[i]=rtn;
		}
		
		
		if (injects[ITEM_OBJ] == null) {
			throw new RunException("For each item inject is null", rcs.getStackTrace());
		}

		// Kac safya olacagini bulmaya calisiyoruz
		float pf = ((float) ja.size() / (maxRow * maxCol));
		// Bolmede asagi yuvarlama var, sonuc tamsayi degilse
		// sayfa sayisi 1 tane fazladir
		int pageCount = (pf == (int) pf) ? (int) pf : (int) pf + 1;
		pageCount = Math.min(maxPage, pageCount);

		// var eklemeleri deprecated, runzone'lari icat etmeden once yapmistim
		// sonra cikartacagim onlari, simdilik hep 2 kullaniyoruz
		rcs.setVariable("_fe_pagecount" + var, pageCount);
		rcs.setVariable("_fe_itemcount" + var, ja.size());

		// Sayfanin ilk elemaninin numarasini hesapliyoruz
		int itemNo = pageId * maxRow * maxCol;
		// Sayafanin son elemaninin numarasini hesapliyoruz
		int jalast = Math.min((pageId + 1) * maxRow * maxCol, ja.size()) - 1;

		// Nasil calistigini anlama icin burasini acabilirsin

		// curElem.appendNodeValue("===========mp:" + maxPage + ",mr:" + maxRow
		// + ",mc:" + maxCol + ",pc:" + pageCount + ",ic:"
		// + ja.size() + ",pi:"+pageId+",in:"+itemNo+",ls:"+jalast);

		// En ustte loopnode var
		Elem loopNode = createDivNode(styleprefix + "_fe");
		curElem.appendChild(loopNode);

		boolean neol = true;

		// First Navigation nav'lari bossa hic girmeyelim
		if (injects[FIRST_NAV_ACTIVE] != null && injects[FIRST_NAV_PASSIVE] != null) {

			// first nav oncesi div
			if (injects[FIRST_NAV_BEFORE] != null) {
				inject(rcs, loopNode, styleprefix + "_fe_nav_first_before fe_nav_first_before", injects, FIRST_NAV_BEFORE);
			}

			Elem navNode = createDivNode(styleprefix + "_fe_nav_first fe_nav_first");
			loopNode.appendChild(navNode);

			// first nav'in ilk div'i
			if (injects[FIRST_NAV_IN] != null) {
				inject(rcs, navNode, styleprefix + "_fe_nav_first_in fe_nav_first_in", injects, FIRST_NAV_IN);
			}

			if (pageCount > 1) {
				// her sayfa icin active veya pasif navigator'lar inject
				// ediliyor
				for (int i = 0; i < pageCount; i++) {
					// sayfa numarasi _fe_nav_page_id2'de
					rcs.setVariable("_fe_nav_page_id" + var, i);
					if (i == pageId) {
						inject(rcs, navNode, styleprefix + "_fe_nav_first_active fe_nav_first_active", injects, FIRST_NAV_ACTIVE);
					} else {
						inject(rcs, navNode, styleprefix + "_fe_nav_first_passive fe_nav_first_passive", injects, FIRST_NAV_PASSIVE);
					}
				}
			}

			// Cikis div'i
			if (injects[FIRST_NAV_OUT] != null) {
				inject(rcs, navNode, styleprefix + "_fe_nav_first_out fe_nav_first_out", injects, FIRST_NAV_OUT);
			}

			// Cikis arkasi div'i
			if (injects[FIRST_NAV_AFTER] != null) {
				inject(rcs, loopNode, styleprefix + "_fe_nav_first_after fe_nav_first_after", injects, FIRST_NAV_AFTER);
			}
		}

		// Sayfa oncesi div'i
		if (injects[PAGE_BEFORE] != null) {
			inject(rcs, loopNode, styleprefix + "_fe_page_before fe_page_before", injects, PAGE_BEFORE);
		}

		// Sonunda sayfa div'i ekleniyor
		Elem pageNode = createDivNode(styleprefix + "_fe_page fe_page");
		loopNode.appendChild(pageNode);

		// Sayfa div'inin ilk div'i ekleniyor
		if (injects[PAGE_IN] != null) {
			inject(rcs, pageNode, styleprefix + "_fe_page_in fe_page_in", injects, PAGE_IN);
		}

		// Satirlara geciliyor
		for (int rowno = 0; rowno < maxRow && neol; ++rowno) {
			// her satir'in numaraasi _fe_row2'de
			rcs.setVariable("_fe_row" + var, rowno);
			if (injects[ROW_BEFORE] != null) {
				// Satir oncesi div'i
				inject(rcs, pageNode, styleprefix + "_fe_row_before", injects, ROW_BEFORE);
			}

			// row div'i
			Elem rowNode = createClassedNode("ul", styleprefix + "_fe_row fe_row");
			pageNode.appendChild(rowNode);

			// row'in ilk div'i
			if (injects[ROW_IN] != null) {
				inject(rcs, rowNode, styleprefix + "_fe_row_in fe_row_in", injects, ROW_IN);
			}

			// Content'e gelmek uzereyiz, colon'larda eklenecek
			for (int colno = 0; colno < maxCol && neol; ++colno) {

				if (itemNo > jalast) {
					neol = false;
					--colno;
					continue;
				}

				JSONValue jo = ja.get(itemNo);
				String key = ja.key(itemNo);
				rcs.setVariable("_fe_item" + var, jo);
				if (key != null)
					rcs.setVariable("_fe_key" + var, new JSONString(key));
				rcs.setVariable("_fe_ind" + var, itemNo);

				Elem itemNode = createClassedNode("li", styleprefix + "_fe_item fe_item");

				if (maxCol > 1 && maxCol < 21) {
					itemNode.setAttribute("style", "width:" + (100 / maxCol) + "%");
					// DecimalFormat df = new DecimalFormat("0.#");
					// double w = 100 / (double) maxCol;
					// itemNode.setAttribute("style", "width:" + df.format(w) +
					// "%");

				}

				rowNode.appendChild(itemNode);
				rcs.runRoutine(injects[ITEM_OBJ], itemNode);
				++itemNo;
			}

			if (injects[ROW_OUT] != null) {
				inject(rcs, rowNode, styleprefix + "_fe_row_out fe_row_out", injects, ROW_OUT);
			}

			if (injects[ROW_AFTER] != null) {
				inject(rcs, pageNode, styleprefix + "_fe_row_after fe_row_after", injects, ROW_AFTER);
			}

		}

		if (injects[PAGE_OUT] != null) {
			inject(rcs, pageNode, styleprefix + "_fe_page_out fe_page_out", injects, PAGE_OUT);
		}

		if (injects[PAGE_AFTER] != null) {
			inject(rcs, loopNode, styleprefix + "_fe_page_after fe_page_after", injects, PAGE_AFTER);
		}

		if (injects[SECOND_NAV_ACTIVE] != null && injects[SECOND_NAV_PASSIVE] != null) {
			if (injects[SECOND_NAV_BEFORE] != null) {
				inject(rcs, loopNode, styleprefix + "_fe_nav_sec_before fe_nav_sec_before", injects, SECOND_NAV_BEFORE);
			}
			Elem navNode = createDivNode(styleprefix + "_fe_nav_sec fe_nav_sec");
			loopNode.appendChild(navNode);

			if (injects[SECOND_NAV_IN] != null) {
				inject(rcs, navNode, styleprefix + "_fe_nav_sec_in fe_nav_sec_in", injects, SECOND_NAV_IN);
			}

			if (pageCount > 1) {
				for (int i = 0; i < pageCount; i++) {
					rcs.setVariable("_fe_nav_page_id" + var, i);
					if (i == pageId) {
						inject(rcs, navNode, styleprefix + "_fe_nav_sec_item_active fe_nav_sec_item_active", injects, SECOND_NAV_ACTIVE);
					} else {
						inject(rcs, navNode, styleprefix + "_fe_nav_sec_item_passive fe_nav_sec_item_passive", injects, SECOND_NAV_PASSIVE);
					}
				}
			}

			if (injects[SECOND_NAV_OUT] != null) {
				inject(rcs, navNode, styleprefix + "_fe_nav_sec_out fe_nav_sec_out", injects, SECOND_NAV_OUT);
			}

			if (injects[SECOND_NAV_AFTER] != null) {
				inject(rcs, loopNode, styleprefix + "_fe_nav_sec_after fe_nav_sec_after", injects, SECOND_NAV_AFTER);
			}
		}
	}

	private void inject(final RenderCallState rcs, Elem container, String cls, String[] injects, int ind) throws RunException {
		Elem injectNode = createDivNode(cls);
		container.appendChild(injectNode);
		rcs.runRoutine(injects[ind], injectNode);
	}

}