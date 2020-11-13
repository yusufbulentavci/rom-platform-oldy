package com.bilgidoku.rom.izle;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.min.Sistem;


public class Astate {

	private final String name;

	public long longTerm;
	public int value;

	private final MC mc;
	
	private int warnTreshold=0;
	private int alarmTreshold=0;
	
	private boolean warnNotified=false;
	private boolean alarmNotified=false;
	
	private int failLongTerm;
	private int failCount;
	private int failureThreshold=1;
	private boolean failureNotified=false;

	public Astate(MC mc, String name) {
		this.mc=mc;
		this.name = name;
	}

	public JSONArray getReport() {
		if(failCount==0 && value==0){
			return null;
		}
		JSONArray ja=new JSONArray();
		ja.put(value);
		ja.put(longTerm);
		ja.put(warnTreshold);
		ja.put(alarmTreshold);
		
		ja.put(failCount);
		ja.put(failLongTerm);
		ja.put(failureThreshold);
		
		return ja;
	}

	public void more() {
		value++;
		longTerm++;
		
		if(alarmTreshold!=0 && longTerm>=alarmTreshold){
			if(!alarmNotified){
				mc.alarmNotify(this);
				alarmNotified=true;
			}
		}else if(warnTreshold!=0 && longTerm>=warnTreshold){
			if(!warnNotified){
				mc.warnNotify(this);
				warnNotified=true;
			}
		}
	}

	public void reset() {
		value = 0;
		failCount=0;
	}
	
	public void uireset() {
		longTerm=0;
		alarmNotified=false;
		warnNotified=false;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public void more(Throwable t, Object... args) {
		failed();
		
		Sistem.errln(mc.getCode()+":"+name);
		if(args!=null)
			for (Object object : args) {
				if(object!=null)
					Sistem.errln(object.toString());
			}
		if(t!=null)
			Sistem.printStackTrace(t);

	}
	
	public void more(String s, Object... args) {
		more();
		StringBuilder sb=new StringBuilder();
		sb.append(mc.getCode());
		sb.append(":");
		sb.append(name);
		sb.append(" What:");
		sb.append(s);
		if(args!=null){
			argsToStr(sb,args);
		}
		Sistem.outln(sb.toString());
	}
	
	public void failed(Throwable t, Object... args) {
		failed();
		StringBuilder sb=new StringBuilder();
		sb.append(mc.getCode());
		sb.append(":");
		sb.append(name);
		sb.append(" failed\n");
		if(args!=null){
			argsToStr(sb, args);
		}
		
		Sistem.errln(sb.toString());
			
		if(t!=null)
			Sistem.printStackTrace(t);

	}

	void argsToStr(StringBuilder sb, Object... args) {
		sb.append("Arguments...\n");
		for (Object object : args) {
			if(object!=null){
				sb.append(object.toString());
				sb.append("\n");
			}
		}
	}
	
	
	public void failed(String s, Object... args) {
		failed();
		
		StringBuilder sb=new StringBuilder();
		sb.append(mc.getCode());
		sb.append(":");
		sb.append(name);
		sb.append(" failed What:");
		sb.append(s);
		if(args!=null){
			argsToStr(sb,args);
		}
		Sistem.errln(sb.toString());
	}

	public void fail(Object... args) {
		failed();
		
		StringBuilder sb=new StringBuilder();
		sb.append(mc.getCode());
		sb.append(":");
		sb.append(name);
		if(args!=null)
			argsToStr(sb,args);

		Sistem.errln(sb.toString());
	}




	public void failed() {
		failCount++;
		failLongTerm++;
		if(failureThreshold>0&&!failureNotified&&failLongTerm>=failureThreshold){
			mc.failNotify(this);
			failureNotified=true;
		}
	}
	
	public Astate at(int level){
		this.alarmTreshold=level;
		return this;
	}
	
	public Astate wt(int level){
		this.warnTreshold=level;
		return this;
	}
	
	public Astate ft(int level){
		this.failureThreshold=level;
		return this;
	}

	public Astate noFailure(){
		this.failureThreshold=0;
		return this;
	}

}
