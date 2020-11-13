package com.bilgidoku.rom.pg.dict.types;

import java.text.ParseException;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;

public class Float4Type implements TypeAdapter {

	@Override
	public void setValue(DbThree three, String obj) throws KnownError {
		if (obj == null) {
			three.setNull(java.sql.Types.FLOAT);
			return;
		}
		Float in = Float.parseFloat(obj);
		three.setFloat(in);
	}

	public String toString() {
		return "float4";
	}

	@Override
	public String[] getSqlNames() {
		final String[] s = { "float4", "real" };
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Float4Type);
	}

	@Override
	public String getJavaType() {
		return "Float";
	}

	@Override
	public Object[] getArrayOf(int size) {
		return new Float[size];
	}

	@Override
	public String getSqlName() {
		return "float4";
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
		float b = db3.getFloat();
		if (db3.wasNull()) {
			return null;
		}
		return b;
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError {
		return (Float[]) db3.getArray();
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
		return Float.parseFloat(str);
	}

	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		writer.value(((Float) value).floatValue());
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
