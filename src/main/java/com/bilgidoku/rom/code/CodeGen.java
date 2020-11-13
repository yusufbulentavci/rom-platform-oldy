package com.bilgidoku.rom.code;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.misc.STMessage;

import com.bilgidoku.rom.ilk.gorevli.Dir;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.CGMethod;
import com.bilgidoku.rom.pg.dict.CGType;
import com.bilgidoku.rom.pg.dict.types.JavaType;
import com.bilgidoku.rom.pg.dict.types.SrvInfo;
import com.bilgidoku.rom.pg.dict.types.TypeFactory;
import com.bilgidoku.rom.pg.sqlunit.RomDb;
import com.bilgidoku.rom.pg.sqlunit.SqlUnitGorevlisi;
import com.bilgidoku.rom.pg.sqlunit.SuException;
import com.bilgidoku.rom.pg.sqlunit.model.Field;
import com.bilgidoku.rom.pg.sqlunit.model.Method;
import com.bilgidoku.rom.pg.sqlunit.model.Schema;
import com.bilgidoku.rom.pg.sqlunit.model.Table;
import com.bilgidoku.rom.pg.sqlunit.model.Type;
import com.bilgidoku.rom.shared.err.KnownError;

/*

*/
public class CodeGen {

	class ErrorListener implements STErrorListener {

		@Override
		public void runTimeError(STMessage msg) {
			Sistem.outln(msg.toString());
		}

		@Override
		public void internalError(STMessage msg) {
			Sistem.outln(msg.toString());
		}

		@Override
		public void compileTimeError(STMessage msg) {
			Sistem.outln(msg.toString());
		}

		@Override
		public void IOError(STMessage msg) {
			Sistem.outln(msg.toString());
		}
	}

//	private static final String HOMEDIR = "/home/rompg/backup/rom-22/phase8/java/rom/target/generated-sources/java";

	private static final String HOMEDIR = "/home/rompg/rom/phase8/java/rom/src/main/java";

	final List<Class> httpCallClasses;

//	httpCallClasses = { com.bilgidoku.rom.app.session.GuestServiceImpl.class,
//			com.bilgidoku.rom.app.session.AuthorizeServiceImpl.class,
//			com.bilgidoku.rom.app.content.LocalFileServiceImpl.class,
//			com.bilgidoku.rom.app.content.PublicFileServiceImpl.class,
//			com.bilgidoku.rom.app.content.SiteFileServiceImpl.class,
//			com.bilgidoku.rom.app.session.SessionFuncsServiceImpl.class,
//			com.bilgidoku.rom.richweb.RichWebServiceImpl.class,
//			com.bilgidoku.rom.app.content.ReadyMediaServiceImpl.class,
//			com.bilgidoku.rom.welcome.WelcomeServiceImpl.class,
//			com.bilgidoku.rom.audit.AuditServiceImpl.class,
//			com.bilgidoku.rom.account.AccountServiceImpl.class
//			
//	};

	final List<Class> directCallClasses;
//	= { com.bilgidoku.rom.charging.PaypalServiceImpl.class,
//			com.bilgidoku.rom.app.content.DirectFileServiceImpl.class,
//			com.bilgidoku.rom.app.directmethods.DirectMethodsServiceImpl.class
//			};

	public CodeGen(List<Class> httpCallClass, List<Class> directCallClass) {
		STGroup.verbose = false;
		this.directCallClasses = directCallClass;
		this.httpCallClasses = httpCallClass;
	}

	String destinationDirectory;
	String clipsDirectory;
	boolean client;
	private RomDb romDb;

//	public void start() {
//
//		try {
//			SqlUnitGorevlisi.tek().load("tepeweb.ready");
//			romDb = SqlUnitGorevlisi.tek().getRomDb();
//			clpClass();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			Sistem.printStackTrace(e);
//			System.exit(0);
//		}
//	}

