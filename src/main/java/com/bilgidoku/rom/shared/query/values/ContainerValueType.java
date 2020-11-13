package com.bilgidoku.rom.shared.query.values;

import java.util.Map;

import com.bilgidoku.rom.shared.query.QueryContext;

public class ContainerValueType extends ValueType{

	@Override
	public Map<String, String> alternatives(QueryContext qc) {
		return qc.vc.getContainers(qc.rk.getHostId());
	}
	

}
