package com.bilgidoku.rom.shared.json;

import com.bilgidoku.rom.shared.Portable;

public class JSONValue  {

	public Object ntv;
	
	public JSONValue(Object o){
//		if(o==null)
//			throw new RuntimeException("Native value shouldnt be null");
		this.ntv=o;
	}
	
	public Object getNative(){
		return ntv;
	}
	
	public JSONString isString() {
		return Portable.one.jsonValueIsString(ntv);
	}

	public JSONNumber isNumber() {
		return Portable.one.jsonValueIsNumber(ntv);
	}

	public JSONObject isObject() {
		return Portable.one.jsonValueIsObject(ntv);
	}

	public JSONNull isNull() {
		return Portable.one.jsonValueIsNull(ntv);
	}

	public JSONBoolean isBoolean() {
		return Portable.one.jsonValueIsBoolean(ntv);
	}

	public JSONArray isArray() {
		return Portable.one.jsonValueIsArray(ntv);
	}
	
	public String toString(){
		if(ntv==null)
			return "null";
		return this.ntv.toString();
	}


}
