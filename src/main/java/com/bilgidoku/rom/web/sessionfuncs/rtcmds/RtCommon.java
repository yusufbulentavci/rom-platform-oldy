package com.bilgidoku.rom.web.sessionfuncs.rtcmds;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;

public class RtCommon extends PushCommand {

	private final String fromCid;

	public RtCommon(JSONObject jo) throws JSONException {
		super(jo);
		fromCid = jo.getString("from");
	}

	public RtCommon(String cmd, String fromCid, String toCid) {
		super(cmd);
		this.jo.safePut("from", fromCid);
		this.fromCid = fromCid;
		if (toCid != null)
			jo.safePut("to", toCid);
	}

	public String getFromCid() {
		return fromCid;
	}
}
