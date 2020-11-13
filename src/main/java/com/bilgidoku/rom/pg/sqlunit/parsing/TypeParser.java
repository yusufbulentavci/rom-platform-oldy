package com.bilgidoku.rom.pg.sqlunit.parsing;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bilgidoku.rom.pg.sqlunit.RomDb;
import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.SuException;
import com.bilgidoku.rom.pg.sqlunit.model.Field;
import com.bilgidoku.rom.pg.sqlunit.model.Type;
import com.bilgidoku.rom.pg.dict.types.TypeAdapter;

public class TypeParser extends CompParser {
	public TypeParser(RomDb romDb) {
		super(romDb);
		noromParser = new StandartParser(romDb,KEYWORD_TYPE, "type",null, true);
	}
	StandartParser noromParser;
	private static final String KEYWORD_TYPE = "--@TYPE";

	Pattern typePre = Pattern
			.compile("^(\\s)*create(\\s)+type(\\s)+([a-z_]+)\\.([a-z_]+)");
	

	@Override
	public String getKeyword() {
		return KEYWORD_TYPE;
	}

	@Override
	public SuComp parse(SqlUnit su, String trimmedLine, String nextLine, int lineNo)
			throws SuException {
		if (nextLine == null) {
			throw new SuException(su.getNamed(), 0,
					"Type defined but create type not found at next line.\n Type definition:"
							+ trimmedLine);
		}
		
		if (trimmedLine.indexOf(" norom") > 0) {
			return noromParser.parse(su, trimmedLine, nextLine, lineNo);
		}

		nextLine = nextLine.toLowerCase();
		
		/**
		 * First of all we need names, go with nextLine
		 */
		Matcher matchFunc = typePre.matcher(nextLine);
		if (!matchFunc.find()) {
			throw new SuException(su.getNamed(), 0, "Table parsing failed:"
					+ nextLine+"\n header:"+trimmedLine);
		}
		
		if(nextLine.length()<=matchFunc.end()){
			throw new SuException(su.getNamed(), 0, "Table parsing failed, no definition:"
					+ nextLine);
		}
		int novirgul=0;
		nextLine=nextLine.trim();
		if(nextLine.endsWith(";"))
			novirgul++;
		List<Field> tableFields=new LinkedList<Field>();
		String fieldStr = nextLine.substring(matchFunc.end()+1,nextLine.length()-1-novirgul);
		int asInd=fieldStr.indexOf("as");
		fieldStr=fieldStr.substring(asInd+2).trim();
		fieldStr=fieldStr.substring(1).trim();
		String[] fields = fieldStr.trim().split(",");
		if(fields.length>0){
			for (String string : fields) {
				String[] parts = string.trim().split(" ");
				if(parts.length<2){
					throw new RuntimeException("Field definition error:"+string+" in table definition:"+nextLine);
				}
				
				int dims=0;
				String typeStr = parts[1];
				if(typeStr.endsWith("[]")){
					dims=1;
					typeStr=typeStr.substring(0,typeStr.length()-2);
				}
				boolean map=false;
				if(typeStr.equals("hstore")){
//					typeStr="text";
					map=true;
				}
				TypeAdapter sqlType=getType(typeStr);
				if(sqlType==null){
					throw new RuntimeException("Type not found for:"+parts[1]+" in table definition:"+nextLine);
				}
				tableFields.add(new Field(parts[0],sqlType,map,dims,false, null));
			}
		}
		
//		syso(fields);

		boolean unitTest=(trimmedLine.indexOf(" utest") > 0);
		
		Type em = new Type(su, matchFunc.group(4), matchFunc.group(5), unitTest, lineNo, tableFields, 0);
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
