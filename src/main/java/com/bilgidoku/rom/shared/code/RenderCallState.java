package com.bilgidoku.rom.shared.code;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.MinRequest;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.RunZone;
import com.bilgidoku.rom.shared.cmds.CommandRepo;
import com.bilgidoku.rom.shared.cmds.CommandRt;
import com.bilgidoku.rom.shared.cmds.RtCommand;
import com.bilgidoku.rom.shared.expr.Evaluator;
import com.bilgidoku.rom.shared.expr.MyLiteral;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.request.JsonResponse;
import com.bilgidoku.rom.shared.state.Changeable;
import com.bilgidoku.rom.shared.state.RtTrigger;
import com.bilgidoku.rom.shared.state.StateTrigger;
import com.bilgidoku.rom.shared.state.TickTrigger;
import com.bilgidoku.rom.shared.state.Trigger;
import com.bilgidoku.rom.shared.xml.Doc;
import com.bilgidoku.rom.shared.xml.Elem;

public class RenderCallState {

	private static final int MAX_NESTED_CODE = 40;

	private int cursorTop = 0;

	private int curCode = 0;

	public String changeHtmlId;
	public AjaxForm form;

	private final CodeStat[] codeStats = new CodeStat[MAX_NESTED_CODE];

	// private final Code codes;
	// private final Scope scope;
	private final RunZone rz;

	public RenderCallState(RunZone rz) {
		this.rz = rz;
		// this.scope=rz.scope;
		// this.codes=rz.codes;
		// this.codes=rz.getCode();

		codeStats[curCode] = new CodeStat();
	}

	public void up() {
		codeStats[curCode].resetUp();
		curCode--;
		// Sistem.outln("up:" + curCode);
	}

	public void down() throws RunException {
		curCode++;

		if (codeStats[curCode] == null) {
			codeStats[curCode] = new CodeStat();
		} else {
			codeStats[curCode].reset();
		}
	}

	public void next(Code code, final Elem curElem) throws RunException {
		if(code==null)
			return;
		codeStats[curCode].reset();
		codeStats[curCode].setCode(code);

		// if(code.tag.equals("w:main_menu")){
		// System.out.println("catch");
		// }

		// if (code == null)
		// Sistem.outln("null");
		// else
		// Sistem.outln(code.tag);

		code.execute(this, curElem);

	}

	public void nextBox(Code code, final Elem curElem) throws RunException {
		if(code==null)
			return;
		codeStats[curCode].reset();
		codeStats[curCode].setCode(code);
		cs().box = true;
		code.execute(this, curElem);
	}

	// private void nextBox(Mobil mobil, Elem curElem) throws RunException {
	// codeStats[curCode].reset();
	// if (mobil.newChanged) {
	// cs().overrideLeft = mobil.newLeft;
	// cs().overrideTop = mobil.newTop;
	// }
	// codeStats[curCode].setCode(mobil.c);
	// cs().box = true;
	// mobil.c.execute(this, curElem);
	// }

	public CodeStat getParentCs() {
		if (curCode == 0)
			return null;
		return codeStats[curCode - 1];
	}

	public Integer injectBoxer(List<Code> children, Elem curElem, Integer height) throws RunException {
		try {
			down();

			// if (getBw() < 900) {
			// Mobilate mbl = new Mobilate(getBw(), children, height);
			//
			// List<Mobil> mobils = mbl.getMobils();
			// for (Mobil mobil : mobils) {
			// nextBox(mobil, curElem);
			// }
			//
			// return mbl.newHeight();
			//
			// } else {
			for (Code child : children) {
				nextBox(child, curElem);
			}

			return height;
			// }

		} finally {
			up();
		}
	}

	private List<Code> mobilate(List<Code> children) {

		return null;
	}

	public void walkChildren(final Elem curElem) throws RunException {
		walkChildren(codeCur(), curElem);
	}

	public void walkChildren(final Code codeCur, final Elem curElem) throws RunException {
		if (codeCur == null) {
			throw new RunException("CodeCur is null", getStackTrace());
		}
		if (codeCur.children == null)
			return;
		walkChildren(curElem, codeCur.children);
	}

