package com.bilgidoku.rom.shared.state;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonUtil;

public class RtTrigger extends Trigger{
	public final String dlgidvar;

	public RtTrigger(String runZoneId, String changeId, String dlgidvar) {
		super(runZoneId, changeId);
		this.dlgidvar=dlgidvar;
	}
	
	public RtTrigger(JSONValue val) throws RunException{
		super(val);
		JSONArray ja=val.isArray();
		this.dlgidvar=JsonUtil.nthString(ja, 2);
	}
	
	@Override
	public JSONValue store() throws RunException {
		JSONArray ja=(JSONArray) super.store();
		ja.set(2, new JSONString(dlgidvar));
		return ja;
	}
}
