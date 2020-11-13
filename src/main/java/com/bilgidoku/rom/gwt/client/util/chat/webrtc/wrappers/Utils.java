package com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers;

import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class Utils {
//
//	public static void consoleErr(String message) {
//		Sistem.outln("ERROR:" + message);
//	}
//
//	public static void consoleStr(String message) {
//		Sistem.outln(message);
//	}
//
//	public static void consoleJs(JavaScriptObject message) {
//		Sistem.outln((new JSONObject(message)).toString());
//	}

	// public static void consoleLog(String message) {
	// Sistem.outln(message);
	// }
	//
	// public static void consoleLog(JavaScriptObject message) {
	// Sistem.outln((new JSONObject(message)).toString());
	// }
	//
	// public static void consoleDebug(JavaScriptObject message) {
	// Sistem.outln((new JSONObject(message)).toString());
	// }
	//
	public static native String createMediaObjectBlobUrl(JavaScriptObject stream) /*-{
		return URL.createObjectURL(stream);
	}-*/;

	
	public static final JavaScriptObject toJSON(JavaScriptObject jo) {
		try{
			return toJSONnative(jo);
		}catch(Exception e){
			return jo;
		}
		
	}
	
	
	private static final native JavaScriptObject toJSONnative(JavaScriptObject jo) /*-{
		return jo.toJSON();
	}-*/;
}
