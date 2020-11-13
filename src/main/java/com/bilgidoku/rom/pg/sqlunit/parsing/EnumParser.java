package com.bilgidoku.rom.pg.sqlunit.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bilgidoku.rom.pg.sqlunit.RomDb;
import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.SuException;
import com.bilgidoku.rom.pg.sqlunit.model.EnumType;

public class EnumParser extends CompParser{
	public EnumParser(RomDb romDb) {
		super(romDb);
	}

	private static final String KEYWORD_ENUM = "--@ENUM";
	Pattern methodPre = Pattern
			.compile("^[\\s]*create[\\s]+type[\\s]+([a-z_]+)\\.([a-z_]+)[\\s]+as enum");
	

	public String getKeyword() {
		return KEYWORD_ENUM;
	}

	@Override
	public SuComp parse(SqlUnit su, String trimmedLine, String nextLine, int lineNo) throws SuException
			{
		if (nextLine == null) {
			throw new SuException(su.getNamed(), 0,
					"Enum defined but create type not found at next line.\n Enum definition:"
							+ trimmedLine);
		}
		nextLine = nextLine.toLowerCase();
		/**
		 * First of all we need names, go with nextLine
		 */
		Matcher matchFunc = methodPre.matcher(nextLine);
		if (!matchFunc.find()) {
			throw new SuException(su.getNamed(), 0, "Enum parsing failed:"
					+ nextLine);
		}
		
		boolean unitTest=(trimmedLine.indexOf(" utest") > 0);

		
		EnumType em = new EnumType(su, matchFunc.group(1), matchFunc.group(2), unitTest, lineNo, 0);
		
		trimmedLine = trimmedLine.toLowerCase();
		String[] methodTokens = trimmedLine.split("\\s+");
		for (String string : methodTokens) {
			 if (string.startsWith("ver=")) {
				String hm = string.substring("ver=".length());
				em.setVersion(Integer.parseInt(hm));
			}
		}
		

		
		
		return em;
	}
}
