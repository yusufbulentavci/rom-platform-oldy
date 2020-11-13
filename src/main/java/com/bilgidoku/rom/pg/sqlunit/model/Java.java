package com.bilgidoku.rom.pg.sqlunit.model;

import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.rom.RomComp;


public class Java extends SuComp{

	private String className;

	public Java(SqlUnit su, String className, boolean unitTest, int lineNo) {
		super(su, false,unitTest, lineNo, true);
		this.className=className;
	}
	
	public boolean equals(Object object) {
		if (object == null
				|| !(object instanceof com.bilgidoku.rom.pg.sqlunit.model.Java)) {
			return false;
		}
		Java m = (Java) object;
		if (m.className.equals(this.className)) {
			return true;
		}
		return false;
	}

	public String toString() {
		return "Java: " + className;
	}

	@Override
	public boolean isSql() {
		return false;
	}
	
	@Override
	public RomComp getComp() {
		return null;
	}

	@Override
	public List<Command> run() {
		List<Command> cmds=new LinkedList<Command>();
//		cmds.add(new Command(className,0,true));
		return cmds;
	}


}
