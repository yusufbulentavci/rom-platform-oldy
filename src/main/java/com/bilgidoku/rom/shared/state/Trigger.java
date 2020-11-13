package com.bilgidoku.rom.shared.state;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonTransfer;
import com.bilgidoku.rom.shared.json.JsonUtil;


public class Trigger implements JsonTransfer{
	public String runZoneId;
	public String changeId;

	public Trigger(String runZoneId, String changeId) {
		this.runZoneId = runZoneId;
		this.changeId = changeId;
	}

	public Trigger(JSONValue jv) throws RunException {
		JSONArray ja=jv.isArray();
		runZoneId=JsonUtil.nthString(ja, 0);
		changeId=JsonUtil.nthString(ja, 1);
	}

	
	@Override
	public JSONValue store() throws RunException {
		JSONArray ja=new JSONArray();
		ja.set(0, new JSONString(runZoneId));
		ja.set(1, new JSONString(changeId));
		return ja;
	}
}
