package com.bilgidoku.rom.web.http.session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONObject;


public class RtDialog {
	long inTime=System.currentTimeMillis();
	final AtomicInteger msgSeq=new AtomicInteger(0);
	final String id;
	final Set<String> cids=new HashSet<String>();
	
	final List<JSONArray> history=new ArrayList<JSONArray>();

	private boolean isOpen;
	private boolean isPermanent;
	private String app;
	private String subject;
	
	public RtDialog(String id, String app, String subject, boolean isOpen, boolean isPermanent){
		this.id=id;
		this.isOpen=isOpen;
		this.isPermanent=isPermanent;
		this.app=app;
		this.subject=subject;
	}
	
	public void addByCid(String cid){
		cids.add(cid);
	}
	
	
	public void removeByCid(String cid){
		cids.remove(cid);
	}
	

	public String getResource() {
		return app;
	}

	public String getSubject() {
		return subject;
	}

//	public String nextMsg(String from, String p_msg, String type) {
//		String msg=id+"é"+msgSeq.incrementAndGet()+"é"+type+"é"+from+"é"+p_msg;
//		inTime=System.currentTimeMillis();
//		return msg;
//	}

	public long notUsedForSeconds() {
		return (System.currentTimeMillis() - inTime) / 1000;
	}
	
	public int broadcastToDlg(AppHostSession hs, JSONObject msgobj) {
		String msg = msgobj.toString();
		msgobj.safePut("seq", msgSeq.incrementAndGet()).safePut("dlg", id);
		
		int i=0;
	
		for (String sid : cids) {
			AppSession s=hs.getSessionsByCid(sid);
			if(s==null)
				continue;
			s.rtMsg(msg);
			i++;
		}
		return i;
	}
}
