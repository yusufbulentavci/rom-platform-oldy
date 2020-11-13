package com.bilgidoku.rom.pg.sqlunit.parsing;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bilgidoku.rom.pg.sqlunit.RomDb;
import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.SuException;
import com.bilgidoku.rom.pg.sqlunit.model.Field;
import com.bilgidoku.rom.pg.sqlunit.model.Table;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.util.NumberParser;
import com.bilgidoku.rom.pg.dict.types.TypeAdapter;
import com.bilgidoku.rom.pg.sembol.SembolGorevlisi;

/**
 * 
 * 
 * 
 * 
 * @author bilo
 *
 */
public class TableParser extends CompParser {
	public TableParser(RomDb romDb) {
		super(romDb);
		noromParser = new StandartParser(romDb,KEYWORD_TABLE, "table",null, true);
	}
	StandartParser noromParser;
	private static final String KEYWORD_TABLE = "--@TABLE";

	Pattern tablePre = Pattern
			.compile("^(\\s)*create(\\s)+table(\\s)+([a-z_]+)\\.([a-z_]+)");
	Pattern inheritPattern = Pattern
			.compile("^\\([\\s]*([a-z_]+)\\.([a-z_]+)\\);?");
	

	@Override
	public String getKeyword() {
		return KEYWORD_TABLE;
	}

	@Override
	public SuComp parse(SqlUnit su, String trimmedLine, String nextLine, int lineNo)
			throws SuException {
		if (nextLine == null) {
			throw new SuException(su.getNamed(), 0,
					"Table defined but create table not found at next line.\n Table definition:"
							+ trimmedLine);
		}
		
		if (trimmedLine.indexOf(" norom") > 0) {
			return noromParser.parse(su, trimmedLine, nextLine, lineNo);
		}

		nextLine = nextLine.toLowerCase();
		
		/**
		 * First of all we need names, go with nextLine
		 */
		Matcher matchFunc = tablePre.matcher(nextLine);
		if (!matchFunc.find()) {
			throw new SuException(su.getNamed(), 0, "Table parsing failed:"
					+ nextLine+"\n header:"+trimmedLine);
		}
		
		if(nextLine.length()<=matchFunc.end()){
			throw new SuException(su.getNamed(), 0, "Table parsing failed, no definition:"
					+ nextLine);
		}
		int novirgul=0;
		if(nextLine.endsWith(";"))
			novirgul++;
		List<Field> tableFields=new LinkedList<Field>();
		String rest=nextLine.substring(matchFunc.end());
		String fieldStr=null;
		TypeAdapter inheritType=null;
		if(rest.indexOf(" inherits ")>0){
			String[] rests=rest.split(" inherits ");
			fieldStr=rests[0].trim();
			fieldStr=fieldStr.substring(1,fieldStr.length()-1);
			Matcher matchInherit = inheritPattern.matcher(rests[1]);
			if(!matchInherit.find()){
				throw new RuntimeException("Table inherit'"+rests[1]+"' parsing failed;"+nextLine);
			}
			inheritType=getType(matchInherit.group(1)+"."+matchInherit.group(2));
			if(inheritType==null){
				throw new RuntimeException("Table inherit parsing; Type not found;"+matchInherit.group(1)+"."+matchInherit.group(2));
			}
			
		}else{
			fieldStr=nextLine.substring(matchFunc.end()+1,nextLine.length()-1-novirgul);
		}
		
		String[] fields = fieldStr.trim().split(",");
		Set<String> pks=new HashSet<String>();
		if(fields.length>0){
			parseField(nextLine, tableFields, fields, pks);
		}
		
		boolean unitTest=(trimmedLine.indexOf(" utest") > 0);
	
	
		Table em = new Table(su, matchFunc.group(4), matchFunc.group(5), unitTest, lineNo, tableFields, 0, inheritType, pks);
		trimmedLine = trimmedLine.toLowerCase();
		String[] methodTokens = trimmedLine.split("\\s+");
		for (String string : methodTokens) {
			if (string.equals("owner")) {
				em.setOwner(true);
			} else if (string.startsWith("ozellikler=")) {
				String[] ozellikler = string.substring("ozellikler=".length()).split(",");
				for (String r : ozellikler) {
					Integer ri;
					try {
						ri = SembolGorevlisi.tek().check(r);
						em.addOzellik(ri);
					} catch (KnownError e) {
						e.printStackTrace();
						throw new SuException(su.getNamed(), 0, "Table parsing failed, no definition:"
								+ nextLine);
					}
				}
			} else if (string.equals("ozellik")) {
				em.setOzellik(true);
			} else if (string.startsWith("roles=")) {
				String[] roles = string.substring("roles=".length()).split(",");
				for (String r : roles) {
					em.addRole(r);
				}
			} else if (string.startsWith("prefix=")) {
				String prefix = string.substring("prefix=".length()).trim();
				em.setPrefix(prefix);
			} else if (string.startsWith("ver=")) {
				String hm = string.substring("ver=".length());
				em.setVersion(Integer.parseInt(hm));
			} else if (string.startsWith("net=")) {
				String hm = string.substring("net=".length());
				em.setNet(hm);
			} else if (string.equals("hsc")) {
				em.setSubContainer(true);
			} else if (string.equals("one")) {
				em.setOne(true);
			} else if (string.equals("nores")) {
				em.setNores();
			} else if (string.equals("clp")) {
				em.setClp();
			}else if (string.equals("js")) {
				em.setJs();
			} else if (string.startsWith("cache=")) {
				String hm = string.substring("cache=".length());
				em.setCache(NumberParser.longOfTimeInSecs(hm, true));
			}
		}

		return em;
	}

