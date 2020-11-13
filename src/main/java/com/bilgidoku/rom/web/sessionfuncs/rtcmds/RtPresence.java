package com.bilgidoku.rom.web.sessionfuncs.rtcmds;

public class RtPresence extends RtCommon{
	
	public RtPresence(String from, int code, String visible){
		super("*rt.presence", from, null);
		this.jo.safePut("code", code);
		this.jo.safePut("visible", visible);
		
	}
}
