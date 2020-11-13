package com.bilgidoku.rom.gwt.server.common.coders;

import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.KnownError;


public class IntegerCoder extends TypeCoder<Integer>{

	@Override
	public Integer decode(Object json) {
		return getInteger(json);
	}
	
	@Override
	public Object encode(Integer obj) {
		if(obj==null){
			return null;
		}
		return obj;
	}

	@Override
	public Integer[] createArray(int size) {
		return new Integer[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Integer val) throws KnownError {
		if(val==null){
			db3.setNull(java.sql.Types.INTEGER);
			return;
		}
		db3.setInt(val);
	}

	@Override
	protected Integer inGetDbValue(DbSetGet db3) throws KnownError {
		return db3.getInt();
	}

	@Override
	public String getSqlName() {
		return "int";
	}

	@Override
	public Integer fromString(String s) {
		if(s==null)
			return null;
		return Integer.parseInt(s);
	}

	@Override
	public String toString(Integer t) throws KnownError {
		if(t==null)
			return "null";
		return t.toString();
	}
	
	public boolean isToQuote() {
		return false;
	}

}
