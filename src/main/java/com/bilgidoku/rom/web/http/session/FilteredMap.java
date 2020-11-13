package com.bilgidoku.rom.web.http.session;

import java.util.HashMap;

interface Filter<K,V>{

	boolean match(V v);
	
	K getKey(V v);
	
}

@SuppressWarnings("serial")
public class FilteredMap<K,V> extends HashMap<K, V> {
	
	final Filter<K,V> filter; 
	
	public FilteredMap(Filter<K, V> filter){
		this.filter=filter;
	}
	
	
	public void removing(V v){
		if(!filter.match(v))
			return;
		this.remove(v);
	}
	
	public void adding(V v){
		if(!filter.match(v))
			return;
		this.put(filter.getKey(v), v);
	}

}
