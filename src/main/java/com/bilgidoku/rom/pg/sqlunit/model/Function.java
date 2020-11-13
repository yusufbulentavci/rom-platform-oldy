package com.bilgidoku.rom.pg.sqlunit.model;

import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.rom.RomComp;


public class Function  extends SuComp{
	final private String named;
	final private String args;
	final private String schema;
	

	public Function(SqlUnit su, String named, String args, boolean unitTest, int lineNo) {
		super(su, true,unitTest, lineNo, true);
		
		int ind = named.indexOf('.');
		if(ind<0){
			this.schema="public";
			this.named=named;
		}else{
			this.schema=named.substring(0, ind);
			this.named=named.substring(ind+1);
		}
		
//		if (!su.getSchemaName().equals("public") 
//				&& named.startsWith(su.getSchemaName() + ".")) {
//			this.named=named.substring((su.getSchemaName() + ".").length());
//		}else{
//			this.named=named;
//		}
		this.args=args;
	}

	public boolean equals(Object object) {
		if (object == null
				|| !(object instanceof com.bilgidoku.rom.pg.sqlunit.model.Function)) {
			return false;
		}
		Function m = (Function) object;
		if (m.named.equals(this.named) 
				&& m.args.equals(this.args)) {
			return true;
		}
		return false;
	}

	public String toString() {
		return "Function:" + named +args;
	}

	public String getNamed() {
		return named;
	}
	
	
	@Override
	public boolean isSql() {
		return true;
	}

	public String getArgs() {
		return args;
	}
	@Override
	public RomComp getComp() {
		return new RomComp("function", this.schema, named, this.getVersion());
	}

	@Override
	public List<Command> upgrade(Integer dbVer) {
		List<Command> upgradeCmds=super.upgrade(dbVer);
		for (Command command : getCommands()) {
			Command c=command.clone();
			String dropFunc = dropFunc();
//			c.setCommand(c.getCommand().replaceAll("create function", "create or replace function"));
			
			upgradeCmds.add(new Command(dropFunc, c.getLineNumber()));
			upgradeCmds.add(c);
		}
		return upgradeCmds;
	}

	private String dropFunc() {
		StringBuilder sb=new StringBuilder();
		sb.append("select dropfunction('");
		sb.append(this.schema);
		sb.append("','");
		sb.append(named);
		sb.append("');");
		
		String dropFunc=sb.toString();
		return dropFunc;
	}
	
	public List<Command> genAbstract() {
		StringBuilder sb=new StringBuilder();
		sb.append("select dropfunction('");
		sb.append(this.schema);
		sb.append("','");
		sb.append(named);
		sb.append("');");
		
		List clone = (List) ((LinkedList) getCommands()).clone();
		clone.add(0, new Command(sb.toString(),0));
		
		return clone;
	}

	

}
