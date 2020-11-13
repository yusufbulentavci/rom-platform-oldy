package com.bilgidoku.rom.pg.sqlunit.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bilgidoku.rom.pg.sqlunit.RomDb;
import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.SuException;
import com.bilgidoku.rom.pg.sqlunit.model.Java;

public class JavaParser extends CompParser {
	public JavaParser(RomDb romDb) {
		super(romDb);
	}

	private static final String KEYWORD = "--@JAVA";
	Pattern methodPre = Pattern
			.compile("^--@JAVA(\\s)+([a-zA-Z_\\.]+)");
	
	@Override
	public String getKeyword() {
		return KEYWORD;
	}

	@Override
	public SuComp parse(SqlUnit su, String trimmedLine, String nextLine, int lineNo)
			throws SuException {
		
		Matcher matchFunc = methodPre.matcher(trimmedLine);
		if (!matchFunc.find()) {
			throw new SuException(su.getNamed(), 0,"Java parsing failed:"
					+ trimmedLine);
		}
		String className = matchFunc.group(2);
		boolean unitTest=(trimmedLine.indexOf(" utest") > 0);
		Java java=new Java(su, className, unitTest, lineNo);
		
		trimmedLine = trimmedLine.toLowerCase();
		String[] methodTokens = trimmedLine.split("\\s+");
		for (String string : methodTokens) {
			 if (string.startsWith("ver=")) {
				String hm = string.substring("ver=".length());
				java.setVersion(Integer.parseInt(hm));
			}
		}
		
		return java;
	}

}
