package com.bilgidoku.rom.shared.state;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONBoolean;
import com.bilgidoku.rom.shared.json.JSONNull;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonTransfer;
import com.bilgidoku.rom.shared.json.JsonUtil;

public class Changeable implements JsonTransfer {
	public String routine;
	public final String htmlId;
	public final boolean appendHtml;
	public final String when;
	public final String goal;
	public final String guardwhen;
	public final String guardgoal;
	public Boolean scrollDown;

	public Changeable(String routine, String htmlId, boolean appendHtml, String when, String goal, String guardwhen, String guardgoal, Boolean scrollDown) {
		this.routine = routine;
		this.htmlId = htmlId;
		this.appendHtml = appendHtml;
		this.when = when;
		this.goal = goal;
		this.guardwhen = guardwhen;
		this.guardgoal = guardgoal;
		this.scrollDown=scrollDown;
	}
	
	public Changeable(JSONValue val) throws RunException{
		JSONArray ja = val.isArray();
		this.htmlId=ja.get(0).isString().stringValue();
		this.appendHtml=ja.get(1).isBoolean().booleanValue();
		this.when=JsonUtil.toStr(ja.get(2));
		this.goal=JsonUtil.toStr(ja.get(3));
		routine=ja.get(4).isString().stringValue();
		this.guardwhen=JsonUtil.toStr(ja.get(5));
		this.guardgoal=JsonUtil.toStr(ja.get(6));
		this.scrollDown=JsonUtil.toBoolean(ja.get(7));
	}

	@Override
	public JSONValue store() throws RunException {
		JSONArray ja = new JSONArray();
		ja.set(0, new JSONString(htmlId));
		ja.set(1, JSONBoolean.getInstance(appendHtml));
		ja.set(2, new JSONString(when));
		ja.set(3, (goal == null) ? JSONNull.getInstance() : new JSONString(goal));
		ja.set(4, new JSONString(routine));
		ja.set(5, new JSONString(guardwhen));
		ja.set(6, new JSONString(guardgoal));
		ja.set(7, (scrollDown == null) ? JSONNull.getInstance() : JSONBoolean.getInstance(scrollDown));
		return ja;
	}

}
