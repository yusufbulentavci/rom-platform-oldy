package com.bilgidoku.rom.gwt.server.common.coders;

import java.util.Arrays;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.KnownError;

public abstract class TypeCoder<T> {
	private boolean isTable;
	private String[] fieldList;
	private int[] trueFieldList;

	public abstract void setDbValue(DbSetGet db3, T val) throws KnownError;

	protected abstract T inGetDbValue(DbSetGet db3) throws KnownError, JSONException;

	public abstract T decode(Object json) throws JSONException;

	public abstract Object encode(T obj) throws JSONException;

	public abstract T[] createArray(int size);

	public abstract String getSqlName();

	public abstract T fromString(String s) throws JSONException;

	public abstract String toString(T t) throws KnownError;

	public TypeCoder() {
		this.isTable = false;
	}

	public TypeCoder(boolean isTable, String[] fieldList) {
		this.isTable = isTable;
		this.fieldList = fieldList!=null?fieldList:new String[] {};
		this.trueFieldList = new int[this.fieldList.length];
		for (int i = 0; i < trueFieldList.length; i++) {
			this.trueFieldList[i] = i;
		}
	}

	private void queryFieldList(String[] qfl) throws KnownError {
		for (int i = 0; i < fieldList.length; i++) {
			int j = 0;
			for (; j < qfl.length; j++) {
				if (fieldList[i].equals(qfl[j])) {
					break;
				}
			}
			if (j >= qfl.length) {
				throw new KnownError(
						"Unmatched query expected:" + Arrays.toString(fieldList) + " Metadata:" + Arrays.toString(qfl));
			}
			trueFieldList[i] = j;
		}
	}

	public T getDbValue(DbSetGet db3) throws KnownError, JSONException {
		if (!isTable) {
			db3.setTrueOrder(null);
			return inGetDbValue(db3);
		}
		if (trueFieldList == null) {
			String[] tfn = db3.genTrueFieldNames();
			queryFieldList(tfn);
		}

		db3.setTrueOrder(trueFieldList);
		return inGetDbValue(db3);

	}

	public static String getString(Object val) {
		if (isNull(val))
			return null;
		return (String) val;
	}

	public static Boolean getBoolean(Object val) {
		if (isNull(val))
			return null;
		if (val.equals(Boolean.FALSE) || (val instanceof String && ((String) val).equalsIgnoreCase("false"))) {
			return false;
		} else if (val.equals(Boolean.TRUE) || (val instanceof String && ((String) val).equalsIgnoreCase("true"))) {
			return true;
		}
		throw new RuntimeException("Not a boolean:" + val);
	}

	public static Integer getInteger(Object val) {
		if (isNull(val))
			return null;
		return val instanceof Number ? ((Number) val).intValue() : Integer.parseInt((String) val);

	}

	public static Long getLong(Object val) {
		if (isNull(val))
			return null;
		return val instanceof Number ? ((Number) val).longValue() : Long.parseLong((String) val);
	}
	
	public static Short getShort(Object val) {
		if (isNull(val))
			return null;
		return val instanceof Number ? ((Number) val).shortValue() : Short.parseShort((String) val);
	}

	public static boolean isNull(Object json) {
		return (json == null);
	}

	public JSONObject getObject(Object js) {
		if (isNull(js))
			return null;
		JSONObject json = (JSONObject) js;
		return json;
	}

	public boolean isToQuote() {
		return true;
	}

	public final String quoted(T t) throws KnownError {
		if (isToQuote()) {
			return SiteUtil.quoteDbText(toString(t));
		}
		return toString(t);
	}

	public final String quoteDbArrayText(T t) throws KnownError {
		if (isToQuote()) {
			return SiteUtil.quoteDbArrayText(toString(t));
		}
		return toString(t);
	}
}
