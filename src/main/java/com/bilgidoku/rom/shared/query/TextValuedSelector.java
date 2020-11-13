package com.bilgidoku.rom.shared.query;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.query.values.ValueType;

public abstract class TextValuedSelector extends SuchCriteria{

	protected ValueType vt;
	protected String value;
	

	public TextValuedSelector(String name, ValueType vt) {
		super(name);
		this.vt=vt;
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
	

}
