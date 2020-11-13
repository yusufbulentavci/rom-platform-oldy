package com.bilgidoku.rom.pg.sqlunit.parsing;

import com.bilgidoku.rom.pg.sqlunit.RomDb;
import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.model.Clips;

public class ClipsParser extends CompParser {
	public ClipsParser(RomDb romDb) {
		super(romDb);
	}

	private static final String KEYWORD_CLIPS = "--@CLIPS";

	public String getKeyword() {
		return KEYWORD_CLIPS;
	}

	@Override
	public SuComp parse(SqlUnit su, String trimmedLine, String nextLine, int lineNo)  {
		String name="";
		trimmedLine = trimmedLine.toLowerCase();
		boolean utest=false;
		String[] methodTokens = trimmedLine.split("\\s+");
		for (String string : methodTokens) {
			if (string.equals("utest")) {
				utest=true;
			} 
		}
		Clips run=new Clips(su,utest,lineNo);
		return run;
	}
	
	
}
