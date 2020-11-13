package com.bilgidoku.rom.shared.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONValue;

public abstract class LogicRelation extends SuchCriteria{

	protected List<SuchCriteria> subs=new ArrayList<>();
	
	public LogicRelation(String name) {
		super(name);
	}
	
	public boolean isLogic() {
		return true;
	}
	
	public void addCriteria(SuchCriteria s) {
		subs.add(s);
	}
	
	public JSONValue store() throws RunException{
		JSONObject ja=new JSONObject();
		for (SuchCriteria suchCriteria : subs) {
			ja.put(suchCriteria.name, suchCriteria.store());
		}
		return ja;
	}
	
	public void loadFromJson(JSONValue jv) throws RunException {
		JSONObject jo=jv.isObject();
		Set<String> ks = jo.keySet();
		for (String k : ks) {
			JSONValue jvx=jo.get(k);
			SuchCriteria sc = QueryFactory.create(k, jvx);
			subs.add(sc);
		}
		
	}


}
