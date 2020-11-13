package com.bilgidoku.rom.gwt.server.common.coders;

import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.KnownError;



public class LongCoder extends TypeCoder<Long>{

	@Override
	public Long decode(Object json) {
		return getLong(json);
	}
	
	@Override
	public Object encode(Long obj) {
		if(obj==null){
			return null;
		}
		return obj;
	}

	@Override
	public Long[] createArray(int size) {
		return new Long[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Long val) throws KnownError {
		if(val==null){
			db3.setNull(java.sql.Types.BIGINT);
			return;
		}
		db3.setLong(val);
	}

	@Override
	protected Long inGetDbValue(DbSetGet db3) throws KnownError {
		return db3.getLong();
	}

	@Override
	public String getSqlName() {
		return "bigint";
	}
	
	@Override
	public Long fromString(String s) {
		if(s==null)
			return null;
		return Long.parseLong(s);
	}

	@Override
	public String toString(Long t) throws KnownError {
		if(t==null)
			return "null";
		return t.toString();
	}

	public boolean isToQuote() {
		return false;
	}
}
