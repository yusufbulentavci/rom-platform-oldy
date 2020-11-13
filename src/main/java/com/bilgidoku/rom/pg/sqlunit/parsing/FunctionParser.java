package com.bilgidoku.rom.pg.sqlunit.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bilgidoku.rom.pg.sqlunit.RomDb;
import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.SuException;
import com.bilgidoku.rom.pg.sqlunit.model.Function;

public class FunctionParser extends CompParser {
	private final String KEYWORD = "--@FUNCTION";
	private final Pattern pattern;
	private String reg;

	public FunctionParser(RomDb romDb) {
		super(romDb);
		reg = "^[\\s]*create[\\s]+function[\\s]+([a-z_\\.]+)";
		reg += MethodParser.ARGS;
		pattern = Pattern.compile(reg);
	}

	public String getKeyword() {
		return KEYWORD;
	}

	@Override
	public SuComp parse(SqlUnit su, String trimmedLine, String nextLine, int lineNo) throws SuException {
		if (nextLine == null) {
			throw new SuException(su.getNamed(), 0, " Function defined but create function"
					+ " not found at next line.\n function definition:" + trimmedLine);
		}
		nextLine = nextLine.toLowerCase();
		/**
		 * First of all we need names, go with nextLine
		 */
		Matcher matchFunc = pattern.matcher(nextLine);
		if (!matchFunc.find()) {
			throw new SuException(su.getNamed(), 0, "Function parsing failed:" + nextLine + "\n pattern:" + reg);
		}

		boolean unitTest = (trimmedLine.indexOf(" utest") > 0);
		Function em = new Function(su, matchFunc.group(1), matchFunc.group(2), unitTest, lineNo);

		trimmedLine = trimmedLine.toLowerCase();
		String[] methodTokens = trimmedLine.split("\\s+");
		for (String string : methodTokens) {
			if (string.startsWith("ver=")) {
				String hm = string.substring("ver=".length());
				em.setVersion(Integer.parseInt(hm));
			}else if (string.equals("abstract")) {
				em.setAbstractFunction(true);
			}
		}

		return em;

	}
}
