package com.bilgidoku.rom.pg.sqlunit.model;

import java.util.List;

import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.types.TypeAdapter;

public abstract class TypeComp extends SuComp implements TypeAdapter{
	final protected String schema;
	final protected String name;
	protected final List<Field> fields;
	final protected int dims;
	protected boolean toGenerate;
	protected boolean tbl=false;

	
	public TypeComp(SqlUnit su, boolean isSql, boolean unitTest, int lineNo, List<Field> fields, 
			String schema, String name, int dims) {
		super(su, isSql, unitTest, lineNo, false);
		this.schema=schema;
		this.name=name;
		this.fields=fields;
		this.dims=dims;
	}



	public void addAtt(Field m) {
		fields.add(m);
	}

	public String getName() {
		return name;
	}
	
	public String getDbName() {
		return schema+"."+name;
	}

	public List<Field> getClsFields() {
		return fields;
	}
	
	public List<Field> getFields() {
		return fields;
	}

	public String getNameFirstUpper() {
		return capitalize(getNameJavaForm());
	}

	public String getNameJavaForm() {
		return name;
	}

	public static String capitalize(String s) {
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	public boolean getNoUri(){
		for (Field at : this.fields) {
			if(at.name.equals("uri")){
				return false;
			}
		}
		return true;
	}

	public boolean isToGenerate() {
		return toGenerate;
	}

	public void setToGenerate(boolean toGenerate) {
		this.toGenerate = toGenerate;
	}
	
	@Override
	public void setValue(DbThree three, String obj) throws KnownError {
		throw new RuntimeException("Type component db3 setvalue is not implemented");
	}

	@Override
	public String[] getSqlNames() {
		return new String[]{schema+"."+name};
	}

	@Override
	public String getJavaType() {
		return getNameFirstUpper();
	}
	
	public boolean isTbl() {
		return tbl;
	}

}
