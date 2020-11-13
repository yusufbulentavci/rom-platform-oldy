package com.bilgidoku.rom.pg.sqlunit.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.rom.RomComp;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.CGAtt;
import com.bilgidoku.rom.pg.dict.CGMethod;
import com.bilgidoku.rom.pg.dict.CGType;
import com.bilgidoku.rom.pg.dict.Net;
import com.bilgidoku.rom.pg.dict.types.TypeAdapter;
import com.bilgidoku.rom.min.Sistem;

public class Table extends TypeComp implements CGType {

	final private String schema;
	final private String table;
	private boolean ownerSuper = false;
	private Set<String> roles = new HashSet<String>();
	public Map<String, Method> methods = new HashMap<String, Method>();
	private final Table inheritType;
	private String prefix = null;
	private boolean subContainer = false;
	private boolean one = false;
	private Net net = Net.INTRA;
	private final Set<String> pks;
	private Long cache;

	private List<Integer> ozellikler=new ArrayList<>();
	private boolean ozellik=false;

	public Table(SqlUnit su, String schema, String table, boolean unitTest, int lineNo, List<Field> tableFields,
			int dims, TypeAdapter inheritType, Set<String> pks) {
		super(su, true, unitTest, lineNo, tableFields, schema, table, dims);
		this.schema = schema;
		if (table.startsWith(schema + ".")) {
			this.table = table.substring((schema + ".").length());
		} else {
			this.table = table;
		}
		if (inheritType != null && !(inheritType instanceof Table))
			throw new RuntimeException("Inherited type:" + inheritType + " should be table but not in table " + table);
		this.inheritType = (Table) inheritType;
		this.pks = pks;
		this.tbl=true;
	}

	public boolean isInheritsResource() {
		if (inheritType == null)
			return false;

		if (inheritType.schema == null)
			return false;
		
		if (inheritType.schema.equals("rom") && inheritType.table.equals("resources"))
			return true;
		
		if (inheritType.schema.equals("site") && inheritType.table.equals("contents"))
			return true;
		
		return false;
	}

	List<Field> generatedFields = null;
	private boolean checkvisible = false;


	public List<Field> getAllFields() {
		if (generatedFields != null)
			return generatedFields;
		final List<Field> allFields = new ArrayList<Field>();
		if (inheritType != null) {
			for (Field field : this.inheritType.getAllFields()) {
				field = (Field) field.clone();
				if (pks.contains(field.name)) {
					field.pk = true;
				}
				allFields.add((Field) field.clone());
			}
		}
		for (Field field : fields) {
			field = (Field) field.clone();
			if (pks.contains(field.name)) {
				field.pk = true;
			}
			allFields.add((Field) field.clone());
		}
		generatedFields = allFields;
		return allFields;
	}

	@Override
	public List<Command> run() {
		// TODO Auto-generated method stub
		return super.run();
	}

	public boolean equals(Object object) {
		if (object == null || !(object instanceof com.bilgidoku.rom.pg.sqlunit.model.Table)) {
			return false;
		}
		Table m = (Table) object;
		if (!m.schema.equals(this.schema) || !m.table.equals(this.table) || !m.ownerSuper == this.ownerSuper
				|| !m.roles.equals(this.roles) || m.dims != dims) {
			return false;
		}
		if (m.fields.size() != this.fields.size())
			return false;
		for (int i = 0; i < m.fields.size(); i++) {
			Field mf = m.fields.get(i);
			Field f = fields.get(i);
			if (!mf.equals(f))
				return false;
		}
		if (!this.pks.equals(m.pks))
			return false;
		return true;
	}

//	public String toString() {
//		
//		return "Table:" + schema + "." + table + " owner:" + ownerSuper + " roles:" + roles.toString();
//	}

	public String getSchema() {
		return schema;
	}

	@Override
	public String toString() {
		return "Table [schema=" + schema + ", table=" + table + ", ownerSuper=" + ownerSuper + ", roles=" + roles
				+ ", methods=" + methods + ", inheritType=" + inheritType + ", prefix=" + prefix + ", subContainer="
				+ subContainer + ", one=" + one + ", net=" + net + ", pks=" + pks + ", cache=" + cache
				+ ", generatedFields=" + generatedFields + ", checkvisible=" + checkvisible + ",clp="+this.clp + "]";
	}

	public String getTable() {
		return table;
	}

	public boolean isOwner() {
		return ownerSuper;
	}

	public void setOwner(boolean isOwner) {
		this.ownerSuper = isOwner;
	}

	public void addRole(String role) {
		this.roles.add(role);
	}

	public Set<String> getRoles() {
		return roles;
	}

	@Override
	public boolean isSql() {
		return true;
	}

	public String getPrefix() {
		if (prefix == null) {
			return "/_/" + table;
		}
		return prefix;
	}

	@Override
	public RomComp getComp() {
		return new RomComp("table", this.schema, table, this.getVersion());
	}

	public void addMethod(Method method) {
		if (methods.get(method.named) != null)
			throw new RuntimeException("Method already exists;" + method.named + " line:" + method.getLineNo());
		methods.put(method.named, method);
		for (String r : roles) {
			method.addRole(r);
		}
	}

	public Method getMethod(String name) {
		Method s = methods.get(name);
		if (s == null)
			throw new RuntimeException("Method " + name + " not exists");
		return s;
	}

	public Collection<Method> getMethods() {
		return methods.values();
	}

	// ////////////////////////////////

