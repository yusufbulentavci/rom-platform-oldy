package com.bilgidoku.rom.shared.query.values;

import java.util.Map;

import com.bilgidoku.rom.shared.query.QueryContext;

public class TagValueType extends ValueType{

	@Override
	public Map<String, String> alternatives(QueryContext qc) {
		return qc.vc.getTags(qc.rk.getHostId());
	}
	

}
