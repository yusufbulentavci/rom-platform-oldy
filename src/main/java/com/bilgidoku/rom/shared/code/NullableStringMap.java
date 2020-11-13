package com.bilgidoku.rom.shared.code;

import java.util.HashMap;
import java.util.Map;

public class NullableStringMap extends NullableMap<String, String> {

	public NullableStringMap(Map<String, String> objToMap) {
		this.wrap = objToMap;
	}

	public NullableStringMap() {
	}

	@Override
	public NullableMap<String, String> cloneWrap() {
		if(wrap==null)
			return new NullableStringMap();
		
		Map<String,String> ret=new HashMap<String,String>();
		for (java.util.Map.Entry<String, String> it : wrap.entrySet()) {
			ret.put(it.getKey(),it.getValue());
		}
		return new NullableStringMap(ret);
	}

}
