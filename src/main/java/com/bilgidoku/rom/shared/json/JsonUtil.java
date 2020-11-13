package com.bilgidoku.rom.shared.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.NullableMap;
import com.bilgidoku.rom.shared.expr.MyLiteral;
import com.bilgidoku.rom.shared.state.Changeable;
import com.bilgidoku.rom.shared.state.Trigger;

public class JsonUtil {
	public static String str(JSONObject o, String name) throws RunException {
		return o.get(name).isString().stringValue();
	}

	public static Boolean bool(JSONObject o, String name) throws RunException {
		JSONValue ov = o.opt(name);
		if (ov == null || ov.isBoolean() == null)
			throw new RunException("Parameter not found or not boolean:" + name);
		return o.get(name).isBoolean().booleanValue();
	}

	public static Integer intgr(JSONObject o, String name) throws RunException {
		return (int) o.get(name).isNumber().doubleValue();
	}

	public static Long lng(JSONObject o, String name) throws RunException {
		return (long) o.get(name).isNumber().doubleValue();
	}

	public static JSONArray arr(JSONObject o, String name) throws RunException {
		return o.get(name).isArray();
	}

	public static JSONObject obj(JSONObject o, String name) throws RunException {
		return o.get(name).isObject();
	}

	public static String mstr(JSONObject o, String name) throws RunException {
		JSONValue r1 = o.opt(name);
		if (r1 == null || r1.isNull() != null)
			return null;
		return r1.isString().stringValue();
	}

	public static Boolean mbool(JSONObject o, String name) throws RunException {
		JSONValue r1 = o.opt(name);
		if (r1 == null || r1.isNull() != null)
			return null;
		if (r1.isBoolean() != null)
			return r1.isBoolean().booleanValue();
		else if (r1.isString() != null && !r1.isString().stringValue().isEmpty()) {
			String val = r1.isString().stringValue();
			if (val.equals("true"))
				return true;
			else if (val.equals("false"))
				return false;
			else
				return null;
		} else
			return null;
	}

	public static Integer mint(JSONObject o, String name) throws RunException {
		JSONValue r1 = o.opt(name);
		if (r1 == null || r1.isNull() != null)
			return null;
		if (r1.isNumber() != null) {
			return (int) r1.isNumber().doubleValue();
		} else if (r1.isString() != null && r1.isString().stringValue() != null) {
			String val = r1.isString().stringValue();
			return Integer.parseInt(val);
		} else {
			return null;
		}

	}

	public static JSONArray marray(JSONObject o, String name) throws RunException {
		JSONValue v = o.opt(name);
		if (v == null)
			return null;
		return v.isArray();
	}

	public static JSONObject extractObject(JSONObject info, String fn) throws RunException {
		JSONValue jv = info.opt(fn);
		if (jv.isString() == null) {
			throw new RunException("info " + fn + " is null");
		}
		jv = JSONParser.parseStrict(jv.isString().stringValue());
		if (jv.isObject() == null) {
			throw new RunException("info " + fn + " is not object");
		}
		return jv.isObject();
	}

	public static String nthString(JSONArray ja, int i) throws RunException {
		JSONString k = ja.get(i).isString();
		if (k == null)
			return null;
		return k.stringValue();
	}

	public static Integer nthInt(JSONArray ja, int i) throws RunException {
		JSONNumber k = ja.get(i).isNumber();
		if (k == null)
			return null;
		return (int) k.doubleValue();
	}

//	public static void storeJsonTransferList(JSONObject jo, String string, List st) throws RunException {
//		JSONArray ja = new JSONArray();
//		Iterator it = st.iterator();
//		int i = 0;
//		while (it.hasNext()) {
//			JsonTransfer k = (JsonTransfer) it.next();
//			ja.set(i++, k.store());
//		}
//		jo.put(string, ja);
//	}
	
	public static void storeJsonTransferList(JSONObject jo, String string, List stateTriggers2) throws RunException {
		JSONArray ja=new JSONArray();
		for(int i=0; i<stateTriggers2.size(); i++){
			JsonTransfer k=(JsonTransfer) stateTriggers2.get(i);
			ja.set(i, k.store());
		}
		jo.put(string, ja);
	}

	// public static void storeJsonTransferMap2(JSONObject jo, String string,
	// Map st) throws RunException {
	// JSONObject ja=new JSONObject();
	// for (Entry<String, JsonTransfer> it : st.entrySet()) {
	// ja.put(it.getKey(), it.getValue().store());
	// }
	// jo.put(string, ja);
	// }

	public static void storeJsonTransferMap(JSONObject jo, String string, Map<String, Trigger> htmlEvents) throws RunException {
		JSONObject j = new JSONObject();
		for (Entry<String, Trigger> it : htmlEvents.entrySet()) {
			j.put(it.getKey(), it.getValue().store());
		}
		jo.put(string, j);
	}

