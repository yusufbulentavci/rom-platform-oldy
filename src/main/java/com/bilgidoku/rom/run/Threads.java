package com.bilgidoku.rom.run;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Genel;
import com.bilgidoku.rom.run.Threads.ThreadUse.ThreadRunImpl;


public class Threads {
	int aliveThreads;
	int runningThreads;
	
	private final Map<String,ThreadUse> groups=new ConcurrentHashMap<String,ThreadUse>();
	private final ThreadFactory genel;
	
	public Threads(){
		this.genel = group("general");
	}
	
	public JSONObject describe(){
		JSONObject jo=new JSONObject();
		jo.safePut("alive", aliveThreads);
		jo.safePut("running", runningThreads);
		
		JSONObject jg=new JSONObject();
		for (Entry<String, ThreadUse> it : groups.entrySet()) {
			jg.safePut(it.getKey(), it.getValue().describe());
		}
		jo.safePut("use", jg);
		
		return jo;
	}
	
	class ThreadUse implements ThreadFactory{
		
		private final ThreadGroup group;
		Map<Long,ThreadRunImpl> inUse=new ConcurrentHashMap<Long,ThreadRunImpl>();

		class ThreadRunImpl extends Thread implements ThreadRun{

			boolean started=false;
			boolean ended=false;
			final Runnable r;
			
			public ThreadRunImpl(Runnable x){
				super(group,x);
				this.r=x;
				aliveThreads++;
				inUse.put(getId(), this);
			}
			@Override
			public void run() {
				runningThreads++;
				started=true;
				try{
					r.run();
				}catch (Exception e) {
					System.out.println(e.toString());
				}finally{
					ended=true;
					aliveThreads--;
					runningThreads--;
					
					ended(this);
				}
			}
			
		}
		
		
		public ThreadUse(String group){
			this.group=new ThreadGroup(group);
		}
		
		public JSONObject describe() {
			JSONObject jo=new JSONObject();
			int scount=0;
			int ecount=0;
			for (ThreadRunImpl it : inUse.values()) {
				if(it.started)
					scount++;
				if(it.ended)
					ecount++;
			}
			jo.safePut("started", scount);
			jo.safePut("ended", ecount);
			return jo;
		}

		@Override
		public Thread newThread(final Runnable r) {
			final Thread t=new ThreadRunImpl(r);
			t.setName(Genel.uniqueName(group.getName()));
			return t;
		}

		protected void ended(ThreadRunImpl runnable) {
			inUse.remove(runnable.getId());
		}

		public void terminate() {
			try {
				synchronized(group){
					group.notifyAll();	
				}
			} catch (Exception e) {
			}
					
		}
	}
	

	public ThreadFactory group(String name){
		ThreadUse tu=new ThreadUse(name);
		groups.put(name, tu);
		return tu;
	}
	
	public ThreadRunImpl thread(Runnable r){
		return (ThreadRunImpl)this.genel.newThread(r);
	}

	public ThreadRunImpl thread(Runnable r, String name) {
		ThreadRunImpl d=(ThreadRunImpl) this.genel.newThread(r);
		d.setName(Genel.uniqueName(name));
		return d;
	}
	
	public void terminate(){
		for (ThreadUse it : groups.values()) {
			it.terminate();
		}
	}
}
