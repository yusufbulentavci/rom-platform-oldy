package com.bilgidoku.rom.site.yerel.issues;

import java.util.Date;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class Task {

	String title;
	Date start;
	Date end;
	boolean isImportant = false;
	boolean isPostponed = false;

	public Json getJson() {
		JSONObject jo = new JSONObject();
		jo.put("title", new JSONString(title));
		jo.put("start", new JSONString(ClientUtil.fmtSqlDate(start)));		
		if (end != null)
			jo.put("end", new JSONString(ClientUtil.fmtSqlDate(end)));

		if(isImportant)
			jo.put("im",  JSONBoolean.getInstance(isImportant));
		if(isPostponed)
			jo.put("pp",  JSONBoolean.getInstance(isPostponed));
		
		return new Json(jo);
	}

	public Task(Json j) {
		JSONValue v = j.getValue();
		if (v.isObject() == null)
			return;
		
		JSONObject jo = v.isObject();
		title = ClientUtil.getString(jo.get("title"));
		String s = ClientUtil.getString(jo.get("start"));
		String e = ClientUtil.getString(jo.get("end"));
		
		start = ClientUtil.getDate(s);
		
		if (!e.isEmpty())
			end = ClientUtil.getDate(e);
		
		isImportant = ClientUtil.getBoolean(jo.get("im"));
		isPostponed = ClientUtil.getBoolean(jo.get("pp"));		
	}

	public Task(String title2) {
		title = title2;
		start = new Date();
	}
	
	public void setImportant(boolean b) {
		isImportant = b;
	}

	public void setPostponed(boolean b) {
		isPostponed = b;
	}

	public boolean isCompleted() {
		return end!=null;
	}

	

}
