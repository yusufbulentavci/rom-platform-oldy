package com.bilgidoku.rom.gwt.client.common.coders;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class StringCoder extends TypeCoder<String>{

	@Override
	public String decode(JSONValue json) {
		return getString(json);
	}

	@Override
	public JSONValue encode(String obj) {
		if(obj==null){
			return JSONNull.getInstance();
		}
		return new JSONString(obj);
	}

	@Override
	public String[] createArray(int size) {
		return new String[size];
	}

}
