package com.bilgidoku.rom.gwt.client.common.coders;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;

public class ShortCoder extends TypeCoder<Short>{

	@Override
	public Short decode(JSONValue json) {
		return getShort(json);
	}
	
	@Override
	public JSONValue encode(Short obj) {
		if(obj==null){
			return JSONNull.getInstance();
		}
		return new JSONNumber(obj);
	}

	@Override
	public Short[] createArray(int size) {
		return new Short[size];
	}

}
