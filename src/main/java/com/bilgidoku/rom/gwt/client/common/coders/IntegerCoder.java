package com.bilgidoku.rom.gwt.client.common.coders;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;

public class IntegerCoder extends TypeCoder<Integer>{

	@Override
	public Integer decode(JSONValue json) {
		return getInteger(json);
	}
	
	@Override
	public JSONValue encode(Integer obj) {
		if(obj==null){
			return JSONNull.getInstance();
		}
		return new JSONNumber(obj);
	}

	@Override
	public Integer[] createArray(int size) {
		return new Integer[size];
	}

}
