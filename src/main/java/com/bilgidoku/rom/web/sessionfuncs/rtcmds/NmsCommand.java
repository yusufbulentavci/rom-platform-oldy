package com.bilgidoku.rom.web.sessionfuncs.rtcmds;

import com.bilgidoku.rom.ilk.json.JSONObject;

public class NmsCommand extends PushCommand{

	public NmsCommand(JSONObject var) {
		super("nms");
		this.jo.safePut("m", var);
		
	}

}
