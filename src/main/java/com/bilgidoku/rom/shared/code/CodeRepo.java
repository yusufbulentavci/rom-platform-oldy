package com.bilgidoku.rom.shared.code;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JsonTransfer;

public class CodeRepo implements JsonTransfer {
	public boolean upgrade = false;
	// rtnMaxId:given max
	private int rtnMaxId = 0;
	public Map<String, Wgt> widgets = new HashMap<String, Wgt>();
	Map<String, Rtn> routines = new HashMap<String, Rtn>();

	public CodeRepo(JSONObject jo) throws RunException {
		for (String key : jo.keySet()) {
			if (key.startsWith("w:")) {
				JSONObject jp = jo.getObject(key);
				try{
					addWidget(key, jp);
				}catch(RunException e){
					Sistem.printStackTrace(e,"Widget has error; ignored/"+key);
				}
			} else if (key.startsWith("r:")) {
				JSONArray jp = jo.optArray(key);
				addRoutine(key, jp);
			}
		}
	}

	private CodeRepo(CodeRepo cr) throws RunException {
		for (Entry<String, Wgt> it : cr.widgets.entrySet()) {
			widgets.put(it.getKey(), it.getValue().cloneWrap(cr));
		}
	}

	public CodeRepo() {
	}

	public CodeRepo cloneRepo() throws RunException {
		return new CodeRepo(this);
	}

	public void refreshRtns() throws RunException {
		// All used
		HashSet<String> usedRoutines = new HashSet<String>();

		// Check in widgets
		for (Wgt it : widgets.values()) {
			checkRtnUsage(usedRoutines, it.getCodes());
		}

		// Check in routines recursively, until new one came
		int rcount;
		do {
			rcount = usedRoutines.size();
			HashSet<String> newRoutines = new HashSet<String>();
			for (String string : usedRoutines) {
				Rtn rtn = getRoutine(string);
				if (rtn.codes == null) {
					continue;
				}
				for (Code cd : rtn.codes) {
					checkRtnUsage(newRoutines, cd);
				}
			}
			usedRoutines.addAll(newRoutines);
		} while (usedRoutines.size() != rcount);

		// Find out unused
		Set<String> notUsed = new HashSet<String>();
		for (String string : routines.keySet()) {
			if (!usedRoutines.contains(string)) {
				notUsed.add(string);
			}
		}

		// Remove unused
		for (String string : notUsed) {
			removeRoutine(string);
		}

		// Update max routine id
		this.rtnMaxId = 0;
		for (String string : routines.keySet()) {
			int k = Integer.parseInt(string.substring(2));
			if (k > rtnMaxId)
				rtnMaxId = k;
		}

	}

	private void removeRoutine(String string) {
		this.routines.remove(string);
	}

	private void checkRtnUsage(Set<String> usedRoutines, Code code) {
		if (code == null)
			return;
		if (code.isCommand() && code.command.getParamDefs() != null && code.params != null) {
			for (Entry<String, Att> entry : code.command.getParamDefs().entrySet()) {
				if (entry.getValue().isMethod() && code.params.get(entry.getKey()) != null) {
					usedRoutines.add(code.params.get(entry.getKey()));
				}
			}
		}
		if (code.children != null) {
			for (Code cc : code.children)
				checkRtnUsage(usedRoutines, cc);
		}
	}

	public JSONObject store() throws RunException {
		JSONObject ret = new JSONObject();
		for (Entry<String, Rtn> it : routines.entrySet()) {
			ret.put(it.getKey(), it.getValue().store());
		}
		for (Entry<String, Wgt> it : widgets.entrySet()) {
			// com.bilgidoku.Sistem.outln(it.getKey());
			String key = it.getKey();
			JSONObject val = it.getValue().store();
			ret.put(it.getKey(), it.getValue().store());
		}
		return ret;
	}

	public void addWidget(String key, JSONObject jp) throws RunException {
		if (!key.startsWith("w:"))
			throw new RunException("add widget illegal name:" + key);

		updateWidget(key, new Wgt(jp, this));
	}

	public Wgt addWidget(boolean ownZone, Map<String, Att> paramDefs, String name, String group, Code codes, Integer defWidth, Integer defHeight)
			throws RunException {

		Wgt w = new Wgt(ownZone, paramDefs, name, group, codes, defWidth, defHeight);
		return updateWidget(name, w);
	}

	private Wgt updateWidget(String name, Wgt w) {
		Wgt o = widgets.get(name);
		if (o != null) {
			o.update(w);
			return o;
		}
		widgets.put(name, w);
		return w;
	}

	public void deleteWidget(String key) throws RunException {
		widgets.remove(key);
	}

	Wgt reqWidget(String key) throws RunException {
		if (!key.startsWith("w:"))
			throw new RunException("Req widget illegal name:" + key);

		Wgt w = widgets.get(key);
		if (w == null) {
			w = new Wgt(key);
			widgets.put(key, w);
		}
		return w;
	}

	public String addRtn(JSONArray ja) throws RunException {
		String key = "r:" + (++rtnMaxId);
		addRoutine(key, ja);
		return key;
	}

