package com.bilgidoku.rom.site.yerel.data;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class JSONResponseHandler implements RequestCallback {
	private DataResponse respObj;

	public JSONResponseHandler(DataResponse response) {
		this.respObj=response;
	}

	public void onError(Request request, Throwable exception) {
		respObj.dataErr(20000,exception.getMessage());
	}

	public void onResponseReceived(Request request, Response response) {
		String responseText = response.getText();
		if(responseText==null  || responseText.length()==0){
			respObj.empty();
			return;
		}
		try {
			JSONValue jsonValue = JSONParser.parseStrict(responseText);
			respObj.dataReady(jsonValue);
		} catch (JSONException e) {
			respObj.dataErr(response.getStatusCode(),responseText);
		}
	}

	
}