	public void start() {

		try {
			SqlUnitGorevlisi.tek().load("tepeweb.ready");

			romDb = SqlUnitGorevlisi.tek().getRomDbForCodeGen();

			Sistem.outln("==rom.gwt.client==");
			destinationDirectory = HOMEDIR + "/com/bilgidoku/rom/gwt/araci/client/";
			Dir.emptyDir(destinationDirectory);
			client = true;
			suexecute();
			servexec();

			Sistem.outln("==rom.gwt.server==");
			destinationDirectory = HOMEDIR + "/com/bilgidoku/rom/gwt/araci/server/";
			Dir.emptyDir(destinationDirectory);
			client = false;
			suexecute();
			servexec();

			dispatcher(false);
			uriwork();

			Sistem.outln("==rom.gwt.direct.client==");
			destinationDirectory = HOMEDIR + "/com/bilgidoku/rom/gwt/araci/direct/client/";
			Dir.emptyDir(destinationDirectory);
			client = true;
			// suexecute();
			directservexec();

			Sistem.outln("==rom.gwt.direct==");

			destinationDirectory = HOMEDIR + "/com/bilgidoku/rom/gwt/araci/direct/";
			Dir.emptyDir(destinationDirectory);
			client = false;
			// suexecute();
			directservexec();

			Sistem.outln("==DISPATCHER==");
			dispatcher(true);
			// uriwork();

//			String line = "chown -R avci:avci /home/avci/workspace/rom-site/src/main/java/com/bilgidoku/rom/gwt";
//			CommandLine cmdLine = CommandLine.parse(line);
//			DefaultExecutor executor = new DefaultExecutor();
//			int exitValue = executor.execute(cmdLine);

//			RunTime.one.terminate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Sistem.printStackTrace(e);
			System.exit(0);
		}
		Sistem.outln("==DONE==");
	}

//	public void clpClass() throws KnownError, IOException, SuException {
//		String dest = destinationDirectory;
//
//		LinkedList<String> requiredModules = new LinkedList<String>();
//		for (Schema schema : romDb.getSchemas()) {
//			if (schema.getSchema().equals("dict") || schema.getSchema().equals("central"))
//				continue;
//			requiredModules.add(schema.getSchema());
//		}
//		StringBuilder sb = new StringBuilder();
//		for (Schema schema : romDb.getSchemas()) {
//			Sistem.outln("+++Schema: " + schema.getSchema() + "");
//			String schemaDir = dest + schema.getSchema() + "/";
//			prepareDirectory(schemaDir);
//			new File(schemaDir).mkdirs();
//			for (Table table : schema.getTables()) {
//				Sistem.outln("++++++Clp " + table.getName() + "");
//
//				sb.append("(;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;\n");
//				sb.append("(;;;\n");
//				sb.append("(defclass ");
//				sb.append(table.getName());
//				sb.append('\n');
//				if (table.getInheritName() != null) {
//					sb.append("(is-a ");
//					sb.append(table.getInheritName());
//					sb.append('\n');
//				}
//
//				List<Field> fs = table.getClsFields();
//				for (Field f : fs) {
//					if(f.getTypeHolder().isArray()) {
//						sb.append("(multislot ");
//						sb.append(f.name);
//						sb.append(')');
//					}else {
//						sb.append("(slot ");
//						sb.append(f.name);
//						sb.append(')');
//					}
//					sb.append('\n');
//				}
//
//				
//				sb.append(");\n");
//			}
//
//			// writeFactory(type);
//		}
//		System.out.println(sb.toString());
//
//		// for (DevType type : DbAccess.get().getTypes().values()){
//		// if(!type.isToGenerate())
//		// continue;
//		// }
//	}

//	private static final String PREFIX = "META-INF/services/";

