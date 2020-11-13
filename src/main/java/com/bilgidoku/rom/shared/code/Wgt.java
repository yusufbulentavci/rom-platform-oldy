package com.bilgidoku.rom.shared.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.json.JSONBoolean;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JsonTransfer;
import com.bilgidoku.rom.shared.json.JsonUtil;

public class Wgt implements Tag, JsonTransfer {
	public boolean ownZone;

	static Map<String, AttImpl> attDefs = new HashMap<String, AttImpl>();

	Map<String, Att> paramDefs = new HashMap<String, Att>();
	public String name;
	private String group;
	private Integer defWidth,defHeight;

	private Code codes;

	public Wgt(boolean ownZone, Map<String, Att> paramDefs, String name, String group, Code codes, Integer defWidth, Integer defHeight) {
		super();
		this.ownZone = ownZone;
		this.paramDefs = paramDefs;
		this.name = name;
		this.group = group;
		this.codes = codes;
		this.defWidth=defWidth;
		this.defHeight=defHeight;
	}

	// ref->c
	// runzone->oz
	// attimpl type->t

	public Wgt(JSONObject jo, CodeRepo codeRepo) throws RunException {
		this.ownZone = jo.getBoolean("runzone", false);
		this.name = jo.getString("title");
		this.group = jo.getString("group");
		this.defWidth=jo.optInt("w");
		this.defHeight=jo.optInt("h");
//		if(this.name.equals("w:aboutus")){
//			this.group=this.group;
//		}
		JSONObject adf = jo.getObject("params");
		for (String key : adf.keySet()) {
			paramDefs.put(key, new AttImpl(key, adf.getObject(key)));
		}
		JSONObject ref = jo.getObject("childs");
		this.setCodes(new Code(null, ref, codeRepo));
	}

	public JSONObject store() throws RunException {
		if (group ==null)
			group = ".hide";
		
				
		JSONObject ret = new JSONObject();
		ret.put("title", new JSONString(name));
		ret.put("group", new JSONString(group));
		ret.put("runzone", JSONBoolean.getInstance(ownZone));
		
		if(defWidth!=null)
			ret.put("w", defWidth);
		if(defHeight!=null)
			ret.put("h", defHeight);
		
		if (this.codes == null)
			ret.put("childs", new JSONObject());
		else
			ret.put("childs", this.codes.store());
		JsonUtil.storeJsonTransferTagImpl(ret, "params", paramDefs);

		return ret;
	}

	public Wgt(String key) {
		this.name = key;
	}

	private Wgt(Wgt wgt, CodeRepo repo) throws RunException {
		this.ownZone = wgt.ownZone;
		for (Entry<String, Att> it : paramDefs.entrySet()) {
			paramDefs.put(it.getKey(), it.getValue().cloneObj());
		}
		this.setCodes(wgt.getCodes().clone(repo));
	}

	public Wgt cloneWrap(CodeRepo repo) throws RunException {
		return new Wgt(this, repo);
	}

	@Override
	public boolean isRoot() {
		return false;
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public String getNamed() {
		return name;
	}

	@Override
	public Att[] getAts() {
		if (attDefs == null)
			return (Att[]) new AttImpl[0];
		return (Att[]) attDefs.values().toArray(new AttImpl[attDefs.size()]);
	}

	@Override
	public String[] getChildren() {
		return null;
	}

	@Override
	public char[] getChildrenOpt() {
		return null;
	}

	@Override
	public String getSummary() {
		return "";
	}

	public Code getCodes() {
		return codes;
	}

	public void setCodes(Code codes) {
		this.codes = codes;
	}

	@Override
	public Att[] getParams() {
		return (Att[]) paramDefs.values().toArray(new AttImpl[paramDefs.size()]);
	}

	public Map<String, Att> getParamDefs() {
		return paramDefs;
	}

	public Att getParam(String key) {
		return paramDefs.get(key);
	}

	public void setParams(Map<String, Att> paramDefs) {
		this.paramDefs = paramDefs;
	}

	public void setRunZone(boolean oz) {
		this.ownZone = oz;
	}

	// TODO bulo check new methods
	public boolean isWidget() {
		return this.name.indexOf("w:") == 0 ? true : false;
	}

	public void deleteAtt(String named) {
		attDefs.remove(named);
	}

	public void deleteParam(String named) {
		paramDefs.remove(named);
	}

	public void setGroup(String grp) {
		this.group = grp;
	}

	@Override
	public Map<String, Att> getAtDefs() {
		return this.paramDefs;
	}

	public void update(Wgt o) {
		this.ownZone = o.ownZone;

		this.attDefs = o.attDefs;

		this.paramDefs = o.paramDefs;
		this.name = o.name;
		this.group = o.group;

		this.codes = o.codes;
	}

	public void setDefWidth(Integer defWidth2) {
		this.defWidth=defWidth2;
	}

	public void setDefHeight(Integer defHeight2) {
		this.defHeight=defHeight2;
	}

	public Integer getDefWidth() {
		return defWidth;
	}
	
	public Integer getDefHeight() {
		return defHeight;
	}

}
