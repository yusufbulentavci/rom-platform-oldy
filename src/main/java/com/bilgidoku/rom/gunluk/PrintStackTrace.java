package com.bilgidoku.rom.gunluk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PrintStackTrace {

	private List<IStackTrace> byId=new ArrayList<IStackTrace>();
	private Map<String,IStackTrace> exceptions=new HashMap<String,IStackTrace>();
	private List<IStackTrace> added=new ArrayList<IStackTrace>();
	
	
	public synchronized IStackTrace gotOne(Throwable e){
		IStackTrace cause=null;
		if(e.getCause()!=null){
			cause=gotOne(e.getCause());
		}
		
		StackTraceElement[] st = e.getStackTrace();
		StringBuilder sb=new StringBuilder();
		sb.append(e.getClass().getSimpleName());
		sb.append("\n");
		for (StackTraceElement ste : st) {
			sb.append(ste.toString());
			sb.append("\n");
		}
		String ststr=sb.toString();
		IStackTrace sto=exceptions.get(ststr);
		if(sto!=null)
			return sto;
		
				
		sto=new IStackTrace(byId.size(), e, cause);
		exceptions.put(ststr, sto);
		byId.add(sto);

		if(added==null)
			added=new ArrayList<IStackTrace>();
		added.add(sto);
			
		return sto;
	}
	
	public synchronized List<IStackTrace> getAdded(){
		if(added==null)
			return null;
		List<IStackTrace> ret=added;
		added=null;
		return ret;
	}
	
	public synchronized void reset(){
		byId.clear();
		exceptions.clear();
		if(added!=null)
			added.clear();
	}
	


}
