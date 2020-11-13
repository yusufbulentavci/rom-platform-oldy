package com.bilgidoku.rom.shared.state;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.shared.Cookie;
import com.bilgidoku.rom.shared.MinRequest;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.expr.MyLiteral;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONParser;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonTransfer;

public class Scope implements JsonTransfer {
	final State stack;
	final State pageState;
	final JSONObject trans;
	final MinRequest cookieStore;
	JSONObject event;
	private Set<String> keepGlobalChange;

	/**
	 * Called by test
	 */
	public Scope(MinRequest req) {
		this.pageState = new State();
		this.stack = new State();
		this.stack.addVariable("_goal", new MyLiteral("start"));
		this.trans = null;
		this.cookieStore = req;
	}

	/**
	 * Called by runner constructor
	 */
	public Scope(MinRequest req, State pageState, JSONObject trans) {
		this.pageState = pageState;
		stack = new State();
		this.stack.addVariable("_goal", new MyLiteral("start"));
		this.trans = trans;
		this.cookieStore = req;
	}

	/**
	 * Called by clone
	 */
	private Scope(Scope scope) {
		this.stack = new State();
		this.stack.addVariable("_goal", new MyLiteral("start"));
		this.pageState = scope.pageState;
		this.trans = scope.trans;
		this.cookieStore = scope.cookieStore;
	}

	/**
	 * Called by runzone
	 */
	public Scope(MinRequest req, State pageState, JSONObject trans, JSONArray arr) throws RunException {
		this.pageState = pageState;
		this.trans = trans;
		this.stack = new State(arr);
		this.cookieStore = req;
	}

	@Override
	public JSONValue store() throws RunException {
		return this.stack.store();
	}

	public String getTrans(String key) throws RunException {
		JSONValue v = trans.opt(key);
		if (v == null || v.isString() == null)
			return key;

		return v.isString().stringValue();
	}

	public Scope clone() {
		return new Scope(this);
	}

	public void setEvent(JSONObject event2) {
		if (event2 == null)
			this.event = null;
		else
			this.event = event2;
	}

	public void setVariable(String myvar, JSONValue json) throws RunException {
		MyLiteral lit = jsonToLiteral(json);
		setVariable(myvar, lit);
	}

	public static MyLiteral jsonToLiteral(JSONValue json) {
		if (json == null) {
			return new MyLiteral();
		} else if (json.isString() != null) {
			return new MyLiteral(json.isString().stringValue());
		} else if (json.isNumber() != null) {
			return new MyLiteral(json.isNumber().doubleValue());
		} else if (json.isBoolean() != null) {
			return new MyLiteral(json.isBoolean().booleanValue());
		} else if (json.isObject() != null) {
			return new MyLiteral(json.isObject());
		}
		return new MyLiteral(json.isArray());
	}

	public boolean setVariable(String myvar, JSONArray jo) throws RunException {
		return this.setVariable(myvar, new MyLiteral(jo));
	}

	// public boolean setVariable(String name, MyLiteral ref) {
	// return this.setVariable(name, ref, 0);
	// }
	public boolean setVariable(String myvar, JSONObject jo) throws RunException {
		return this.setVariable(myvar, new MyLiteral(jo));
	}

	public boolean setVariable(String name, MyLiteral ref) throws RunException {
		if (name.equals("cid") || name.equals("user") || name.equals("cname") || name.equals("roles")) {
			return true;
		}

		// if(name.equals("wantcid")){
		// ("wantcid:"+ref.type);
		// }
		//
		if (name.startsWith("_")) {
			return stack.setVariable(name, ref);
		}
		return pageState.setVariable(name, ref);
	}

	public boolean setVariable(String name, Integer ref) throws RunException {
		return this.setVariable(name, new MyLiteral(ref));
	}

	public boolean setVariable(String name, Boolean ref) throws RunException {
		return this.setVariable(name, new MyLiteral(ref));
	}

	public boolean setVariable(String name, String ref) throws RunException {
		return this.setVariable(name, new MyLiteral(ref));
	}

	public String scopeVariables() {
		String ps = pageState.scopeVariables();
		String local = stack.scopeVariables();
		return "pageState:[" + ps + "] local:[" + local + "]";
	}

