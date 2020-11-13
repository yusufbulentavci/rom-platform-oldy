package com.bilgidoku.rom.web.html;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.bilgidoku.rom.ilk.ServerSistem;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONTokener;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.CodeEditor;
import com.bilgidoku.rom.shared.Portability;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONBoolean;
import com.bilgidoku.rom.shared.json.JSONNull;
import com.bilgidoku.rom.shared.json.JSONNumber;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.util.Knife;

public abstract class PortabilityServerImpl implements Portability{

	private JSONBoolean btrue=new JSONBoolean(Boolean.TRUE);
	private JSONBoolean bfalse=new JSONBoolean(Boolean.FALSE);
	
	public PortabilityServerImpl() {
		Sistem.cur = new ServerSistem();
	}
	
//	private JSONNull jsonnull=new JSONNull(null);
	@Override
	public JSONString jsonValueIsString(Object ntv) {
		if(ntv==null)
			return null;
		if(ntv instanceof String)
			return new JSONString((String)ntv);
		return null;
	}

	@Override
	public JSONNumber jsonValueIsNumber(Object ntv) {
		if(ntv==null)
			return null;
		if(ntv instanceof Integer)
			return new JSONNumber((Integer)ntv);
		
		if(ntv instanceof Double)
			return new JSONNumber((Double)ntv);
		
		return null;
	}

	@Override
	public JSONObject jsonValueIsObject(Object ntv) {
		if(ntv==null)
			return null;
		if(ntv instanceof com.bilgidoku.rom.ilk.json.JSONObject)
			return new JSONObject(ntv);
		return null;
	}

	@Override
	public JSONNull jsonValueIsNull(Object ntv) {
		if(ntv==null)
			return JSONNull.getInstance();
		return null;
	}

	@Override
	public JSONBoolean jsonValueIsBoolean(Object ntv) {
		if(ntv==null)
			return null;
		if(!(ntv instanceof Boolean))
			return null;
		Boolean b=(Boolean) ntv;
		return jsonBooleanGetInstance(b);
	}

	@Override
	public JSONArray jsonValueIsArray(Object ntv) {
		if(ntv instanceof com.bilgidoku.rom.ilk.json.JSONArray)
			return new JSONArray(ntv);
		return null;
	}

	@Override
	public String jsonStringStringValue(Object ntv) {
		return (String) ntv;
	}

	@Override
	public Object jsonStringConstructor(String str) {
		return str;
	}

	@Override
	public JSONValue jsonParserParseStrict(String text) throws RunException {
		JSONTokener tk=new JSONTokener(text);
		try {
			char c = tk.nextClean();
			tk.back();
			if (c == '['){
				return new JSONArray(new com.bilgidoku.rom.ilk.json.JSONArray(text));
			}
			return new JSONObject(new com.bilgidoku.rom.ilk.json.JSONObject(text));
		} catch (JSONException e) {
			throw new RunException("Json parsing exception", e);
		}
	}

	@Override
	public JSONValue jsonObjectGet(Object ntv, String key) throws RunException {
		Object val;
		try {
			val = ((com.bilgidoku.rom.ilk.json.JSONObject)ntv).getNullable(key);
		} catch (JSONException e) {
			throw new RunException("JsonException jsonobjectget",e);
		}
		return selectValue(val);
	}
	
	@Override
	public JSONValue jsonObjectOpt(Object ntv, String key) {
		Object val = ((com.bilgidoku.rom.ilk.json.JSONObject)ntv).opt(key);
		return selectValue(val);
	}

	private JSONValue selectValue(Object val) {
		if(val==null || val==JSONNull.getInstance()){
			return JSONNull.getInstance();
		}else if(val instanceof String){
			return new JSONString((String)val);
		}else if(val instanceof com.bilgidoku.rom.ilk.json.JSONObject){
			return new JSONObject(val);
		}else if(val instanceof com.bilgidoku.rom.ilk.json.JSONArray){
			return new JSONArray(val);
		}else if(val instanceof Boolean){
			return new JSONBoolean(val);
		}else if(val instanceof Integer){
			return new JSONNumber(val);
		}
		return null;
	}

	@Override
	public Set<String> jsonObjectKeySet(Object ntv) {
		com.bilgidoku.rom.ilk.json.JSONObject obj=(com.bilgidoku.rom.ilk.json.JSONObject) ntv;
		Set<String> s=new HashSet<String>();
		Iterator<String> it = obj.keys();
		while(it.hasNext()){
			s.add(it.next());
		}
		return s;
	}

