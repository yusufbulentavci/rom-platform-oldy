package com.bilgidoku.rom.web.sessionfuncs.rtcmds;

public class RtAkilSoru extends PushCommand{
	
	public RtAkilSoru(String prm, String soru, String[] olasi, boolean mecburmu, boolean bosOlurmu, String regexp){
		super("*akil.soru");
		this.jo.safePut("soru", soru);
		this.jo.safePut("olasi", olasi);
		this.jo.safePut("bosolurmu", bosOlurmu);
		this.jo.safePut("regexp", regexp);
	}
}
