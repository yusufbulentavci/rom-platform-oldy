package com.bilgidoku.rom.pg.sqlunit.parsing;

import com.bilgidoku.rom.pg.sqlunit.RomDb;
import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.model.Run;

public class RunParser extends CompParser {
	public RunParser(RomDb romDb) {
		super(romDb);
	}

	private static final String KEYWORD_RUN = "--@RUN";

	public String getKeyword() {
		return KEYWORD_RUN;
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
		Run run=new Run(su,true,utest,lineNo);
		return run;
	}
	
	
}
