package com.bilgidoku.rom.pg.sqlunit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.pg.dict.types.TypeAdapter;
import com.bilgidoku.rom.pg.sqlunit.model.EnumType;
import com.bilgidoku.rom.pg.sqlunit.model.Method;
import com.bilgidoku.rom.pg.sqlunit.model.Schema;
import com.bilgidoku.rom.pg.sqlunit.model.Standart;
import com.bilgidoku.rom.pg.sqlunit.model.Table;
import com.bilgidoku.rom.pg.sqlunit.model.Type;

public class RomDb {
	public Map<String,Schema> schemas=new HashMap<String,Schema>();
	public Map<String,Standart> langs=new HashMap<String,Standart>();
	public Map<String,Standart> triggers=new HashMap<String,Standart>();
	public Map<String,Standart> groups=new HashMap<String,Standart>();
	public Map<String, Standart> roles = new HashMap<String, Standart>();
	public Map<String, Standart> rules = new HashMap<String, Standart>();
	public Map<String, Standart> tspaces = new HashMap<String, Standart>();
	public Map<String, Standart> users = new HashMap<String, Standart>();
	private StringBuilder clipsTemplates;
	private Map<String,Table> clipsTables=new HashMap<>();
	
	public Map<String,Table> getClipsTables() {
		return clipsTables;
	}
	public void addComp(SuComp parseComp) {
		if(parseComp instanceof Schema){
			Schema s=(Schema) parseComp;
			schemas.put(s.getSchema(),s);
//			syso("Schema:"+s.getSchema());
		}else if(parseComp instanceof Table){
			Table t=(Table) parseComp;
			Schema s = getSchema(t.getSchema());
			s.addTable(t);
//			syso("Table:"+t.getName());
		}else if(parseComp instanceof Method){
			Method t=(Method) parseComp;
			Schema s = getSchema(t.getSchema());
			Table ta=s.getTable(t.getTable());
			if(ta==null){
				throw new RuntimeException("table not found:"+s.getSchema()+"."+t.getTable()+" for parseComp:"+parseComp.getSu().named);
			}
			ta.addMethod(t);
		}else if(parseComp instanceof Type){
			Type t=(Type) parseComp;
			Schema s = getSchema(t.getSchema());
			s.addType(t);
		}else if(parseComp instanceof EnumType){
			EnumType t=(EnumType) parseComp;
//			syso("Enum:"+t.getSchema()+"."+t.getName());
			Schema s = getSchema(t.getSchema());
			s.addEnumType(t);
		}
	}
	public Schema getSchema(String schema) {
		Schema s = schemas.get(schema);
		if(s==null)
			throw new RuntimeException("Schema not found:"+schema);
		return s;
	}
	public TypeAdapter getAnyType(String string) {
		String[] schemaType=string.trim().split("\\.");
		if(schemaType.length!=2)
			return null;
		Schema s=schemas.get(schemaType[0]);
		if(s==null)
			return null;
		return s.getAnyType(schemaType[1]);
	}
	public Collection<Schema> getSchemas() {
		return schemas.values();
	}
	
	public Table getTable(String schemaName, String named) {
		Schema s=getSchema(schemaName);
		Table t = s.getTable(named);
		if(t==null)
			throw new RuntimeException("unknown table:"+schemaName+"."+named);
		return t;
	}
	public void startMethods() {
		for (Schema s : schemas.values()) {
			for(Table t: s.tables.values()){
				for(Method m: t.methods.values()){
					m.start();
				}
			}
		}
	}
	
	public void init(){
		for (Schema s : schemas.values()) {
			for(Table t: s.tables.values()){
				for(Method m: t.methods.values()){
					m.setTablePrefix(t.getPrefix());
					m.setInherit(t.getInheritName());
					if(t.isSubContainer())
						m.setHsc("hsc");
					else if(t.isOne())
						m.setOne("one");
					m.setNet(t.getNet().name());
					if(m.getCache()==null)
						m.setCache(t.getCache());
				}
			}
		}
		genClips();
	}
	
	private void genClips() {
		this.clipsTemplates=new StringBuilder();
		for (Schema s : schemas.values()) {
			for(Table t: s.tables.values()){
				if(t.isClp()) {
					TableToDeftemplate.gen(clipsTemplates, t);
					this.clipsTables.put(t.getSqlName(), t);
				}
			}
		}
		
		System.out.println(getClipsTemplates().toString());
	}
	
	
	public String getClipsTemplates() {
		return clipsTemplates.toString();
	}

}
