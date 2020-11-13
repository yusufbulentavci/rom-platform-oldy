package com.bilgidoku.rom.pg.veritabani;

import java.sql.Array;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.util.SiteUtil;


public class StringDbSetGet implements DbSetGet{

	final StringBuilder sb;
	int index=0;
	
	public StringDbSetGet(StringBuilder sb){
		this.sb=sb;
	}
	
	@Override
	public DbThree setNull(int b) throws KnownError {
		appendComma();
		sb.append("null");
		return null;
	}

	private void appendComma() {
		if(index==0)
			return;
		sb.append(",");
	}

	@Override
	public DbThree setString(String val) throws KnownError {
		appendComma();
		sb.append(SiteUtil.escapeQuoteWithBackslash(val));
		return null;
	}

	@Override
	public String getString() throws KnownError {
		throw new RuntimeException("Not to be called");
	}

	@Override
	public boolean getBoolean() throws KnownError {
		throw new RuntimeException("Not to be called");
	}

	@Override
	public DbThree setBoolean(boolean val) throws KnownError {
		appendComma();
		sb.append(val?"t":"f");
		return null;
	}

	@Override
	public DbThree setArray(String sqlName, Object[] objs) throws KnownError {
		throw new RuntimeException("Not to be called");
	}


	@Override
	public Array getArray() throws KnownError {
		throw new RuntimeException("Not to be called");
	}

	@Override
	public DbThree setInt(Integer val) throws KnownError {
		appendComma();
		sb.append(val);
		return null;
	}

	@Override
	public Integer getInt() throws KnownError {
		throw new RuntimeException("Not to be called");
	}

	@Override
	public long getLong() throws KnownError {
		throw new RuntimeException("Not to be called");
	}
	
	@Override
	public Short getShort() throws KnownError {
		throw new RuntimeException("Not to be called");
	}

	@Override
	public DbThree setLong(Long val) throws KnownError {
		appendComma();
		sb.append(val);
		return null;
	}

	@Override
	public DbThree setShort(Short val) throws KnownError {
		appendComma();
		sb.append(val);
		return null;
	}
	
	@Override
	public void setTrueOrder(int[] trueFieldList) {
		
	}

	@Override
	public String[] genTrueFieldNames() throws KnownError {
		return null;
	}

}
