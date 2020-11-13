package com.bilgidoku.rom.gwt.client.common;

import com.google.gwt.json.client.JSONValue;

public class Json {

	final JSONValue val;
	public Json(JSONValue json) {
		val=json;
	}

	public JSONValue getValue() {
		return val;
	}
	
}
