package com.bilgidoku.rom.site.yerel.data;

import com.google.gwt.json.client.JSONValue;

public interface DataResponse {
	public void dataReady(JSONValue jsonValue);
	public void dataErr(int statusCode, String statusText);
	public void empty();
}