	public void servexec() throws IOException {
		String dest = destinationDirectory + "/service/";

		LinkedList<String> requiredModules = new LinkedList<String>();
		for (Schema schema : romDb.getSchemas()) {
			if (schema.getSchema().equals("dict") || schema.getSchema().equals("central"))
				continue;
			requiredModules.add(schema.getSchema());
		}
		CallResolver callResolver = new CallResolver();
		List<SrvInfo> csm = new ArrayList<SrvInfo>();
		for (Class c : httpCallClasses) {
			SrvInfo s = callResolver.resolve(c);
			csm.add(s);
		}
		Sistem.outln(dest);
		SiteUtil.deleteDirContent(new File(dest));
		new File(dest).mkdirs();
		for (SrvInfo serv : csm) {
			if (client) {
				if (serv.custom)
					continue;
				srvclientdao(serv, false);
			} else {
				if (serv.custom) {
					// List<String> la=new ArrayList<String>();
					// if(serv.roles!=null)
					// for (String string : serv.roles) {
					// la.add(string);
					// }
					// customServiceDao(serv, requiredModules, la);
					customServiceDao(serv, requiredModules, serv.roles, false);
				} else
					serviceDao(serv, requiredModules, serv.roles, false);
			}
		}
		for (JavaType type : TypeFactory.one.getJavaComplexTypes()) {
			Sistem.outln("++++++++JavaType " + type.getJavaType() + "");
			if (client) {
				writeTypeResponse(dest, "service", type, false);
				serverTypeClientCoder(dest, "service", type, false);
				continue;
			}

			serverTypeCoder(dest, "service", type, false);
		}
	}

	public void directservexec() throws IOException {
		TypeFactory.reset();
		String dest = destinationDirectory + "/service/";

		LinkedList<String> requiredModules = new LinkedList<String>();
		// for (Schema schema : romDb.getSchemas()) {
		// if (schema.getSchema().equals("dict"))
		// continue;
		// requiredModules.add(schema.getSchema());
		// }
		CallResolver callResolver = new CallResolver();
		List<SrvInfo> csm = new ArrayList<SrvInfo>();
		for (Class c : directCallClasses) {
			SrvInfo s = callResolver.resolve(c);
			csm.add(s);
		}
		Sistem.outln(dest);
		SiteUtil.deleteDirContent(new File(dest));
		new File(dest).mkdirs();
		for (SrvInfo serv : csm) {
			if (client) {
				if (serv.custom)
					continue;
				srvclientdao(serv, true);
			} else {
				if (serv.custom) {
					// List<String> la=new ArrayList<String>();
					// if(serv.roles!=null)
					// for (String string : serv.roles) {
					// la.add(string);
					// }
					// customServiceDao(serv, requiredModules, la);
					customServiceDao(serv, requiredModules, serv.roles, true);
				} else
					serviceDao(serv, requiredModules, serv.roles, true);
			}
		}
		for (JavaType type : TypeFactory.one.getJavaComplexTypes()) {
			Sistem.outln("++++++++JavaType " + type.getJavaType() + "");
			if (client) {
				writeTypeResponse(dest, "service", type, true);
				serverTypeClientCoder(dest, "service", type, true);
				continue;
			}

			serverTypeCoder(dest, "service", type, true);
		}
	}

	public void srvclientdao(SrvInfo serv, boolean direct) throws IOException {
		String dest = destinationDirectory + "/service/";
		String daoFile = dest + serv.getNameAsClass() + "Dao.java";
		Sistem.outln("Creating dao:" + daoFile);
		STGroup group = new STGroupDir("gwtTemplates", '%', '%');
		ST st = group.getInstanceOf("servicerender");
		for (CGMethod a : serv.getClientDaomethods()) {
			Sistem.outln(a.getNameJavaForm());
			Sistem.outln(a.getRetType().toString());
		}
		st.add("service", serv);
		st.add("direct", direct);
		int a = st.write(new File(daoFile), new ErrorListener());
	}

