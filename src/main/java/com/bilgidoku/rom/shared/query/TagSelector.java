package com.bilgidoku.rom.shared.query;

import com.bilgidoku.rom.shared.query.values.TagValueType;

public class TagSelector extends TextValuedSelector{
	static final String NAME = "tag";

	public TagSelector() {
		super(NAME, new TagValueType());
	}

	@Override
	public void asText(StringBuilder sb) {
		sb.append("Etiketi ");
		sb.append(value);
		sb.append(" olsun");
	}

	@Override
	public void buildQuery(QueryContext qc) {
		qc.append("rtags contains ");
		qc.appendEscaped(value);
	}

}
