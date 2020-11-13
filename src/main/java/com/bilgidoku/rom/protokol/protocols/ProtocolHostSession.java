package com.bilgidoku.rom.protokol.protocols;

import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;


public class ProtocolHostSession<T extends ProtocolSessionActivity> {
	
	private static MC mc = new MC(ProtocolHostSession.class);
	
	private Map<String, T> activities=new HashMap<String, T>();
	

	public void loggedIn(ProtocolSession<T> ses) {
		T t=activities.get(ses.getCid());
		if(t==null){
			t=ses.createActivity();
			activities.put(ses.getCid(), t);
			t.initialLogIn();
		}
		
		t.touch();
		
		ses.getCid();
		
	}
	
	final static private Astate rhc = mc.c("host-session-logout-no-session");

	public void logout(ProtocolSession<T> ses) {
		T t=activities.get(ses.getCid());
		if(t==null){
			rhc.more();
			return;
		}
		t.touch();
	}

	public boolean isOnline(String cid) {
		T a = activities.get(cid);
		if(a==null)		
			return false;
		return a.isOnline();
	}

	
	public T getActivity(String cid) {
		return activities.get(cid);
	}
	
	
	

}
