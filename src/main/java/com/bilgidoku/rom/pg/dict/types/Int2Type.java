package com.bilgidoku.rom.pg.dict.types;

import java.text.ParseException;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;

public class Int2Type implements TypeAdapter {

	@Override
	public void setValue(DbThree three, String obj)
			throws KnownError{
		if(obj==null){
			three.setNull( java.sql.Types.SMALLINT);
			return;
		}
			Short in=Short.parseShort(obj);
			three.setShort( in);
		
	}

	@Override
	public String toString(){
		return "int2";
	}
	

	@Override
	public String[] getSqlNames() {
		final String[] s={"int2","smallint"};
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Int2Type);
	}

	@Override
	public String getJavaType() {
		return "Short";
	}

	@Override
	public Object[] getArrayOf(int size) {
		return new Short[size];
	}

	@Override
	public String getSqlName() {
		return "int2";
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
		int b= db3.getShort();
		if(db3.wasNull()){
			return null;
		}
		return b;
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError{
		return (Short[]) db3.getArray();
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
		return Short.parseShort(str);
	}
	
	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		writer.value(((Short)value).shortValue());
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
