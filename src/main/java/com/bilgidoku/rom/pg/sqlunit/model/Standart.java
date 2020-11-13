package com.bilgidoku.rom.pg.sqlunit.model;

import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.rom.RomComp;


public class Standart extends SuComp{
	final private String named;
	final private String type;

	public Standart(SqlUnit su, String type, String named, boolean unitTest, int lineNo, boolean noRom) {
		super(su, true,unitTest, lineNo, noRom);
		this.type=type;
		if (!su.getSchemaName().equals("public") 
				&& named.startsWith(su.getSchemaName() + ".")) {
			this.named=named.substring((su.getSchemaName() + ".").length());
		}else{
			this.named=named;
		}
	}

	public boolean equals(Object object) {
		if (object == null
				|| !(object instanceof com.bilgidoku.rom.pg.sqlunit.model.Standart)) {
			return false;
		}
		Standart m = (Standart) object;
		if (m.named.equals(this.named) && m.type.equals(this.type)) {
			return true;
		}
		return false;
	}

	public String toString() {
		return type+":" + named ;
	}

	public String getNamed() {
		return named;
	}
	
	@Override
	public boolean isSql() {
		return true;
	}
	
	@Override
	public RomComp getComp() {
		return new RomComp(type, this.getSu().getSchemaName(), named, this.getVersion());
	}


}
