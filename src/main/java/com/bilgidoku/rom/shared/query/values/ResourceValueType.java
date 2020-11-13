package com.bilgidoku.rom.shared.query.values;

import java.util.Map;

import com.bilgidoku.rom.shared.query.QueryContext;
import com.bilgidoku.rom.shared.query.ResourceSelector;

public class ResourceValueType extends ValueType{

	@Override
	public Map<String, String> alternatives(QueryContext qc) {
		ResourceSelector rs=(ResourceSelector) qc.currentCriteria;
		return qc.vc.getResources(qc.rk.getHostId(), rs.getCls());
	}
	

}
