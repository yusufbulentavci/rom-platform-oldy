package com.bilgidoku.rom.pg.dict.types;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;

public class BoolType implements TypeAdapter {

	public void setValue(DbThree three, String obj) throws KnownError {
		if (obj == null) {
			three.setNull(java.sql.Types.BOOLEAN);
			return;
		}

		Boolean in = Boolean.parseBoolean(obj);
		three.setBoolean(in);

	}

	public void checkValue(Object obj, int length) {

	}

	public String toString() {
		return "boolean";
	}

	@Override
	public String[] getSqlNames() {
		final String[] s = { "boolean", "bool" };
		return s;
	}

	@Override
	public String getSqlName() {
		return "boolean";
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof BoolType);
	}

	@Override
	public String getJavaType() {
		return "Boolean";
	}

	@Override
	public Object[] getArrayOf(int size) {
		return new Boolean[size];
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
		boolean b = db3.getBoolean();
		if (db3.wasNull()) {
			return null;
		}
		return b;
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError {
		return (Boolean[]) db3.getArray();
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
	public Object fromString(String str) {
		return Boolean.parseBoolean(str);
	}

	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		Boolean b = (Boolean) value;
		writer.value(b.booleanValue());
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