	public void walk(final Code codeCur, final Elem curElem) throws RunException {
		next(codeCur, curElem);
	}

	public void walkChildren(final Elem curElem, List<Code> children) throws RunException {
		try {
			down();
			for (Code child : children) {
				next(child, curElem);
			}
		} finally {
			up();
		}
	}

	public void resetEdit() {
		cursorTop = 0;
	}

	public CodeStat cs() {
		return codeStats[curCode];
	}

	public void resetBox() {
	}

	// public void cantElse() {
	// cs().canElse = false;
	// }
	//
	// public void canElse() {
	// cs().canElse = true;
	// }
	//
	// public boolean getCanElse() {
	// return cs().canElse;
	// }
	//
	// public boolean curBox(){
	// return cs().curBox;
	// }
	//
	// public void setBox(){
	// cs().curBox=true;
	// }

	// public void setCodeCur(Code cc) {
	// this.codeCur = cc;
	// }

	public String getAtt(String string) throws RunException {
		return codeCur().getAtt(string);
	}

	private Code codeCur() {
		return this.cs().getCode();
	}

	public String getParamStr(String paramName, boolean nullable, String defVal) throws RunException {
		return getParamOf(codeCur(), paramName, nullable, defVal);
	}

	public String getParamOf(Code codeCur2, String paramName, boolean nullable, String defVal) throws RunException {
		String val = codeCur2.getParam(paramName);
		if (val == null) {
			if (!nullable)
				throw new RunException("Param not found; paramname: " + paramName, getStackTrace());
			return defVal;
		}

		return val;
	}

	public String evaluateText(String string) throws RunException {
		try {
			return Evaluator.evaluateText(string, rz.scope);
		} catch (RunException e) {
			throw new RunException("Evalutation failed for string:" + string, e, getStackTrace());
		}
	}

	public MyLiteral evaluate(String string) throws RunException {
		try {
			return Evaluator.evaluate(string, rz.scope);
		} catch (RunException e) {
			throw new RunException("Evalutation failed:" + string, e, getStackTrace());
		}
	}

	public MyLiteral evaluate(String string, int type) throws RunException {
		try {
			MyLiteral k = Evaluator.evaluate(string, rz.scope);
			if (k != null && k.type != type) {
				k = k.cast(type);
			}
			return k;
		} catch (RunException e) {
			throw new RunException("Evalutation failed:" + string, e, getStackTrace());
		}
	}

	public String evaluateParam(String paramName, boolean nullable, String defVal) throws RunException {
		String paramStr = getParamStr(paramName, true, null);
		if (paramStr == null) {
			if (nullable)
				return defVal != null ? defVal : null;

			if (defVal == null)
				throw new RunException("Evalutation failed for paramName:" + paramName + " is null", getStackTrace());
			return defVal;
		}
		try {
			return Evaluator.evaluateText(paramStr, rz.scope);
		} catch (RunException e) {
			throw new RunException("Evalutation failed for paramName:" + paramName + " of value:" + paramStr, e,
					getStackTrace());
		}
	}

	public boolean evaluateCondition(String when) throws RunException {
		return Evaluator.evaluateCondition(when, rz.scope);
	}

	public MyLiteral evaluateLiteral(String paramName, boolean nullable, MyLiteral defVal) throws RunException {
		String paramStr = getParamStr(paramName, true, null);
		if (paramStr == null) {
			if (nullable)
				return defVal != null ? defVal : null;

			if (defVal == null)
				throw new RunException("Evalutation failed for paramName:" + paramName + " is null", getStackTrace());
			return defVal;
		}
		try {
			return Evaluator.evaluate(paramStr, rz.scope);
		} catch (RunException e) {
			throw new RunException("Evalutation failed for paramName:" + paramName + " of value:" + paramStr, e,
					getStackTrace());
		}
	}

