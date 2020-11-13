package com.bilgidoku.rom.ilk.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventSourceImpl<K> implements EventSource<K> {
	private final List<RomEventListener<K>> remove = new ArrayList< RomEventListener<K>> ();
	private final Set<RomEventListener<K>> all=new HashSet<RomEventListener<K>>();

	@Override
	public synchronized void subscribe(RomEventListener<K> l) {
		all.add(l);
	}

	@Override
	public synchronized void unsubscribe(RomEventListener<K> l) {
		all.remove(l);
	}
	
	@Override 
	public synchronized void broadcast(K k, int code, Object... more){
		for (RomEventListener<K> romEventListener : all) {
			if(!romEventListener.romEvent(k, code, more)){
				remove.add(romEventListener);
			}
		}
		for (RomEventListener<K> romEventListener : remove) {
			all.remove(romEventListener);
		}
	}

}
