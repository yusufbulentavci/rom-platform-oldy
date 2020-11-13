package com.bilgidoku.rom.haber.fastQueue;

import com.bilgidoku.rom.haber.TalkUtil;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.min.Sistem;

public class Cmd extends CmdRef {
	public byte retryCount;

	private JSONObject jo;
	public long time;

	public Cmd(int hostId, String file, JSONObject jo2) {
		super(hostId, file);
		this.jo = jo2;
	}

	public void retry() {
		this.retryCount++;
	}

	public JSONObject getJo() {
		return jo;
	}

	public void sleep() {
		jo = null;
	}

	public void wakeUp(JSONObject jo) throws JSONException {
		this.jo = jo;

	}

	public String toString() {
		return "Cmd / file:" + file + " pos:" + hostId + " jo:"+jo.toString();
	}

	public String getCmd() throws JSONException {
		return TalkUtil.cmd(jo);
	}

	public boolean ani() throws JSONException {
		return TalkUtil.ani(jo);
	}
	
	public boolean line() throws JSONException {
		return TalkUtil.line(jo);
	}

	public void report(String string) {
		Sistem.errln("Cmd ref:"+refStr());
		try {
			Sistem.errln("Cmd :"+getCmd());
		} catch (JSONException e) {
		}
		Sistem.errln("Cmd report:"+string);
	}

	

}