	public Boolean evaluateCondition(String paramName, boolean nullable, Boolean defVal) throws RunException {
		String paramStr = getParamStr(paramName, true, null);
		if (paramStr == null) {
			if (nullable)
				return defVal != null ? defVal : null;

			if (defVal == null)
				throw new RunException("Evalutation failed for paramName:" + paramName + " is null", getStackTrace());
			return defVal;
		}
		try {
			return Evaluator.evaluateCondition(paramStr, rz.scope);
		} catch (RunException e) {
			throw new RunException("Evalutation failed for paramName:" + paramName + " of value:" + paramStr, e,
					getStackTrace());
		}
	}

	public Boolean checkGoal(String paramName, boolean nullable, Boolean defVal) throws RunException {
		String paramStr = getParamStr(paramName, true, null);
		if (paramStr == null) {
			if (nullable)
				return defVal != null ? defVal : null;

			if (defVal == null)
				throw new RunException("Evalutation failed for paramName:" + paramName + " is null", getStackTrace());
			return defVal;
		}
		return paramStr.equals(getGoal());
	}

	public Integer evaluateInt(String paramName, boolean nullable, Integer defVal) throws RunException {
		String paramStr = getParamStr(paramName, true, null);
		if (paramStr == null) {
			if (nullable)
				return defVal != null ? defVal : null;

			if (defVal == null)
				throw new RunException("Evalutation failed for paramName:" + paramName + " is null", getStackTrace());
			return defVal;
		}
		try {
			return Evaluator.evaluateInt(paramStr, rz.scope);
		} catch (RunException e) {
			throw new RunException("Evalutation failed for paramName:" + paramName + " of value:" + paramStr, e,
					getStackTrace());
		}
	}

	public Integer getParamInt(String string, boolean nullable, Integer defVal) throws RunException {
		String s = getParamStr(string, nullable, defVal == null ? null : defVal.toString());
		if (s == null) {
			return null;
		}
		return Integer.parseInt(s);
	}

	public Boolean getParamBool(String string, boolean nullable, Boolean defVal) throws RunException {
		String s = getParamStr(string, nullable, defVal == null ? null : defVal.toString());
		if (s == null) {
			return null;
		}
		return Boolean.parseBoolean(s);
	}

	public JSONArray getScopeArray(String name, boolean nullable) throws RunException {
		return rz.scope.getArray(name, nullable);
	}

	public JSONObject getScopeObj(String myvar, boolean b) throws RunException {
		return rz.scope.getObj(myvar, b);
	}

	public Integer getScopeInt(String string, boolean nullable, Integer def) throws RunException {
		Integer a = rz.scope.getInt(string, nullable);
		if (a == null) {
			if (nullable)
				return def;
			throw new RunException("childs param is empty", getStackTrace());
		}
		return a;
	}

	public String getScopeStr(String string, boolean nullable, String def) throws RunException {
		String a = rz.scope.getStr(string, nullable);
		if (a == null) {
			if (nullable)
				return def;
			throw new RunException("childs param is empty", getStackTrace());
		}
		return a;
	}

	public String getCurTag() {
		return codeCur().tag;
	}

	public Animation getAnimation() {
		return codeCur().getAnimation();
	}

	public boolean getMobileVisible() {
		return codeCur().visibleInMobile;
	}

	public String evalCodeText() throws RunException {
		String s = codeCur().getText();
		if (s == null)
			return null;

		try {
			return Evaluator.evaluateText(s, rz.scope);
		} catch (RunException e) {
			throw new RunException("Evalutation code text failed for:" + s, e, getStackTrace());
		}
	}

	public Map<String, Map<String, String>> evalCodeStyle() throws RunException {
		return evalNestedMap(codeCur().getStyle());
	}

	public Map<String, String> evalCodeAts() throws RunException {
		return evalMap(codeCur().getAts());
	}

	public Map<String, Map<String, String>> getCodeEvents() throws RunException {
		return codeCur().getEvents();
	}

	public Map<String, String> getCodeParams() throws RunException {
		return codeCur().getParams();
	}

	public List<Code> getCodeChilds() throws RunException {
		return codeCur().children;
	}

