package com.bilgidoku.rom.pg.dict.types;

import java.text.ParseException;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;

public class Int8Type implements TypeAdapter {

	@Override
	public void setValue(DbThree three, String obj) throws KnownError {
		if (obj == null) {
			three.setNull(java.sql.Types.BIGINT);
			return;
		}
		Long in = Long.parseLong(obj);
		three.setLong(in);
	}

	public String toString() {
		return "int8";
	}

	@Override
	public String[] getSqlNames() {
		final String[] s = { "int8", "bigint" };
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Int8Type);
	}

	@Override
	public String getJavaType() {
		return "Long";
	}

	@Override
	public Object[] getArrayOf(int size) {
		return new Long[size];
	}

	@Override
	public String getSqlName() {
		return "int8";
	}

	@Override
	public boolean needSqlConversion() {
		return false;
	}

	@Override
	public boolean isToQuote() {
		return false;
	}

	@Override
	public boolean needCast() {
		return false;
	}

	@Override
	public Object getValue(DbThree db3) throws KnownError {
		long b = db3.getLong();
		if (db3.wasNull()) {
			return null;
		}
		return b;
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError {
		return (Long[]) db3.getArray();
	}

	@Override
	public void writeValue(DbThree db3, JSONWriter writer) throws KnownError,
			JSONException {
		writer.value(getValue(db3));
	}

	@Override
	public void writeArrayValue(DbThree db3, JSONWriter writer)
			throws KnownError, JSONException {
		writer.value(getArrayValue(db3));
	}

	@Override
	public Object fromString(String str) throws ParseException {
		return Long.parseLong(str);
	}

	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		writer.value(((Long) value).longValue());
	}

	@Override
	public boolean isJavaType() {
		return true;
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
