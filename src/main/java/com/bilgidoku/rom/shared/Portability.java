package com.bilgidoku.rom.shared;

import java.util.Set;

import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONBoolean;
import com.bilgidoku.rom.shared.json.JSONNull;
import com.bilgidoku.rom.shared.json.JSONNumber;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;

public interface Portability extends ISistem{
	
	JSONString jsonValueIsString(Object ntv);

	JSONNumber jsonValueIsNumber(Object ntv);

	JSONObject jsonValueIsObject(Object ntv);

	JSONNull jsonValueIsNull(Object ntv);

	JSONBoolean jsonValueIsBoolean(Object ntv);

	JSONArray jsonValueIsArray(Object ntv);

	String jsonStringStringValue(Object ntv);

	Object jsonStringConstructor(String str);

	JSONValue jsonParserParseStrict(String text) throws RunException;

	JSONValue jsonObjectGet(Object ntv, String key) throws RunException;
	JSONValue jsonObjectOpt(Object ntv, String key);

	Set<String> jsonObjectKeySet(Object ntv);

	void jsonObjectPut(Object ntv, String key, JSONValue value);

	int jsonObjectSize(Object ntv);

	double jsonNumberDoubleValue(Object ntv);

	Object jsonNullGetInstance();

	JSONBoolean jsonBooleanGetInstance(boolean b);

	boolean jsonBooleanBooleanValue(Object ntv);

	int jsonArraySize(Object ntv);

	JSONValue jsonArrayGet(Object ntv, int i) throws RunException;



//	void work(Object session, RunZone rz, Elem container) throws RunException;

	void domShow(String item, Boolean inverse) throws RunException;

	void domAppend(String htmlId, String htmlStr)  throws RunException;

	void domSet(String htmlId, String htmlStr)  throws RunException;

	Object jsonObjectConstruct();

	Object jsonArrayConstuct();

	Object jsonNumberConstruct(Integer o);

	Object jsonNumberConstruct(Double o);

	void jsonArraySet(Object ntv, int i, JSONValue value) throws RunException;

	void error(Exception e);

	void error(String string, String string2);

	void fatal(Exception e);

	void fatal(String string, String string2);

	void error(String string);
	
	public JSONArray jsonArrayConstuctFromJS(Object params);
	public JSONObject jsonObjectConstuctFromJS(Object event);

	public void redirect(String sucRedirect);
	public String[] fieldValidate(JSONObject conf, String fieldName, String value) throws RunException;

//	public void rtConnect(String name, String rzid, String app, String subject);

	public void domScroll(String changeHtmlId, String refchange, boolean tobottom);

	void jsonArrayAdd(Object ntv, JSONValue val);
	String urlEncode(String str);

	CodeEditor codeEditor();

	void printStackTrace(Throwable x, String extra);

	boolean isClient();

	void tick(int millisec, Runnable runnable);

	JSONArray select(String query);
}
