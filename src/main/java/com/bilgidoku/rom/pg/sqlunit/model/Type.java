package com.bilgidoku.rom.pg.sqlunit.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.rom.RomComp;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.CGAtt;
import com.bilgidoku.rom.pg.dict.CGMethod;
import com.bilgidoku.rom.pg.dict.CGType;


public class Type extends TypeComp implements CGType{

	public Type(SqlUnit su, String schema, String name, boolean unitTest, int lineNo, List<Field> tableFields, int dims) {
		super(su, true, unitTest, lineNo, tableFields, schema, name, dims);
	}

	public boolean equals(Object object) {
		if (object == null
				|| !(object instanceof com.bilgidoku.rom.pg.sqlunit.model.Type)) {
			return false;
		}
		Type m = (Type) object;
		if (m.schema.equals(this.schema) && m.name.equals(this.name)  && m.dims==dims
				) {
			return true;
		}
		return false;
	}

	public String toString() {
		return "Type:" + schema + "." + name;
	}

	public String getSchema() {
		return schema;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean isSql() {
		return true;
	}

	@Override
	public RomComp getComp() {
		return new RomComp("type", this.getSu().getSchemaName(), name, this.getVersion());
	}

	@Override
	public Object[] getArrayOf(int size) {
		return new String[size];
	}

	@Override
	public String getSqlName() {
		return schema+"."+name;
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
		StringBuilder sb = new StringBuilder();
		JSONWriter jsonWriter = new JSONWriter(sb);
		try {
			jsonWriter.object();
			for (Field field : fields) {
				jsonWriter.key(field.name);
				field.getTypeHolder().writeJson(jsonWriter, db3);
			}
			jsonWriter.endObject();
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError {
		throw new RuntimeException("Array of type not supported yet");
	}

	@Override
	public void writeValue(DbThree db3, JSONWriter jsonWriter) throws KnownError, JSONException {
		jsonWriter.object();
		for (Field field : fields) {
			jsonWriter.key(field.name);
			field.getTypeHolder().writeJson(jsonWriter, db3);
		}
		jsonWriter.endObject();
	}

	@Override
	public void writeArrayValue(DbThree db3, JSONWriter writer) throws KnownError, JSONException {
		throw new RuntimeException("Array of type not supported yet");
	}

	@Override
	public Collection<CGMethod> getDaomethods() {
		return null;
	}

	public List<CGAtt> getAtts() {
		List<CGAtt> myats=new ArrayList<CGAtt>();
		for (CGAtt cgAtt : getFields()) {
			myats.add(cgAtt);
		}
		return myats;
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
	public Collection<CGMethod> getClientDaomethods() {
		return null;
	}

	@Override
	public boolean isPrimitive() {
		return false;
	}

	@Override
	public String callProtoPart() {
		StringBuilder sb=new StringBuilder();
		sb.append("row(");
		List<Field> af = getFields();
		for (Field field : af) {
			sb.append(field.getTypeHolder().callProtoPart());
		}
		sb.append(")::");
		sb.append(getSqlName());
		
		return sb.toString();
	}
}
