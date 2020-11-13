package com.bilgidoku.rom.gwt.server.common.coders;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.util.Assert;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.KnownError;

public class ArrayCoder<T> extends TypeCoder<T[]> {

	private TypeCoder<T> coder;

	public ArrayCoder(TypeCoder<T> coder) {
		this.coder = coder;
	}

	@Override
	public T[] decode(Object v) throws JSONException {
		if (v == null)
			return null;

		if (v instanceof String) {
			v = new JSONArray((String) v);
		} else if (!(v instanceof JSONArray)) {
			throw new RuntimeException("Array decoding failed, not a jsonarray or string:" + v);
		}

		JSONArray o = (JSONArray) v;
		T[] rets = coder.createArray(o.length());
		for (int i = 0; i < rets.length; i++) {
			rets[i] = coder.decode(o.get(i));
		}
		return rets;
	}

	@Override
	public Object encode(T[] obj) throws JSONException {
		if (obj == null) {
			return null;
		}
		JSONArray o = new JSONArray();
		for (T t : obj) {
//			syso(t);
			o.put(coder.encode(t));
		}
		return o;
	}

	@Override
	public T[][] createArray(int size) {
		return null;
	}

	@Override
	public void setDbValue(DbSetGet db3, T[] val) throws KnownError {
		if (val == null) {
			db3.setNull(java.sql.Types.VARCHAR);
			return;
		}
		db3.setString(toString(val));
	}

	@Override
	public String toString(T[] val) throws KnownError {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		int last = val.length - 1;
		for (int i = 0; i < val.length; i++) {
			T t = val[i];
			if (t == null) {
				sb.append("null");
			} else {
				String s = coder.quoteDbArrayText(val[i]);
				
				sb.append(s);
			}
			if (i != last)
				sb.append(',');
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	protected T[] inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		String str = db3.getString();
		return fromString(str);
	}

	@Override
	public T[] fromString(String str) throws JSONException {
		if (str == null)
			return null;

		Assert.error(str.length() >= 2 && str.charAt(0) == '{' && str.charAt(str.length() - 1) == '}',"Unexpected structure");
		
		String[] ms = SiteUtil.splitDbArray(str);
		if(ms==null)
			return null;
		T[] ts=coder.createArray(ms.length);
		for (int i=0; i<ms.length; i++) {
			ts[i]=coder.fromString(ms[i]);
		}
		return ts;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Shouldnt be called");
	}

}
