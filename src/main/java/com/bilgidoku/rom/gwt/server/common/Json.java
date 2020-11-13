package com.bilgidoku.rom.gwt.server.common;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONObject;

public class Json {
	final JSONArray array;
	final JSONObject object;

	public Json(JSONArray ja) {
		array=ja;
		object=null;
	}

	public Json(JSONObject jsonObject) {
		array=null;
		object=jsonObject;
	}

	public String getAsString() {
		return object!=null?object.toString():array.toString();
	}

	public Object getValue() {
		return object!=null?object:array;
	}

	public JSONObject getObject() {
		return object;
	}

}
