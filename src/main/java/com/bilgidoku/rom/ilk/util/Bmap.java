package com.bilgidoku.rom.ilk.util;

import java.util.HashMap;
import java.util.Map;


public class Bmap {
	
	public Map<String,Object> map=new HashMap<String,Object>();
	public class Key{
		
		private String key;

		public Key(String key) {
			this.key=key;
		}

		public Bmap val(Object o){
			Bmap.this.map.put(key,o);
			return Bmap.this;
		}
	}
	public Key key(String key){
		return new Key(key);
	}
	public Map<String,Object> ret(){
		return map;
	}
}
