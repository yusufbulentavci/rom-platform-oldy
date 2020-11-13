package com.bilgidoku.rom.shared.json;

import java.util.Set;

import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.err.KnownError;

public class JSONObject  extends JSONValue{

	public JSONObject() {
		super(Portable.one.jsonObjectConstruct());
	}
	
	public JSONObject(Object ntv) {
		super(ntv);
	}

	public JSONValue get(String key) throws RunException {
		return Portable.one.jsonObjectGet(ntv, key);
	}
	
	public JSONValue opt(String key) {
		return Portable.one.jsonObjectOpt(ntv, key);
	}

	public Set<String> keySet() {
		return Portable.one.jsonObjectKeySet(ntv);
	}

	public void put(String key, JSONValue value){
		Portable.one.jsonObjectPut(ntv, key, value);
	}

	public int size() {
		return Portable.one.jsonObjectSize(ntv);
	}
	
	public String optString(String key){
		JSONValue o = opt(key);
		if(o==null)
			return null;
		JSONString s = o.isString();
		if(s==null){
//			if(o.isNull()==null)
//				throw new RunException("Json Key:"+key+" expected string but"+o.toString());
			return null;
		}
		return s.stringValue();
	}
	
	public boolean optBoolean(String key, boolean ret){
		JSONValue o = opt(key);
		if(o==null)
			return ret;
		JSONBoolean s = o.isBoolean();
		if(s==null){
			if(o.isNull()==null)
				throw new RuntimeException("Json Key:"+key+" expected string but"+o.toString());
			return ret;
		}
		return s.booleanValue();
	}
	
	public Integer optInt(String key){
		JSONValue o = opt(key);
		if(o==null)
			return null;
		JSONNumber s = o.isNumber();
		if(s==null){
			if(o.isNull()==null)
				throw new RuntimeException("Json Key:"+key+" expected int but"+o.toString());
			return null;
		}
		return s.intValue();
	}

	public String getString(String key) throws RunException {
		if (opt(key) == null || opt(key).isString() == null) {
			throw new RunException("Key:"+key+" not found for object:"+this.toString());
		}
		
		return opt(key).isString().stringValue();
		
	}

	public JSONObject optObject(String key) throws RunException {
		JSONValue o = opt(key);
		if(o==null)
			return null;
		JSONObject jo=o.isObject();
		if(jo==null){
			if(o.isNull()==null)
				throw new RunException("Json Key:"+key+" expected object but"+o.toString());
			return null;
		}
		return jo;
	}

	public JSONArray optArray(String key) throws RunException {
		JSONValue o = opt(key);
		if(o==null)
			return null;
		JSONArray ja=o.isArray();
		if(ja==null){
			if(o.isNull()==null)
				throw new RunException("Json Key:"+key+" expected array but"+o.toString());
			return null;
		}
		return ja;
	}

	public int getInt(String key) throws RunException {
		JSONValue o = opt(key);
		if(o==null)
			throw new RunException("Json Key:"+key+" expected not found in "+this.toString());
		JSONNumber b=o.isNumber();
		if(b==null){
			if(o.isNull()==null)
				throw new RunException("Json Key:"+key+" expected int but"+o.toString());
			throw new RunException("Json Key:"+key+" expected not found in "+this.toString());
		}
		return b.intValue();
	}
	
	public long getLong(String key) throws RunException {
		JSONValue o = opt(key);
		if(o==null)
			throw new RunException("Json Key:"+key+" expected not found in "+this.toString());
		JSONNumber b=o.isNumber();
		if(b==null){
			if(o.isNull()==null)
				throw new RunException("Json Key:"+key+" expected long but"+o.toString());
			throw new RunException("Json Key:"+key+" expected not found in "+this.toString());
		}
		return b.longValue();
	}
	
	
	public boolean getBoolean(String key) throws RunException {
		JSONValue o = opt(key);
		if(o==null)
			throw new RunException("Json Key:"+key+" expected not found in "+this.toString());
		JSONBoolean b=o.isBoolean();
		if(b==null){
			if(o.isNull()==null)
				throw new RunException("Json Key:"+key+" expected boolean but"+o.toString());
			throw new RunException("Json Key:"+key+" expected not found in "+this.toString());
		}
		return b.booleanValue();
	}
	
	public boolean getBoolean(String key, boolean def) throws RunException {
		JSONValue o = opt(key);
		if(o==null)
			return def;
		JSONBoolean b=o.isBoolean();
		if(b==null){
			if(o.isNull()==null)
				throw new RunException("Json Key:"+key+" expected boolean but"+o.toString());
			return def;
		}
		return b.booleanValue();
	}
	
	public int getInt(String key, int def) throws RunException {
		JSONValue o = opt(key);
		if(o==null)
			return def;
		JSONNumber b=o.isNumber();
		if(b==null){
			if(o.isNull()==null)
				throw new RunException("Json Key:"+key+" expected int but"+o.toString());
			return def;
		}
		return b.intValue();
	}

	public JSONObject getObject(String key) throws RunException {
		JSONValue o = opt(key);
		if(o==null)
			return null;
		JSONObject jo=o.isObject();
		if(jo==null){
			if(o.isNull()==null)
				throw new RunException("Json Key:"+key+" expected object but"+o.toString());
			throw new RunException("Json Key:"+key+" shouldnt have been null but it is");
		}
		return jo;
	}


	public JSONObject cloneWrap() throws RunException {
		return Portable.one.jsonParserParseStrict(this.toString()).isObject();
	}

	public void put(String key, String named){
		this.put(key, new JSONString(named));
	}

	public void put(String key, boolean bol){
		this.put(key, JSONBoolean.getInstance(bol));
	}
	
	public void put(String key, int i){
		this.put(key, new JSONNumber(i));
	}

	public String[] getStringArray(String string) throws RunException {
		JSONValue v=opt(string);
		if(v==null)
			return null;
		JSONArray ja=v.isArray();
		if(ja==null)
			return null;
		return ja.toStringArray();
	}

	public JSONArray getArray(String key) throws RunException {
		JSONValue o = opt(key);
		if(o==null)
			return null;
		JSONArray jo=o.isArray();
		if(jo==null){
			if(o.isNull()==null)
				throw new RunException("Json Key:"+key+" expected array but"+o.toString());
			throw new RunException("Json Key:"+key+" shouldnt have been null but it is");
		}
		return jo;
	}

	public void safePut(String string, int i) {
		// TODO Auto-generated method stub
		
	}

	public void safePut(String string, JSONObject describe) {
		// TODO Auto-generated method stub
		
	}

	public void safePut(String string, String string2) {
		// TODO Auto-generated method stub
		
	}

	public void safePut(String string, long size) {
		// TODO Auto-generated method stub
		
	}

	public String prettyPrint() {
		// TODO Auto-generated method stub
		return null;
	}

	public void put(String key, Object o) {
		// TODO Auto-generated method stub
		
	}

	

	
	

	
}
