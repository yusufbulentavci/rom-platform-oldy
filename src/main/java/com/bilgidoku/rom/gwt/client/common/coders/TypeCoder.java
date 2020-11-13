package com.bilgidoku.rom.gwt.client.common.coders;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;


public abstract class TypeCoder<T> {
	public abstract T decode(JSONValue json);

	public abstract JSONValue encode(T obj);

	public abstract T[] createArray(int size);

	public static String getString(JSONValue val) {
		if (isNull(val))
			return null;
		JSONString s = val.isString();
		if (s == null)
			return null;
		return s.stringValue();
	}

	public static Boolean getBoolean(JSONValue val) {
		if (isNull(val))
			return null;
		JSONBoolean s = val.isBoolean();
		if (s == null)
			return null;
		return s.booleanValue();
	}

	public static Integer getInteger(JSONValue val) {
		if (isNull(val))
			return null;
		JSONNumber s = val.isNumber();
		if (s == null)
			return null;
		return (int) s.doubleValue();
	}
	
	public static Short getShort(JSONValue val) {
		if (isNull(val))
			return null;
		JSONNumber s = val.isNumber();
		if (s == null)
			return null;
		return (short) s.doubleValue();
	}

	public static Long getLong(JSONValue val) {
		if (isNull(val))
			return null;
		JSONNumber s = val.isNumber();
		if (s == null)
			return null;
		return (long) s.doubleValue();
	}

	public static boolean isNull(JSONValue json) {
		return (json == null || json.isNull() != null);
	}

	public JSONObject getObject(JSONValue js) {
		if (js == null || js.isNull() != null)
			return null;
		JSONObject json = js.isObject();
		if (json == null) {
			throw new RuntimeException("Is not an jsonobject:" + js.toString());
		}
		return json;
	}

}
