package com.bilgidoku.rom.pg.sqlunit.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bilgidoku.rom.pg.sqlunit.RomDb;
import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.SuException;
import com.bilgidoku.rom.pg.sqlunit.model.Schema;

public class SchemaParser extends CompParser {
	public SchemaParser(RomDb romDb) {
		super(romDb);
		noromParser = new StandartParser(romDb, KEYWORD_SCHEMA, "schema",null, true);
	}
	StandartParser noromParser;

	private static final String KEYWORD_SCHEMA = "--@SCHEMA";
	
	Pattern schemaPre = Pattern
			.compile("^(\\s)*create(\\s)+schema(\\s)+([a-z_]+)");
	

	@Override
	public String getKeyword() {
		return KEYWORD_SCHEMA;
	}


	@Override
	public SuComp parse(SqlUnit su, String trimmedLine, String nextLine, int lineNo)
			throws SuException {
		if (nextLine == null) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder
					.append("Schema defined but create table not found at next line.\n Schema definition:");
			stringBuilder.append(trimmedLine);
			throw new SuException(su.getNamed(), 0, stringBuilder.toString());
		}
		
		if (trimmedLine.indexOf(" norom") > 0) {
			return noromParser.parse(su, trimmedLine, nextLine, lineNo);
		}
		
		nextLine = nextLine.toLowerCase();
		/**
		 * First of all we need names, go with nextLine
		 */
		Matcher matchFunc = schemaPre.matcher(nextLine);
		if (!matchFunc.find()) {
			throw new SuException(su.getNamed(), 0, "Schema parsing failed:"
					+ nextLine);
		}
		
		boolean unitTest=(trimmedLine.indexOf(" utest") > 0);

		Schema em = new Schema(su, matchFunc.group(4), unitTest, lineNo, false);
		trimmedLine = trimmedLine.toLowerCase();
		String[] methodTokens = trimmedLine.split("\\s+");
		for (String string : methodTokens) {
			if (string.equals("owner")) {
				em.setOwner(true);
			} else if (string.startsWith("roles=")) {
				String[] roles = string.substring("roles=".length()).split(",");
				for (String r : roles) {
					em.addRole(r);
				}
			}else if (string.startsWith("ver=")) {
				String hm = string.substring("ver=".length());
				em.setVersion(Integer.parseInt(hm));
			}
		}

		return em;
	}

}
