package com.bilgidoku.rom.pg.sqlunit.model;

import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.rom.RomComp;

public class Run extends SuComp {
	public Run(SqlUnit su, boolean isSql, boolean unitTest, int lineNo) {
		super(su, isSql, unitTest, lineNo, true);
	}
	
	@Override
	public boolean isLineBased() {
		return true;
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
	
	
	
}