	public Map<String, Map<String, String>> evalNestedMap(Map<String, Map<String, String>> s) throws RunException {
		if (s == null || s.isEmpty())
			return null;
		Map<String, Map<String, String>> ret = new HashMap<String, Map<String, String>>();
		try {
			for (String innerKey : s.keySet()) {
				Map<String, String> innerValue = s.get(innerKey);
				Map<String, String> ev = evalMap(innerValue);
				if (ev != null)
					ret.put(innerKey, ev);
			}
			return ret;
		} catch (RunException e) {
			throw new RunException("Evalutation code text failed for:" + s, e, getStackTrace());
		}
	}

	public Map<String, String> evalMap(Map<String, String> s) throws RunException {
		if (s == null || s.isEmpty())
			return null;
		Map<String, String> ret = new HashMap<String, String>();
		try {
			for (String innerKey : s.keySet()) {
				String innerValue = s.get(innerKey);
				String ev = innerValue = Evaluator.evaluateText(innerValue, rz.scope);
				if (ev != null)
					ret.put(innerKey, ev);
			}
			return ret;
		} catch (RunException e) {
			throw new RunException("Evalutation code text failed for:" + s, e, getStackTrace());
		}
	}

	public JSONObject evalJSONObject(JSONObject s) throws RunException {
		JSONObject ret = new JSONObject();
		try {
			for (String innerKey : s.keySet()) {
				String innerValue = s.get(innerKey).isString().stringValue();
				String ev = innerValue = Evaluator.evaluateText(innerValue, rz.scope);
				if (ev != null)
					ret.put(innerKey, new JSONString(ev));
			}
			return ret;
		} catch (RunException e) {
			throw new RunException("Evalutation code text failed for:" + s, e, getStackTrace());
		}
	}

	public String getStackTrace() {
		StringBuilder sb = new StringBuilder();
		// Code mcc = codeCur();
		for (int i = 0; i <= this.curCode; i++) {
			// if(mcc == null){
			// if(i==curCode){
			// continue;
			// }else{
			// break;
			// }
			// }
			CodeStat mcc = codeStats[i];
			if (mcc == null)
				continue;
			sb.append(i);
			if (mcc.getCode() == null) {
				sb.append("null");
				sb.append("\t");
			} else {
				sb.append(mcc.getCode().tag);
				sb.append("\t");
			}

			//
			// if (mcc == codeCur().getParent())
			// break;
			// mcc = mcc.getParent();
		}

		return sb.toString();
	}

	public String getGoal() throws RunException {
		return rz.scope.getStr("_goal", false);
	}

	public String addStyleClass(Map<String, Map<String, String>> style) throws RunException {
		return rz.getRunner().addStyleClass(getHtmlTag(), style, getAnimation());
	}

	private String getHtmlTag() {
		Code c = codeCur();
		if (c.isWidget())
			return "div";
		return c.tag;
	}

	public RunZone rz() {
		return rz;
	}

	public Boolean isToRunChange(Changeable change, String changeId) throws RunException {
		if (change == null) {
			change = rz.changes.get(changeId);
		}
		if (change == null) {
			throw new RunException("Changeable not found for id:" + changeId, getStackTrace());
		}
		/**
		 * Fails if when or goal armed and fails Continues in unarmed condition
		 */
		if ((change.guardwhen != null && !evaluateCondition(change.guardwhen))
				|| (change.guardgoal != null && !change.guardgoal.equals(getScopeStr("_goal", true, null))))
			return null;
		boolean visible = !((change.when != null && !evaluateCondition(change.when))
				|| (change.goal != null && !change.goal.equals(getScopeStr("_goal", true, null))));
		// Sistem.outln(change.when+":"+visible);
		return visible;
	}

	public void setVariable(String myvar, JSONValue val) throws RunException {
		this.rz.scope.setVariable(myvar, val);
	}

	public void setVariable(String name, Integer i) throws RunException {
		this.rz.scope.setVariable(name, i);
	}

	public void setVariable(String name, MyLiteral valSet) throws RunException {
		this.rz.scope.setVariable(name, valSet);
	}

