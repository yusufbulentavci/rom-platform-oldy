package com.bilgidoku.rom.shared.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;

public class RomStore {
//	StringBuilder sb=new StringBuilder();
//	public RomStore(JSONArray ja) throws RunException {
//		if(ja==null || ja.isNull()!=null)
//			return;
//		List<Elem> elems=new ArrayList<Elem>();
//		for(int i=0; i< ja.size(); i++){
//			JSONObject jo=ja.get(i).isObject();
//			elems.add(new Elem(jo));
//		}
//	}
//	private void toXml(JSONObject jo) {
//		sb.append("<");
//		sb.append(jo.get("tag").isString().stringValue());
//
//		JSONValue k = jo.get("ats");
//		if (k != null){
//			JSONObject ats = k.isObject();
//			if(ats!=null){
//				for (String entry : ats.keySet()) {
//					String tv=ats.get(entry).isString().stringValue();
//					if(tv!=null && tv.equals("romignore"))
//						continue;
//
//					sb.append(" ");
//					sb.append(entry);
//					sb.append("='");
//					sb.append(tv);
//					sb.append("'");
//				}				
//			}
//		}
//		
//		
//		JSONValue tv=jo.get("text");
//		JSONValue chv=jo.get("childs");
//		if(chv!=null){
//			JSONArray ja=chv.isArray();
//			if(ja!=null){
//				for(int i=0; i<ja.size(); i++){
//					JSONObject jn=ja.get(i).isObject();
//					appendChild(new Elem(jn));
//				}
//			}
//		}
//		JSONValue tv=jo.get("text");
//		if(tv!=null){
//			JSONString tvs = jo.isString();
//			if(tvs!=null)
//				this.inner=new StringBuilder(tvs.stringValue());
//		}
//		
//		sb.append(">");
//		
////		if(tag.equals("head")){
////			sb.append("<style>.site-hili {border: 2px solid #FF9900; background-color: #F1F1F1;opacity: 0.7;}</style>");
////		}
////		if(tag.equals("tr")){
////			syso("");
////		}
//		super.makeStr(sb);
//		if(inner!=null) {
//			sb.append(inner.toString());
//		}
//		sb.append("</");
//		sb.append(tag);
//		sb.append(">");		
//	}
//	
	

}
