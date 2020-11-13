package com.bilgidoku.rom.izle;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;

public class MC {
	

	private Map<String, Astate> states = new HashMap<String, Astate>();

	private final String code;
	
	private boolean eyeOn=true;

	public MC(Class clazz) {
		String sn = clazz.getSimpleName();
		if(sn.startsWith("com.bilgidoku.rom."))
			sn=sn.substring("com.bilgidoku.rom.".length());
		this.code = sn;
		IzlemeGorevlisi.tek().mc(this);
	}

	public Astate c(String string) {
		Astate s = new Astate(this, string);
		states.put(string, s);
		return s;
	}

//	public Class getClazz() {
//		return clazz;
//	}
//
	public void out(String s){
		Sistem.outln("From:" + code + " Msg:" + s);
	}	

	public void trace(String s){
		if(Eye.check() && eyeOn){
			Sistem.outln(Eye.whoAmI()+"Code:"+code+"Msg:"+s);
		}
	}

	public void alarmNotify(Astate astate) {
		Sistem.errln("Alarm:"+code+" counters:"+astate.getReport().toString());
	}

	public void warnNotify(Astate astate) {
		Sistem.outln("Notify:"+code+" counters:"+astate.getReport().toString());
	}

	public void failNotify(Astate astate) {
		Sistem.errln("Fail:"+code+" counters:"+astate.getReport().toString());
	}

	public JSONObject report() throws KnownError {
		JSONObject jo = new JSONObject();
//		for (Entry<String, Astate> it : states.entrySet()) {
//			JSONArray v = it.getValue().getReport();
//			if (v != null) {
//				jo.put(it.getKey(), v);
//				it.getValue().reset();
//			}
//		}
//		if (jo.length() == 0)
//			return null;
		return jo;
	}

	public String getCode() {
		return code;
	}

	public void eyeOn(boolean match) {
		this.eyeOn=match;
	}
}
