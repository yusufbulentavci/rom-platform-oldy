package com.bilgidoku.rom.shared.json;

import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.json.JSONValue;

public class JSONBoolean  extends JSONValue{

	public JSONBoolean(Object o) {
		super(o);
	}

	public static JSONBoolean getInstance(boolean b) {
		return Portable.one.jsonBooleanGetInstance(b);
	}

	public boolean booleanValue() {
		return Portable.one.jsonBooleanBooleanValue(ntv);
	}

}
