package com.bilgidoku.rom.gwt.client.common.coders;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class JsonCoder extends TypeCoder<Json>{

	@Override
	public Json decode(JSONValue json) {
		return new Json(json);
	}

	@Override
	public JSONValue encode(Json obj) {
		if(obj==null)
			return JSONNull.getInstance();
		return obj.getValue();
	}

	@Override
	public Json[] createArray(int size) {
		return new Json[size];
	}

	
}
