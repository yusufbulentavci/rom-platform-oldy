package com.bilgidoku.rom.shared.code;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.shared.RunException;

public abstract class NullableMap<K, V> implements Map<K, V>, Serializable, Cloneable  {
	protected Map<K,V> wrap=new HashMap<K,V>();
	
	
	public abstract NullableMap<K, V> cloneWrap() throws RunException;

	@Override
	public String toString() {
		if(wrap==null)
			return "empty";
		return wrap.toString();
	}
	
	@Override
	public int size() {
		if(wrap==null)
			return 0;
		return wrap.size();
	}

	@Override
	public boolean isEmpty() {
		if(wrap==null)
			return true;
		return wrap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		if(wrap==null)
			return false;
		return wrap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		if(wrap==null)
			return false;
		return wrap.containsValue(value);
	}

	@Override
	public V get(Object key) {
		if(wrap==null)
			return null;
		return wrap.get(key);
	}

	@Override
	public V put(K key, V value) {
		if(wrap==null){
			wrap=new HashMap<K,V>();
		}
		
		return wrap.put(key,value);
	}

	@Override
	public V remove(Object key) {
		if(wrap==null)
			return null;
		return wrap.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		if(wrap==null){
			wrap=new HashMap<K,V>();
		}
		wrap.putAll(m);
	}

	@Override
	public void clear() {
		if(wrap==null)
			return;
		wrap.clear();
	}

	@Override
	public Set<K> keySet() {
		if(wrap==null)
			return null;
		return wrap.keySet();
	}

	@Override
	public Collection<V> values() {
		if(wrap==null)
			return null;
		return wrap.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		if(wrap==null)
			return null;
		return wrap.entrySet();
	}

	public void set(Map<K, V> ats2) {
		this.wrap=ats2;
	}

}