	public void setVariable(String name, String i) throws RunException {
		this.rz.scope.setVariable(name, i);
	}

	public boolean runRoutine(String string, Elem itemNode) throws RunException {
		Rtn rtn = rz().runner.getCodeRepo().getRoutine(string);
		if (rtn.isEmpty())
			return false;
		walkChildren(itemNode, rtn.codes);
		return true;
	}

	// public void injectEditMe(Elem node) throws RunException {
	// this.codeCur = new Code(this.codeCur, runner.getCodeRepo(), "p");
	// this.codeCur.text = "edit?";
	// this.codeCur.execute(this, node, rcs);
	//
	// }

	// public Object getSession() {
	// return rz().getSession();
	// }

	public String getLang() throws RunException {
		JSONObject o = rz.scope.getObj("note", false);
		return o.get("lang").isString().stringValue();
	}

	public String getChangeHtmlId() throws RunException {
		if (this.changeHtmlId == null)
			throw new RunException("Change html id is null", getStackTrace());
		return this.changeHtmlId;
	}

	// public JsonResponse post(String href, String data) throws RunException {
	// final JsonResponse obj = new JsonResponse();
	// rz.scope.saveCookies();
	// obj.post(null, href, data, getLang());
	// rz.scope.updateCookieModifieds();
	// return obj;
	// }
	public JsonResponse submit(String s, String resetFields) throws RunException {
		final JsonResponse obj = new JsonResponse(getRequest().getReqLang(), getRequest().getPostman());
		rz.scope.saveCookies();
		obj.submit(s, resetFields);
		rz.scope.updateCookieModifieds();
		return obj;
	}

	public void endForm(Elem newNode) throws RunException {
		if (form != null && form.accepted)
			newNode.setAttribute("onsubmit", "rom.tje(\"" + rz().getId() + "\",null,null,\"form\",event,"
					+ form.store().toString() + ",null);return false;");
	}

	public int estimateZindex() {
		double alan = codeCur().ensureHeight() * codeCur().ensureWidth();
		// canvas is at 100, we cant pass it
		return Math.min(99, (int) (500000f / alan));

	}

	public int getBw() {
		return rz.runner.getBw();
	}

	public JSONObject scopeLangedWiki(String symbol, String column) throws RunException {
		JSONObject jo = scopeLangedVarPropertyJson(symbol, column);
		if (jo == null)
			return null;
		return jo;
	}

	public String scopeLangedText(String symbol, String column) throws RunException {
		return scopeLangedVarProperty(symbol, column);
	}

	public String scopeText(String symbol, String column) throws RunException {
		JSONObject obj = rz.scope.getObj(symbol, false);
		JSONValue s = obj.get(column);
		if (s == null || s.isString() == null)
			return null;
		return s.isString().stringValue();
	}

	public String getLang(String symbol) throws RunException {
		JSONObject obj = rz.scope.getObj(symbol, false);
		if (obj == null)
			return null;
		String[] lc = obj.getStringArray("langcodes");
		return lc[0];
	}

	private JSONObject scopeLangedVarPropertyJson(String var, String part) throws RunException {
		JSONObject obj = rz.scope.getObj(var, false);
		JSONValue s = obj.get(part);
		if (s == null || s.isArray() == null || s.isArray().size() == 0)
			return null;
		JSONArray ja = s.isArray();
		JSONValue k = ja.get(0);
		if (k.isNull() != null)
			return null;

		return k.isObject();
	}

	private String scopeLangedVarProperty(String var, String part) throws RunException {
		JSONObject obj = rz.scope.getObj(var, false);
		JSONValue s = obj.get(part);
		if (s == null || s.isArray() == null || s.isArray().size() == 0)
			return null;
		JSONArray ja = s.isArray();
		JSONValue k = ja.get(0);
		if (k.isNull() != null)
			return "";

		return k.isString().stringValue();
	}

	public void newRunZone(String rzHtmlId, Elem nod) throws RunException {
		rz.runner.newRunZone(this.rz, rzHtmlId, codeCur(), nod);
	}

