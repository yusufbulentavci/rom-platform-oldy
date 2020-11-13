package com.bilgidoku.rom.pg.dict.types;

import java.text.ParseException;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;

public class VoidType implements TypeAdapter {
	public static TypeAdapter one=new VoidType();
	@Override
	public void setValue(DbThree three, String obj) throws KnownError{
		throw new RuntimeException("No op for void");
	}

	public String toString(){
		return "void";
	}

	@Override
	public String[] getSqlNames() {
		final String[] s={"void"};
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof VoidType);
	}

	@Override
	public String getJavaType() {
		return "void";
	}

	@Override
	public Object[] getArrayOf(int size) {
		throw new RuntimeException("No array op for void");
	}

	@Override
	public String getSqlName() {
		return "void";
	}

	@Override
	public boolean needSqlConversion() {
		return false;
	}

	@Override
	public boolean isToQuote() {
		throw new RuntimeException("Nothing to do with VOID");
	}

	@Override
	public boolean needCast() {
		throw new RuntimeException("Nothing to do with VOID");
	}

	@Override
	public Object getValue(DbThree db3) throws KnownError {
		return null;
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError {
		return null;
	}

	@Override
	public void writeValue(DbThree db3, JSONWriter writer) throws KnownError, JSONException {
		throw new RuntimeException("Nothing to do with VOID");
	}

	@Override
	public void writeArrayValue(DbThree db3, JSONWriter writer) throws KnownError, JSONException {
		throw new RuntimeException("Nothing to do with VOID");
	}

	@Override
	public Object fromString(String str) throws ParseException {
		throw new RuntimeException("Nothing to do with VOID");
	}

	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		throw new RuntimeException("Nothing to do with VOID");
	}
	
	@Override
	public boolean isJavaType() {
		return true;
	}
	
	@Override
	public String callProtoPart() {
		throw new RuntimeException("Nothing to do with VOID");
	}

	@Override
	public boolean isPrimitive() {
		// TODO Auto-generated method stub
		return false;
	}
}
