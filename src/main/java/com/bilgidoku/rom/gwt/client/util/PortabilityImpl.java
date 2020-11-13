package com.bilgidoku.rom.gwt.client.util;

import java.util.Set;

import com.bilgidoku.rom.gwt.client.util.cmd.client.AddToCartCommandImpl;
import com.bilgidoku.rom.gwt.client.util.cmd.client.CafeCommandImpl;
import com.bilgidoku.rom.gwt.client.util.cmd.client.CartCheckoutCommandImpl;
import com.bilgidoku.rom.gwt.client.util.cmd.client.CommentCommandImpl;
import com.bilgidoku.rom.min.ILogger;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.CodeEditor;
import com.bilgidoku.rom.shared.Portability;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.cmds.CommandRepo;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONBoolean;
import com.bilgidoku.rom.shared.json.JSONNull;
import com.bilgidoku.rom.shared.json.JSONNumber;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.http.client.URL;

public class PortabilityImpl implements Portability, ILogger {

	private JSONBoolean btrue = new JSONBoolean(com.google.gwt.json.client.JSONBoolean.getInstance(true));
	private JSONBoolean bfalse = new JSONBoolean(com.google.gwt.json.client.JSONBoolean.getInstance(false));

	private CodeEditor codeEditor = null;

	public PortabilityImpl() {
		Sistem.log = this;

		CommandRepo.one().upgradeCommand(new CommentCommandImpl());
		CommandRepo.one().upgradeCommand(new AddToCartCommandImpl());
		CommandRepo.one().upgradeCommand(new CartCheckoutCommandImpl());
		CommandRepo.one().upgradeCommand(new CafeCommandImpl());

	}

	// @Override
	// public void rtConnect(String name, String rzid, String app, String
	// subject) {
	// WebSocketClient wsc = websockets.get(name);
	// if (wsc != null) {
	// wsc.close();
	// websockets.remove(name);
	// }
	// wsc = new WebSocketClient(rzid, app, subject, new RtReceiver() {
	// @Override
	// public void onMsg(RtEvent rte) {
	// // HtmlEventQueue.getOne().push(rte);
	// }
	// });
	// wsc.connect();
	// websockets.put(name, wsc);
	// }

	@Override
	public JSONString jsonValueIsString(Object ntv) {
		if (ntv == null)
			return null;
		com.google.gwt.json.client.JSONValue tv = (com.google.gwt.json.client.JSONValue) ntv;
		com.google.gwt.json.client.JSONString str = tv.isString();
		if (str == null)
			return null;
		return new JSONString(str.stringValue());
	}

	@Override
	public JSONNumber jsonValueIsNumber(Object ntv) {
		if (ntv == null)
			return null;
		com.google.gwt.json.client.JSONValue tv = (com.google.gwt.json.client.JSONValue) ntv;
		com.google.gwt.json.client.JSONNumber n = tv.isNumber();
		if (n == null)
			return null;
		return new JSONNumber(n);
	}

	@Override
	public JSONObject jsonValueIsObject(Object ntv) {
		if (ntv == null)
			return null;
		com.google.gwt.json.client.JSONValue tv = (com.google.gwt.json.client.JSONValue) ntv;
		com.google.gwt.json.client.JSONObject o = tv.isObject();
		if (o == null)
			return null;
		return new JSONObject(o);
	}

	@Override
	public JSONNull jsonValueIsNull(Object ntv) {
		if (ntv == null)
			return null;
		if (ntv == com.google.gwt.json.client.JSONNull.getInstance() || ntv == null)
			return JSONNull.getInstance();
		return null;
	}

	@Override
	public JSONBoolean jsonValueIsBoolean(Object ntv) {
		if (ntv == null)
			return null;
		com.google.gwt.json.client.JSONValue tv = (com.google.gwt.json.client.JSONValue) ntv;
		com.google.gwt.json.client.JSONBoolean b = tv.isBoolean();
		if (b == null)
			return null;
		return jsonBooleanGetInstance(b.booleanValue());
	}

	@Override
	public JSONArray jsonValueIsArray(Object ntv) {
		if (ntv == null)
			return null;
		com.google.gwt.json.client.JSONValue tv = (com.google.gwt.json.client.JSONValue) ntv;
		com.google.gwt.json.client.JSONArray a = tv.isArray();
		if (a == null)
			return null;
		return new JSONArray(a);
	}

	@Override
	public String jsonStringStringValue(Object ntv) {
		com.google.gwt.json.client.JSONString s = (com.google.gwt.json.client.JSONString) ntv;
		return s.stringValue();
	}

	@Override
	public Object jsonStringConstructor(String str) {
		return new com.google.gwt.json.client.JSONString(str);
	}

