package com.bilgidoku.rom.pg.sqlunit.model;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.SuException;
import com.bilgidoku.rom.pg.sqlunit.parsing.CompParser;
import com.bilgidoku.rom.pg.sqlunit.rom.RomComp;
import com.bilgidoku.rom.shared.gorevli.Uygulama;
import com.bilgidoku.rom.pg.dict.AfterHook;
import com.bilgidoku.rom.pg.dict.ArgMapper;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.CGAtt;
import com.bilgidoku.rom.pg.dict.CGMethod;
import com.bilgidoku.rom.pg.dict.CallMethod;
import com.bilgidoku.rom.pg.dict.CookieFinder;
import com.bilgidoku.rom.pg.dict.mapper.ContactMapper;
import com.bilgidoku.rom.pg.dict.mapper.ContactNoMapper;
import com.bilgidoku.rom.pg.dict.mapper.CookieMapper;
import com.bilgidoku.rom.pg.dict.mapper.FileMapper;
import com.bilgidoku.rom.pg.dict.mapper.HeaderMapper;
import com.bilgidoku.rom.pg.dict.mapper.HostMapper;
import com.bilgidoku.rom.pg.dict.mapper.LangMapper;
import com.bilgidoku.rom.pg.dict.mapper.ParamMapper;
import com.bilgidoku.rom.pg.dict.mapper.RemoteAddrMapper;
import com.bilgidoku.rom.pg.dict.mapper.RolesMapper;
import com.bilgidoku.rom.pg.dict.mapper.SelfMapper;
import com.bilgidoku.rom.pg.dict.mapper.SidMapper;
import com.bilgidoku.rom.pg.dict.types.TextType;
import com.bilgidoku.rom.pg.dict.types.TypeAdapter;
import com.bilgidoku.rom.pg.dict.types.TypeFactory;
import com.bilgidoku.rom.pg.dict.types.TypeHolder;
import com.bilgidoku.rom.pg.dict.types.VoidType;
import com.bilgidoku.rom.min.Sistem;

public class Method extends SuComp implements CGMethod, CallMethod{
	final private String schema;
	final private String table;
	final String named;

	private boolean forObject = true;
	private String[] auditParams=null;

	private String http = "get";

	private Set<String> roles = new HashSet<String>();
	private String args;
	private String after = null;
	private String before = null;
	private List<Field> argList = new LinkedList<Field>();

	private BeforeHook beforeHook;
	private AfterHook afterHook;

	private ArgMapper[] argMappers = new ArgMapper[0];
	private String callProto;
	private int[] retColumnTypes;
	private boolean isRetSet;
	private TypeHolder retType;
	
	private boolean hasFileParam=false;
	private boolean retFile=false;
	private boolean cantBeInline=true;
	
	private String tablePrefix;
	
	private String hsc;
	private String one;
	private String net;
	private String accesslevel="read";
	private String inherit;
	
	private Long cache;
	private int cpu=100;
	private CookieFinder cf;
	private String[] menu;


	@Override
	public boolean cantBeInline() {
		return cantBeInline;
	}
	
	public Method(CookieFinder cf, SqlUnit su, String schema, String table, String named,
			String args, boolean unitTest, int lineNo, boolean isRetSet,
			TypeHolder retType) {
		super(su, true, unitTest, lineNo, false);
		this.cf=cf;
		this.schema = schema;
		this.table = table;
		this.named = named;
		this.args = args;
		this.setRetSet(isRetSet);
		this.setRetType(retType);
	}

	public void init(CompParser parser) throws SuException {
		this.cantBeInline=(beforeHook!=null || afterHook!=null || !http.equals("get") || isRetFile() || hasFileParam||isVoid());
		initHooks();
		initArgMappers(parser);
		this.callProto = makeCallProto(schema, table, named);
	}

	public void start() {
		if (beforeHook != null)
			beforeHook.start();
		if (afterHook != null)
			afterHook.start();
	}

