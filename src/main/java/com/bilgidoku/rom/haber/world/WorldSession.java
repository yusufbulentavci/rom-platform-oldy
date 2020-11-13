package com.bilgidoku.rom.haber.world;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.bilgidoku.rom.haber.Msg;
import com.bilgidoku.rom.haber.fastQueue.Cmd;
import com.bilgidoku.rom.shared.err.KnownError;

public class WorldSession {
	private static AtomicInteger idCounter = new AtomicInteger(0);

	List<WorldConnection> cons=new ArrayList<WorldConnection>();
	
	private final List<Cmd> toSend = new ArrayList<Cmd>();
	String to;
	

	private long watch;

	public WorldSession(String to) {
		this.to=to;
	}

	public void send(Cmd msg) {
		if(cons.size()==0){
			toSend.add(msg);
			return;
		}
		cons.get(0).send(msg);
	}

	public void addCon(WorldConnection con) {
		cons.add(con);
		sendQueue(con);
	}

	public void sendQueue(WorldConnection con) {
		while(toSend.size()>0){
			Cmd go=toSend.get(0);
			con.send(go);
			toSend.remove(0);
		}
	}

	public void removeCon(WorldConnection con) {
		cons.remove(con);
	}

	public boolean empty() {
		return cons.size()==0 && toSend.size()==0;
	}

	public boolean hasActiveCon() {
		return cons.size()!=0;
	}

	public void watch(long b) {
		this.watch=b;
	}


	public void sendWatch(long cat, Msg m) throws KnownError {
		if((watch & cat)==0)
			return;
		if(cons.size()==0){
			return;
		}
		cons.get(0).send(m.to(this.to).msg());
	}

}
