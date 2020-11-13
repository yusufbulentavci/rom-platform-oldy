package com.bilgidoku.rom.pg.veritabani;

import com.bilgidoku.rom.shared.err.KnownError;

public interface DbSetGet {
	public DbThree setNull(int b) throws KnownError;
	public DbThree setString(String val) throws KnownError;
	public DbThree setBoolean(boolean val) throws KnownError;
	public DbThree setArray(String sqlName, Object[] objs) throws KnownError;
	public DbThree setInt(Integer val) throws KnownError;
	public DbThree setLong(Long val) throws KnownError;
	public DbThree setShort(Short val) throws KnownError;

	public void setTrueOrder(int[] trueFieldList);
	public String[] genTrueFieldNames() throws KnownError;

	public long getLong() throws KnownError;
	public Short getShort() throws KnownError;
	public Object getArray() throws KnownError;
	public Integer getInt() throws KnownError;
	public String getString() throws KnownError;
	public boolean getBoolean() throws KnownError;
	
}