	private void initArgMappers(CompParser parser) throws SuException {
		ArgMapper[] ams = new ArgMapper[argList.size()];
		TypeAdapter sqlType = null;
		TypeFactory typeFactory = TypeFactory.one;
		for (short i = 0; i < argList.size(); i++) {
			Field field = argList.get(i);
			String argName = field.name;
			Boolean canBeNull = true;
			Character argType = argName.charAt(0);
			String varName = argName.substring(2);
			short index = (short) (i + 1);
//			syso("table" + table + "--name:" + named
//					+ "methodCol:" + field.name);
			// if (methodCol.sqlType.getTypeStr()[0].equals("rom.langs")) {
			// syso(methodCol.sqlType.getTypeStr()[0]);
			// }

			// sqlType = parser.getType(methodCol.sqlType.getTypeStr()[0]);
			// if(sqlType==null){
			// throw new
			// RuntimeException("Type:"+methodCol.sqlType.getTypeStr()[0]+" not found while parsing method:"+table+"."+named);
			// }
			switch (argType) {
			case 'a':
				if (varName.equals("self")) {
					ams[i] = new SelfMapper(index, canBeNull);
				} else if (varName.equals("host")) {
					ams[i] = new HostMapper(index, false);
				} else if (varName.equals("lang")) {
					ams[i] = new LangMapper(cf,index);
				} else if (varName.equals("contact")) {
					ams[i] = new ContactMapper(index, canBeNull);
				} else if (varName.equals("contactno")) {
					ams[i] = new ContactNoMapper(index, canBeNull);
				} else if (varName.equals("roles")) {
					ams[i] = new RolesMapper(index, canBeNull);
				} else if (varName.equals("remote_addr")) {
					ams[i] = new RemoteAddrMapper(index, canBeNull);
				} else if (varName.equals("iscont")) {
					ams[i] = new RemoteAddrMapper(index, canBeNull);
				}else if (varName.equals("sid")) {
					ams[i] = new SidMapper(index, canBeNull);
				}else {
					throw new SuException("",0,schema+"."+table+" Unknown argument prefix 'a' for :"+argName);
				}
				break;
			case 'h':
				String headerName = varName.replace('_', '-');
				ams[i] = new HeaderMapper(index, headerName,
						field.getTypeHolder(), canBeNull);
				break;
			case 'c':
				ams[i] = new CookieMapper(cf, index, varName, canBeNull);
				break;
			case 'p':
				ams[i] = new ParamMapper(index, varName, field.getTypeHolder(),
						canBeNull);
				break;
			case 'f':
				ams[i] = new FileMapper(index, varName, field.getTypeHolder(),
						canBeNull);
				hasFileParam=true;
				break;
			default:
				throw new SuException("",0,schema+"."+table+" Unknown argument prefix 'a' for :"+argName);
			}
		}
		argMappers = ams;
	}

	private void initHooks() {
		if(!Uygulama.tek().integrationTest)
			return;
		
		if (before != null) {
			try {
				@SuppressWarnings("unchecked")
				Class<BeforeHook> c = (Class<BeforeHook>) Class.forName(before);
				beforeHook = c.newInstance();
				beforeHook.initialize(this);
			} catch( ClassNotFoundException e){
				beforeHook = null;
				Sistem.printStackTrace(e);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		} else {
			beforeHook = null;
		}

		if (after != null) {
			try {
				@SuppressWarnings("unchecked")
				Class<AfterHook> c = (Class<AfterHook>) Class.forName(after);
				afterHook = c.newInstance();
				afterHook.initialize(this);
			} catch( ClassNotFoundException e){
				afterHook = null;
				Sistem.printStackTrace(e);
			} catch (InstantiationException | IllegalAccessException e) {
				Sistem.printStackTrace(e);
				throw new RuntimeException(e);
			} 
		} else {
			afterHook = null;
		}
	}

	private String makeCallProto(String schemaName, String tableName,
			String methodName) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(schemaName);
		sb.append(".");
		sb.append(tableName);
		sb.append("_");
		sb.append(methodName);
		sb.append("(");

		for (int i = 0; i < argMappers.length; i++) {
			if (i != 0)
				sb.append(",");
			sb.append("?");
//			sb.append(argMappers[i].callProto());
			
			String cnv = argMappers[i].getConversion();
			if (cnv != null) {
				sb.append("::");
				sb.append(cnv);
			}

		}
		sb.append(")");

		return sb.toString();
	}