	public String addRtn(Code cin) {
		String key = "r:" + (++rtnMaxId);
		Rtn r = new Rtn(cin);
		routines.put(key, r);
		return key;
	}

	public String addRtn(List<Code> cin) {
		String key = "r:" + (++rtnMaxId);
		Rtn r = new Rtn(cin);
		routines.put(key, r);
		return key;
	}

	private Rtn addRoutine(String key, JSONArray jp) throws RunException {
		Rtn r = reqRoutine(key);
		r.init(jp, this);
		return r;
	}

	Rtn reqRoutine(String key) throws RunException {
		Rtn w = routines.get(key);
		if (w == null) {
			w = new Rtn();
			int k = Integer.parseInt(key.substring(2));
			if (k >= rtnMaxId) {
				rtnMaxId = k + 1;
			}
			routines.put(key, w);
		}
		return w;
	}

	public Wgt getWidget(String key) throws RunException {
		if (!key.startsWith("w:"))
			throw new RunException("Get widget illegal name:" + key);

		Wgt w = widgets.get(key);
		if (w == null) {
			throw new RunException("Widget not found:"+w);
		}
		return w;
	}

	public void setWidget(String key, Wgt w) throws RunException {

		widgets.put(key, w);

	}

	public Rtn getRoutine(String key) throws RunException {
		Rtn w = routines.get(key);
		if (w == null) {
			throw new RunException("Routine not found:" + key);
		}
		return w;
	}

	public Rtn optRoutine(String key) throws RunException {
		Rtn w = routines.get(key);
		return w;
	}

	public Code wiki(JSONObject codes2) throws RunException {
		if (codes2 == null) {
			return new Code(null, this, "div");
		}
		return new Code(null, codes2, this);
	}

	// public void upgrade(Code code) throws RunException {
	// if (!upgrade)
	// return;
	// if (code.tag.equals("c:text")) {
	// upgradeCtext(code);
	// } else if (code.tag.equals("c:changeable")) {
	// code.params.put("then", addRtn(code.children));
	// code.children = null;
	// } else if (code.tag.equals("c:foreach")) {
	// // 0 1 4 19 20
	// for (Code it : code.children) {
	// int id = Integer.parseInt(it.ats.get("id"));
	// it.ats.remove("id");
	// String rname = ForeachCommand.Routines[id];
	// code.params.put(rname, addRtn(it));
	// // com.bilgidoku.Sistem.outln(it.store().toString());
	// }
	// code.children = null;
	// // com.bilgidoku.Sistem.outln(code.store().toString());
	// // com.bilgidoku.Sistem.outln(code.children.toArray(new
	// // Code[code.children.size()]));
	// }
	// }
	//
	// private void upgradeCtext(Code code) {
	// String ptext = code.params.get("text");
	// if (ptext != null) {
	// code.params.remove("text");
	// if (code.text == null) {
	// code.text = ptext;
	// } else {
	// code.text += " " + ptext;
	// }
	//
	// if (code.text.equals("${_fe_item2.summary[0]}")) {
	// code.text = null;
	// code.params.put("wiki", "true");
	// code.params.put("symbl", "_fe_item2");
	// code.params.put("clmn", "summary");
	// code.params.put("tbl", "site.contents");
	// }
	// }
	// String cw = code.params.get("conttext");
	// if (cw != null) {
	// code.params.remove("conttext");
	// if (cw.equals("title")) {
	// // code.params.put("wiki", "false");
	// code.params.put("symbl", "item");
	// code.params.put("clmn", "title");
	// code.params.put("tbl", "site.contents");
	//
	// }
	// } else {
	// cw = code.params.get("contwiki");
	// if (cw != null) {
	// code.params.remove("contwiki");
	// if (cw.equals("foot")) {
	// code.params.put("wiki", "true");
	// code.params.put("symbl", "info");
	// code.params.put("clmn", "site_footer");
	// code.params.put("tbl", "site.info");
	// } else if (cw.equals("head")) {
	// code.params.put("wiki", "true");
	// code.params.put("symbl", "info");
	// code.params.put("clmn", "headertext");
	// code.params.put("tbl", "site.info");
	// } else if (cw.equals("spot")) {
	// code.params.put("wiki", "true");
	// code.params.put("symbl", "item");
	// code.params.put("clmn", "spot");
	// code.params.put("tbl", "site.writings");
	// } else if (cw.equals("body")) {
	// code.params.put("wiki", "true");
	// code.params.put("symbl", "item");
	// code.params.put("clmn", "body");
	// code.params.put("tbl", "site.writings");
	// }
	// }
	// }
	//
	// // rnameParam("")
	// //
	// // com.bilgidoku.Sistem.outln("==============================");
	// // com.bilgidoku.Sistem.outln("text:"+code.text);
	// // com.bilgidoku.Sistem.outln("ats:"+code.ats.toString());
	// // com.bilgidoku.Sistem.outln("params:"+code.params.toString());
	// // com.bilgidoku.Sistem.outln("events:"+code.events.toString());
	// // com.bilgidoku.Sistem.outln("style:"+code.style.toString());
	// }

}
