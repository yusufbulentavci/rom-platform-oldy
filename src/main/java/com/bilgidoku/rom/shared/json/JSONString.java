package com.bilgidoku.rom.shared.json;

import com.bilgidoku.rom.shared.Portable;

public class JSONString  extends JSONValue{

	public JSONString(String str) {
		super(Portable.one.jsonStringConstructor(str));
	}

	public String stringValue() {
		return Portable.one.jsonStringStringValue(ntv);
	}

}
