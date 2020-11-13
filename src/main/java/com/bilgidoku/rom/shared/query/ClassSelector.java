package com.bilgidoku.rom.shared.query;

import com.bilgidoku.rom.shared.data.Sozluk;
import com.bilgidoku.rom.shared.query.values.ClassValueType;

public class ClassSelector extends TextValuedSelector{

	static final String NAME = "cls";

	public ClassSelector() {
		super(NAME, new ClassValueType());
	}

	@Override
	public void asText(StringBuilder sb) {
		sb.append("Tür adı ");
		sb.append(value);
		sb.append(" olsun");
	}

	@Override
	public void buildQuery(QueryContext qc) {
		qc.addFrom(Sozluk.turAdiToTableName(value));
	}

}