	public String getCallProto() {
		return callProto;
	}

	public BeforeHook getBeforeHook() {
		return beforeHook;
	}

	public AfterHook getAfterHook() {
		return afterHook;
	}

//	public String[] retColumnNames(ResultSet rs) throws KnownError {
//		if (retColumnNames == null) {
//			ResultSetMetaData rmd = rs.getMetaData();
//			retColumnNames = new String[rmd.getColumnCount()];
//			retColumnTypes = new int[rmd.getColumnCount()];
//			for (int i = 0; i < retColumnNames.length; i++) {
//				retColumnNames[i] = rmd.getColumnName(i + 1);
//				retColumnTypes[i] = rmd.getColumnType(i + 1);
//			}
//		}
//		return retColumnNames;
//	}

	public boolean isColumnBoolean(int i) {
		return this.retColumnTypes[i] == Types.BOOLEAN;
	}

	public boolean isColumnInteger(int i) {
		return this.retColumnTypes[i] == Types.SMALLINT
				|| this.retColumnTypes[i] == Types.INTEGER
				|| this.retColumnTypes[i] == Types.BIGINT;
	}

	public boolean isColumnDouble(int i) {
		return this.retColumnTypes[i] == Types.DOUBLE
				|| this.retColumnTypes[i] == Types.FLOAT;
	}

	public boolean equals(Object object) {
		if (object == null
				|| !(object instanceof com.bilgidoku.rom.pg.sqlunit.model.Method)) {
			return false;
		}
		Method m = (Method) object;
		if (m.schema.equals(this.schema)
				&& m.table.equals(this.table)
				&& m.named.equals(this.named)
				&& m.roles.equals(this.roles)
				&& m.http.equals(this.http)
				&& m.isRetSet == this.isRetSet
				&& m.getRetType().equals(this.getRetType())
				&& ((m.before != null && m.before.equals(this.before)) || (m.before == null && this.before == null))
				&& ((m.after != null && m.after.equals(this.after)) || (m.after == null && this.after == null))
				&& m.args.equals(this.args) && m.forObject == this.forObject) {
			return true;
		}
		return false;
	}

	public String toString() {
		return "Method:" + schema + "." + table + "." + named + args
				+ " forObject:" + forObject + " http:" + http + " roles:"
				+ roles.toString() + " before:" + this.before + " after:"
				+ this.after + "retSet:"+isRetSet+" retType:"+retType.toString();
	}

	public String getSchema() {
		return schema;
	}

	public String getTable() {
		return table;
	}

	public String getNamed() {
		return named;
	}

	public void addRole(String role) {
		this.roles.add(role);
	}

	public Set<String> getRoles() {
		return roles;
	}

	public String getHttp() {
		return http;
	}

	public void setHttp(String http) {
		this.http = http;
	}

	@Override
	public boolean isSql() {
		return true;
	}

	public boolean isForObject() {
		return forObject;
	}

	public void setForObject(boolean forObject) {
		this.forObject = forObject;
	}

	public void setBefore(String trim) {
		this.before = trim;
	}

	public void setAfter(String trim) {
		this.after = trim;
	}

	public String getAfter() {
		return after;
	}

	public String getBefore() {
		return before;
	}

	@Override
	public RomComp getComp() {
		return new RomComp("function", this.schema, table
				+ "_" + named, this.getVersion());
	}