	public void suexecute() throws KnownError, IOException, SuException {
		String dest = destinationDirectory;

		LinkedList<String> requiredModules = new LinkedList<String>();
		for (Schema schema : romDb.getSchemas()) {
			if (schema.getSchema().equals("dict") || schema.getSchema().equals("central"))
				continue;
			requiredModules.add(schema.getSchema());
		}
		for (Schema schema : romDb.getSchemas()) {
			Sistem.outln("+++Schema: " + schema.getSchema() + "");
			String schemaDir = dest + schema.getSchema() + "/";
			prepareDirectory(schemaDir);
			new File(schemaDir).mkdirs();
			for (Table table : schema.getTables()) {
				Sistem.outln("++++++Table " + table.getName() + "");
				writeDao(schemaDir, table, requiredModules, false);
				writeTable(schemaDir, schema.getSchema(), table, requiredModules);
				writeTableResponse(schemaDir, schema.getSchema(), table);
				writeTableCoder(schemaDir, schema.getSchema(), table, requiredModules);
			}
			for (Type type : schema.getTypes()) {
				Sistem.outln("++++++++Type " + type.getName() + "");
				writeType(schemaDir, schema.getSchema(), type, requiredModules);
				writeTypeResponse(schemaDir, schema.getSchema(), type, false);
				writeTypeCoder(schemaDir, schema.getSchema(), type, requiredModules);
			}

			// writeFactory(type);
		}

		// for (DevType type : DbAccess.get().getTypes().values()){
		// if(!type.isToGenerate())
		// continue;
		// }
	}

	public void dispatcher(boolean direct) throws IOException {
		LinkedList<String> requiredModules = new LinkedList<String>();

		List<CGMethod> methods = new ArrayList<CGMethod>();
		List<SrvInfo> customServices = new ArrayList<SrvInfo>();
		if (direct) {
			CallResolver callResolver = new CallResolver();
			List<SrvInfo> csm = new ArrayList<SrvInfo>();
			for (Class c : directCallClasses) {
				SrvInfo s = callResolver.resolve(c);
				csm.add(s);
			}
			for (SrvInfo serv : csm) {
				if (serv.custom) {
					if (!serv.norom)
						customServices.add(serv);
				} else {
					methods.addAll(serv.getDaomethods());
				}
			}
		} else {
			for (Schema schema : romDb.getSchemas()) {
				if (schema.getSchema().equals("dict") || schema.getSchema().equals("central"))
					continue;
				requiredModules.add(schema.getSchema());
			}
			for (Schema schema : romDb.getSchemas()) {
				for (Table table : schema.getTables()) {
					methods.addAll(table.getMethods());
				}
			}

			CallResolver callResolver = new CallResolver();
			List<SrvInfo> csm = new ArrayList<SrvInfo>();
			for (Class c : httpCallClasses) {
				SrvInfo s = callResolver.resolve(c);
				csm.add(s);
			}
			for (SrvInfo serv : csm) {
				if (serv.custom) {
					if (!serv.norom)
						customServices.add(serv);
				} else {
					methods.addAll(serv.getDaomethods());
				}
			}
		}

		String dest = destinationDirectory + "/DispatchData.java";
		STGroup group = new STGroupDir("gwtTemplates", '%', '%');
		ST st = group.getInstanceOf("appdispatch");
		st.add("allmethods", methods);
		st.add("customserv", customServices);
		st.add("modules", requiredModules);
		st.add("direct", direct);

		int a = st.write(new File(dest), new ErrorListener());
	}

	public void uriwork() throws IOException {
		List<Table> tables = new ArrayList<Table>();
		for (Schema schema : romDb.getSchemas()) {
			for (Table table : schema.getTables()) {
				tables.add(table);
			}
		}

		CallResolver callResolver = new CallResolver();

		List<String> services = new ArrayList<String>();
		for (Class c : httpCallClasses) {
			SrvInfo s = callResolver.resolve(c);
			services.add(s.getUri());
		}

		String dest = destinationDirectory + "/UriWork.java";
		STGroup group = new STGroupDir("gwtTemplates", '%', '%');
		ST st = group.getInstanceOf("uriwork");
		st.add("tables", tables);
		st.add("services", services);

		int a = st.write(new File(dest), new ErrorListener());
	}

