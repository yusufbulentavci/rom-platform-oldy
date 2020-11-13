package com.bilgidoku.rom.shared.json;

import com.bilgidoku.rom.shared.Portable;

public class JSONNull extends JSONValue{
	private static JSONNull one;
	
	public JSONNull(Object ntv) {
		super(ntv);
	}
	

	public static JSONNull getInstance() {
		if(one==null)
			one=new JSONNull(Portable.one.jsonNullGetInstance());
		return one;
	}

}