	public static String capitalize(String s) {
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public TypeAdapter getInheritType() {
		return inheritType;
	}

	public boolean isSubContainer() {
		return subContainer;
	}

	public boolean isHsc() {
		return subContainer;
	}

	public void setSubContainer(boolean subContainer) {
		this.subContainer = subContainer;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setOne(boolean b) {
		this.one = b;
	}

	public boolean isOne() {
		return one;
	}

	@Override
	public Object[] getArrayOf(int size) {
		return new String[size];
	}

	@Override
	public String getSqlName() {
		return schema + "." + name;
	}

	@Override
	public boolean needSqlConversion() {
		return true;
	}

	public Field getFieldFromAll(String named) {
		List<Field> a = getAllFields();
		for (Field field : a) {
			if (field.name.equals(named))
				return field;
		}
		return null;
	}

	@Override
	public boolean isToQuote() {
		return true;
	}

	@Override
	public boolean needCast() {
		return true;
	}

	@Override
	public Object getValue(DbThree db3) throws KnownError {
		StringBuilder sb = new StringBuilder();
		JSONWriter jsonWriter = new JSONWriter(sb);
		try {
			jsonWriter.object();
			for (Field field : getAllFields()) {
				jsonWriter.key(field.name);
				field.getTypeHolder().writeJson(jsonWriter, db3);
			}
			jsonWriter.endObject();
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

	@Override
	public Object[] getArrayValue(DbThree db3) throws KnownError {
		throw new RuntimeException("Array of table not supported yet");
	}

	@Override
	public void writeValue(DbThree db3, JSONWriter jsonWriter) throws KnownError, JSONException {
		jsonWriter.object();
		for (Field field : getAllFields()) {
			jsonWriter.key(field.name);
			field.getTypeHolder().writeJson(jsonWriter, db3);
		}
		jsonWriter.endObject();
	}

	@Override
	public void writeArrayValue(DbThree db3, JSONWriter writer) throws KnownError, JSONException {
		throw new RuntimeException("Array of table not supported yet");
	}

	public String getNameJavaForm() {
		return name;
	}

	// public List<Field> getFields() {
	// try {
	// if (inheritType == null) {
	// List<Field> f = new ArrayList<Field>();
	// for (Field field : fields) {
	// if (!field.name.equals("host_id")) {
	// Field ff = (Field) field.clone();
	// if (pks.contains(ff.name))
	// ff.pk = true;
	// f.add(ff);
	// }
	// }
	// return f;
	// }
	// List<Field> inherited = inheritType.getFields();
	// List<Field> f = new ArrayList<Field>();
	// f.addAll(inherited);
	// for (Field field : fields) {
	// if (!field.name.equals("host_id")) {
	// Field ff = (Field) field.clone();
	// if (pks.contains(ff.name))
	// ff.pk = true;
	// f.add(ff);
	// }
	// }
	// return f;
	// } catch (Exception e) {
	// Sistem.printStackTrace(e);
	// throw e;
	// }
	// }
	//
	public List<Field> getFields() {
		try {
			if (inheritType == null) {
				List<Field> f = new ArrayList<Field>();
				for (Field field : fields) {
					Field ff = (Field) field.clone();
					if (pks.contains(ff.name))
						ff.pk = true;
					f.add(ff);
				}
				return f;
			}
			List<Field> inherited = inheritType.getFields();
			List<Field> f = new ArrayList<Field>();
			f.addAll(inherited);
			for (Field field : fields) {
				Field ff = (Field) field.clone();
				if (pks.contains(ff.name))
					ff.pk = true;
				f.add(ff);
			}
			return f;
		} catch (Exception e) {
			Sistem.printStackTrace(e);
			throw e;
		}
	}

	// /////////////////////
	public String getNameFirstUpper() {
		return capitalize(getNameJavaForm());
	}

	public boolean getNoUri() {
		for (Field at : getFields()) {
			if (at.name.equals("uri")) {
				return false;
			}
		}
		return true;
	}

	public List<CGAtt> getAtts() {
		// com.bilgidoku.rom.gunluk.Sistem.errln("Looked for " +
		// getFields().size());
		List<CGAtt> myats = new ArrayList<CGAtt>();
		for (CGAtt cgAtt : getFields()) {
			myats.add(cgAtt);
		}
		return myats;
	}

	@Override
	public Object fromString(String str) throws ParseException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void writeJson(JSONWriter writer, Object value) throws JSONException {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isJavaType() {
		return false;
	}

	public void setNet(String hm) {
		this.net = Net.getEnum(hm);
	}

	public Net getNet() {
		return net;
	}

	public Collection<CGMethod> getDaomethods() {
		List<CGMethod> daomethods = new ArrayList<CGMethod>();
		for (Method method : methods.values()) {
			daomethods.add(method);
		}
		return daomethods;
	}

	@Override
	public Collection<CGMethod> getClientDaomethods() {
		List<CGMethod> daomethods = new ArrayList<CGMethod>();
		for (Method method : methods.values()) {
			if (!method.isHasFileParam() && !method.isRetFile())
				daomethods.add(method);
		}
		return daomethods;
	}

	@Override
	public boolean isPrimitive() {
		return false;
	}

	@Override
	public String callProtoPart() {
		StringBuilder sb = new StringBuilder();
		sb.append("row(");
		List<Field> af = getAllFields();
		for (Field field : af) {
			sb.append(field.getTypeHolder().callProtoPart());
		}
		sb.append(")::");
		sb.append(getSqlName());

		return sb.toString();
	}

	public String getInheritName() {
		return inheritType == null ? null : inheritType.getDbName();
	}

	public Long getCache() {
		return cache;
	}

	public void setCache(Long cache) {
		this.cache = cache;
	}

	public void addOzellik(Integer r) {
		ozellikler.add(r);
	}

	public void setOzellik(boolean b) {
		this.ozellik=true;
	}
}
