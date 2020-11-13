package com.bilgidoku.rom.web.sessionfuncs.rtcmds;

public class RtSay extends RtCommon{
	
	public RtSay(String from, String to, String sentence){
		super("*rt.say", from, to);
		jo.safePut("text", sentence);
	}
}
