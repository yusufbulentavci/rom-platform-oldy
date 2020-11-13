package com.bilgidoku.rom.gwt.client.common.coders;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;

public class LongCoder extends TypeCoder<Long>{

	@Override
	public Long decode(JSONValue json) {
		return getLong(json);
	}
	
	@Override
	public JSONValue encode(Long obj) {
		if(obj==null){
			return JSONNull.getInstance();
		}
		return new JSONNumber(obj);
	}

	@Override
	public Long[] createArray(int size) {
		return new Long[size];
	}

}
