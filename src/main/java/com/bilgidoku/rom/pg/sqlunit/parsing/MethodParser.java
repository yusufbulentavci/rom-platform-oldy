package com.bilgidoku.rom.pg.sqlunit.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bilgidoku.rom.pg.sqlunit.RomDb;
import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.SuException;
import com.bilgidoku.rom.pg.sqlunit.model.Field;
import com.bilgidoku.rom.pg.sqlunit.model.Method;
import com.bilgidoku.rom.ilk.util.NumberParser;
import com.bilgidoku.rom.pg.dict.CookieFinder;
import com.bilgidoku.rom.pg.dict.types.TypeAdapter;
import com.bilgidoku.rom.pg.dict.types.TypeHolder;
import com.bilgidoku.rom.pg.dict.types.VoidType;
/**
 * 
 * returns ile as ayni satirda olmali
 * 
 * 
 * @author bilo
 *
 */
public class MethodParser extends CompParser {
	private CookieFinder cf;

	public MethodParser(CookieFinder cf, RomDb romDb) {
		super(romDb);
		this.cf=cf;
		noromParser = new FunctionParser(romDb);
	}

	FunctionParser noromParser;
	private static final String KEYWORD_METHOD = "--@FUNCTION";
	public static final String ARGS = "(\\((?:[\\s]*(?:[a-z0-9_\\-]+)"
			+ "[\\s]+(?:[a-z_\\-\\.]+)" + "[\\s]*" + "(?:\\[\\])*"
			+ "(?:\\([0-9]+\\))?" + "(?:default[\\s]+[_\\/'0-9a-zA-Z]+)?"
			+ "[,]?" + ")*" + "\\))";

	public static final String RETURNS = "[\\s]*returns[\\s]*(setof)?[\\s]*([a-z_\\-\\.\\[\\]]+)";

	Pattern methodPre = Pattern
			.compile("^(?:\\s)*create(?:\\s)+function(?:\\s)+([a-z_]+)\\.([a-z]+)_([a-z_]+)"
					+ ARGS + RETURNS);

	public static final String ARGS2 = "" + "[\\s]*([a-z0-9_\\-]+)"
			+ "[\\s]+([a-z_\\-\\.]+\\[?\\]?)" + "[\\s]*"
			+ "(?:default[\\s]+([_\\/'0-9a-zA-Z]+))?";
	Pattern methodArgs = Pattern.compile(ARGS2);

	public String getKeyword() {
		return KEYWORD_METHOD;
	}

