package com.bilgidoku.rom.shared.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bilgidoku.rom.shared.RunException;

public class Elem {

	private static int incr = 0;

	private String tag;
	protected List<Elem> childs = null;
	protected Map<String, String> ats = null;
	protected StringBuilder inner = null;
	protected Elem parent = null;
	
	public Elem(String tag) {
		this.tag = tag;
	}

	// public Elem(JSONObject jo) throws RunException {
	// tag=jo.get("tag").isString().stringValue();
	// JSONValue atsval = jo.get("ats");
	// if (atsval != null){
	// JSONObject ja=atsval.isObject();
	// if(ja!=null){
	// for(String s:ja.keySet()){
	// String v=ja.get(s).isString().stringValue();
	// setAttribute(s, v);
	// }
	// }
	// }
	// JSONValue chv=jo.get("childs");
	// if(chv!=null){
	// JSONArray ja=chv.isArray();
	// if(ja!=null){
	// for(int i=0; i<ja.size(); i++){
	// JSONObject jn=ja.get(i).isObject();
	// appendChild(new Elem(jn));
	// }
	// }
	// }
	// JSONValue tv=jo.get("text");
	// if(tv!=null){
	// JSONString tvs = jo.isString();
	// if(tvs!=null)
	// this.inner=new StringBuilder(tvs.stringValue());
	// }
	// }

	public void smakeStr(StringBuilder sb) {
		if (childs == null)
			return;
		for (Elem n : childs) {
			n.makeStr(sb);
		}
	}

	public void makeStr(StringBuilder sb) {
		if (tag.equals("br")) {
			sb.append("<br />");
			return;
		} else if (tag.equals("hr")) {
			sb.append("<hr />");
			return;
		}
		// if((inner==null||inner.length()==0) && (childs==null ||
		// childs.size()==0)){
		// sb.append("<");
		// sb.append(tag);
		// if(ats!=null){
		// for (Entry<String, String> entry : ats.entrySet()) {
		// String tv=entry.getValue();
		// if(tv!=null && tv.equals("romignore"))
		// continue;
		//
		// sb.append(" ");
		// sb.append(entry.getKey());
		// sb.append("='");
		// sb.append(tv);
		// sb.append("'");
		// }
		// }
		// sb.append(" />");
		// return;
		// }

		if (tag.equals("body")) {
			smakeStr(sb);
			return;
		}
		sb.append("<");
		sb.append(tag);
		// if(tag.equals("html")){
		// sb.append(" xmlns='http://www.w3.org/1999/xhtml'");
		// }
		if (ats != null) {
			for (Entry<String, String> entry : ats.entrySet()) {
				String tv = entry.getValue();
				if (tv != null && tv.equals("romignore"))
					continue;

				sb.append(" ");
				sb.append(entry.getKey());
				sb.append("='");
				sb.append(tv);
				sb.append("'");
			}
		}
		sb.append(">");
		// if(tag.equals("head")){
		// sb.append("<style>.site-hili {border: 2px solid #FF9900; background-color: #F1F1F1;opacity: 0.7;}</style>");
		// }
		// if(tag.equals("tr")){
		// syso("");
		// }
		smakeStr(sb);
		if (inner != null) {
			sb.append(inner.toString());
		}
		sb.append("</");
		sb.append(tag);
		sb.append(">");
	}

	//
	// public void toXml(RomStore store, StringBuilder sb){
	// sb.append("<");
	// sb.append(tag);
	// // if(tag.equals("html")){
	// // sb.append(" xmlns='http://www.w3.org/1999/xhtml'");
	// // }
	// if(ats!=null){
	// for (Entry<String, String> entry : ats.entrySet()) {
	// String tv=entry.getValue();
	// if(tv!=null && tv.equals("romignore"))
	// continue;
	//
	// sb.append(" ");
	// sb.append(entry.getKey());
	// sb.append("='");
	// sb.append(tv);
	// sb.append("'");
	// }
	// }
	//
	// // if()
	//
	// sb.append(">");
	//
	// // if(tag.equals("head")){
	// //
	// sb.append("<style>.site-hili {border: 2px solid #FF9900; background-color: #F1F1F1;opacity: 0.7;}</style>");
	// // }
	// // if(tag.equals("tr")){
	// // syso("");
	// // }
	// smakeStr(sb);
	// if(inner!=null) {
	// sb.append(inner.toString());
	// }
	// sb.append("</");
	// sb.append(tag);
	// sb.append(">");
	// }
	//

