package com.bilgidoku.rom.pg.sqlunit.model;

import java.text.ParseException;
import java.util.LinkedList;

import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.rom.RomComp;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;


public class EnumType extends TypeComp{
	final private boolean unitTest;

	public EnumType(SqlUnit su, String schema, String named, boolean unitTest, int lineNo, int dims) {
		super(su, true,unitTest, lineNo, new LinkedList<Field>(), schema, named, dims);
		this.unitTest=unitTest;
	}

	public boolean equals(Object object) {
		if (object == null
				|| !(object instanceof com.bilgidoku.rom.pg.sqlunit.model.EnumType)) {
			return false;
		}
		EnumType m = (EnumType) object;
		if (m.schema.equals(this.schema) && m.name.equals(this.name) && m.dims==dims) {
			return true;
		}
		return false;
	}

	public String toString() {
		return "Enum:" + name ;
	}

	public String getNamed() {
		return name;
	}
	
	@Override
	public boolean isSql() {
		return true;
	}

	@Override
	public boolean isUnitTest() {
		return unitTest;
	}

	@Override
	public RomComp getComp() {
		return new RomComp("type", this.getSu().getSchemaName(), name, this.getVersion());
	}

	public String getSchema() {
		return schema;
	}
	
	@Override
	public String getJavaType() {
		return "String";
	}
	
	@Override
	public void setValue(DbThree three, String obj) throws KnownError {
		if(obj==null){
			three.setNull( java.sql.Types.VARCHAR);
			return;
		}

		three.setString(obj);
	}

	@Override
	public Object[] getArrayOf(int size) {
		return new String[size];
	}

	@Override
	public String getSqlName() {
		return getSqlNames()[0];
	}

	@Override
	public boolean needSqlConversion() {
		return true;
	}

	@Override
	public boolean isToQuote() {
		return true;
	}

	@Override
	public boolean needCast() {
		return true;
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
//		Array array = db3.getArray();
//		if (array == null) {
//			return null;
//		}
//		
//		return (String[]) array.getArray();
		
		String s=db3.getString();
		if(s==null){
			return null;
		}
		if(s.length()>2 && s.startsWith("{")){
			s=s.substring(1,s.length()-1);
		}
		return s.split(",");
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
		throw new RuntimeException("Not implemented");
	}
	
	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isJavaType() {
		return false;
	}
	
	@Override
	public String callProtoPart() {
		return "?::"+getSqlName();
	}

	@Override
	public boolean isPrimitive() {
		return false;
	}
	
}