	@Override
	public List<Command> upgrade(Integer dbVer) {
		List<Command> upgradeCmds = super.upgrade(dbVer);
		for (Command command : getCommands()) {
			Command c = command.clone();
			c.setCommand(c.getCommand().replaceAll("create function",
					"create or replace function"));
			upgradeCmds.add(c);
			
//			Command c=command.clone();
//			StringBuilder sb=new StringBuilder();
//			sb.append("select dropfunction('");
//			sb.append(schema);
//			sb.append("','");
//			sb.append(this.table);
//			sb.append("_");
//			sb.append(this.named);
//			sb.append("');");
//			
//			c.setCommand(c.getCommand().replaceAll("create function", "create or replace function"));
//			
//			upgradeCmds.add(new Command(sb.toString(), c.getLineNumber()));
//			upgradeCmds.add(c);
		}
		return upgradeCmds;
	}

	public void addArg(Field ma) {
		getArgList().add(ma);
	}

	public List<Field> getArgList() {
		return argList;
	}

	public ArgMapper[] getArgMappers() {
		return argMappers;
	}



	// public boolean getRetset() {
	// return isRetSet;
	// }

	public void setRetSet(boolean isRetSet) {
		this.isRetSet = isRetSet;
	}

	// //////////////////////////////////////////////////////////////////////////////

	public String getNameJavaFormFirstUpper() {
		return capitalize(getNameJavaForm());
	}


