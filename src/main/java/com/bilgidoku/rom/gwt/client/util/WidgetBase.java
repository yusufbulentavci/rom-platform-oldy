package com.bilgidoku.rom.gwt.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class WidgetBase {
	
	private static JSONObject ws=new JSONObject(base());
	public static JSONObject getWidget(String name) {
		JSONValue jv=ws.get(name);
		if(jv==null)
			return null;
		return jv.isObject();
	}
	public static JSONObject getWidgets() {
		return ws;
	}

///RESTISAUTO
	private static native JavaScriptObject base()
	/*-{
var a={};
return a;
	}-*/;
}
