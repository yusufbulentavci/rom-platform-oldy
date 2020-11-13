package com.bilgidoku.rom.gwt.server.common.coders;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.KnownError;



public class BooleanCoder extends TypeCoder<Boolean>{

	@Override
	public Boolean[] createArray(int size) {
		return new Boolean[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Boolean val) throws KnownError {
		if(val==null){
			db3.setNull(java.sql.Types.BOOLEAN);
			return;
		}
		db3.setBoolean(val);
	}

	@Override
	protected  Boolean inGetDbValue(DbSetGet db3) throws KnownError {
		return db3.getBoolean();
	}

	@Override
	public Boolean decode(Object json) throws JSONException {
		return getBoolean(json);
	}

	@Override
	public Object encode(Boolean obj) throws JSONException {
		return obj;
	}

	@Override
	public String getSqlName() {
		return "boolean";
	}

	@Override
	public Boolean fromString(String s) {
		if(s==null)
			return null;
		return s.equals("t");
	}

	@Override
	public String toString(Boolean t) throws KnownError {
		if(t==null)
			return null;
		if(t)
			return "t";
		return "f";
	}
	
	public boolean isToQuote() {
		return false;
	}

}
