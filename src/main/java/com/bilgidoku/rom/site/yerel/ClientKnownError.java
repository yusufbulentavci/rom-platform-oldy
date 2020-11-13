package com.bilgidoku.rom.site.yerel;

import com.google.gwt.json.client.JSONException;

public class ClientKnownError extends Exception {

	public ClientKnownError(JSONException e) {
		super(e);
	}

	public ClientKnownError(String string) {
		super(string);
	}

}
