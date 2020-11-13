package com.bilgidoku.rom.haber.fastQueue;

import java.util.ArrayList;
import java.util.List;

public class DelayQueue {
	int period;
	final boolean forget;

	List<Cmd> current=new ArrayList<Cmd>();
	List<Cmd> ready=new ArrayList<Cmd>();
	
	public DelayQueue(int period, boolean forget) {
		this.period=period;
		this.forget=forget;
	}
	
	private boolean check(int now) {
		return (now%period)==0;
	}
	
	public synchronized List<Cmd> tick(int now){
		if(!check(now)){
			return null;
		}
		List<Cmd> ret = ready;
		ready=current;
		current=new ArrayList<Cmd>();
		return ret;
	}

	public synchronized void add(Cmd cmd) {
		if(forget)
			cmd.sleep();
		current.add(cmd);
	}

}
