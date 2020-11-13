package com.bilgidoku.rom.shared.query.values;

import java.util.Map;

import com.bilgidoku.rom.shared.query.QueryContext;

public abstract class ValueType {
	
	public abstract Map<String, String> alternatives(QueryContext qc);

}