	private void writeTableResponse(String schemaDir, String schema, Table type) throws IOException {
		if (!client)
			return;
		String typeFile = schemaDir + "/" + type.getNameFirstUpper() + "Response.java";
		STGroup group = new STGroupDir("gwtTemplates", '%', '%');
		ST st = group.getInstanceOf("responserender");
		st.add("schema", schema);
		st.add("type", type);
		st.add("isclient", client);
		int a = st.write(new File(typeFile), new ErrorListener());
	}

	private void writeTypeResponse(String schemaDir, String schema, CGType type, boolean direct) throws IOException {
		if (!client)
			return;
		String typeFile = schemaDir + "/" + type.getNameFirstUpper() + "Response.java";
		STGroup group = new STGroupDir("gwtTemplates", '%', '%');
		ST st = group.getInstanceOf("responserender");
		st.add("schema", schema);
		st.add("type", type);
		st.add("isclient", client);
		st.add("direct", direct);
		int a = st.write(new File(typeFile), new ErrorListener());
	}

	//
	private void writeType(String schemaDir, String schema, CGType type, LinkedList<String> requiredModules)
			throws IOException {
		String typeFile = schemaDir + "/" + type.getNameFirstUpper() + ".java";
		STGroup group = new STGroupDir("gwtTemplates", '%', '%');
		ST st = group.getInstanceOf("typerender");
		st.add("schema", schema);
		st.add("type", type);
		st.add("isclient", client);
		st.add("modules", requiredModules);
		int a = st.write(new File(typeFile), new ErrorListener());
	}

	private void writeTable(String schemaDir, String schema, Table type, LinkedList<String> requiredModules)
			throws IOException {
		String typeFile = schemaDir + "/" + type.getNameFirstUpper() + ".java";
		STGroup group = new STGroupDir("gwtTemplates", '%', '%');
		ST st = group.getInstanceOf("typerender");
		st.add("schema", schema);
		st.add("type", type);
		st.add("isclient", client);
		st.add("modules", requiredModules);
		int a = st.write(new File(typeFile), new ErrorListener());
	}

	private void writeTableCoder(String schemaDir, String schema, Table type, LinkedList<String> requiredModules)
			throws IOException {
		String typeFile = schemaDir + "/" + type.getNameFirstUpper() + "Coder.java";
		STGroup group = new STGroupDir("gwtTemplates", '%', '%');

		ST st = group.getInstanceOf(client ? "factoryrender" : "dbcoder");
		st.add("schema", schema);
		st.add("type", type);
		st.add("modules", requiredModules);
		int a = st.write(new File(typeFile), new ErrorListener());
	}

	private void writeTypeCoder(String schemaDir, String schema, CGType type, LinkedList<String> requiredModules)
			throws IOException {
		String typeFile = schemaDir + "/" + type.getNameFirstUpper() + "Coder.java";
		STGroup group = new STGroupDir("gwtTemplates", '%', '%');
		ST st = group.getInstanceOf(client ? "factoryrender" : "dbcoder");
		st.add("schema", schema);
		st.add("type", type);
		st.add("modules", requiredModules);
		int a = st.write(new File(typeFile), new ErrorListener());
	}

	private void serverTypeCoder(String schemaDir, String schema, CGType type, boolean direct) throws IOException {
		String typeFile = schemaDir + "/" + type.getNameFirstUpper() + "Coder.java";
		STGroup group = new STGroupDir("gwtTemplates", '%', '%');
		ST st = group.getInstanceOf("servertypecoder");
		st.add("schema", schema);
		st.add("type", type);
		st.add("direct", direct);
		int a = st.write(new File(typeFile), new ErrorListener());
	}