	@Override
	public void jsonObjectPut(Object ntv, String key, JSONValue value){
		com.bilgidoku.rom.ilk.json.JSONObject obj=(com.bilgidoku.rom.ilk.json.JSONObject) ntv;
		try {
			obj.put(key, value.getNative());
		} catch (JSONException e) {
			Sistem.printStackTrace(e);
		}
	}

	@Override
	public int jsonObjectSize(Object ntv) {
		com.bilgidoku.rom.ilk.json.JSONObject obj=(com.bilgidoku.rom.ilk.json.JSONObject) ntv;
		return obj.length();
	}

	@Override
	public double jsonNumberDoubleValue(Object ntv) {
		if(ntv instanceof Integer)
			return ((Integer)ntv).doubleValue();
		return (Double)ntv;
	}

	@Override
	public JSONNull jsonNullGetInstance() {
		return null;
	}

	@Override
	public JSONBoolean jsonBooleanGetInstance(boolean b) {
		if(b)
			return btrue;
		return bfalse;
	}

	@Override
	public boolean jsonBooleanBooleanValue(Object ntv) {
		return (Boolean)ntv;
	}

	@Override
	public int jsonArraySize(Object ntv) {
		com.bilgidoku.rom.ilk.json.JSONArray obj=(com.bilgidoku.rom.ilk.json.JSONArray) ntv;
		return obj.length();
	}
	
	@Override
	public void jsonArrayAdd(Object ntv, JSONValue val){
		com.bilgidoku.rom.ilk.json.JSONArray obj=(com.bilgidoku.rom.ilk.json.JSONArray) ntv;
		obj.put(val.getNative());
	}

	@Override
	public JSONValue jsonArrayGet(Object ntv, int i) throws RunException {
		com.bilgidoku.rom.ilk.json.JSONArray obj=(com.bilgidoku.rom.ilk.json.JSONArray) ntv;
		try {
			if(obj.isNull(i))
				return JSONNull.getInstance();
			return selectValue(obj.get(i));
		} catch (JSONException e) {
			throw new RunException("JsonException jsonArrayGet", e);
		}
	}


	@Override
	public void jsonArraySet(Object ntv, int i, JSONValue value) throws RunException {
		com.bilgidoku.rom.ilk.json.JSONArray obj=(com.bilgidoku.rom.ilk.json.JSONArray) ntv;
		try {
			obj.put(i, value.ntv);
		} catch (JSONException e) {
			throw new RunException("JsonException jsonarrayset failed", e);
		}
	}
	
	
	
	@Override
	public Object jsonObjectConstruct() {
		return new  com.bilgidoku.rom.ilk.json.JSONObject();
	}

	@Override
	public Object jsonArrayConstuct() {
		return new  com.bilgidoku.rom.ilk.json.JSONArray();
	}
	
	@Override
	public Object jsonNumberConstruct(Integer o) {
		return o;
	}

	@Override
	public Object jsonNumberConstruct(Double o) {
		return o;
	}

	@Override
	public void redirect(String uri){
		throw new RuntimeException("Redirect can not be called from server");
	}

	@Override
	public JSONArray jsonArrayConstuctFromJS(Object params) {
		throw new RuntimeException("Not be called from server");
	}


	@Override
	public String[] fieldValidate(JSONObject conf, String fieldName, String value) throws RunException {
		throw new RuntimeException("Not be called from server");
	}
	
	@Override
	public JSONObject jsonObjectConstuctFromJS(Object event) {
		throw new RuntimeException("Not be called from server");
	}
	
	

	public abstract void domShow(String item, Boolean inverse) throws RunException; 
	public abstract void domAppend(String htmlId, String htmlStr) throws RunException;

	public abstract void domSet(String htmlId, String htmlStr) throws RunException;


	@Override
	public String urlEncode(String str) {
		return Knife.urlEncode(str);
	}
	

	@Override
	public CodeEditor codeEditor() {
		return null;
	}


	@Override
	public boolean isClient() {
		return false;
	}
	
	@Override
	public void tick(int millisec, Runnable runnable) {
		throw new RuntimeException(
				"Shouldnt be called(server side)-tick");		
	}
}
