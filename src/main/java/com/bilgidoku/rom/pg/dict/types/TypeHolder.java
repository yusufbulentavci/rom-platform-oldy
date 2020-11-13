package com.bilgidoku.rom.pg.dict.types;

import java.text.ParseException;
import java.util.Map;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.json.JSONWriter;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.min.Sistem;

public class TypeHolder {
	final int dim;
	final TypeAdapter sqlType;
	private TypeHolder mapTo = null;
	private Class<?> internalClass;

	public String toString() {
		return sqlType.toString() + " Dim:" + dim;
	}

	public boolean isToQuote() {
		if (dim > 0 || isMap())
			return true;
		return sqlType.isToQuote();
	}

	public boolean needCast() {
		if (dim > 0 || isMap())
			return true;
		return sqlType.needCast();
	}

	public int getDim() {
		return dim;
	}

	public TypeAdapter getSqlType() {
		return sqlType;
	}

	public TypeHolder(TypeAdapter sqlType, int dim) {
		this.dim = dim;
		this.sqlType = sqlType;
		if (sqlType == null) {
			throw new RuntimeException("Type is null");
		}
	}

	public TypeHolder(int dim2, TypeHolder to) {
		this.dim = dim2;
		this.sqlType = null;
		this.mapTo = to;
	}

	public TypeHolder(Class<?> pt) {
		this.sqlType = new InternalClass(pt);
		this.dim = 0;
	}

	public void setValue(DbThree three, String obj) throws KnownError,
			JSONException, ParseException {
		if (isArray()) {
			if (obj == null) {
				three.setNull(java.sql.Types.ARRAY);
				return;
			}
			JSONArray myarray = new JSONArray(obj);
			Object[] objs = sqlType.getArrayOf(myarray.length());
			for (int i = 0; i < objs.length; i++) {
				objs[i] = myarray.get(i).toString();
			}
			three.setArray(sqlType.getSqlName(), objs);
			return;
		}
		sqlType.setValue(three, obj);
	}

	public String getJavaType() {

		try {

			if (isMap()) {
				return "java.util.Map<String," + mapTo.getJavaType() + ">";
			}

			if (dim == 0)
				return sqlType.getJavaType();
			if (dim == 1)
				return sqlType.getJavaType() + "[]";
			return sqlType.getJavaType() + "[][]";
		} catch (Exception e) {
			Sistem.printStackTrace(e);
			throw e;
		}
	}

	public boolean isArray() {
		return dim != 0;
	}

