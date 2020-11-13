package com.bilgidoku.rom.shared.query.values;

import java.util.Map;

import com.bilgidoku.rom.shared.query.QueryContext;

public class ClassValueType extends ValueType{

	private static Map<String,String> map;
	
	@Override
	public Map<String, String> alternatives(QueryContext qc) {
		return map;
	}
	

}
