package com.bilgidoku.rom.web.sessionfuncs.rtcmds;


public class RtYou extends RtCommon{
	
	public RtYou(String cid){
		super("*rt.you", "", null);
		this.jo.safePut("cid", cid);
		this.jo.safePut("time", System.currentTimeMillis());
		
	}
}
