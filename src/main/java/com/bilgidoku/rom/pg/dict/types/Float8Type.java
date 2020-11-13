package com.bilgidoku.rom.pg.dict.types;

import java.text.ParseException;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;

public class Float8Type implements TypeAdapter {

	public void setValue(DbThree three, String obj) throws KnownError {
		if (obj == null) {
			three.setNull(java.sql.Types.DOUBLE);
			return;
		}
		Double in = Double.parseDouble(obj);
		three.setDouble(in);
	}

	public String toString() {
		return "float8";
	}

	@Override
	public String[] getSqlNames() {
		final String[] s = { "float8" };
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Float8Type);
	}

	@Override
	public String getJavaType() {
		return "Double";
	}

	@Override
	public Object[] getArrayOf(int size) {
		return new Double[size];
	}

	@Override
	public String getSqlName() {
		return "float8";
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
		double b = db3.getDouble();
		if (db3.wasNull()) {
			return null;
		}
		return b;
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError {
		return (Double[]) db3.getArray();
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
		return Double.parseDouble(str);
	}

	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		writer.value(((Double) value).doubleValue());
	}

	@Override
	public boolean isJavaType() {
		return true;
	}
	
	@Override
	public boolean isPrimitive() {
		return true;
	}
	
	@Override
	public String callProtoPart() {
		return "?";
	}
}
