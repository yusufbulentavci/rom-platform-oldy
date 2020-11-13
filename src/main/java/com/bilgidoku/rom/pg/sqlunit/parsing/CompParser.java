package com.bilgidoku.rom.pg.sqlunit.parsing;

import com.bilgidoku.rom.pg.sqlunit.RomDb;
import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.SuException;
import com.bilgidoku.rom.pg.dict.types.TypeAdapter;
import com.bilgidoku.rom.pg.dict.types.TypeFactory;

public abstract class CompParser {

	private static TypeFactory typeFactory=new TypeFactory();
	protected final RomDb romDb;
	public CompParser(RomDb romDb){
		this.romDb=romDb;
	}
	public abstract String getKeyword();
	public abstract SuComp parse(SqlUnit su, String trimmedLine, String nextLine, int lineNo) throws SuException;


	public TypeAdapter getType(String string) {
		if(string.startsWith("char(")){
			string="char";
		}
		TypeAdapter st=typeFactory.getSqlType(string);
		if(st!=null){
			return st;
		}
		st=romDb.getAnyType(string);
		return st;
	}
}
