package com.bilgidoku.rom.web.sessionfuncs.rtcmds;

import com.bilgidoku.rom.ilk.json.JSONObject;

public class RtExchange extends RtCommon{
	
	public RtExchange(String from, String to, String subcmd, String sentence, JSONObject ext){
		super("*rt.exchange", from, to);
		this.jo.safePut("to", to).safePut("subcmd",subcmd);
		if(sentence!=null)
			jo.safePut("text", sentence);
		if(ext!=null){
			jo.safePut("ext", ext);
		}
	}
}
