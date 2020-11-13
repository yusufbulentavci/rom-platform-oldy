package com.bilgidoku.rom.gwt.client.common;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bilgidoku.rom.gwt.client.common.coders.TypeCoder;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

public abstract class  DataResponse<T> implements RequestCallback {
	
	protected TypeCoder<T> coder;
	private boolean isArray=false;
	
	private Map<String,JSONValue> params=new HashMap<String,JSONValue>();

	public void setCoder(TypeCoder<T> coder){
		this.coder=coder;
	}
	public void enableArray() {
		this.isArray = true;
	}

	
	public void addParam(String key,JSONValue p){
		if(p==null || p.isNull()!=null)
			return;
		params.put(key,p);
	}
	
	public void rawReady(JSONValue val){
		T t=coder.decode(val);
		ready(t);
	}
	void rawArray(JSONValue value){
		ObjectArray<T> myarr=new ObjectArray<T>(value,coder);
		array(myarr);
	}	

	
	public void err(int statusCode, String statusText, Throwable exception){
		if(statusCode==402){
			Sistem.errln("Not enough credits, you should load credits before doing such action");
		}else
			Sistem.errln("Error occured:"+statusText+" code("+statusCode+")");
	}
	
	public void onError(Request request, Throwable exception) {
		err(20000, request.toString(), exception);
	}

	public void onResponseReceived(Request request, Response response) {
		if(response.getStatusCode()!=200){
			err(response.getStatusCode(), response.getText(), null);
			return;
		}
		
//		if(response.getHeader("Set-Cookie")!=null){
			ClientDeep.cookieChanged();
//		}
		
		
		String responseText = response.getText();
		try {
			if(responseText==null||responseText.length()==0){
				ready(null);
				return;
			}
			JSONValue jsonValue = JSONParser.parseStrict(responseText);
			if (jsonValue == null || jsonValue.isNull() != null) {
				ready(null);
				return;
			}
			if (isArray) {
				rawArray(jsonValue);
				return;
			}
			JSONObject obj=jsonValue.isObject();
			JSONArray ja=jsonValue.isArray();
			if (obj == null && ja==null) {
				ready(null);
				return;
			}
			if(ja!=null){
				rawPrepare(ja);
				return;
			}
			jsonValue=obj.get("def");
			if (jsonValue == null || jsonValue.isNull() != null) {
				rawPrepare(obj);
				return;
			}
			rawPrepare(jsonValue);
		} catch (JSONException e) {
			err(response.getStatusCode(), "json Response"+responseText,e);
		} finally{
			this.beforeLeave();
		}
	}
	
	private void rawPrepare(JSONArray jsonValue) {
		rawReady(jsonValue);
	}
	
	private void rawPrepare(JSONValue jsonValue) {
		if(jsonValue.isObject()!=null){
			JSONObject jo = jsonValue.isObject();
			JSONValue romerror=jo.get("romerror");
			if(romerror!=null && romerror.isString()!=null){
				err(-1,romerror.isString().stringValue(), null);
				return;
			}
		}
		rawReady(jsonValue);
	}
	public void beforeLeave() {
	}
	public void ready(T value){
	}
	public void array(List<T> myarr) {
	}

	
	public void postNow(String uri) {
		if(uri.startsWith("//"))
			uri=uri.substring(1);
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST,
				uri);
		rb.setHeader("Content-type", "application/x-www-form-urlencoded");
		rb.setHeader("Accept", "application/json");

		try {
			String s=getParamStr();
			rb.sendRequest(s, this);
		} catch (RequestException e) {
			err(20001, "post->"+uri, e);
		}
	}
	
	public void getNow(String self) {
		if(self.startsWith("//"))
			self=self.substring(1);
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, self+"?"+getParamStr());
		rb.setHeader("Accept", "application/json");
		try {
			rb.sendRequest(null, this);
		} catch (RequestException e) {
			err(20001,"get->"+self, e);
		}
	}
	
	public void deleteNow(String self) {
		if(self.startsWith("//"))
			self=self.substring(1);
		RequestBuilder rb = new RequestBuilder(RequestBuilder.DELETE, self);
		rb.setHeader("Accept", "application/json");
		try {
			rb.sendRequest(null, this);
		} catch (RequestException e) {
			err(20001, "delete->"+self,e);
		}
	}
	
	private String getParamStr(){
		StringBuilder sb=new StringBuilder();
		sb.append("outform=json&romnocache=");
		sb.append(System.currentTimeMillis());
		for (Entry<String, JSONValue> p : params.entrySet()) {
			sb.append("&");
			sb.append(p.getKey());
			sb.append("=");
			String toap=null;
			if(p.getValue().isString()!=null){
				toap=p.getValue().isString().stringValue();
			}else{
				toap=p.getValue().toString();
			}
//			System.err.println("toap:"+toap);
//			if(p.getKey().equals("codes")){
//				JSONObject jo=p.getValue().isObject();
//				JSONValue j = jo.get("w:carousel");
//				String t=j.toString();
//				if(t.indexOf('+')>0){
//					Window.alert("v:"+t.substring(t.indexOf('+')-20));
//				}	
//			}
//			if(toap.indexOf('+')>0){
//				Window.alert("val:"+toap.substring(toap.indexOf('+')-20));
//			}
			String encoded=URL.encodeQueryString(toap);
//			if(encoded.indexOf('+')>0){
//				Window.alert("enc:"+encoded.substring(encoded.indexOf('+')-20));
//			}
			sb.append(encoded);
		}
		return sb.toString();
	}
}