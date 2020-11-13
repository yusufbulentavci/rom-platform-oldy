package com.bilgidoku.rom.shared.state;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.expr.MyLiteral;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonTransfer;

public class State implements JsonTransfer {
	protected final Map<String, MyLiteral> nameRefs = new HashMap<String, MyLiteral>();
	private Set<String> modifieds = new HashSet<String>();

	public State() {
	}

	public State(State stat) {
		for (Entry<String, MyLiteral> it : nameRefs.entrySet()) {
			nameRefs.put(it.getKey(), it.getValue());
		}
	}

	public State(JSONValue ret) throws RunException {
		JSONArray reta = ret.isArray();
		JSONArray ms = reta.get(0).isArray();
		for (int i = 0; i < ms.size(); i++) {
			modifieds.add(ms.get(i).isString().stringValue());
		}
		JSONArray a = reta.get(1).isArray();
		int elementCount = a.size();
		for (int i = 0; i < elementCount; i++) {
			JSONArray arr = a.get(i).isArray();
			JSONString kstr = arr.get(0).isString();
			JSONArray v = arr.get(1).isArray();
			this.nameRefs.put(kstr.stringValue(), new MyLiteral(v.get(0).isNumber().intValue(), v.get(1)));
		}
	}

	@Override
	public JSONArray store() throws RunException {
		JSONArray ms = new JSONArray();
		int i = 0;
		for (String n : modifieds) {
			ms.set(i++, new JSONString(n));
		}
		i = 0;
		JSONArray a = new JSONArray();
		for (Entry<String, MyLiteral> it : nameRefs.entrySet()) {
			JSONArray o = new JSONArray();
			o.set(0, new JSONString(it.getKey()));
			o.set(1, it.getValue().store());
			a.set(i++, o);
		}

		JSONArray ret = new JSONArray();
		ret.set(0, ms);
		ret.set(1, a);

		return ret;
	}

	public MyLiteral getValue(String name) {
		return nameRefs.get(name);
	}

	public void addVariable(String name, JSONArray val) {
		addVariable(name, new MyLiteral(val));
	}

	public void addVariable(String name, JSONObject val) {
		addVariable(name, new MyLiteral(val));
	}

	public void addVariable(String name, MyLiteral val) {
		nameRefs.put(name, val);
		modifieds.add(name);
	}

	public boolean setVariable(String name, MyLiteral val) throws RunException {
		if (val == null){
//			throw new RunException("Setting variable to null, variable name:" + name);
			val=new MyLiteral();
		}

//		Logger logger = Logger.getLogger("STATE");
//		logger.log(Level.SEVERE, ">>>" + name + ":" + val.toString());

		MyLiteral old = nameRefs.get(name);
		if (old != null) {
			if (val.getNull()) {
				nameRefs.put(name, val);
			} else {
				if (old.getNull()) {
					nameRefs.put(name, val);
				} else {
					val = val.cast(old.type);
					nameRefs.put(name, val.cast(old.type));
				}
			}
			if (!old.equals(val)) {
				modifieds.add(name);
			}
			return true;
		}
		addVariable(name, val);
		return false;
	}

	public State clone() {
		return new State(this);
	}

	// public MyLiteral getArr(String name) {
	// MyLiteral js=getValue(name);
	// if(js==null)
	// return null;
	// JSONArray arr=js.isArray();
	// if(arr==null){
	// throw new
	// StateException("Expected array in state but it is not; name:"+name+" value:"+js);
	// }
	// return arr;
	// }
	//
	// public JSONString getString(String name) {
	// JSONValue js=getValue(name);
	// if(js==null)
	// return null;
	// JSONString arr=js.isString();
	// if(arr==null){
	// throw new
	// StateException("Expected string in state but it is not; name:"+name+" value:"+js);
	// }
	// return arr;
	// }
	//
	// public JSONObject getObj(String name, boolean notNull) {
	// JSONValue js=getValue(name);
	// if(js==null)
	// return null;
	// JSONObject arr=js.isObject();
	// if(arr==null){
	// throw new
	// StateException("Expected object in state but it is not; name:"+name+" value:"+js);
	// }
	// return arr;
	// }

	public String scopeVariables() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, MyLiteral> it : nameRefs.entrySet()) {
			sb.append(it.getKey());
			sb.append("->");
			sb.append(it.getValue());
			sb.append(";");
		}
		return sb.toString();
	}

	public Set<String> getModifieds() {
		return this.modifieds;
	}

	public void resetModifieds() {
		modifieds = new HashSet<String>();
	}

	public void setModifieds(Set<String> keepGlobalChange) {
		this.modifieds = keepGlobalChange;
	}

	public void watchOut(String string, List<String[]> ret) {
		for (Entry<String, MyLiteral> it : nameRefs.entrySet()) {
			ret.add(new String[]{string+"."+it.getKey(),it.getValue().toString()});
		}
	}

}