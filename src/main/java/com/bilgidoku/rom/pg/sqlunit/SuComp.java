package com.bilgidoku.rom.pg.sqlunit;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.bilgidoku.rom.pg.sqlunit.model.Command;
import com.bilgidoku.rom.pg.sqlunit.rom.RomComp;


public abstract class SuComp {
	private final static String SQL_STATEMENT_DELIMITER = ";";
	private final boolean isSql;
	protected boolean clp=false;
	protected boolean js=false;

	private final boolean unitTest;
	private final int lineNo;
	private final SqlUnit su;
	private int version=0;
	private final boolean noRom;
	private boolean noRes=false;
	
	private boolean abstractFunction=false;


	
	private final List<Command> commands=new LinkedList<Command>();
	private final SortedMap<Integer,List<Command>> upgrades=new TreeMap<Integer,List<Command>>();
	
	public SuComp(SqlUnit su, boolean isSql,boolean unitTest, int lineNo, boolean noRom){
		this.isSql=isSql;
		this.unitTest=unitTest;
		this.lineNo=lineNo;
		this.su=su;
		this.noRom=noRom;
	}

	public List<Command> afterRun() {
		return null;
	}
	public List<Command> beforeRun() {
		return null;
	}

	public abstract RomComp getComp();

	public List<Command> run(){
		return commands;
	}
	
	public List<Command> upgrade(Integer dbVer){
		List<Command> ret=new LinkedList<Command>();
		for (Integer version : upgrades.keySet()) {
			if(dbVer>=version)
				continue;
			List<Command> cmds = upgrades.get(version);
			ret.addAll(cmds);
		}
		return ret;
	}
	
	
	public void addCommand(Command command){
		commands.add(command);
	}

	public List<Command> getCommands() {
		return commands;
	}


	public boolean isSql() {
		return isSql;
	}


	public boolean isUnitTest() {
		return unitTest;
	}
	
	public boolean justRun() {
		return false;
	}


	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}

	public void addUpgrade(int version2, Command upCmd) {
		List<Command> ls = upgrades.get(version2);
		if(ls==null){
			ls=new LinkedList<Command>();
			upgrades.put(version2, ls);
		}
		ls.add(upCmd);
	}

	public SqlUnit getSu() {
		return su;
	}

	public int getLineNo() {
		return lineNo;
	}

	public boolean isNoRom() {
		return noRom;
	}
	
	
	public boolean isAbstractFunction() {
		return abstractFunction;
	}

	public void setAbstractFunction(boolean abstractFunction) {
		this.abstractFunction = abstractFunction;
	}

	public List<Command> genAbstract() {
		return getCommands();
	}

	public boolean isNoRes() {
		return noRes;
	}
	
	public void setNores() {
		noRes=true;
	}
	
	public boolean isClp() {
		return this.clp;
	}
	
	public void setClp() {
		this.clp=true;
	}

	
	public boolean isJs() {
		return this.js;
	}
	
	public void setJs() {
		this.js=true;
	}
	public boolean isLineBased() {
		return false;
	}

}
