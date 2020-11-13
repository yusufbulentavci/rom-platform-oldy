package com.bilgidoku.rom.pg.dict.types;

import java.text.ParseException;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;

public class InternalClass implements TypeAdapter {

	
	private Class<?> pt;

	public InternalClass(Class<?> pt) {
		this.pt=pt;
	}

	@Override
	public boolean isJavaType() {
		return true;
	}

	@Override
	public String getJavaType() {
		return pt.getCanonicalName();
	}

	@Override
	public void setValue(DbThree three, String obj) throws KnownError{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Object[] getArrayOf(int size) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public String[] getSqlNames() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean needSqlConversion() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isToQuote() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean needCast() {
		return false;
	}

	@Override
	public Object getValue(DbThree db3) throws KnownError {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void writeValue(DbThree db3, JSONWriter writer) throws KnownError,
			JSONException {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void writeArrayValue(DbThree db3, JSONWriter writer)
			throws KnownError, JSONException {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public Object fromString(String str) throws ParseException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		throw new RuntimeException("Not implemented");
	}
	
	@Override
	public String callProtoPart() {
		return "?";
	}
	@Override
	public boolean isPrimitive() {
		return true;
	}

}
