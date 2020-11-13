package com.bilgidoku.rom.shared.code;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONNull;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonTransfer;
import com.bilgidoku.rom.shared.json.JsonUtil;

public class AjaxForm implements JsonTransfer{
	public boolean accepted=false;
	public String var;
	public String sucgoal;
	public String sucRedirect;
	public String errgoal;
	public String errRedirect;
	public String resetFields="";
	final public JSONArray remove;
	final public JSONObject set;
	final public JSONObject validations;
	
	public AjaxForm(){
		remove=new JSONArray();
		set=new JSONObject();
		validations=new JSONObject();
	}
	
	public AjaxForm(JSONArray ja) throws RunException{
		var=JsonUtil.nthString(ja, 0);
		sucgoal=JsonUtil.nthString(ja, 1);
		sucRedirect=JsonUtil.nthString(ja, 2);
		remove=ja.get(3).isArray();
		set=ja.get(4).isObject();
		validations=ja.get(5).isObject();
		errgoal=JsonUtil.nthString(ja, 6);
		errRedirect=JsonUtil.nthString(ja, 7);
		resetFields=JsonUtil.nthString(ja, 8);
	}

	@Override
	public JSONValue store() throws RunException {
		JSONArray ja=new JSONArray();
		ja.set(0, var==null?JSONNull.getInstance():new JSONString(var));
		ja.set(1, sucgoal==null?JSONNull.getInstance():new JSONString(sucgoal));
		ja.set(2, sucRedirect==null?JSONNull.getInstance():new JSONString(sucRedirect));
		ja.set(3, remove);
		ja.set(4, set);
		ja.set(5, validations);
		ja.set(6, errgoal==null?JSONNull.getInstance():new JSONString(errgoal));
		ja.set(7, errRedirect==null?JSONNull.getInstance():new JSONString(errRedirect));
		ja.set(8, new JSONString(resetFields));
		return ja;
	}
}
