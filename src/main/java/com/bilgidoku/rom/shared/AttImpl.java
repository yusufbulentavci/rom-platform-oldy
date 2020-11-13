package com.bilgidoku.rom.shared;

import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JsonTransfer;
import com.bilgidoku.rom.shared.json.JsonUtil;

public class AttImpl implements Att, JsonTransfer {
	protected String named;
	protected int type;

	protected String[] enumeration = null;
	protected String summary = null;

	protected int context = 0;

	public int internalId;

	private String defvalue = null;

	protected boolean isMethod = false;
	private boolean req = false;
	private boolean declare = false;
	private boolean fixed=false;

	public AttImpl(String named, int type) {
		this.named = named;
		this.type = type;
	}

	public AttImpl(String named, JSONObject attdef) throws RunException {
		this.named = named;
		this.type = attdef.getInt("t");
		this.isMethod = attdef.getBoolean("m", false);
		this.enumeration = attdef.getStringArray("e");
		this.context = attdef.getInt("c", 0);
		this.declare = attdef.getBoolean("d", false);
		this.defvalue = attdef.optString("v");
		this.req = attdef.getBoolean("r", false);
		this.fixed = attdef.getBoolean("f", false);

	}

	private AttImpl(AttImpl w) throws RunException {
		this(w.named, false, w.type, null, 0, "");
		this.declare = w.declare;
		this.req = w.req;
		this.defvalue = w.defvalue;
		// if(w.defvalue!=null){
		// if(w.defvalue.isArray()!=null || w.defvalue.isObject()!=null){
		// this.defvalue=Portable.one.jsonParserParseStrict(w.defvalue.toString());
		// }else{
		// this.defvalue=w.defvalue;
		// }
		// }

	}

	public JSONObject store() throws RunException {
		JSONObject ret = new JSONObject();
		ret.put("t", type);
		if (isMethod)
			ret.put("m", isMethod);
		if (context != 0)
			ret.put("c", context);
		if (declare != false)
			ret.put("d", declare);
		if (defvalue != null)
			ret.put("v", defvalue);
		if (req != false)
			ret.put("r", req);
		if (fixed != false)
			ret.put("f", fixed);
		if (enumeration != null && enumeration.length > 0)
			JsonUtil.storeStringArray(ret, "e", enumeration);

		return ret;
	}

	public AttImpl(String named, boolean isMethod, int type, String[] enumeration, int optionality, String summary) {
		this.named = named;
		this.type = type;
		this.isMethod = isMethod;
		this.enumeration = enumeration;
		this.summary = summary;
	}

	public AttImpl(String named, int type, String[] enumeration, boolean req, boolean dec, String defValue,
			int context) {
		this.named = named;
		this.type = type;
		this.enumeration = enumeration;
		this.context = context;
		this.defvalue = defValue;
		this.req = req;
		this.declare = dec;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("new Att(\"");
		sb.append(this.named);
		sb.append("\",");
		sb.append(isMethod);
		sb.append(",");
		sb.append(type);

		sb.append(",new String[]{");
		if (enumeration != null && enumeration.length > 0) {
			boolean virgul = false;
			for (String child : enumeration) {
				if (virgul)
					sb.append(",");
				else
					virgul = true;
				sb.append("\"");
				sb.append(child);
				sb.append("\"");
			}
		}
		sb.append("},");
		sb.append("\"\"");
		sb.append(")");
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj.toString().equals(this.toString()));
	}

	public String getNamed() {
		return named;
	}

	public void setNamed(String named) {
		this.named = named;
	}

	public boolean isMethod() {
		return isMethod;
	}

	public void setMethod(boolean isMethod) {
		this.isMethod = isMethod;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String[] getEnumeration() {
		return enumeration;
	}

	public void setEnumeration(String[] enumeration) {
		this.enumeration = enumeration;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public int getContext() {
		return context;
	}

	public AttImpl routine() {
		this.context = Att.CONTEXT_ROUTINE;
		return this;
	}

	public AttImpl list() {
		this.context = Att.CONTEXT_LIST;
		return this;
	}

	public AttImpl link() {
		this.context = Att.CONTEXT_LINK;
		return this;
	}

	public AttImpl file() {
		this.context = Att.CONTEXT_FILE;
		return this;
	}

	public AttImpl img() {
		this.context = Att.CONTEXT_IMG;
		return this;
	}

	@Override
	public AttImpl cloneObj() throws RunException {
		return new AttImpl(this);
	}

	@Override
	public boolean isDeclare() {
		return declare;
	}

	@Override
	public boolean isReq() {
		return req;
	}

	@Override
	public String getDefvalue() {
		return defvalue;
	}

	@Override
	public boolean isFixed() {
		return fixed;
	}

}
