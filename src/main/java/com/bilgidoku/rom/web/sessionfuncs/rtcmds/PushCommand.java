package com.bilgidoku.rom.web.sessionfuncs.rtcmds;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;

public class PushCommand {

	protected final JSONObject jo;
	protected String cmd;

	public PushCommand(JSONObject jo) throws JSONException{
		this.jo=jo;
		jo.getString("cmd");
	}
	
	public PushCommand(String cmd, String eylem) {
		this.cmd=cmd;
		this.jo = new JSONObject().safePut("cmd", cmd);
		this.jo.safePut("eylem", eylem);
	}
	public PushCommand(String cmd) {
		this.cmd=cmd;
		this.jo = new JSONObject().safePut("cmd", cmd);
	}

	
	public JSONObject getJo() {
		return jo;
	}
	
	@Override
	public String toString(){
		return jo.toString();
	}

}
