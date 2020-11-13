package com.bilgidoku.rom.pg.dict.types;

import java.text.ParseException;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;

public class Int4Type implements TypeAdapter{

	@Override
	public void setValue(DbThree three, String obj) throws KnownError{
		if(obj==null){
			three.setNull( java.sql.Types.INTEGER);
			return;
		}
			Integer in=Integer.parseInt(obj);
			three.setInt(in);
		
	}

	public String toString(){
		return "int4";
	}

	@Override
	public String[] getSqlNames() {
		final String[] s={"int4","integer","int"};
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Int4Type);
	}

	@Override
	public String getJavaType() {
		return "Integer";
	}

	@Override
	public Object[] getArrayOf(int size) {
		return new Integer[size];
	}

	@Override
	public String getSqlName() {
		return "int4";
	}

	@Override
	public boolean needSqlConversion() {
		return false;
	}

	@Override
	public boolean isToQuote() {
		return false;
	}
	
	@Override
	public boolean needCast() {
		return false;
	}

	@Override
	public Object getValue(DbThree db3) throws KnownError {
		int b= db3.getInt();
		if(db3.wasNull()){
			return null;
		}
		return b;
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError{
		return (Integer[]) db3.getArray();
	}
	
	@Override
	public void writeValue(DbThree db3, JSONWriter writer) throws KnownError, JSONException {
		writer.value(getValue(db3));
	}

	@Override
	public void writeArrayValue(DbThree db3, JSONWriter writer) throws KnownError, JSONException  {
		writer.value(getArrayValue(db3));
	}

	@Override
	public Object fromString(String str) throws ParseException {
		return Integer.parseInt(str);
	}
	
	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		writer.value(((Integer)value).intValue());
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
