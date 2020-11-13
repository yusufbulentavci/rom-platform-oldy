package com.bilgidoku.rom.haber;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.gorevli.Gorevli;

public class ServerCmd {

	private final Method method;
	private final Gorevli si;
	private final String cmd;
	private final byte retryPattern;
	private final byte alarmLevel;
	private Method err;

	public ServerCmd(String cmd, Gorevli instance, Method m, byte retryPattern, byte alarmLevel) {
		this.cmd=cmd;
		this.si = instance;
		this.method = m;
		this.retryPattern=retryPattern;
		this.alarmLevel=alarmLevel;
	}

	public TalkResult execute(JSONObject msg) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, KnownError {
		
		return (TalkResult) method.invoke(si, msg);
	}

	public String getCmd() {
		return cmd;
	}

	public byte getRetryPattern() {
		return retryPattern;
	}
	
	public void err(Method m){
		err=m;
	}

	public void err(JSONObject msg) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(err==null)
			return;
		err.invoke(si, msg);
	}

}