	public Code newWikiCode(JSONObject codes2) throws RunException {
		return rz.runner.getCodeRepo().wiki(codes2);
	}

	public String getId() {
		return rz.getId();
	}

	public boolean amitop() {
		return codeCur() == codeStats[0].getCode();
	}

	public Wgt getWidget() throws RunException {
		if (codeCur().wgt == null)
			throw new RunException("Widget is null", getStackTrace());
		return codeCur().wgt;
	}

	public RunZone getBoxHolderFeedback() {
		return rz;
	}

	public void sendForm(JSONObject event) throws RunException {
		// sscope.setVariable("event",
		// Portable.one.jsonObjectConstuctFromJS(event));
		rz.scope.setEvent(event);
		JSONArray ja = event.getArray("params");
		AjaxForm af = new AjaxForm(ja);
		String s = evaluate("${event.targetid}").getString();
		if (s.length() == 0)
			return;
		final JsonResponse obj = submit(s, af.resetFields);
		if (obj.succ) {
			if (af.sucRedirect != null) {
				Portable.one.redirect(af.sucRedirect);
				return;
			}
			if (af.sucgoal != null) {
				setVariable("_goal", af.sucgoal);
			}
			if (af.var != null) {
				setVariable(af.var, obj.obj);
			}
		} else {
			if (af.errRedirect != null) {
				Portable.one.redirect(af.errRedirect);
				return;
			}
			if (af.errgoal != null) {
				setVariable("_goal", af.errgoal);
			}
			// if(af.var!=null){
			// setVariable(af.var, obj.obj);
			// }
			// setVariable("_submitok", JSONBoolean.getInstance(false));
			// setVariable("_submitwarn", JSONBoolean.getInstance(obj.warn));
			// setVariable("_submiterr", new JSONString(obj.errText));

		}
		rz.scope.setEvent(null);
		isChanged(true);
	}

	public void changeByHtmlEvent(JSONObject event2) throws RunException {

		JSONArray ja = event2.getArray("params");
		rz.scope.setEvent(event2);
		try {
			if (ja != null) {
				JSONValue l = ja.get(0);
				JSONString goal = l.isString();
				if (goal != null) {
					rz.scope.setVariable("_goal", goal);
				}

				for (int i = 1; i < ja.size(); i = i + 2) {
					JSONValue k = ja.get(i);
					if (k == null || k.isString() == null)
						continue;
					String key = ja.get(i).isString().stringValue();

					JSONArray val = ja.get(i + 1).isArray();
					JSONValue vd = val.get(1);
					int vt = val.get(0).isNumber().intValue();
					if (vt == Att.STRING) {
						JSONString trys = vd.isString();
						// String kk = event2.value();
						// JSONValue kkk = ev.get("value");

						MyLiteral lit = (trys == null) ? new MyLiteral()
								: Evaluator.evaluate(trys.stringValue(), rz.scope);
						rz.scope.setVariable(key, lit);
					} else {
						rz.scope.setVariable(key, new MyLiteral(vt, vd));
					}
				}
			}
			String routine = event2.optString("routine");
			if (routine != null) {
				Doc dontuse = new Doc();
				if (runRoutine(routine, dontuse))
					return;
			}
			String changeid = event2.optString("changeid");
			if (changeid == null || changeid.length() == 0) {
				isChanged(true);
			} else {
				change(changeid, true);
			}
		} finally {
			rz.scope.setEvent(null);
		}
	}

