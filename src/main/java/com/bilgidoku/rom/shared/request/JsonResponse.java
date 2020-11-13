package com.bilgidoku.rom.shared.request;

import com.bilgidoku.rom.shared.Postman;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONParser;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;

public class JsonResponse {
	public JSONValue obj;
	public boolean succ;
	public String errText;
	public int errCode;
	public boolean warn=false;
	
	private final Postman postman;
	private String lang;
	
	
	public JsonResponse(String lang, Postman postman) {
		this.postman=postman;
		this.lang=lang;
	}

	public void load(String href) throws RunException {
		if (href.indexOf('?') > 0) {
			href += "&";
		} else {
			href += "?";
		}
		href += "outform=json&lng="+lang;
		resolve(postman.postManGetJson(href));
	}
	
	public void post(Object session, String href, String data, String lang) throws RunException {
		if (data==null || data.length() == 0) {
			data = "outform=json&lng="+lang;
		} else {
			data += "&outform=json&lng="+lang;
		}

		resolve(postman.postManSynch(session, href,data));
	}
	
	public void submit(String formId, String resetFields) throws RunException {
//		if (method.equalsIgnoreCase("get")) {
//			if (action.indexOf('?') > 0) {
//				action += "&";
//			} else {
//				action += "?";
//			}
//			action += data;
//		} 
//		
		resolve(postman.postManSubmit(formId, resetFields));
	}

	
	private void resolve(String responseText) throws RunException {
		JSONValue responseVal = null;
		JSONObject respObj = null;
			if (responseText != null && responseText.length() > 0) {
				try{
					responseVal = JSONParser.parseStrict(responseText);
				}catch(RunException re){
					onSubmitErr(re);
					return;
				}
				respObj = responseVal.isObject();
				if (respObj != null) {
					JSONValue signVal = respObj.opt("romerror");
					if (notNull(signVal)) {
						onSubmitWarn(signVal.isString());
					}else{
						onSubmitSuc( respObj);
					}
				}else{
					onSubmitSuc(responseVal);
				}
			}

		
	}

	private boolean notNull(JSONValue b) {
		return b != null && b.isNull()==null;
	}

	private void onSubmitErr(Throwable exception) {
		this.succ=false;
		this.errText=exception.getMessage();
	}
	private void onSubmitSuc(JSONValue respObj) {
		this.succ=true;
		obj=respObj;
	}
	private void onSubmitWarn( JSONString string) {
		this.succ=false;
		this.warn=true;
		if(string==null)
			errText="";
			else
		this.errText=string.stringValue();
	}
}