	private void serverTypeClientCoder(String schemaDir, String schema, CGType type, boolean direct)
			throws IOException {
		String typeFile = schemaDir + "/" + type.getNameFirstUpper() + "Coder.java";
		STGroup group = new STGroupDir("gwtTemplates", '%', '%');
		ST st = group.getInstanceOf("servertypeclientcoder");
		st.add("schema", schema);
		st.add("type", type);
		st.add("direct", direct);
		int a = st.write(new File(typeFile), new ErrorListener());
	}

	private void customServiceDao(SrvInfo serv, LinkedList<String> requiredModules, String[] roles, boolean direct)
			throws IOException {
		String dest = destinationDirectory + "/service/";
		String daoFile = dest + serv.getNameAsClass() + ".java";
		Sistem.outln("Creating dao:" + daoFile);
		STGroup group = new STGroupDir("gwtTemplates", '%', '%');
		ST st = group.getInstanceOf("customserverdao");
		st.add("table", serv);
		st.add("modules", requiredModules);
		st.add("roles", roles);
		st.add("direct", direct);
		st.write(new File(daoFile), new ErrorListener());
	}

	private void serviceDao(SrvInfo serv, LinkedList<String> requiredModules, String[] roles, boolean direct)
			throws IOException {
		String dest = destinationDirectory + "/service/";
		for (CGMethod a : serv.getDaomethods()) {
			String daoFile = dest + serv.getNameAsClass() + "_" + a.getNameJavaForm() + ".java";
			Sistem.outln("Creating dao:" + daoFile);
			STGroup group = new STGroupDir("gwtTemplates", '%', '%');
			ST st = group.getInstanceOf("serverdao");
			Sistem.outln(a.getNameJavaForm());

			try {
//				com.bilgidoku.rom.gunluk.Sistem.outln(a.getRetType().toString());
				st.add("table", serv);
				st.add("method", a);
				st.add("modules", requiredModules);
				st.add("roles", roles);
				st.add("direct", direct);
				st.write(new File(daoFile), new ErrorListener());
			} catch (NullPointerException e) {
				Sistem.printStackTrace(e);
			}
		}
	}

	private void writeDao(String schemaDir, Table table, List<String> requiredModules, boolean isService)
			throws IOException {
		if (client) {
			String daoFile = schemaDir + "/" + table.getNameFirstUpper() + "Dao.java";
			Sistem.outln("Creating dao:" + daoFile);
			STGroup group = new STGroupDir("gwtTemplates", '%', '%');
			ST st = group.getInstanceOf("daorender");
			st.add("schema", table.getSchema());
			st.add("table", table);
			st.add("modules", requiredModules);
			st.add("isclient", client);
			int a = st.write(new File(daoFile), new ErrorListener());
			return;
		} else {
			Collection<Method> ms = table.getMethods();
			for (Method method : ms) {
				String daoFile = schemaDir + "/" + table.getNameFirstUpper() + "_" + method.getNameJavaForm() + ".java";
				Sistem.outln("Creating dao:" + daoFile);
				STGroup group = new STGroupDir("gwtTemplates", '%', '%');
				ST st = group.getInstanceOf("dbdao");
				st.add("schema", table.getSchema());
				st.add("table", table);
				st.add("modules", requiredModules);
				st.add("method", method);
				int a = st.write(new File(daoFile), new ErrorListener());
			}
		}
	}

	private void prepareDirectory(String schemaDir) throws IOException {
		File destination = new File(schemaDir);
		if (destination.exists()) {
			Sistem.outln("Clearing destination source directory");
			for (File c : destination.listFiles()) {
				if (c.getName().endsWith("java")) {
					c.delete();
				}
			}
		} else {
			Sistem.outln("Creating source directory");
			destination.mkdirs();
		}
	}
	//
	// void delete(File f) throws IOException {
	// if (f.isDirectory()) {
	// for (File c : f.listFiles()){
	// }
	// }
	// // if (!f.delete())
	// // throw new FileNotFoundException("Failed to delete file: " + f);
	// }

}