	private void parseField(String nextLine, List<Field> tableFields, String[] fields, Set<String> pks) {
		boolean getPrimary=false;
		for (String stringMore : fields) {
			String string=stringMore;
			int annStart=stringMore.indexOf("--");
			String[] anns=null;
			if(annStart>=0) {
				string=stringMore.substring(0, annStart).trim();
				String annString = stringMore.substring(annStart+2).trim();
				anns=annString.split(" ");
			}
						
			
			
			if(getPrimary){
				int endPK=string.indexOf(')');
				if(endPK==0){
					break;
				}else if(endPK>0){
					pks.add(string.substring(0, endPK).trim());
					break;
				}
				pks.add(string.trim());
				continue;
			}
			String[] parts = string.trim().split("\\s+");
			if(parts.length<2){
				throw new RuntimeException("Field definition error:"+string+" in table definition:"+nextLine);
			}
			// Todo primary key(xx,yy) ler sonda yer almali bunu ve sonrasini ignore ediyorum
			if(parts[0].equals("primary")){
				if(parts.length<2){
					throw new RuntimeException("Key field is absent for primary key:"+string+" in table definition:"+nextLine);
				}
				
				if(parts[1].length()<="key(".length()){
					throw new RuntimeException("Invalid primary key:"+string+" in table definition:"+nextLine);
				}
				
				int endPK=parts[1].indexOf(')');
				if(endPK<0){
					pks.add(parts[1].substring("key(".length()).trim());
					getPrimary=true;
					continue;
				}else{
					pks.add(parts[1].substring("key(".length(), endPK).trim());
					break;
				}
					
			}
			int dims=0;
			boolean map=false;
			String typeStr = parts[1];
			if(typeStr.endsWith("[]")){
				dims=1;
				typeStr=typeStr.substring(0,typeStr.length()-2);
			}
			if(typeStr.equals("serial"))
				typeStr="integer";
			if(typeStr.equals("bigserial"))
				typeStr="bigint";
			if(typeStr.equals("hstore")){
//					typeStr="text";
				map=true;
			}
			TypeAdapter sqlType=getType(typeStr);
			if(sqlType==null){
				throw new RuntimeException("Type not found for:"+parts[1]+" in table definition:"+nextLine);
			}
			tableFields.add(new Field(parts[0],sqlType,map, dims, false, anns));
		}
	}

}
