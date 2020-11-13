package com.bilgidoku.rom.gwt.client.util.common;

import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Waiting;
import com.bilgidoku.rom.gwt.araci.client.rom.WaitingDao;

public class WaitingButton extends TimesButton{

	private Map<String,Waiting> waitings=new HashMap<String,Waiting>();
	private String app; 
	
	public WaitingButton(String app, String img) {
		super(img);
		this.app=app;
	}
	
	public void waitingIssue(Waiting waiting){
		waitings.put(waiting.code, waiting);
		refresh();
	}

	public void waiting(Waiting waiting){
		waitings.put(waiting.code, waiting);
		refresh();
	}

	
	private void refresh(){
		int times=0;
		for (Waiting it : waitings.values()) {
			times+=it.times;
		}
		this.setTimes(times);
	}
	
	public void gotIt(){
		for (Waiting it : waitings.values()) {
			WaitingDao.gotit(it.uri, new StringResponse());
		}
		clear();
	}
	
	public void clear(){
		this.waitings.clear();
		refresh();		
	}

	public Map<String,Waiting> getWaitings() {
		return waitings;
	}

	public void setWaitings(Map<String,Waiting> waitings) {
		this.waitings = waitings;
	}
	

}
