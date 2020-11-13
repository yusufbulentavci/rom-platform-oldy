package com.bilgidoku.rom.shared.state;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONNumber;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonUtil;

public class TickTrigger extends Trigger {
	public final int tickPeriod;
	public final int tickDelay;
	public final Integer tickTimes;
	public int times;
	
	public TickTrigger(String runZoneId, String changeId, Integer tickPeriod, Integer tickDelay, Integer tickTimes) {
		super(runZoneId, changeId);
		this.tickDelay=tickDelay==null?0:tickDelay;
		this.tickPeriod=tickPeriod;
		this.tickTimes=tickTimes;
	}
	
	public TickTrigger(JSONValue jv) throws RunException{
		super(jv);
		JSONArray ja=jv.isArray();
		tickPeriod=JsonUtil.nthInt(ja, 2);
		tickDelay=JsonUtil.nthInt(ja, 3);
		tickTimes=JsonUtil.nthInt(ja, 4);
		times=JsonUtil.nthInt(ja, 5);
	}

	@Override
	public JSONValue store() throws RunException {
		JSONArray ja=(JSONArray) super.store();
		ja.set(2, new JSONNumber(tickPeriod));
		ja.set(3, new JSONNumber(tickDelay));
		ja.set(4, new JSONNumber(tickTimes));
		ja.set(5, new JSONNumber(times));
		return ja;
	}
	public boolean check(int tickNo) {
		if(tickDelay>tickNo)
			return false;
		if(tickNo%tickPeriod!=0)
			return false;
		if(tickTimes!=null && times>tickTimes)
			return false;
		return true;
	}
	

}
