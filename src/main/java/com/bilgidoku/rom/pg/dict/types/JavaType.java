package com.bilgidoku.rom.pg.dict.types;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.CGAtt;
import com.bilgidoku.rom.pg.dict.CGMethod;
import com.bilgidoku.rom.pg.dict.CGType;

public class JavaType implements TypeAdapter, CGType {

	private Class c;

	private List<SrvAtt> fields=new ArrayList<SrvAtt>();
	
	public JavaType(Class c) {
		this.c=c;
		
	}
	
	public void setup(){
		System.out.println("-->"+c.getSimpleName());
		for (Field fi: c.getFields()) {
			TypeHolder th=TypeFactory.one.getTypeHolder(fi.getType(), fi.getGenericType());
			if(th==null){
				throw new RuntimeException("No type for "+fi.getType()+" for class:"+c.getSimpleName());
			}
			fields.add(new SrvAtt(fi.getName(), th, fi));
		}
	}

	@Override
	public String getJavaType() {
		return c.getCanonicalName();
	}

	@Override
	public void setValue(DbThree three, String obj) throws KnownError{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Object[] getArrayOf(int size) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public String[] getSqlNames() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean needSqlConversion() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isToQuote() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean needCast() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Object getValue(DbThree db3) throws KnownError {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void writeValue(DbThree db3, JSONWriter writer) throws KnownError,
			JSONException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void writeArrayValue(DbThree db3, JSONWriter writer)
			throws KnownError, JSONException {

		throw new RuntimeException("Not implemented");
	}

	@Override
	public Object fromString(String str) throws ParseException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		writer.object();
		for (SrvAtt fi : fields) {
			writer.key(fi.name);
			try {
				fi.type.writeJsonObject(writer, fi.field.get(value));
			} catch (IllegalArgumentException | IllegalAccessException | NullPointerException
					e) {
				throw new RuntimeException("Json conversion error, field:"+fi.name,e);
			}
		}
		writer.endObject();
	}

	@Override
	public boolean isJavaType() {
		return true;
	}

	@Override
	public String getNameFirstUpper() {
		return this.c.getSimpleName();
	}

	@Override
	public Collection<CGMethod> getDaomethods() {
		return new ArrayList<CGMethod>();
	}

	@Override
	public boolean getNoUri() {
		return false;
	}

	@Override
	public List<CGAtt> getAtts() {
		List<CGAtt> arr=new ArrayList<CGAtt>();
		for (CGAtt cgAtt : fields) {
			arr.add(cgAtt);
		}
		return arr;
	}

	@Override
	public Collection<CGMethod> getClientDaomethods() {
		return new ArrayList<CGMethod>();
	}

	@Override
	public boolean isPrimitive() {
		return false;
	}

	@Override
	public String callProtoPart() {
		throw new RuntimeException("Not to be called");
	}

}
