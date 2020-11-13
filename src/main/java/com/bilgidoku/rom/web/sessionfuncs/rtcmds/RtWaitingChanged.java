package com.bilgidoku.rom.web.sessionfuncs.rtcmds;

import com.bilgidoku.rom.ilk.json.JSONArray;

public class RtWaitingChanged extends RtCommon{
	
	public RtWaitingChanged(String app, String code, JSONArray inref, int times){
		super("*waiting.changed", "", null);
		jo.safePut("app", app).safePut("code", code).safePut("inref", inref).safePut("times", times);
	}
}