	@Override
	public JSONValue jsonParserParseStrict(String text) throws RunException {
		try {
			return new JSONValue(com.google.gwt.json.client.JSONParser.parseStrict(text));
		} catch (com.google.gwt.json.client.JSONException je) {
			throw new RunException("Portable json parser error:" + text, je);
		}
	}

	@Override
	public JSONValue jsonObjectGet(Object ntv, String key) throws RunException {
		com.google.gwt.json.client.JSONObject tv = (com.google.gwt.json.client.JSONObject) ntv;
		com.google.gwt.json.client.JSONValue v = tv.get(key);
		if (v == null) {
			throw new RunException("Expected field of obj is null: Field name" + key + " Ntv:" + ntv.toString());
		}

		return new JSONValue(v);
	}

	@Override
	public JSONValue jsonObjectOpt(Object ntv, String key) {
		com.google.gwt.json.client.JSONObject tv = (com.google.gwt.json.client.JSONObject) ntv;
		com.google.gwt.json.client.JSONValue v = tv.get(key);
		if (v == null)
			return null;
		return new JSONValue(v);
	}

	@Override
	public Set<String> jsonObjectKeySet(Object ntv) {
		com.google.gwt.json.client.JSONObject tv = (com.google.gwt.json.client.JSONObject) ntv;
		return tv.keySet();
	}

	@Override
	public void jsonObjectPut(Object ntv, String key, JSONValue value) {
		com.google.gwt.json.client.JSONObject tv = (com.google.gwt.json.client.JSONObject) ntv;
		tv.put(key, (com.google.gwt.json.client.JSONValue) value.ntv);
	}

	@Override
	public int jsonObjectSize(Object ntv) {
		com.google.gwt.json.client.JSONObject tv = (com.google.gwt.json.client.JSONObject) ntv;
		return tv.size();
	}

	@Override
	public double jsonNumberDoubleValue(Object ntv) {
		com.google.gwt.json.client.JSONNumber tv = (com.google.gwt.json.client.JSONNumber) ntv;
		return tv.doubleValue();

	}

	@Override
	public Object jsonNullGetInstance() {
		return com.google.gwt.json.client.JSONNull.getInstance();
	}

	@Override
	public JSONBoolean jsonBooleanGetInstance(boolean b) {
		if (b)
			return btrue;
		return bfalse;
	}

	@Override
	public boolean jsonBooleanBooleanValue(Object ntv) {
		com.google.gwt.json.client.JSONBoolean tv = (com.google.gwt.json.client.JSONBoolean) ntv;
		return tv.booleanValue();
	}

	@Override
	public int jsonArraySize(Object ntv) {
		com.google.gwt.json.client.JSONArray a = (com.google.gwt.json.client.JSONArray) ntv;
		return a.size();
	}

	@Override
	public JSONValue jsonArrayGet(Object ntv, int i) throws RunException {
		com.google.gwt.json.client.JSONArray a = (com.google.gwt.json.client.JSONArray) ntv;
		return new JSONValue(a.get(i));
	}

	@Override
	public void jsonArraySet(Object ntv, int i, JSONValue value) throws RunException {
		com.google.gwt.json.client.JSONArray a = (com.google.gwt.json.client.JSONArray) ntv;
		a.set(i, (com.google.gwt.json.client.JSONValue) value.ntv);
	}

	@Override
	public void jsonArrayAdd(Object ntv, JSONValue value) {
		com.google.gwt.json.client.JSONArray a = (com.google.gwt.json.client.JSONArray) ntv;
		a.set(a.size(), (com.google.gwt.json.client.JSONValue) value.ntv);
	}

	@Override
	public void domShow(String item, Boolean inverse) throws RunException {
		Element hi = Document.get().getElementById(item);
		if (hi == null)
			throw new RunException("item could not be found for showing:" + item);
		if (inverse)
			hi.getStyle().setDisplay(Display.NONE);
		else
			hi.getStyle().setDisplay(Display.BLOCK);
	}

	@Override
	public void domAppend(String htmlId, String htmlStr) throws RunException {
		Document document = Document.get();
		Element element = document.getElementById(htmlId);
		element.setInnerHTML(element.getInnerHTML() + htmlStr);
	}

	@Override
	public void domSet(String htmlId, String htmlStr) throws RunException {
		Document document = Document.get();
		Element element = document.getElementById(htmlId);
		element.setInnerHTML(htmlStr);
	}

	@Override
	public Object jsonObjectConstruct() {
		return new com.google.gwt.json.client.JSONObject();
	}

	@Override
	public Object jsonArrayConstuct() {
		return new com.google.gwt.json.client.JSONArray();
	}

	@Override
	public Object jsonNumberConstruct(Integer o) {
		return new com.google.gwt.json.client.JSONNumber(o);
	}

