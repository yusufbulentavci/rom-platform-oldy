package com.bilgidoku.rom.pg.sqlunit.model;

import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.rom.RomComp;

public class Js extends SuComp {
	public Js(SqlUnit su, boolean unitTest, int lineNo) {
		super(su, false, unitTest, lineNo, true);
		this.js=true;
	}

	@Override
	public RomComp getComp() {
		return null;
	}

	@Override
	public List<Command> upgrade(Integer dbVer) {
		return new LinkedList<Command>();
	}
	
	@Override
	public boolean justRun() {
		return true;
	}
	
	public boolean isSql() {
		return false;
	}
	
	public void addCommand(Command command){
		command.setJs(true);
		super.addCommand(command);
	}
	
}
