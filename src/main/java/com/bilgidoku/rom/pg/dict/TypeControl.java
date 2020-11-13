package com.bilgidoku.rom.pg.dict;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/*
For each dao table: schema+table
	superType: parent

	

*/
public class TypeControl {
	public final Net net;
	final UriHierarychy hier;
	final Map<String,DispatchMethod> methods=new HashMap<String,DispatchMethod>();
	final String schema;
	final String table;
	final String schemaTable;
	private final String getResourceStatement;
	private boolean checkVisible;
	final String superTypeName;
	private TypeControl superType=null;
	private final int cpu;
	

	public TypeControl(Net net2, UriHierarychy hier, 
			boolean isService, String schema, String table, String superTypeName, int cpu) {
		this.net=net2;
		this.hier=hier;
		this.schema=schema;
		this.table=table;
		this.schemaTable=schema+"."+table;
		this.superTypeName=superTypeName;
		this.cpu=cpu;
		this.getResourceStatement=isService?null:("select container,html_file,modified_date,creation_date,delegated,ownercid,gid,relatedcids,mask " +
				"from "+schema+"."+table+ " where host_id=? and uri=?");
	}

	public void addMethod(String methodName, DispatchMethod dn) {
		this.methods.put(methodName,dn);
	}

	public DispatchMethod getMethod(String method) {
		return this.methods.get(method);
	}
	
	public String getResourceStatement(){
		return getResourceStatement;
	}

	public boolean toCheckVisibility() {
		return checkVisible;
	}
	
	public void resolveInheritance(Map<String,TypeControl> typeControlsByName){
		if(superTypeName==null || superType!=null)
			return;
		TypeControl spr=typeControlsByName.get(superTypeName);
		if(spr==null)
			throw new RuntimeException("Unknown super type:"+superTypeName+" in "+this.getDbName());
		spr.resolveInheritance(typeControlsByName);
		for (Entry<String, DispatchMethod> it : spr.getMethods().entrySet()) {
			if(methods.containsKey(it.getKey()))
				continue;
			methods.put(it.getKey(), it.getValue());
		}
	}

	private Map<String,DispatchMethod> getMethods() {
		return this.methods;
	}

	public String getDbName() {
		return schemaTable;
	}

	public boolean isOne() {
		return hier==UriHierarychy.ONE;
	}

	public int getCpu() {
		return cpu;
	}
}
