package com.bilgidoku.rom.shared.json;

import com.bilgidoku.rom.shared.Portable;

public class JSONNumber extends JSONValue{

	public JSONNumber(Object o) {
		super(o);
	}
	
	public JSONNumber(Integer o) {
		super(Portable.one.jsonNumberConstruct(o));
	}
	
	public JSONNumber(Double o) {
		super(Portable.one.jsonNumberConstruct(o));
	}

	public double doubleValue() {
		return Portable.one.jsonNumberDoubleValue(ntv);
	}
	
	public int intValue() {
		return (int) doubleValue();
	}
	
	public long longValue() {
		return (long) doubleValue();
	}

}
