package com.bilgidoku.rom.gwt.server.common.coders;

import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.KnownError;

public class StringCoder extends TypeCoder<String>{

	@Override
	public String decode(Object json) {
		return getString(json);
	}

	@Override
	public Object encode(String obj) {
		if(obj==null){
			return null;
		}
		return obj;
	}

	@Override
	public String[] createArray(int size) {
		return new String[size];
	}



	@Override
	public void setDbValue(DbSetGet db3, String val) throws KnownError {
		if(val==null){
			db3.setNull(java.sql.Types.VARCHAR);
			return;
		}
		db3.setString(val);
	}

	@Override
	protected String inGetDbValue(DbSetGet db3) throws KnownError {
		return db3.getString();
	}

	@Override
	public String getSqlName() {
		return "text";
	}

	@Override
	public String fromString(String s) {
		if(s.equals("NULL"))
			return null;
		return s;
	}

	@Override
	public String toString(String t) throws KnownError {
		return t;
	}

}
