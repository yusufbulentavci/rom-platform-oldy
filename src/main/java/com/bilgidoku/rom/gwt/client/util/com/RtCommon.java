package com.bilgidoku.rom.gwt.client.util.com;

import com.bilgidoku.rom.shared.json.JSONObject;

public class RtCommon {

	private JSONObject jo;

	public JSONObject getJo() {
		return jo;
	}

	public RtCommon(String cmd) {
		this.jo = new JSONObject();
		this.jo.put("cmd", cmd);

	}
}
