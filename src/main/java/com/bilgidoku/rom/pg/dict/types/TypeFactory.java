package com.bilgidoku.rom.pg.dict.types;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeFactory {
	public static TypeFactory one = new TypeFactory();

	public static void reset() {
		one = new TypeFactory();
	}

	private final Map<String, TypeAdapter> sqlTypeTable = new HashMap<String, TypeAdapter>();
	private final Map<String, TypeAdapter> javaTypeTable = new HashMap<String, TypeAdapter>();

	private TypeAdapter[] types = { new BoolType(), new Int8Type(), new Int4Type(), new Int2Type(), new TimeType(),
			new DateType(), new TimestampType(), new Float4Type(), new Float8Type(), new TextType(),
			new IntervalType(), new DateRangeType(), new MoneyType(), new TsvectorType(), new HstoreType(),
			new JsonType(), new VoidType() };

	public TypeFactory() {
		for (TypeAdapter it : types) {
			for (String typeStr : it.getSqlNames()) {
				sqlTypeTable.put(typeStr, it);
			}
		}

		for (TypeAdapter it : types) {
			if (it.isJavaType())
				javaTypeTable.put(it.getJavaType(), it);
		}
	}

	public TypeAdapter getSqlType(String name) {
		TypeAdapter ty = sqlTypeTable.get(name);
		if (ty == null)
			return null;
		return ty;
	}

	public TypeAdapter getJavaType(String name) {
		TypeAdapter ty = javaTypeTable.get(name);
		if (ty == null)
			return null;
		return ty;
	}

	public TypeAdapter getTypeNN(String name, int dim) {
		TypeAdapter t = sqlTypeTable.get(name);
		if (t == null)
			throw new RuntimeException("Sql type not found for name:" + name);
		return t;
	}

	public TypeHolder getTypeHolder(Class c, java.lang.reflect.Type gen) {
		String tn = c.getSimpleName();
		String canon = c.getCanonicalName();
		int dim = 0;
		if (c.isArray()) {
			if (!tn.endsWith("[]")) {
				throw new RuntimeException(tn + " should be ended with []");
			}
			tn = tn.substring(0, tn.length() - 2);
			canon = canon.substring(0, canon.length() - 2);
			dim = 1;
		}
		TypeHolder from = null, to = null;
		if (gen != null && gen instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) gen;
			if (!(pt.getRawType() instanceof Class) || ((Class) pt.getRawType()) != java.util.Map.class) {
				throw new RuntimeException("Unknown parameterized type for type factory:" + pt.getRawType().toString()
						+ " of " + c);
			}

			if (pt.getActualTypeArguments() == null || pt.getActualTypeArguments().length != 2) {
				throw new RuntimeException("Type params not specified of" + c);
			}
			Class tfrom = (Class) pt.getActualTypeArguments()[0];
			from = getTypeHolder(tfrom, null);
			Class tto = (Class) pt.getActualTypeArguments()[1];
			//			syso(tto.getSimpleName());
			to = getTypeHolder(tfrom, null);
			return new TypeHolder(dim, to);
		}

		TypeAdapter ta = TypeFactory.one.getJavaType(tn);
		if (ta == null) {
			Class toCheck = c;
			if (c.isArray()) {
				try {
					toCheck = Class.forName(canon);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
			if (!romJavaType(toCheck)) {
				throw new RuntimeException("Is not extends Transfer:" + toCheck.getCanonicalName());
			}
			//			syso(toCheck.getSimpleName());
			JavaType jt = new JavaType(toCheck);
			ta = jt;
			TypeFactory.one.registerJavaType(toCheck, (JavaType) ta);
			jt.setup();
		}
		return new TypeHolder(ta, dim);
	}

	private void registerJavaType(Class c, JavaType javaType) {
		this.javaTypeTable.put(c.getSimpleName(), javaType);
	}

	private boolean romJavaType(Class c) {
		for (Class it : c.getInterfaces()) {
			if (it.getName().equals("com.bilgidoku.rom.gwt.shared.Transfer")) {
				return true;
			}
		}
		return false;
	}

	public List<JavaType> getJavaComplexTypes() {
		List<JavaType> ret = new ArrayList<JavaType>();
		for (TypeAdapter ty : this.javaTypeTable.values()) {
			if (!(ty instanceof JavaType))
				continue;
			ret.add((JavaType) ty);
		}
		return ret;
	}
	
}