	public void change(String changeId, boolean checkChange) throws RunException {
		Changeable change = rz.changes.get(changeId);

		// Logger logger = Logger.getLogger(runZoneId + " changed");
		// logger.log(Level.SEVERE, ">>>change:" + changeId + " when:" +
		// change.when + " goal:" + change.goal);

		Boolean visible = isToRunChange(change, null);

		if (visible == null)
			return;

		if (!visible) {
			if (!change.appendHtml) {

				// logger.log(Level.SEVERE, ">>>change:" + changeId +
				// " invisible");
				Portable.one.domSet(change.htmlId, "");
			}
			return;
		}

		// logger.log(Level.SEVERE, ">>>change:" + changeId + " Visible");

		Doc doc = new Doc();
		this.changeHtmlId = change.htmlId;
		try {
			// Nuote.one().debug("changable:"+change.htmlId, change.routine);
			runRoutine(change.routine, doc);
		} finally {
			this.changeHtmlId = null;
		}

		String htmlStr = doc.toString();
		if (change.appendHtml) {
			Portable.one.domAppend(change.htmlId, htmlStr);
			if (change.scrollDown != null) {
				Portable.one.domScroll(change.htmlId, "..", change.scrollDown);
			}
		} else {
			Portable.one.domSet(change.htmlId, htmlStr);
		}
		change = null;
		if (checkChange) {
			isChanged(true);
		}
	}

	public void isChanged(boolean broadcast) throws RunException {

		if (broadcast) {
			if (rz.scope.isGlobalChange()) {
				rz.runner.broadcastChange();
			}
		}

		// Logger logger = Logger.getLogger("isChanged");

		if (rz.scope.startChange()) {
			checkTriggers();
			rz.scope.endChange();
		}
	}

	// boolean checkingTriggers = false;

	protected void checkTriggers() throws RunException {
		// if (checkingTriggers)
		// return;
		try {
			// checkingTriggers = true;
			Set<StateTrigger> alreadyrun = new HashSet<StateTrigger>();

			for (int i = 0; i < 4; i++) {
				Set<String> all = rz.scope.resetModifieds();
				if (all.size() == 0)
					break;

				for (int k = 0; k < 2; k++) {
					for (int j = 0; j < rz.stateTriggers.size(); j++) {
						// for (StateTrigger it : stateTriggers) {
						StateTrigger it = rz.stateTriggers.get(j);
						// logger.log(Level.SEVERE, "Check ST:" + it.changeId +
						// " trigger:" + it.likes);
						if (!alreadyrun.contains(it) && it.check(all)) {
							alreadyrun.add(it);
							change(it.changeId, false);
						}
					}
				}

			}

		} finally {
			// checkingTriggers = false;
		}

	}

	public void tick(int tickno) throws RunException {
		for (TickTrigger tt : rz.tickTriggers) {
			if (tt.check(tickno)) {
				change(tt);
			}

		}
	}

	private void change(Trigger tt) throws RunException {
		change(tt.changeId, true);
	}

	public void pageLoaded() throws RunException {
		for (Trigger tt : rz.pageLoadedTriggers) {
			change(tt);
		}
	}

	// public JSONObject getParamObj() throws RunException {
	// JSONValue params = codeCur.get("params");
	// return params.isObject();
	// }

	public void rtEvent(JSONObject event) throws RunException {
		rz.scope.setEvent(event);
		for (RtTrigger it : rz.rtTriggers) {
			String wdi = rz.scope.getStr(it.dlgidvar, true);
			if (wdi == null || !wdi.equals(event.getString("dlgid"))) {
				continue;
			}
			change(it.changeId, true);
		}
		rz.scope.setEvent(null);
		isChanged(true);
	}

	public NullableMap<String, String> getAllParams() {
		return codeCur().params;
	}

	// public void addEditable(String ensureId) {
	// rz.runner.addEditable(ensureId, this);
	// }
	//
	// public void addWidgetInstance(String id) {
	// rz.runner.addWidgetInstance(id, this.codeCur, this);
	// }

	//
	public void watchOut(List<String[]> ret) {
		String pr = "rz";
		// if (parent != null) {
		// pr += "." + parent;
		// }
		rz.scope.watchOut(pr + "." + rz.runZoneId, ret);
	}

	public void processRt(JSONArray cmds) throws RunException {
		rz.processRt(cmds);
	}

	public void runRt(String htmlId, String cid, JSONObject jo) throws RunException {
		CommandRt cmd = CommandRepo.one().getCommandRt(jo.getString("cmd"));
		cmd.exec(false, htmlId, cid, this, null, jo);
	}

	public MinRequest getRequest() {
		return rz.getRunner().getRequest();
	}

}
