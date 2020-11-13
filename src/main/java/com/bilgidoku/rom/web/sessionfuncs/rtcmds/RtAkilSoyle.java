package com.bilgidoku.rom.web.sessionfuncs.rtcmds;

public class RtAkilSoyle extends PushCommand{
	
	public RtAkilSoyle(String level, String soz){
		super("*akil.soyle");
		this.jo.safePut("level", level);
		this.jo.safePut("soz", soz);
	}
}
