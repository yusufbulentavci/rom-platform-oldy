package com.bilgidoku.rom.shared.query;

import com.bilgidoku.rom.shared.query.values.ContainerValueType;

public class ContainerSelector extends TextValuedSelector{

	static final String NAME = "con";

	public ContainerSelector() {
		super(NAME, new ContainerValueType());
	}

	@Override
	public void asText(StringBuilder sb) {
		sb.append("Klasörü ");
		sb.append(value);
		sb.append(" olsun");
	}

	@Override
	public void buildQuery(QueryContext qc) {
		qc.append("container=");
		qc.appendEscaped(value);
	}

}
