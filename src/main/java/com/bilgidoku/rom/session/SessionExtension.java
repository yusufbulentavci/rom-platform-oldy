package com.bilgidoku.rom.session;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.PushCommand;

public abstract class SessionExtension {
	
	protected final String name;

	public SessionExtension(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}

	public void check(){
		
	}
	
	public abstract JSONObject selfDescribe();
	

	public abstract boolean isOnline(int hostId, String cid) throws KnownError;
	
	public boolean canPush(){
		return false;
	}

	public void waitingChanged(int hostId, String cid, String app, String code, JSONArray inref, int times, JSONArray title, JSONArray user) {
	}


	public void push(int hostId, String cid, PushCommand pc) throws KnownError {
		
	}

	public void broadcast(JSONObject jo) {
		
	}
	
	
}
