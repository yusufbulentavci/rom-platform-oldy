package com.bilgidoku.rom.pg.sqlunit.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.rom.RomComp;
import com.bilgidoku.rom.pg.dict.types.TypeAdapter;

public class Schema extends SuComp {
	final private String schema;
	private boolean ownerSuper = false;

	private Set<String> roles = new HashSet<String>();

	public Map<String, Table> tables = new HashMap<String, Table>();
	public Map<String, Function> functions = new HashMap<String, Function>();
	public Map<String, Type> types = new HashMap<String, Type>();
	public Map<String, EnumType> enumTypes = new HashMap<String, EnumType>();
	public Map<String, Standart> seqs = new HashMap<String, Standart>();
	public Map<String, Standart> aggs = new HashMap<String, Standart>();
	public Map<String, Standart> domains = new HashMap<String, Standart>();
	public Map<String, Standart> operators = new HashMap<String, Standart>();
	

	public Schema(SqlUnit su, String schema, boolean unitTest, int lineNo, boolean noRom) {
		super(su, true, unitTest, lineNo, noRom);
		this.schema = schema;
	}

	public boolean equals(Object object) {
		if (object == null || !(object instanceof com.bilgidoku.rom.pg.sqlunit.model.Schema)) {
			return false;
		}
		Schema m = (Schema) object;
		if (m.schema.equals(this.schema) && m.ownerSuper == this.ownerSuper && m.roles.equals(this.roles)) {
			return true;
		}
		return false;
	}

	public String toString() {
		return "Method:" + schema + " owner:" + ownerSuper + " roles:" + roles.toString();
	}

	public String getSchema() {
		return schema;
	}

	public boolean isOwner() {
		return ownerSuper;
	}

	public void setOwner(boolean isOwner) {
		this.ownerSuper = isOwner;
	}

	public void addRole(String role) {
		this.roles.add(role);
	}

	public Set<String> getRoles() {
		return roles;
	}

	@Override
	public boolean isSql() {
		return true;
	}

	@Override
	public RomComp getComp() {
		return new RomComp("schema", this.getSu().getSchemaName(), "", this.getVersion());
	}

	public void addTable(Table t) {
		tables.put(t.getTable(),t);
	}

	public Table getTable(String table) {
		Table t=this.tables.get(table);
		return t;
	}

	public Type getType(String table) {
		Type t=this.types.get(table);
		return t;
	}
	
	public EnumType getEnumType(String table) {
		EnumType t=this.enumTypes.get(table);
		return t;
	}
	
	public Table getTableNN(String table) {
		Table t=this.tables.get(table);
		if(t==null)
			throw new RuntimeException("Table:"+table+" not found in schema:"+schema);
		return t;
	}

	public void addType(Type t) {
		types.put(t.getName(),t);
	}
	
	public void addEnumType(EnumType t) {
		enumTypes.put(t.getNamed(),t);
	}
	
	public TypeAdapter getAnyType(String string) {
		if(string.endsWith("[]")){
			string=string.substring(0, string.length()-2);
		}
		TypeAdapter st=getTable(string);
		if(st!=null)
			return st;
		st=getType(string);
		if(st!=null)
			return st;
		return getEnumType(string);
	}

	public Collection<Table> getTables() {
		return tables.values();
	}

	public Collection<Type> getTypes() {
		return types.values();
	}
}