	public MyLiteral getValue(String name, boolean nullable) throws RunException {
		MyLiteral ret = null;
		if (name.startsWith("_")) {
			ret = stack.getValue(name);
		} else if (name.equals("event")) {
			return new MyLiteral(event);
		} else {
			if (cookieStore.isACookie(name)) {
				Cookie cok = cookieStore.getCookie();
				if (cok != null)
					ret = getFrom(cok, name);
			} else {
				if (name.startsWith("tr_") && name.length() > 3 && trans != null) {
					String key = name.substring(3);
					JSONValue tv = trans.opt(key);
					if (tv != null) {
						JSONString val = tv.isString();
						if (val != null)
							ret = new MyLiteral(val.stringValue());
					}
					if (ret == null)
						ret = new MyLiteral("?" + name);
				} else {
					ret = pageState.getValue(name);
				}

			}
		}

		if (ret == null) {
			if (nullable)
				return new MyLiteral();
			throw new RunException("No variable with name:" + name);
		}
		return ret;
	}

	public MyLiteral getFrom(Cookie cookie, String name) throws RunException {

		if (name.equals("cid")) {
			return new MyLiteral(cookie.getCid());
		}

		if (name.equals("roles")) {
			if (cookie.getRoles() == null)
				return new MyLiteral();
			String[] vals = cookie.getRoles().split(",");
			JSONArray arr = new JSONArray();
			for (int i = 0; i < vals.length; i++) {
				arr.set(i, new JSONString(vals[i]));
			}
			return new MyLiteral(arr);
		}

		if (name.equals("lang")) {
			return new MyLiteral(cookie.getCookieLang());
		}
		if (name.equals("sid")) {
			return new MyLiteral(cookie.getSid());
		}
		if (name.equals("user")) {
			return new MyLiteral(cookie.getCookieUser());
		}
		if (name.equals("cname")) {
			return new MyLiteral(cookie.getCname());
		}
		return new MyLiteral();
	}

	public JSONValue getJson(String name, boolean nullable) throws RunException {
		MyLiteral i = getValue(name, nullable);
		if (nullable && (i == null || i.getNull()))
			return null;

		if (i.isObj() || i.isArray())
			return (JSONValue) i.getValue();
		if (i.isString()) {
			String v = i.getString();
			if (v != null) {
				try {
					return JSONParser.parseStrict(v);
				} catch (Exception e) {
				}
			}
		}
		throw new RunException("Type cast error for " + name + "; is not a object or array:" + i.toDesc());
	}

	public Integer getInt(String name, boolean nullable) throws RunException {
		MyLiteral i = getValue(name, nullable);
		if (nullable && i == null)
			return null;
		return i.getInt();
	}

	public String getStr(String name, boolean nullable) throws RunException {
		MyLiteral i = getValue(name, nullable);
		if (nullable && i == null)
			return null;
		return i.getString();
	}

	public Double getDouble(String name, boolean nullable) throws RunException {
		MyLiteral i = getValue(name, nullable);
		if (nullable && i == null)
			return null;
		return i.getDouble();
	}

	public Boolean getBool(String name, boolean nullable) throws RunException {
		MyLiteral i = getValue(name, nullable);
		if (nullable && i == null)
			return null;
		return i.getBool();
	}

	public JSONObject getObj(String name, boolean nullable) throws RunException {
		MyLiteral i = getValue(name, nullable);
		if (nullable && (i == null || !i.isObj()))
			return null;
		return i.getObj();
	}

	public JSONArray getArray(String name, boolean nullable) throws RunException {
		MyLiteral i = getValue(name, nullable);
		if (nullable && (i == null || !i.isArray()))
			return null;
		return i.getArray();
	}

	public Set<String> resetModifieds() {
		Set<String> mod = this.stack.getModifieds();
		mod.addAll(this.pageState.getModifieds());

		this.keepGlobalChange.addAll(this.pageState.getModifieds());
		this.pageState.resetModifieds();
		this.stack.resetModifieds();
		return mod;

	}

	// TODO: 
	public void saveCookies() throws RunException {
//		cookieStore.saveState();
	}

	public void updateCookieModifieds() throws RunException {
//		List<String> k = cookieStore.getModifieds();
//		this.pageState.getModifieds().addAll(k);
	}

	public boolean isGlobalChange() {
		return this.pageState.getModifieds().size() > 0;
	}

	public Set<String> getGlobalChange() {
		return this.pageState.getModifieds();
	}

	public boolean startChange() {
		Set<String> gm = this.pageState.getModifieds();
		if (gm.size() == 0 && this.stack.getModifieds().size() == 0)
			return false;

		keepGlobalChange = new HashSet<String>();
		keepGlobalChange.addAll(gm);
		return true;
	}

	public void endChange() {
		this.pageState.setModifieds(keepGlobalChange);
	}

	public void watchOut(String string, List<String[]> ret) {
		this.stack.watchOut(string, ret);
	}

}
