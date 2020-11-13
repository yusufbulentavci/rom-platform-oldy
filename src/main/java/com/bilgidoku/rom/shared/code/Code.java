package com.bilgidoku.rom.shared.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.cmds.CommandRepo;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JsonTransfer;
import com.bilgidoku.rom.shared.json.JsonUtil;
import com.bilgidoku.rom.shared.render.Css;
import com.bilgidoku.rom.shared.xml.Elem;

public class Code implements JsonTransfer {

	// private static final String CHILDS1 = "childs";
	// private static final String STYLE1 = "style";
	// private static final String EVENTS1 = "events";
	// private static final String PARAMS1 = "params";
	// private static final String ATS1 = "ats";
	// private static final String TEXT1 = "text";
	// private static final String TAG1 = "tag";
	// private static int incr = 0;

	public static final String CHILDS = "c";
	public static final String STYLE = "s";
	public static final String EVENTS = "e";
	public static final String PARAMS = "p";
	public static final String ATS = "a";
	public static final String TEXT = "tx";
	public static final String TAG = "t";
	public static final String MOBILE = "m";
	public static final String ANIMATION = "an";

	public final String tag;
	public final Command command;

	Code parent;
	public List<Code> children;

	public String text;

	public NullableMap<String, String> params = new NullableStringMap();
	public NullableMap<String, String> ats = new NullableStringMap();

	public NullableMap<String, Map<String, String>> events = new NullableNestedStringMap();
	public NullableMap<String, Map<String, String>> style = new NullableNestedStringMap();

	private Animation animation;
	public boolean visibleInMobile = true;

	public final Wgt wgt;
	public Rtn rtn;

	public Code() {
		this.tag = "c:myer";
		wgt = null;
		command = CommandRepo.widgetCommand;
	}

	/**
	 * 
	 * ElementToCode
	 * 
	 * @param tag
	 * @throws RunException
	 */
	public Code(Code parent, CodeRepo repo, String tag) throws RunException {
		this.tag = tag;
		this.parent = parent;
		if (tag.startsWith("w:")) {

			wgt = repo.reqWidget(tag);
			command = CommandRepo.widgetCommand;
		} else if (tag.equals("c:text")) {
			wgt = null;
			command = CommandRepo.textCommand;
		} else if (tag.startsWith("r:")) {
			wgt = null;
			rtn = repo.reqRoutine(tag);
			if (rtn == null) {
				command = CommandRepo.myErCommand;
				return;
				// throw new RunException("routine not found:" + tag);
			}
			command = CommandRepo.routineCommand;
		} else {
			wgt = null;
			command = CommandRepo.tagCommand;
		}

	}

	/**
	 * For app
	 */
	public Code(String tag, CodeRepo repo) throws RunException {
		this.parent = null;
		this.tag = tag;
		if (!tag.startsWith("w:")) {
			throw new RunException("This constructor is for app widgets");
		}

		wgt = repo.getWidget(tag);
		command = CommandRepo.widgetCommand;
	}

	public Code(Code parent, JSONObject jo, CodeRepo repo) throws RunException {
		this.parent = parent;
		try {
			tag = jo.getString(TAG);
		} catch (Exception e) {
			Code p = this;
			do {
				Sistem.outln("-->" + p.tag);
				Sistem.outln("-->" + jo.toString());
				p = p.parent;
			} while (p != null);
			throw e;
		}
		visibleInMobile = jo.optBoolean(MOBILE, true);
		ats = new NullableStringMap(JsonUtil.objToMap(jo.optObject(ATS)));
		params = new NullableStringMap(JsonUtil.objToMap(jo.optObject(PARAMS)));

		// id = jo.optString("id");
		events = new NullableNestedStringMap(JsonUtil.objToNestedMap(jo.optObject(EVENTS)));
		style = new NullableNestedStringMap(JsonUtil.objToNestedMap(jo.optObject(STYLE)));
		JSONObject aniobj = jo.optObject(ANIMATION);
		if (aniobj != null) {
			animation = new Animation(aniobj);
		}

		if (tag.startsWith("w:")) {
			wgt = repo.reqWidget(tag);
			command = CommandRepo.widgetCommand;
			// if(cd!=null){
			// cd.newWidget(this);
			// }

		} else {
			wgt = null;
			text = jo.optString(TEXT);
			// if(tag.equals("h3")){
			// com.bilgidoku.Sistem.outln("h3");
			// }
			if (tag.startsWith("c:")) {
				command = CommandRepo.one().get(tag);
			} else {
				command = CommandRepo.tagCommand;
			}
			JSONArray childs = jo.optArray(CHILDS);
			if (childs != null) {
				children = new ArrayList<Code>();
				for (int i = 0; i < childs.size(); i++) {
					children.add(new Code(this, childs.get(i).isObject(), repo));
				}
			}
		}
		// repo.upgrade(this);

	}

