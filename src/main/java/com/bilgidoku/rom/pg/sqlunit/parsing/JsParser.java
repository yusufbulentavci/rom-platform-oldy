package com.bilgidoku.rom.pg.sqlunit.parsing;

import com.bilgidoku.rom.pg.sqlunit.RomDb;
import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.model.Js;

public class JsParser extends CompParser {
	public JsParser(RomDb romDb) {
		super(romDb);
	}

	private static final String KEYWORD_JS = "--@JS";

	public String getKeyword() {
		return KEYWORD_JS;
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
		Js run=new Js(su,utest,lineNo);
		return run;
	}
	
	
}
