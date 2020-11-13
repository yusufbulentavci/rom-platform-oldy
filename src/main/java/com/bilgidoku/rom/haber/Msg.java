package com.bilgidoku.rom.haber;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Genel;
import com.bilgidoku.rom.shared.err.KnownError;

public class Msg {
	
	private JSONObject msg=new JSONObject();
	private JSONObject data=null;
	
	public Msg(String cmd, JSONObject replyTo) throws KnownError{
		this(cmd);
		try {
			to(TalkUtil.from(replyTo));
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}

	public Msg(String cmd) throws KnownError{
		try {
			msg.put("f", Genel.getHostName());
			msg.put("to", Genel.getHostName());
			msg.put("c", cmd);
			msg.put("h", 0);
			msg.put("t", System.currentTimeMillis());
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}
	
//	public void go() throws KnownError{
//		RunTime.talking(msg);
//	}

	public Msg to(String to) throws KnownError {
		try {
			msg.put("to", to);
			return this;
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}


	public Msg data(JSONObject data) throws KnownError{
		try {
			msg.put("d", data);
		} catch (JSONException e) {
			throw new KnownError(e);
		}
		return this;
	}
	
	public Msg data() throws KnownError{
		this.data=new JSONObject();
		data(this.data);
		return this;
	}
	
	public Msg data(String key, String val) throws KnownError{
		checkData();
		try {
			this.data.put(key, val);
		} catch (JSONException e) {
			throw new KnownError(e);
		}
		return this;
	}
	public Msg data(String key, JSONObject val) throws KnownError{
		checkData();
		try {
			this.data.put(key, val);
		} catch (JSONException e) {
			throw new KnownError(e);
		}
		return this;
	}
	
	private void checkData() throws KnownError {
		if(data==null)
			data();
	}

	public Msg ani() throws KnownError{
		try {
			msg.put("ani", true);
		} catch (JSONException e) {
			throw new KnownError(e);
		}
		return this;
	}

	public Msg data(String key, long val) throws KnownError {
		checkData();
		try {
			this.data.put(key, val);
		} catch (JSONException e) {
			throw new KnownError(e);
		}
		return this;
	}

	public Msg line() throws KnownError {
		try {
			msg.put("line", true);
		} catch (JSONException e) {
			throw new KnownError(e);
		}
		return this;
	}
	
	public JSONObject msg(){
		return msg;
	}

	public String str() {
		return msg.toString();
	}
	
}