	public JSONObject store() throws RunException {
		JSONObject ret = new JSONObject();
		ret.put(TAG, new JSONString(tag));
		ret.put(MOBILE, visibleInMobile);

		if (text != null)
			ret.put(TEXT, text);
		JsonUtil.storeNullableStringMap(ret, ATS, ats);
		JsonUtil.storeNullableStringMap(ret, PARAMS, params);
		// if(params!=null)
		// ret.put("params", params);
		// if(id!=null)
		// ret.put("id", new JSONString(id));
		JsonUtil.storeNestedNullableStringMap(ret, EVENTS, events);
		JsonUtil.storeNestedNullableStringMap(ret, STYLE, style);
		if (children != null) {
			JSONArray ja = new JSONArray();
			for (Code it : children) {
				JSONObject s = it.store();
				ja.add(s);
			}
			ret.put(CHILDS, ja);
		}

		if (animation != null)
			ret.put(ANIMATION, animation.store());

		return ret;
	}

	private Code(Code parent, Code c, CodeRepo repo) throws RunException {
		this.parent = parent;
		this.text = c.text;
		this.tag = c.tag;
		this.command = c.command;

		// this.id = c.id;
		this.params = c.params.cloneWrap();
		this.ats = c.ats.cloneWrap();
		this.events = c.events.cloneWrap();
		this.style = c.style.cloneWrap();
		if (c.animation != null)
			this.animation = c.animation.cloneWrap();

		if (c.wgt != null)
			this.wgt = repo.reqWidget(tag);
		else
			this.wgt = null;
		if (c.children != null) {
			children = new ArrayList<Code>();
			for (Code cd : c.children) {
				children.add(new Code(this, cd, repo));
			}
		}
	}

	public Code clone(CodeRepo repo) throws RunException {
		return new Code(null, this, repo);
	}

	public static Code widget(String tag, CodeRepo repo) throws RunException {
		Code ret = new Code(null, repo, tag);

		return ret;
	}

	public static Code routine(String tag, CodeRepo repo) throws RunException {
		Code ret = new Code(null, repo, tag);

		return ret;
	}

	public static Map<String, Styles> objToMap(JSONObject optObject) throws RunException {
		if (optObject == null)
			return null;
		HashMap<String, Styles> ret = new HashMap<String, Styles>();
		for (String it : optObject.keySet()) {
			ret.put(it, new Styles(optObject.getObject(it)));
		}
		return ret;
	}

	public String ensureId() throws RunException {
		if (ats == null) {
			return addId();
		}
		String rid = ats.get("id");
		if (rid != null) {
			return rid;
		}

		return addId();
	}

	private static int k = 0;

	private String addId() throws RunException {
		String id = "romwi" + System.currentTimeMillis() + "-" + (k++);
		ats.put("id", id);
		return id;
	}

	public void toXhtml(StringBuilder sb) {

	}

	public boolean isWidget() {
		return wgt != null;
	}

	public boolean isHtml() {
		return command == CommandRepo.tagCommand;
	}

	public String getParam(String paramName) throws RunException {
		return params.get(paramName);
	}

	public String getAtt(String att) {
		return ats.get(att);
	}

	void execute(RenderCallState rz, Elem curElem) throws RunException {
		this.command.exec(curElem, rz);
	}

	public String getText() {
		return text;
	}

	public Map<String, Map<String, String>> getStyle() {
		return style.wrap;
	}

	public Map<String, String> getAts() {
		return this.ats;
	}

	public Map<String, Map<String, String>> getEvents() {
		return this.events;
	}

	public NullableMap<String, String> getParams() {
		return this.params;
	}

	public void addChild(Code cin) {
		if (children == null)
			children = new ArrayList<Code>();
		this.children.add(cin);
	}

	public void addMethodParam(String param, Code cin, CodeRepo repo) throws RunException {
		List<Code> ch = cin.children;
		if (ch == null) {
			ch = new ArrayList<Code>();
		}
		String oldRtn = this.params.get(param);
		if (oldRtn != null) {
			Rtn oldy = repo.optRoutine(oldRtn);
			if (oldy != null && new Rtn(ch).store().toString().equals(oldy.store().toString())) {
				return;
			}
		}

		String rname = repo.addRtn(ch);
		this.params.put(param, rname);
	}

	public boolean isCommand() {
		if (this.tag.trim().startsWith("c:"))
			return true;
		return false;
	}

	public void setStyleByType(String styleType, String name, String value) {
		if (style.get(styleType) != null)
			style.get(styleType).put(name, value);
		else {
			Map<String, String> styles = new HashMap<String, String>();
			styles.put(name, value);
			style.put(styleType, styles);
		}

	}

	public void addStyleByType(String sType, String styleName, String styleValue) {
		HashMap<String, String> styles = (HashMap<String, String>) style.get(sType);
		if (styles == null)
			styles = new HashMap<String, String>();

		styles.put(styleName, styleValue);
		style.put(sType, styles);

	}

