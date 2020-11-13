package com.bilgidoku.rom.gwt.server.common.coders;

import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.KnownError;


public class ShortCoder extends TypeCoder<Short>{

	@Override
	public Short decode(Object json) {
		return getShort(json);
	}
	
	@Override
	public Object encode(Short obj) {
		if(obj==null){
			return null;
		}
		return obj;
	}

	@Override
	public Short[] createArray(int size) {
		return new Short[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Short val) throws KnownError {
		if(val==null){
			db3.setNull(java.sql.Types.SMALLINT);
			return;
		}
		db3.setShort(val);
	}

	@Override
	protected Short inGetDbValue(DbSetGet db3) throws KnownError {
		return db3.getShort();
	}

	@Override
	public String getSqlName() {
		return "smallint";
	}

	@Override
	public Short fromString(String s) {
		if(s==null)
			return null;
		return Short.parseShort(s);
	}

	@Override
	public String toString(Short t) throws KnownError {
		if(t==null)
			return "null";
		return t.toString();
	}
	
	public boolean isToQuote() {
		return false;
	}

}
