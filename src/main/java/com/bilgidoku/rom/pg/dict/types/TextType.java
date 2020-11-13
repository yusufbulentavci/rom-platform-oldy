package com.bilgidoku.rom.pg.dict.types;

import java.text.ParseException;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;

public class TextType implements TypeAdapter {

	@Override
	public void setValue(DbThree three, String obj)
			throws KnownError {
		if(obj==null){
			three.setNull( java.sql.Types.VARCHAR);
			return;
		}

		three.setString(obj);
	}
	
	public String toString(){
		return "text";
	}

	@Override
	public String[] getSqlNames() {
		final String[] s={"text","char"};
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof TextType);
	}

	@Override
	public String getJavaType() {
		return "String";
	}

	@Override
	public Object[] getArrayOf(int size) {
		return new String[size];
	}

	@Override
	public String getSqlName() {
		return "text";
	}

	@Override
	public boolean needSqlConversion() {
		return false;
	}

	@Override
	public boolean isToQuote() {
		return true;
	}
	
	@Override
	public boolean needCast() {
		return false;
	}

	@Override
	public Object getValue(DbThree db3) throws KnownError {
		String b= db3.getString();
		if(db3.wasNull()){
			return null;
		}
		return b;
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError{
		return (String[]) db3.getArray();
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
		return str;
	}
	

	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		writer.value(value.toString());
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