	public void delStyleByType(String sType, String styleName) {
		HashMap<String, String> styles = (HashMap<String, String>) style.get(sType);
		if (styles == null)
			styles = new HashMap<String, String>();

		styles.remove(styleName);
		style.put(sType, styles);

	}

	public Map<String, String> getStyleByType(String key) {
		return style.get(key);
	}

	public NullableMap<String, String> getAttributes() {
		return this.ats;
	}

	public void setAttribute(String name, String value) {
		ats.put(name, value);
	}

	public void setParam(String name, String value) {
		params.put(name, value);
	}

	public void setText(String value) {
		this.text = value;
	}

	public Map<String, Att> getParamDefs() {
		if (wgt != null)
			return wgt.getParamDefs();
		return command.getParamDefs();
	}

	public Map<String, Att> getAtDefs() {
		if (wgt != null)
			return null;
		return command.getAtDefs();
	}

	public void detach() {
		this.parent = null;
		if (children != null)
			this.children.clear();
	}

	public void setAts(Map<String, String> ats2) {
		this.ats.set(ats2);
	}

	public Code getParent() {
		return parent;
	}

	public int getChildSize() {
		return this.children == null ? 0 : this.children.size();
	}

	public Code findTag(String string) {
		if (this.command == CommandRepo.tagCommand && this.tag.equals(string)) {
			return this;
		}
		if (this.children != null) {
			for (Code it : children) {
				Code cr = it.findTag(string);
				if (cr != null)
					return cr;
			}
		}
		return null;
	}

	public void removeParam(String key) {
		this.params.remove(key);
	}

	public void removeAtt(String name) {
		this.ats.remove(name);
	}

	public void removeStyle(String name) {
		this.style.remove(name);
	}

	public Map<String, String> getDefaultStyle() {
		return this.getStyleByType("defaultstyle");
	}

	public String getDefaultStyleAtt(String att) {
		Map<String, String> def = getDefaultStyle();
		if (def == null)
			return null;
		String left = def.get(att);
		return left;
	}

	public Integer getPx(String att) {
		Map<String, String> def = getDefaultStyle();
		if (def == null)
			return null;
		String left = def.get(att);
		if (left == null)
			return null;
		return intPx(left);
	}

	public static Integer intPx(String left) {
		if (left == null || !left.trim().endsWith("px"))
			return null;
		int ipx = left.indexOf("px");
		if (ipx > 0) {
			left = left.substring(0, ipx);
		}

		return Integer.parseInt(left);
	}

	public void setPx(String name, Integer att) {
		addStyleByType("defaultstyle", name, att + "px");
	}

	public void delPx(String name) {
		delStyleByType("defaultstyle", name);
	}

	public Integer getParamInt(String string) throws RunException {
		String p = getParam(string);
		if (p == null)
			return null;
		return Integer.parseInt(p);
	}

	public int ensureWidth() {
		Integer w = getPx("width");
		if (w == null) {
			setPx("width", 100);
			return 100;
		}
		return w;
	}

	public int ensureHeight() {
		Integer w = getPx("height");
		if (w == null) {
			setPx("height", 100);
			return 100;
		}
		return w;
	}

	public void ensurePositionAbsolute() {
		setStyleByType("defaultstyle", "position", "absolute");
	}

	public String defaultStyleToStr() throws RunException {
		return Css.defaultStyleToStr(style);
	}

	public void replaceStyleText(Elem elem) throws RunException {
		elem.setAttribute("style", defaultStyleToStr());
	}

	public void childrenEmpty() {
		if (this.children != null)
			this.children.clear();
	}

	public List<Code> children() {
		if (children == null)
			return new ArrayList<Code>();
		return children;
	}

	public void replaceChildren(List<Code> ret) {
		this.children = ret;
	}

	public void text(String string) {
		this.text = string;
	}

	private void addClass(String string) {
		String clsattr = getAtt("class");
		if (clsattr == null || clsattr.length() == 0) {
			setAttribute("class", string);
		}
		String[] allcls = clsattr.split(" ");
		for (String string2 : allcls) {
			if (string2.equals(string))
				return;
		}

		setAttribute("class", clsattr + " " + string);
	}

	public Animation getAnimation() {
		return this.animation;
	}

	//
	// public void setBox() {
	// this.isBox = true;
	// }
	//
	// public boolean isBox() {
	// return isBox;
	// }

	public void setAnimation(Animation ai) {
		this.animation = ai;
	}

	// public void youAreAboxSetZIndex() {
	// String def=getDefaultStyleAtt("z-index");
	// if(def!=null)
	// return;
	// double alan=ensureHeight()*ensureWidth();
	//
	// int zindex=(int)(1000 / Math.sqrt(alan));
	//
	// }

}
