package com.bilgidoku.rom.shared.query;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.query.values.ValueType;

public class ResourceSelector extends SuchCriteria{
	static final String NAME = "res";
	
	protected ValueType vt;
	protected String value;

	protected String cls;

	public ResourceSelector() {
		super(NAME);
	}
	
	public void setValue(String value) {
		this.value=value;
	}
	
	public JSONValue store() throws RunException{
		return new JSONString(value);
	}
	
	public void loadFromJson(JSONValue jv) {
		value=jv.isString().stringValue();
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

	public String getCls() {
		return cls;
	}

}
