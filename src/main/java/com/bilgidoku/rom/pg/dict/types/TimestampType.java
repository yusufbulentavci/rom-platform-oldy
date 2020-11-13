package com.bilgidoku.rom.pg.dict.types;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;

public class TimestampType implements TypeAdapter {

	ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>();

	@Override
	public void setValue(DbThree three, String obj) throws KnownError {
		if (obj == null) {
			three.setNull(java.sql.Types.TIMESTAMP);
			return;
		}
		Date d;
		try {
			d = getDateFormat().parse(obj);
		} catch (ParseException e) {
			throw new KnownError(e);
		}
		three.setTimestamp(new java.sql.Timestamp(d.getTime()));

	}

	public String toString() {
		return "timestamp";
	}

	@Override
	public String[] getSqlNames() {
		final String[] s = { "timestamp" };
		return s;
	}

	private DateFormat getDateFormat() {
		DateFormat d = dateFormat.get();
		if (d == null) {
			d = new SimpleDateFormat();
			dateFormat.set(d);
		}
		return d;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof TimestampType);
	}

	@Override
	public String getJavaType() {
		return "String";
	}

	@Override
	public Object[] getArrayOf(int size) {
		return new Timestamp[size];
	}

	@Override
	public String getSqlName() {
		return "timestamp";
	}

	@Override
	public boolean needSqlConversion() {
		return true;
	}

	@Override
	public boolean isToQuote() {
		return true;
	}

	@Override
	public boolean needCast() {
		return true;
	}

	@Override
	public Object getValue(DbThree db3) throws KnownError {
		java.sql.Timestamp b = db3.getTimestamp();
		if (db3.wasNull()) {
			return null;
		}
		return b;
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError {
		return (java.sql.Timestamp[]) db3.getArray();
	}

	@Override
	public void writeValue(DbThree db3, JSONWriter writer) throws KnownError, JSONException {
		writer.value(getValue(db3));
	}

	@Override
	public void writeArrayValue(DbThree db3, JSONWriter writer) throws KnownError, JSONException {
		writer.value(getArrayValue(db3));
	}

	@Override
	public Object fromString(String str) throws ParseException {
		Date d = getDateFormat().parse(str);
		return new Timestamp(d.getTime());
	}

	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		writer.value(value.toString());
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
