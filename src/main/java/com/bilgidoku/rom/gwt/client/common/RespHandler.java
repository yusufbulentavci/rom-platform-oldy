package com.bilgidoku.rom.gwt.client.common;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.coders.TypeCoder;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

public abstract class  RespHandler<T> implements RequestCallback {
	
	private final TypeCoder<T> coder;
	private boolean isArray;
	private int argCount;
	public RespHandler(TypeCoder<T> coder, boolean isArray, int argCount){
		this.coder=coder;
		this.isArray=isArray;
		this.argCount=argCount;
	}
	
	void rawReady(JSONValue val){
		T t=coder.decode(val);
		ready(t);
	}
	void rawArray(JSONValue value){
		ObjectArray<T> myarr=new ObjectArray<T>(value,coder);
		array(myarr);
	}	

	public void err(int statusCode, String statusText){
//		Quote.error("dataresponse","statuscode:"+statusCode+" statustext:"+statusText);
		Window.alert("Error");
	}
	
	public void onError(Request request, Throwable exception) {
		err(20000, exception.getMessage());
	}

	public void onResponseReceived(Request request, Response response) {
		String responseText = response.getText();
		try {
			if(responseText==null||responseText.length()==0)
				empty();
			JSONValue jsonValue = JSONParser.parseStrict(responseText);
			if (jsonValue == null || jsonValue.isNull() != null) {
				empty();
				return;
			}
			if (isArray) {
				rawArray(jsonValue);
				return;
			}
			rawReady(jsonValue);
		} catch (JSONException e) {
			err(response.getStatusCode(), "Error:"+e.getMessage()+" Response"+responseText);
		}
	}
	
	public void ready(T value){
	}
	public void array(List<T> myarr) {
	}

	public void empty(){
	}
	
	public void postNow(String uri, 
			String tempParamStr) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST,
				uri);
		rb.setHeader("Content-type", "application/x-www-form-urlencoded");
		rb.setHeader("Accept", "application/json");

		try {
			rb.sendRequest(tempParamStr, this);
		} catch (RequestException e) {
			err(20001, e.getMessage());
		}
	}
	
	public void getNow(String tempUri) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, tempUri);
		rb.setHeader("Accept", "application/json");
		try {
			rb.sendRequest(null, this);
		} catch (RequestException e) {
			err(20001, e.getMessage());
		}
	}
	
	public void deleteNow(String self) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.DELETE, self);
		rb.setHeader("Accept", "application/json");
		try {
			rb.sendRequest(null, this);
		} catch (RequestException e) {
			err(20001, e.getMessage());
		}
	}
}