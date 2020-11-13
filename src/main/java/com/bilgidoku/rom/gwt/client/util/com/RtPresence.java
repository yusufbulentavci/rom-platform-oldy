package com.bilgidoku.rom.gwt.client.util.com;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONObject;

public class RtPresence extends RtCommon {

	private JSONObject jo = new JSONObject();

	public RtPresence(String visible) {
		super("presence");
		this.jo.put("visible", visible);

	}
}
