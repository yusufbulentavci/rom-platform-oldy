package com.bilgidoku.rom.web.http;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;

public interface PushInterface {
	public void pushEvent(JSONObject ge) throws JSONException;
}