	@Override
	public Object jsonNumberConstruct(Double o) {
		return new com.google.gwt.json.client.JSONNumber(o);
	}

	@Override
	public JSONArray jsonArrayConstuctFromJS(Object params) {
		return new JSONArray(new com.google.gwt.json.client.JSONArray((JavaScriptObject) params));
	}

	@Override
	public String[] fieldValidate(JSONObject conf, String fieldName, String value) throws RunException {
		Validator v = new Validator(conf);
		return v.validate(fieldName, value);
	}

	@Override
	public JSONObject jsonObjectConstuctFromJS(Object event) {
		return new JSONObject(new com.google.gwt.json.client.JSONObject((JavaScriptObject) event));
	}

	@Override
	public void domScroll(String changeHtmlId, String refchange, boolean tobottom) {
		Element el = Document.get().getElementById(changeHtmlId);
		if (el == null)
			return;

		String[] parents = refchange.trim().split(" ");
		Element ret = el;
		for (String string : parents) {
			if (string.equals("..")) {
				ret = ret.getParentElement();
			}
		}

		if (tobottom) {
			ret.setPropertyInt("scrollTop", ret.getPropertyInt("scrollHeight"));
		} else {
			ret.setPropertyInt("scrollTop", 0);
		}
	}

	public void codeEditor(CodeEditor ce) {
		this.codeEditor = ce;
	}

	@Override
	public String urlEncode(String str) {
		return URL.encode(str);
	}

	@Override
	public void outln(String s) {
		error(s);
	}

	@Override
	public void errln(String s) {
		error(s);
	}

	@Override
	public CodeEditor codeEditor() {
		return codeEditor;
	}

	public static String st(Throwable aThrowable) {
		// add the class name and any message passed to constructor
		StringBuilder result = new StringBuilder("TRACE: ");
		result.append(aThrowable.toString());
		String NEW_LINE = "-- \n";
		result.append(NEW_LINE);

		// add each element of the stack trace
		for (StackTraceElement element : aThrowable.getStackTrace()) {
			result.append(element);
			result.append(NEW_LINE);
		}
		return result.toString();
	}

	@Override
	public void error(Exception e) {
		trace(e.fillInStackTrace());
	}

	private void trace(Throwable fillInStackTrace) {
		error(st(fillInStackTrace));
	}

	@Override
	public void error(String name, String desc) {
		error("Name:" + name + " Desc:" + desc);
	}

	@Override
	public void fatal(Exception e) {
		trace(e);
	}

	@Override
	public void fatal(String name, String desc) {
		error("Fatal: Name:" + name + " Desc:" + desc);
	}

	// @Override
	// public void error(String string) {
	// Logger.getGlobal().log(Level.SEVERE, string);
	// }

	public native void error(String string)/*-{
		console.log(string);
	}-*/;

	@Override
	public void printStackTrace(Throwable x, String extra) {
		error(extra);
		trace(x);
	}

	@Override
	public void outln(Object s) {
		if (s == null)
			s = "null";
		error(s.toString());

	}

	@Override
	public void errln(Object s) {
		if (s == null)
			s = "null";
		error(s.toString());
	}

	@Override
	public void printStackTrace(Throwable x, Object extra) {
		if (extra == null)
			extra = "null";
		error(extra.toString());
		trace(x);
	}

	@Override
	public void printStackTrace(Throwable x) {
		trace(x);
	}

	@Override
	public boolean isClient() {
		return true;
	}

	@Override
	public void redirect(String sucRedirect) {
		RomEntryPoint.com().set("_wnd.redirect", sucRedirect);
	}

	@Override
	public void tick(int millisec, final Runnable runnable) {
		Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {

			@Override
			public boolean execute() {
				runnable.run();

				return true;
			}

		}, millisec);
	}

	private final native NodeList<Element> querySelectorAll(String selectors) /*-{
		return $doc.querySelectorAll(selectors);
	}-*/;

	int selectorId=0;
	
	@Override
	public JSONArray select(String query) {
		NodeList<Element> fnd = querySelectorAll(query);
		JSONArray ja = new JSONArray();
		if(fnd==null)
			return ja;
		for(int i=0; i< fnd.getLength(); i++){
			Element ith = fnd.getItem(i);
			
			JSONObject jo=new JSONObject();
			if(ith.getId()!=null){
				ith.setId("romsel-"+(selectorId++));
			}
			if(ith.getId()!=null){
				jo.put("id", ith.getId());
				jo.put("uri", "#"+ith.getId());
			}
			if(ith.getTagName()!=null){
				jo.put("tag", ith.getTagName());
			}
			
			if(ith.getInnerText()!=null){
				jo.put("text", ith.getInnerText().trim());
			}
			ja.add(jo);
		}

		return ja;
	}

}
