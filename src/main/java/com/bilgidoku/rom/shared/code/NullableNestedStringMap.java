package com.bilgidoku.rom.shared.code;

import java.util.HashMap;
import java.util.Map;

public class NullableNestedStringMap extends NullableMap<String, Map<String,String> > {

	public NullableNestedStringMap(Map<String, Map<String,String> > objToMap) {
		this.wrap = objToMap;
	}

	public NullableNestedStringMap() {
	}

	@Override
	public NullableMap<String, Map<String,String> > cloneWrap() {
		if(wrap==null)
			return new NullableNestedStringMap();
		
		Map<String,Map<String,String> > ret=new HashMap<String,Map<String,String> >();
		for (java.util.Map.Entry<String, Map<String,String> > it : wrap.entrySet()) {
			
			HashMap<String, String> v = (HashMap<String, String>) it.getValue();
			
			ret.put(it.getKey(),(Map<String, String>)v.clone());
		}
		return new NullableNestedStringMap(ret);
	}

}
