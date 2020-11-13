package com.bilgidoku.rom.site.yerel.common;

import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PanelViewy extends ScrollPanel {

	private static final String TICK = "5";
	private static final String PAGEWIDTH = "980";

	private final CheckBox header = new CheckBox();
	private final CheckBox footer = new CheckBox();
	private final CheckBox login = new CheckBox();
	private final CheckBox logo = new CheckBox();
	private final CheckBox headmenu = new CheckBox();
	private final CheckBox headtext = new CheckBox();

	private final CheckBox footmenu = new CheckBox();
	private final CheckBox foottext = new CheckBox();

	private final CheckBox comment = new CheckBox();

	private final TextBox pagewidth = new TextBox();
	private final TextBox tick = new TextBox();
	private final CheckBox title = new CheckBox();
	private final CheckBox share = new CheckBox();

	public PanelViewy() {
		FlexTable holder = new FlexTable();
		Widget[] ws = new Widget[] { header, footer, login, logo, headmenu, headtext, footmenu, foottext, comment, title, share, pagewidth,
				tick };
		String[] lbls = new String[] { Ctrl.trans.showheader(), Ctrl.trans.showfooter(), Ctrl.trans.showlogin(), Ctrl.trans.showlogo(),
				Ctrl.trans.showheadmenu(), Ctrl.trans.showheadtext(), Ctrl.trans.showfootmenu(), Ctrl.trans.showfoottext(),
				Ctrl.trans.showcomments(), Ctrl.trans.pageTitle(), Ctrl.trans.faceBookShare(), Ctrl.trans.pagewidth(), Ctrl.trans.tick() };
		for (int i = 0; i < ws.length; i++) {
			holder.setHTML(i, 0, lbls[i]);
			holder.setWidget(i, 1, ws[i]);
		}
		this.add(holder);

	}

	public void load(JSONValue j) {
		JSONObject jo;
		if (j == null || j.isObject() == null) {
			jo = new JSONObject();
		} else {
			jo = j.isObject();
		}
		header.setValue(getbool(jo.get("header")));
		footer.setValue(getbool(jo.get("footer")));
		login.setValue(getbool(jo.get("login")));
		logo.setValue(getbool(jo.get("logo")));
		headmenu.setValue(getbool(jo.get("headmenu")));
		headtext.setValue(getbool(jo.get("headtext")));
		footmenu.setValue(getbool(jo.get("footmenu")));
		foottext.setValue(getbool(jo.get("foottext")));
		comment.setValue(getbool(jo.get("comment")));
		title.setValue(getbool(jo.get("title")));
		share.setValue(getbool(jo.get("share")));
		pagewidth.setValue(getint(jo.get("pagewidth"), PAGEWIDTH));
		tick.setValue(getint(jo.get("tick"), TICK));
	}

	private String getint(JSONValue jsonValue, String def) {
		if (jsonValue == null || jsonValue.isNumber() == null)
			return def;
		return "" + (int) (jsonValue.isNumber().doubleValue());
	}

	private Boolean getbool(JSONValue jsonValue) {
		if (jsonValue == null || jsonValue.isBoolean() == null) {
			return false;
		}
		return jsonValue.isBoolean().booleanValue();
	}

	public JSONObject get() {
		JSONObject jo = new JSONObject();
		jo.put("header", bool(header.getValue()));
		jo.put("footer", bool(footer.getValue()));
		jo.put("login", bool(login.getValue()));
		jo.put("logo", bool(logo.getValue()));
		jo.put("headmenu", bool(headmenu.getValue()));
		jo.put("headtext", bool(headtext.getValue()));
		jo.put("footmenu", bool(footmenu.getValue()));
		jo.put("foottext", bool(foottext.getValue()));
		jo.put("comment", bool(comment.getValue()));
		jo.put("title", bool(title.getValue()));
		jo.put("share", bool(share.getValue()));
		jo.put("pagewidth", inte(pagewidth.getValue(), PAGEWIDTH));
		jo.put("tick", inte(tick.getValue(), TICK));
		return jo;
	}

	private JSONValue inte(String value, String def) {
		if (value == null)
			value = def;
		return new JSONNumber(Double.parseDouble(value));
	}

	private JSONBoolean bool(Boolean boolean1) {
		if (boolean1 == null)
			return JSONBoolean.getInstance(false);
		return JSONBoolean.getInstance(boolean1);
	}

}