	public static JSONValue storeSet(Set<String> tagStyles) throws RunException {
		JSONArray ja = new JSONArray();
		int i = 0;
		for (String string : tagStyles) {
			ja.set(i++, new JSONString(string));
		}
		return ja;
	}

	public static void storeJsonTransferMap2(JSONObject jo, String string, Map<String, Changeable> st) throws RunException {
		JSONObject ja = new JSONObject();
		for (Entry<String, Changeable> it : st.entrySet()) {
			ja.put(it.getKey(), it.getValue().store());
		}
		jo.put(string, ja);
	}
	
	public static void storeJsonTransferMap3(JSONObject jo, String string, Map<String, MyLiteral> st) throws RunException {
		JSONObject ja = new JSONObject();
		for (Entry<String, MyLiteral> it : st.entrySet()) {
			ja.put(it.getKey(), it.getValue().store());
		}
		jo.put(string, ja);
	}
	
	public static void storeJsonTransferTagImpl(JSONObject jo, String string, Map<String, Att> st) throws RunException {
		JSONObject ja = new JSONObject();
		for (Entry<String, Att> it : st.entrySet()) {
			ja.put(it.getKey(), it.getValue().store());
		}
		jo.put(string, ja);
	}

	public static List<String> toListString(JSONArray array) throws RunException {
		List<String> ret=new ArrayList<String>();
		if(array!=null){
			for(int i=0; i<array.size(); i++){
				JSONString k = array.get(i).isString();
				if(k!=null)
					ret.add(k.stringValue());
			}
		}
		return ret;
	}

	public static String toStr(JSONValue g) {
		return (g!=null && g.isString()!=null)?g.isString().stringValue():null;
	}

	public static Boolean toBoolean(JSONValue g) {
		return (g!=null && g.isBoolean()!=null)?g.isBoolean().booleanValue():null;
	}
	
	public static Map<String, String> objToMap(JSONObject optObject) throws RunException {
		if(optObject==null)
			return null;
		HashMap<String,String> ret=new HashMap<String,String>();
		for(String it:optObject.keySet()){
//			String o=optObject.optString(it);
//			if(o!=null)
				ret.put(it, optObject.getString(it));
		}
		return ret;
	}

	public static Map<String, Map<String, String>> objToNestedMap(JSONObject optObject) throws RunException {
		if(optObject==null)
			return null;
		HashMap<String,Map<String, String>> ret=new HashMap<String,Map<String, String>>();
		for(String it:optObject.keySet()){
			Map<String, String> m = objToMap(optObject.getObject(it));
			if(m!=null){
				ret.put(it, m);
			}
		}
		return ret;
	}

	public static void storeNullableStringMap(JSONObject ret, String string, NullableMap<String, String> ats) throws RunException {
		if(ats==null || ats.size()==0){
			return;
		}
		ret.put(string, mapToObj(ats));
	}

	public static JSONObject mapToObj(Map<String, String> ats) throws RunException {
		JSONObject add=new JSONObject();
		for (Entry<String, String> it : ats.entrySet()) {
			add.put(it.getKey(), new JSONString(it.getValue()));
		}
		return add;
	}

	public static void storeNestedNullableStringMap(JSONObject ret, String string,
			NullableMap<String, Map<String, String>> ats) throws RunException {
		if(ats.isEmpty()){
			return;
		}
		JSONObject add=new JSONObject();
		for (Entry<String, Map<String, String>> it : ats.entrySet()) {
			if(it.getValue().size()>0)
				add.put(it.getKey(), mapToObj(it.getValue()));
		}
		ret.put(string, add);
	}

	public static JSONObject cloneObj(JSONObject params) throws RunException {
		if(params==null)
			return null;
		return params.cloneWrap();
	}

	public static void storeStringArray(JSONObject ret, String string, String[] enumeration) throws RunException {
		if(enumeration==null)
			return;
		JSONArray ja=new JSONArray();
		for (String string2 : enumeration) {
			ja.add(string2);
		}
		ret.put(string, ja);
	}
	
	public static JSONObject storeTreeMap(TreeMap<Integer, Map<String,String> > tm) throws RunException{
		JSONObject frames=new JSONObject();
		for(Entry<Integer, Map<String, String>> f : tm.entrySet()){
			frames.put(f.getKey()+"", JsonUtil.mapToObj(f.getValue()));
		}
		return frames;
	}
	
	public static Map<String, Map<String,String> > restoreHashMap(JSONObject optObject) throws RunException{
		HashMap<String, Map<String,String> > ret=new HashMap<String, Map<String,String> >();
		if(optObject==null)
			return null;
		for(String it:optObject.keySet()){
			Map<String, String> m = objToMap(optObject.getObject(it));
			if(m!=null){
				ret.put(it, m);
			}
		}
		
		return ret;
	}
	
	

}
