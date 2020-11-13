package com.bilgidoku.rom.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.cmds.CommandCap;
import com.bilgidoku.rom.shared.cmds.TagCommand;
import com.bilgidoku.rom.shared.cmds.WidgetCommand;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonTransfer;
import com.bilgidoku.rom.shared.json.JsonUtil;
import com.bilgidoku.rom.shared.state.Changeable;
import com.bilgidoku.rom.shared.state.RtTrigger;
import com.bilgidoku.rom.shared.state.Scope;
import com.bilgidoku.rom.shared.state.State;
import com.bilgidoku.rom.shared.state.StateTrigger;
import com.bilgidoku.rom.shared.state.TickTrigger;
import com.bilgidoku.rom.shared.state.Trigger;

public class RunZone implements JsonTransfer, CommandCap {

	public final static WidgetCommand widgetCommand = new WidgetCommand();
	public final static TagCommand tagCommand = new TagCommand();

	public String runZoneId;
	public Scope scope;
	private final String parent;

	public final Runner runner;

	private int changeCounter = 0;
	public Map<String, Changeable> changes = new HashMap<String, Changeable>();

	// private String curTag;

	public RunException renderError;

	public List<StateTrigger> stateTriggers = new ArrayList<StateTrigger>();
	public List<RtTrigger> rtTriggers = new ArrayList<RtTrigger>();
	public List<TickTrigger> tickTriggers = new ArrayList<TickTrigger>();
	public List<Trigger> pageLoadedTriggers = new ArrayList<Trigger>();

	private String htmlid;

	private JSONArray clientCommands = new JSONArray();

	// public Code codes;

	@Override
	public JSONValue store() throws RunException {
		JSONObject jo = new JSONObject();
		jo.put("rz", new JSONString(runZoneId));
		jo.put("scope", scope.store());
		jo.put("htmlid", new JSONString(htmlid));
		if (parent != null)
			jo.put("parent", new JSONString(parent));
		JsonUtil.storeJsonTransferList(jo, "statrig", stateTriggers);
		JsonUtil.storeJsonTransferList(jo, "tictrig", tickTriggers);
		JsonUtil.storeJsonTransferList(jo, "pagtrig", pageLoadedTriggers);
		JsonUtil.storeJsonTransferList(jo, "rttrig", rtTriggers);
		JsonUtil.storeJsonTransferMap2(jo, "changes", changes);
		if (clientCommands.size() > 0)
			jo.put("cc", clientCommands);
		return jo;
	}

	public RunZone(final Runner templating, State pageState, JSONObject trans, JSONValue val) throws RunException {
		this.runner = templating;
		JSONObject obj = val.isObject();
		this.runZoneId = obj.get("rz").isString().stringValue();
		this.scope = new Scope(templating.getRequest(), pageState, trans, obj.get("scope").isArray());
		this.parent = JsonUtil.mstr(obj, "parent");
		this.htmlid = JsonUtil.mstr(obj, "htmlid");
		JSONArray ar = JsonUtil.arr(obj, "statrig");
		for (int i = 0; i < ar.size(); i++) {
			this.stateTriggers.add(new StateTrigger(ar.get(i)));
		}
		ar = JsonUtil.arr(obj, "pagtrig");
		for (int i = 0; i < ar.size(); i++) {
			this.pageLoadedTriggers.add(new Trigger(ar.get(i)));
		}
		ar = JsonUtil.arr(obj, "tictrig");
		for (int i = 0; i < ar.size(); i++) {
			this.tickTriggers.add(new TickTrigger(ar.get(i)));
		}
		ar = JsonUtil.arr(obj, "rttrig");
		for (int i = 0; i < ar.size(); i++) {
			this.rtTriggers.add(new RtTrigger(ar.get(i)));
		}
		JSONObject cg = JsonUtil.obj(obj, "changes");
		for (String s : cg.keySet()) {
			Changeable c = new Changeable(cg.get(s));
			this.changes.put(s, c);
		}
		this.clientCommands = obj.optArray("cc");

	}

	public RunZone(final Runner templating, String parent, String rzHtmlId, Scope scope) throws RunException {
		this.runZoneId = templating.nextRunZoneSeq();
		this.runner = templating;
		this.parent = parent;
		this.scope = scope;
		this.htmlid = rzHtmlId;
	}

	// private String extractString(JSONObject htmlJson, String name) throws
	// RunException {
	// JSONValue tagRaw = htmlJson.get(name);
	// if (tagRaw == null) {
	// throw new RunException("Element couldnt be found:" + name + " in json" +
	// htmlJson.toString(),
	// getStackTrace());
	// }
	// JSONString tagString = tagRaw.isString();
	// if (tagString == null) {
	// throw new RunException("Element is not a string:" + name + " in json" +
	// htmlJson.toString(),
	// getStackTrace());
	// }
	// return tagString.stringValue();
	// }

	//
	// public JSONValue getParamValue(String paramName, boolean nullable,
	// JSONValue defVal) throws RunException {
	// JSONValue val = codeCur.getParam(paramName);
	//
	// if (val == null) {
	// if (!nullable)
	// throw new RunException("Param not found; paramname: " + paramName);
	// return defVal;
	// }
	// if (val.isNull() != null) {
	// if (!nullable)
	// throw new RunException("Params not defined; looked for " + paramName);
	// return defVal;
	// }
	// return val;
	// }