	public boolean hasJdbcSqlConversion() {
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TypeHolder))
			return false;
		TypeHolder sth = (TypeHolder) obj;

		if (isMap()) {
			if (!sth.isMap()) {
				return false;
			}
			return mapTo.equals(sth.mapTo);
		}

		return sth.sqlType.equals(sqlType) && sth.dim == dim;
	}

	public String getJavaTypeName() {
		return sqlType.getJavaType();
	}

	public String getSimpleJavaTypeName() {
		if (isMap()) {
			return this.mapTo.getSimpleJavaTypeName();
		}
		String ret = getJavaTypeName();
		if (ret.indexOf('.') < 0) {
			return ret;
		}
		return ret.substring(ret.lastIndexOf('.') + 1);
	}

	public String getSqlDefinition() {
		if(isMap()){
			if (dim == 0)
				return this.mapTo.getSqlDefinition();
			if (dim == 1)
				return this.mapTo.getSqlDefinition()+ "[]";
		}
		
		if (dim == 0)
			return sqlType.getSqlName();
		if (dim == 1)
			return sqlType.getSqlName() + "[]";
		return sqlType.getSqlName() + "[][]";
	}

	public static String capitalize(String s) {
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	@Override
	public Object clone() {
		if(isMap()){
			return new TypeHolder(dim, mapTo);
		}
		return new TypeHolder(sqlType, dim);
	}

	public void writeJson(JSONWriter writer, DbThree db3) throws JSONException,
			KnownError {
		if(isArray() && isMap()){
			String[] s=(String[]) mapTo.getSqlType().getArrayValue(db3);
			if(s==null){
				writer.value(null);
			}else{
				writer.array();
				for (String string : s) {
					JSONObject js=new JSONObject();
					String[] pairs=string.split(",");
					for (String pair : pairs) {
						String[] kv = pair.split("=>");
						if(kv.length==2){
							js.put(unquote(kv[0].trim()), unquote(kv[1].trim()));
						}
					}
					writer.value(js);
				}
				writer.endArray();
			}
		}else if (isArray()) {
			// Object[] os=sqlType.getArrayValue(db3);
			// if(os==null)
			// writer.value(null);
			// else{
			// for (int i = 0; i < os.length; i++) {
			// writer.value(os[i]);
			// }
			// }
			sqlType.writeArrayValue(db3, writer);
		} else if(isMap()){
			String s=db3.getString();
			if(s==null){
				writer.value(null);
			}else{
				JSONObject js=new JSONObject();
				String[] pairs=s.split(",");
				for (String pair : pairs) {
					String[] kv = pair.split("=>");
					if(kv.length==2){
						js.put(unquote(kv[0].trim()), unquote(kv[1].trim()));
					}
				}
				writer.value(js);
			}
		}else{
			sqlType.writeValue(db3, writer);
		}
	}
	
	private static String unquote(String s) {
		if (s.startsWith("\"") && s.endsWith("\"")) {
		    s = s.substring(1, s.length() - 1);
		    // check for any escaped characters
		    if (s.indexOf('\\') >= 0) {
			StringBuffer sb = new StringBuffer(s.length());	// approx
			for (int i = 0; i < s.length(); i++) {
			    char c = s.charAt(i);
			    if (c == '\\' && i < s.length() - 1)
				c = s.charAt(++i);
			    sb.append(c);
			}
			s = sb.toString();
		    }
		}
		return s;
	    }

	public String clientJavaName() {
		if (sqlType.isPrimitive()) {
			return sqlType.getJavaType() + "Primitive";
		}
		return sqlType.getJavaType();
	}

	public boolean isPrimitive() {
		if(sqlType==null)
			return false;
		return sqlType.isPrimitive();
	}

	public Object fromString(String val) throws JSONException, ParseException {
		if (dim > 0) {
			if (val == null)
				return null;
			JSONArray array = new JSONArray(val);
			Object[] arr = sqlType.getArrayOf(array.length());
			for (int i = 0; i < arr.length; i++) {
				String jv = array.getString(i);
				arr[i] = sqlType.fromString(jv);
			}
			return arr;
		}
		return sqlType.fromString(val);
	}

	public void writeJsonObject(JSONWriter writer, Object o)
			throws JSONException {
		if (o == null) {
			writer.value(null);
			return;
		}

		if (isArray() && isMap()) {
			Object[] os = (Object[]) o;
			writer.array();
			for (int i = 0; i < os.length; i++) {
				if (os[i] == null)
					writer.value(null);
				else{
					Map osi = (Map) os[i];
					writer.object();
					for (Object it : osi.keySet()) {
						Object value = osi.get(it);
						writer.key(it.toString());
						this.mapTo.writeJsonObject(writer, value);
					}
					writer.endObject();					
				}
			}
			writer.endArray();
		} else if (isArray()) {
			Object[] os = (Object[]) o;
			writer.array();
			for (int i = 0; i < os.length; i++) {
				if (os[i] == null)
					writer.value(null);
				else
					sqlType.writeJson(writer, os[i]);
			}
			writer.endArray();
		} else if (isMap()) {
			writer.object();
			Map os = (Map) o;
			for (Object it : os.keySet()) {
				Object value = os.get(it);
				writer.key(it.toString());
				this.mapTo.writeJsonObject(writer, value);
			}
			writer.endObject();
		} else{
			sqlType.writeJson(writer, o);
		}
	}

	public boolean isMap() {
		return this.mapTo != null;
	}

	public String getJavaResponseType() {
		if (isArray()) {
			return sqlType.getJavaType() + "Array";
		}
		return sqlType.getJavaType();
	}

	public String callProtoPart() {
		if (dim == 0)
			return sqlType.callProtoPart();
		
		return "("+sqlType.callProtoPart()+")::"+sqlType.getSqlName() + "[]";
	}

}
