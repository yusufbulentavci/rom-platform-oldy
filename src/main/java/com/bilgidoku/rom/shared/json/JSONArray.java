package com.bilgidoku.rom.shared.json;

import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;

public class JSONArray extends JSONValue{

	public JSONArray() {
		super(Portable.one.jsonArrayConstuct());
	}

	public JSONArray(Object ntv) {
		super(ntv);
	}

	public int size() {
		return Portable.one.jsonArraySize(ntv);
	}

	public JSONValue get(int i) throws RunException {
		return Portable.one.jsonArrayGet(ntv, i);
	}
	
	public void set(int i, JSONValue val) throws RunException {
		Portable.one.jsonArraySet(ntv, i, val);
	}
	
	public void add(JSONValue val){
		Portable.one.jsonArrayAdd(ntv, val);
	}

	public void add(String string2){
		this.add(new JSONString(string2));
	}

	public String[] toStringArray() throws RunException {
		String[] ret=new String[size()];
		for(int i=0; i<ret.length; i++){
			ret[i]=get(i).isString().stringValue();
		}
		return ret;
	}


}