	// public void makeStr(StringBuilder sb){
	// if(tag.startsWith("w:")){
	//
	// }
	// if(tag.equals("body")){
	// super.makeStr(sb);
	// return;
	// }
	// sb.append("<");
	// sb.append(tag);
	// // if(tag.equals("html")){
	// // sb.append(" xmlns='http://www.w3.org/1999/xhtml'");
	// // }
	// if(ats!=null){
	// for (Entry<String, String> entry : ats.entrySet()) {
	// String tv=entry.getValue();
	// if(tv!=null && tv.equals("romignore"))
	// continue;
	//
	// sb.append(" ");
	// sb.append(entry.getKey());
	// sb.append("='");
	// sb.append(tv);
	// sb.append("'");
	// }
	// }
	// sb.append(">");
	// // if(tag.equals("head")){
	// //
	// sb.append("<style>.site-hili {border: 2px solid #FF9900; background-color: #F1F1F1;opacity: 0.7;}</style>");
	// // }
	// // if(tag.equals("tr")){
	// // syso("");
	// // }
	// super.makeStr(sb);
	// if(inner!=null) {
	// sb.append(inner.toString());
	// }
	// sb.append("</");
	// sb.append(tag);
	// sb.append(">");
	// }

	public String getTag() {
		return this.tag;
	}

	public void setTag(String listTag2) {
		this.tag = listTag2;
	}

	public void appendChild(Elem newNode) {
		if (childs == null)
			childs = new ArrayList<Elem>();
		newNode.setParent(this);
		childs.add(newNode);
	}

	private void setParent(Elem nod) {
		this.parent = nod;
	}

	public Elem getParent() {
		return this.parent;
	}

	public void addError(String error) {
		Elem e = new Elem("p");
		e.setNodeValue("Error:" + error);
		appendChild(e);
	}

	public void setAttribute(String key, String value) {
		if (ats == null) {
			ats = new HashMap<String, String>();
		}
		ats.put(key, value);
	}

	public void appendForAttribute(String key, String value) {
		if (ats == null) {
			ats = new HashMap<String, String>();
			ats.put(key, value);
			return;
		}
		String clsnow = ats.get(key);
		if (clsnow != null) {
			if(clsnow.indexOf(value)<0)
				ats.put(key, clsnow + " " + value);
		} else {
			ats.put(key, value);
		}
	}

	public void appendForAttributeStyle(String value) {
		if (ats == null) {
			ats = new HashMap<String, String>();
			ats.put("style", value);
			return;
		}
		String clsnow = ats.get("style");
		if (clsnow != null) {
			ats.put("style", clsnow + ";" + value);
		} else {
			ats.put("style", value);
		}
	}

	public String getAttribute(String string) {
		if (ats == null)
			return null;
		return ats.get(string);
	}

	public void setNodeValue(String mytext) {
		if (mytext == null)
			return;
		inner = new StringBuilder(mytext);
	}

	public void appendNodeValue(String mytext) {
		if (inner == null) {
			inner = new StringBuilder(mytext);
		} else {
			inner.append(mytext);
		}
	}

	public String renewId() throws RunException {
		return addId();
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

	private String addId() throws RunException {
		String id = newId();
		setAttribute("id", id);
		return id;
	}

	public static String newId() {
		String id = "romei" + System.currentTimeMillis() + "-" + (incr++);
		return id;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		makeStr(sb);
		return sb.toString();
	}
}
