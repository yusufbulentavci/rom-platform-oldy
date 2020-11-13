package com.bilgidoku.rom.shared.query;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonTransfer;

public abstract class SuchCriteria implements JsonTransfer{
	final String name;
	
	public SuchCriteria(String name) {
		this.name = name;
	}
	
	public abstract void loadFromJson(JSONValue jv) throws RunException;
	
	public abstract void asText(StringBuilder sb);
	
	public boolean isLogic() {
		return false;
	}
	
	public abstract void buildQuery(QueryContext qc);
	
	

}
