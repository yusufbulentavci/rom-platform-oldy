package com.bilgidoku.rom.gwt.client.common.coders;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class MapCoder extends TypeCoder< Map<String,String> > {


	@Override
	public Map<String, String> decode(JSONValue v) {
		if(isNull(v))
			return null;
		JSONObject o=v.isObject();
		if(o==null){
			throw new RuntimeException("Not a object");
		}
		Map<String,String> rets=new HashMap<String,String>();
		for(String key: o.keySet()){
			JSONValue value=o.get(key);
			if(value==null)
				rets.put(key, null);
			else{
				String t=new StringCoder().decode(value);
				rets.put(key, t);
			}
		}
		return rets;
	}

	@Override
	public JSONValue encode(Map<String, String> obj) {
		if(obj==null){
			return JSONNull.getInstance();
		}
		JSONObject ret=new JSONObject();
		for (Entry<String, String> it : obj.entrySet()) {
			if(it.getValue()==null)
				continue;
			ret.put(it.getKey(), new StringCoder().encode(it.getValue()));
		}
		return ret;
	}

	@Override
	public Map<String, String>[] createArray(int size) {
		// TODO Auto-generated method stub
		return null;
	}

}