	// public JSONObject getParamObject(String paramName, boolean nullable,
	// JSONObject defVal) throws RunException {
	// JSONValue s = getParamValue(paramName, nullable, defVal);
	// if(s==null)
	// return null;
	// JSONObject jo=s.isObject();
	//
	// if(!nullable && jo==null){
	// throw new
	// RunException("Param found but it is not jsonobject; paramname: " +
	// paramName+" value:"+s.toString());
	// }
	// return jo;
	// }

	// public Tag getTagDef(String waitTag) {
	// return runner.htmlTagMap.getTagDefs().get(waitTag);
	// }
	//
	// public Map<String, Tag> getTagDefs() {
	// return runner.htmlTagMap.getTagDefs();
	// }
	//
	// public Map<String, StyleDef> getStyleDefs() {
	// return runner.htmlTagMap.getStyleMap();
	// }

	// public JSONArray getCodeChildren(boolean nullable) throws RunException {
	// JSONValue a = codeCur.get("childs");
	// if (a == null) {
	// if (nullable)
	// return null;
	// throw new RunException("childs param is empty");
	// }
	// JSONArray nodes = a.isArray();
	// TemplatingUtil.assertNotNull(nodes, "childs param is not an jsonarray");
	// return nodes;
	// }

	// public String addStyleClass(Map<String, Map<String, String>> style)
	// throws RunException {
	// return runner.addStyleClass(getCurTag(), style);
	// }

	public String addEvent(String changeId) {
		return runner.addEvent(runZoneId, changeId);
	}

	// public String getTextTitle() throws RunException {
	// return evaluateText(scopeVarProperty("item", "title"));
	// }

	// public JSONObject getWikiSpot() throws RunException {
	// return scopeVarPropertyJson("item", "spot");
	// }
	//
	// public JSONObject getWikiBody() throws RunException {
	// return scopeVarPropertyJson("item", "body");
	// }
	//
	//
	// public JSONObject getWikiHeaderText() throws RunException {
	// return scopeVarPropertyJson("info", "headertext");
	// }
	//
	// public JSONObject getWikiFooterText() throws RunException {
	// return scopeVarPropertyJson("info", "site_footer");
	// }

	// private JSONArray scopeVar(String var, String part) throws RunException {
	// JSONObject obj = scope.getObj(var, false);
	// JSONValue s = obj.get(part);
	// if (s == null || s.isArray() == null || s.isArray().size() == 0)
	// return null;
	// JSONArray ja = s.isArray();
	// return ja;
	// }

	public String getTrans(String name) throws RunException {
		return scope.getTrans(name);
	}

	// public JSONObject getCodeRoot() {
	// return codes;
	// }

	public String getId() {
		return runZoneId;
	}

	// public Trigger getEvent() {
	// Quote.assertNotNull(this.event,
	// "RunZone.getEvent called but not in event process mode");
	// return event;
	// }

//	public Object getSession() {
//		return runner.getSession();
//	}
//
//	public Object getDomain() {
//		return runner.getDomain();
//	}

	public String addChangeable(String routine, String name, String htmlId2, Boolean appendHtml, String when,
			String goal, String guardwhen, String guardgoal, Boolean scrollDown) {
		String changeId = name == null ? "" + changeCounter++ : name;
		Changeable c = new Changeable(routine, htmlId2, appendHtml, when, goal, guardwhen, guardgoal, scrollDown);
		this.changes.put(changeId, c);
		return changeId;
	}

	public void addStateTrigger(String changeId, String likes, boolean hasGoal) {
		StateTrigger st = new StateTrigger(this.runZoneId, changeId, likes, hasGoal);
		this.stateTriggers.add(st);
	}

	public void addTickTrigger(String changeId, Integer tickPeriod, Integer tickDelay, Integer tickTimes) {
		TickTrigger tt = new TickTrigger(runZoneId, changeId, tickPeriod, tickDelay, tickTimes);
		this.tickTriggers.add(tt);
	}

	public void addPageLoadedTrigger(String changeId) {
		Trigger t = new Trigger(runZoneId, changeId);
		pageLoadedTriggers.add(t);
	}

	public void addRtTrigger(String changeId, String rtdlgvar) {
		RtTrigger st = new RtTrigger(this.runZoneId, changeId, rtdlgvar);
		this.rtTriggers.add(st);
	}

	// public Code getCode() {
	// return this.codeCur;
	// }
	//
	// public Code getParentCur() {
	// return parentCur;
	// }
	//
	// public boolean isBox() {
	// return this.codeCur.isBox();
	// }

	public Runner getRunner() {
		return runner;
	}

	public void addClientCommand(JSONArray clientCommand) {
		this.clientCommands.add(clientCommand);
	}

	/**
	 * cmds last element is htmlid of running div
	 * 
	 * @param cmds
	 * @throws RunException
	 */
	public void processRt(JSONArray cmds) throws RunException {
		if (Portable.one.isClient()) {
			String elm = JsonUtil.nthString(cmds, cmds.size() - 2);
			String cid = JsonUtil.nthString(cmds, cmds.size() - 1);
			if (cid.equals("no"))
				cid = null;
			RenderCallState rcs = new RenderCallState(this);
			for (int i = 0; i < cmds.size() - 1; i++) {
				JSONObject jo = cmds.get(i).isObject();
				rcs.runRt(elm, cid, jo);
			}
		} else {
			this.clientCommands.add(cmds);
		}
	}

	public void processDelayedClientCommands() {
		if(clientCommands==null)
			return;
		for (int i = 0; i < clientCommands.size(); i++) {
			JSONArray ja;
			try {
				ja = clientCommands.get(i).isArray();
				processRt(ja);
			} catch (RunException e) {
				Sistem.printStackTrace(e);
			}
		}
	}

}
