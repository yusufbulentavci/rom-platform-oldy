package com.bilgidoku.rom.gwt.client.common.coders;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class ArrayCoder<T> extends TypeCoder<T[]> {

	private TypeCoder<T> coder;

	public ArrayCoder(TypeCoder<T> coder) {
		this.coder = coder;
	}

	@Override
	public T[] decode(JSONValue v) {
		if (v == null || v.isNull() != null)
			return null;
		JSONArray o = v.isArray();
		if (o == null) {
			JSONObject k = v.isObject();
			if (k != null) {
				//It is null 
				return null;
			}

			throw new RuntimeException("Not an array:" + v.toString());
		}
		T[] rets = coder.createArray(o.size());
		for (int i = 0; i < rets.length; i++) {
			rets[i] = coder.decode(o.get(i));
		}
		return rets;
	}

	@Override
	public JSONValue encode(T[] obj) {
		if (obj == null) {
			return JSONNull.getInstance();
		}
		JSONArray o = new JSONArray();
		for (int i = 0; i < obj.length; i++) {
			T t = obj[i];
			if (t == null) {
				o.set(i, JSONNull.getInstance());
				continue;
			}
			o.set(i, coder.encode(t));
		}
		return o;
	}

	@Override
	public T[][] createArray(int size) {
		return null;
	}

}