	public static String capitalize(String s) {
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public List<Field> getArgs() {
		List<Field> params = new ArrayList<Field>();
		for (Field arg : argList) {
			if (!arg.name.startsWith("p_")) {
				continue;
			}
			String cut = arg.normalName();
			params.add(new Field(cut, arg.getTypeHolder()));
		}

		if (!isBreed()) {
			params.add(new Field("self", new TextType(), false, 0,false, null));
		}

		return params;
	}


	public List<CGAtt> getServerArgs() {
		List<CGAtt> params = new ArrayList<CGAtt>();
		for (Field arg : argList) {
			params.add(arg);
		}
		return params;
	}

	


	public String getHttpUp() {
		return this.http.toUpperCase();
	}
	
	public boolean isHasFileParam() {
		return hasFileParam;
	}

	public void setHasFileParam(boolean hasFileParam) {
		this.hasFileParam = hasFileParam;
	}

	public TypeHolder getRetType() {
		return retType;
	}

	public void setRetType(TypeHolder retType) {
		this.retType = retType;
	}
	
	public boolean isVoid(){
		return this.retType.getSqlType()==VoidType.one;
	}

	public boolean returnsPrimitive() {
		return this.retType.isPrimitive();
	}

////////////////////////////////////////////////////////
	
	public String getNameJavaForm() {
		if (named.equals("new"))
			return "neww";
		return named;
	}
	
	public boolean getHasArgs() {
		return this.getArgs().size() != 0;
	}

	public boolean isBreed() {
		return named.equals("breed");
	}

	public String getSchemaName() {
		return schema;
	}

	public String getTableName() {
		return table;
	}
	
	public boolean getHasParams() {
		return this.getParams().size() != 0;
	}
	

	public int getParamCount() {
		return this.getParams().size();
	}

	public List<CGAtt> getParams() {
		List<CGAtt> params = new ArrayList<CGAtt>();
		for (Field arg : argList) {
			if (!arg.name.startsWith("p_")) {
				continue;
			}
			String cut = arg.normalName();
			params.add(new Field(cut, arg.getTypeHolder()));
		}

		if (isBreed()) {
			params.add(new Field("schema", new TextType(), false, 0,false, null));
			params.add(new Field("table", new TextType(), false, 0,false, null));
		}

		return params;
	}
	public boolean getFormPosting() {
		return this.http.equalsIgnoreCase("post");
	}

	public boolean getFormDeleting() {
		return this.http.equalsIgnoreCase("delete");
	}
	
	public String getUriPostfix() {
		if (named.equals("new") || named.equals("breed")) {
			return "/new.rom";
		} else if (named.equals("list")) {
			return "";
		} else if (named.equals("change")) {
			return "";
		} else if (named.equals("destroy")) {
			return "";
		} else if (named.equals("get")) {
			return "";
		}
		return "/" + named + ".rom";
	}

	public boolean isRetset() {
		return isRetSet;
	}

	public void setRetFile(boolean b) {
		this.retFile=true;
	}
	public boolean isRetFile() {
		return retFile;
	}

	@Override
	public boolean isHook() {
		return this.before!=null || this.after!=null;
	}

	@Override
	public int getArgLen() {
		return argList.size();
	}

	@Override
	public String getClassName() {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public String getJavaMethodName() {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public boolean isReturnsStream() {
		return false;
	}

	@Override
	public boolean isReturnsVoid() {
		return false;
	}

	@Override
	public String getBeforeStr() {
		return before;
	}

	@Override
	public String getAfterStr() {
		return after;
	}

	@Override
	public boolean isReturnPrimitive() {
		return returnsPrimitive();
	}

	@Override
	public String getUri() {
		return tablePrefix+getUriPostfix();
	}
	
	public void setTablePrefix(String prefix){
		this.tablePrefix=prefix;
	}

	@Override
	public String getCapTableName() {
		return capitalize(table);
	}
	
	public String getHsc() {
		return hsc;
	}

	public void setHsc(String hsc) {
		this.hsc = hsc;
	}

	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	@Override
	public String getPrefix() {
		return tablePrefix;
	}

	@Override
	public String getRoleStr() {
		if(roles==null||roles.size()==0)
			return "";
		StringBuilder sb=new StringBuilder();
		boolean virgul=false;
		for (String it : roles) {
			if(virgul)
				sb.append(",");
			else
				virgul=true;
			sb.append("\"");
			sb.append(it);
			sb.append("\"");
		}
		return sb.toString();
	}

	@Override
	public boolean isService() {
		return false;
	}

	public void setAudits(String[] as) {
		this.auditParams=as;
//		if(auditParams==null){
//			com.bilgidoku.rom.gunluk.Sistem.outln("null");
//		}
	}
	
	public boolean getAudit(){
		return auditParams!=null;
	}
	
	public String[] getAuditparams(){
		return auditParams;
	}
	
	public String getAuditname(){
		return this.schema+"."+this.table+"."+named;
	}
	
	public boolean getHasAuditParam(){
		if(auditParams==null)
			return false;
		return true;
	}
	
	public String getAuditparamnames(){
		if(auditParams==null || auditParams.length==0){
			return null;
		}
		
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<auditParams.length; i++){
			if(i!=0)
				sb.append(",");
			sb.append('"');
			sb.append(auditParams[i].substring(2));
			sb.append('"');
		}
		return sb.toString();
	}

	public void setAccesslevel(String string) {
		this.accesslevel=string;
	}

	public String getAccesslevel() {
		return this.accesslevel;
	}

	public String getInherit() {
		return inherit;
	}

	public void setInherit(String inherit) {
		this.inherit = inherit;
	}

	public Long getCache() {
		return cache;
	}

	public void setCache(Long cache) {
		this.cache = cache;
	}
	
	public String[] getMenu() {
		return menu;
	}

	public void setMenu(String[] menu) {
		this.menu = menu;
	}

	public void setCpu(int cpu) {
		this.cpu=cpu;
	}
	public int getCpu(){
		return this.cpu;
	}

	@Override
	public String getReturnMime() {
		return null;
	}

	@Override
	public boolean isReturnJust() {
		return false;
	}
	
	

	public List<Command> genAbstract() {
		StringBuilder sb=new StringBuilder();
		sb.append("select dropfunction('");
		sb.append(this.schema);
		sb.append("','");
		sb.append(named);
		sb.append("');");
		
		List clone = (List) ((LinkedList) getCommands()).clone();
		clone.add(0, new Command(sb.toString(),0));
		
		return clone;
	}
}