	@Override
	public SuComp parse(SqlUnit su, String trimmedLine, String nextLine,
			int lineNo) throws SuException {
		if (nextLine == null) {
			throw new SuException(su.getNamed(), 0,
					"Method defined but create function not found at next line.\n Method definition:"
							+ trimmedLine);
		}
		if (trimmedLine.indexOf(" norom") > 0) {
			return noromParser.parse(su, trimmedLine, nextLine, lineNo);
		}

		nextLine = nextLine.toLowerCase();

		/**
		 * First of all we need names, go with nextLine
		 */
		Matcher matchFunc = methodPre.matcher(nextLine);
		if (!matchFunc.find()) {
			throw new SuException(su.getNamed(), 0, "Function parsing failed:"
					+ nextLine);
		}

		boolean unitTest = (trimmedLine.indexOf(" utest") > 0);

		// for(int i=0; i<6;i++){
		// syso(i+":"+matchFunc.group(i));
		// }
		TypeHolder typeHolder=null;
		String retType = matchFunc.group(6);
		if(retType.equals("void")){
			typeHolder=new TypeHolder(VoidType.one, 0);
//			syso("===Found ret void type===");
		}else{
			boolean isArray=false;
			if(retType.endsWith("[]")){
				isArray=true;
				retType=retType.substring(0, retType.length()-2);
			}
//			syso("===Parsed ret type:" + retType);
			if (retType == null)
				throw new RuntimeException("Return type not found for method:"
						+ nextLine);
			TypeAdapter sType = getType(retType);
			if (sType == null) {
				throw new SuException(su.getNamed(), 0,"JavaType not found for sql type:" + retType + " Line:"
						+ nextLine);
			}
			typeHolder=new TypeHolder(sType, isArray?1:0);
//			syso("===Found ret type:" + sType.getJavaType());
	
			if (sType == null) {
				throw new RuntimeException("Unknow ret type:<" + retType
						+ "> for method:" + nextLine);
			}
		}
		Method em = new Method(cf, su, matchFunc.group(1), matchFunc.group(2),
				matchFunc.group(3), matchFunc.group(4), unitTest, lineNo,
				matchFunc.group(5) != null, typeHolder);

		if (matchFunc.group(4).length() > 3) {
			String bul = matchFunc.group(4).substring(1,
					matchFunc.group(4).length() - 1);
			String[] bull = bul.split(",");
			for (String field : bull) {
				Matcher matchArgs = methodArgs.matcher(field);
				if (matchArgs.find()) {

					int dims = 0;
					String typeStr = matchArgs.group(2);
					if (typeStr.endsWith("[]")) {
						dims = 1;
						typeStr = typeStr.substring(0, typeStr.length() - 2);
					}
					boolean map=false;
					if(typeStr.equals("hstore")){
//						typeStr="text";
						map=true;
					}
					TypeAdapter sqlType = getType(typeStr);

					if (sqlType == null) {
						throw new RuntimeException("Type not found for:"
								+ matchArgs.group(2) + " in table definition:"
								+ nextLine);
					}
					em.addArg(new Field(matchArgs.group(1), sqlType, map, dims, false, null));

					// em.addArg(new MethodArg(matchArgs.group(1),
					// matchArgs.group(2),
					// matchArgs.group(3), matchArgs.group(4)));
				}
			}

		}
		trimmedLine = trimmedLine.toLowerCase();
		String[] methodTokens = trimmedLine.split("\\s+");
		for (String string : methodTokens) {
			if (string.equals("retfile")) {
				em.setRetFile(true);
			} else if (string.startsWith("roles=")) {
				String[] roles = string.substring("roles=".length()).split(",");
				for (String r : roles) {
					em.addRole(r);
				}
			} else if (string.startsWith("audit=")) {
				String[] as = string.substring("audit=".length()).split(",");
				em.setAudits(as);
			}else if (string.startsWith("menu=")) {
				String[] as = string.substring("menu=".length()).split("->");
				em.setMenu(as);
			}else if (string.startsWith("audit")) {
				em.setAudits(null);
			} else if (string.startsWith("http=")) {
				String hm = string.substring("http=".length());
				em.setHttp(hm.trim());
			} else if (string.equals("list")) {
				em.setForObject(false);
			} else if (string.startsWith("before=")) {
				String hm = string.substring("before=".length());
				em.setBefore(hm.trim());
			} else if (string.startsWith("after=")) {
				String hm = string.substring("after=".length());
				em.setAfter(hm.trim());
			} else if (string.startsWith("ver=")) {
				String hm = string.substring("ver=".length());
				em.setVersion(Integer.parseInt(hm));
			} else if (string.equals("read")) {
				em.setAccesslevel("read");
			} else if (string.equals("write")) {
				em.setAccesslevel("write");
			} else if (string.equals("exec")) {
				em.setAccesslevel("exec");
			}else if (string.startsWith("cache=")) {
				String hm = string.substring("cache=".length());
				em.setCache(NumberParser.longOfTimeInSecs(hm, true));
			}else if (string.startsWith("cpu=")) {
				String hm = string.substring("cpu=".length());
				em.setCpu(Integer.parseInt(string));
			}else if (string.equals("abstract")) {
				em.setAbstractFunction(true);
			} 
		}

		if (em.getNamed().equals("list")) {
			em.setHttp("get");
			em.setForObject(false);
		} else if (em.getNamed().equals("new")) {
			em.setHttp("post");
			em.setForObject(false);
			if(em.getAccesslevel()==null)
				em.setAccesslevel("write");
		} else if (em.getNamed().equals("destroy")) {
			em.setHttp("delete");
			if(em.getAccesslevel()==null)
				em.setAccesslevel("write");
		} else if (em.getNamed().equals("get")) {
			em.setHttp("get");
		} else if (em.getNamed().equals("change")) {
			em.setHttp("post");
			if(em.getAccesslevel()==null)
				em.setAccesslevel("write");
		} else if (em.getNamed().equals("breed")) {
			em.setHttp("post");
			em.setForObject(false);
			if(em.getAccesslevel()==null)
				em.setAccesslevel("exec");
		} else if (em.getNamed().equals("extinct")) {
			em.setHttp("post");
			em.setForObject(false);
			if(em.getAccesslevel()==null)
				em.setAccesslevel("exec");
		}
		em.init(this);
		return em;

	}

}
