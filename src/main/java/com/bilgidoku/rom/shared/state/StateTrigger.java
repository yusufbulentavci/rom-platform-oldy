package com.bilgidoku.rom.shared.state;

import java.util.HashSet;
import java.util.Set;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonUtil;

public class StateTrigger extends Trigger{
	public Set<String> likes=new HashSet<String>();
	

	public StateTrigger(String runZoneId, String changeId, String likes, boolean hasGoal) {
		super(runZoneId, changeId);
		setLikes(likes, hasGoal);
	}

	private void setLikes(String likes, boolean hasGoal) {
		if(hasGoal){
			if(likes==null){
				likes="_goal";
			} else{
				likes+=" "+"_goal";
			}
		}
		String[] ls = likes.split(" ");
		for (String string : ls) {
			this.likes.add(string);
		}
	}
	
	public StateTrigger(JSONValue val) throws RunException{
		super(val);
		JSONArray ja=val.isArray();
		setLikes(JsonUtil.nthString(ja, 2), false);
	}
	
	@Override
	public JSONValue store() throws RunException {
		JSONArray ja=(JSONArray) super.store();
		StringBuilder sb=new StringBuilder();
		boolean first=true;
		for (String it : likes) {
			if(first){
				first=false;
			}else{
				sb.append(" ");
			}
			sb.append(it);
		}
		ja.set(2, new JSONString(sb.toString()));
		return ja;
	}


	public boolean check(Set<String> modifieds) {
//		Sistem.outln("check likes:"+likes);
		for (String string : likes) {
			if(modifieds.contains(string)){
//				if(likes.contains("_switchshow")){
//					Sistem.outln("check true:"+modifieds);
//				}
				return true;
			}
		}
//		if(likes.contains("_switchshow")){
//			Sistem.outln("check false:"+modifieds);
//		}
		return false;
	}
}
