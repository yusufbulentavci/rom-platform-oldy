package com.bilgidoku.rom.gwt.client.common;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public abstract class RespFactory<T> {
	public abstract T create(Object  based);
	
	public static String getString(Object based, String key){
		JSONObject o=((JSONValue)based).isObject();
		if(o==null)
			return null;
		JSONValue val=o.get(key);
		return getString(val);
	}
	
	public static String getString(JSONValue val){
		JSONString s = val.isString();
		if(s==null)
			return null;
		return s.stringValue();
	}
	
	public static String[] getStringArray(Object based, String key){
		JSONObject obj=((JSONObject)based).isObject();
		if(obj==null){
			throw new RuntimeException("Not an object in string array");
		}
		JSONValue v = obj.get(key);
		if(v==null || (v.isNull()!=null))	
			return null;
		
		JSONArray o=v.isArray();
		if(o==null){
			throw new RuntimeException("Not an array in string array");
		}
		String[] rets=new String[o.size()];
		for(int i=0; i<rets.length; i++){
			JSONString s=o.get(i).isString();
			if(s!=null){
				rets[i]=s.stringValue();
			}
		}
		return rets;
	}	
	public static Long getLong(Object based, String key){
		JSONObject o=((JSONValue)based).isObject();
		if(o==null)
			return null;
		JSONValue val=o.get(key);
		JSONNumber s=val.isNumber();
		if(s==null)
			return null;
		return (long) s.doubleValue();
	}	
	
	public static Integer getInteger(Object based, String key){
		JSONObject o=((JSONValue)based).isObject();
		if(o==null)
			return null;
		JSONValue val=o.get(key);
		JSONNumber s=val.isNumber();
		if(s==null)
			return null;
		return (int) s.doubleValue();
	}	
	public static Boolean getBoolean(Object based, String key){
		JSONObject o=((JSONValue)based).isObject();
		if(o==null)
			return null;
		JSONValue val=o.get(key);
		JSONBoolean s=val.isBoolean();
		if(s==null)
			return null;
		return s.booleanValue();
	}	
	public static Long[] getLongArray(Object based, String key){
		JSONObject obj=((JSONObject)based).isObject();
		if(obj==null){
			throw new RuntimeException("Not an object in long array");
		}
		JSONValue v = obj.get(key);
		if(v==null || (v.isNull()!=null))	
			return null;
		
		JSONArray o=v.isArray();
		if(o==null){
			throw new RuntimeException("Not an array in long array");
		}
		Long[] rets=new Long[o.size()];
		for(int i=0; i<rets.length; i++){
			JSONNumber s=o.get(i).isNumber();
			if(s!=null){
				rets[i]=(long) s.doubleValue();
			}
		}
		return rets;
	}
}
