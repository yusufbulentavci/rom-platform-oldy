package com.bilgidoku.rom.gwt.client.common.coders;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class BooleanCoder extends TypeCoder<Boolean>{

	@Override
	public Boolean decode(JSONValue json) {
		return getBoolean(json);
	}
	
	@Override
	public JSONValue encode(Boolean obj) {
		if(obj==null){
			return JSONNull.getInstance();
		}
		return JSONBoolean.getInstance(obj);
	}

	@Override
	public Boolean[] createArray(int size) {
		return new Boolean[size];
	}

}
