package com.bilgidoku.rom.gwt.server.common.coders;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.KnownError;


public class MapCoder extends TypeCoder< Map<String,String> > {

	@Override
	public Map<String, String> decode(Object v) throws JSONException {
		if(isNull(v))
			return null;
		JSONObject o=(JSONObject) v;
		Map<String,String> rets=new HashMap<String,String>();
		Iterator ki = o.keys();
		while(ki.hasNext()){
			String key=(String) ki.next();
			Object value=o.get(key);
			if(value==null)
				rets.put(key, null);
			else{
				String t=new StringCoder().decode(value);
				rets.put(key, t);
			}
		}
		return rets;
	}

	@Override
	public Object encode(Map<String, String> obj) throws JSONException {
		if(obj==null){
			return null;
		}
		JSONObject ret=new JSONObject();
		for (Entry<String, String> it : obj.entrySet()) {
			if(it.getValue()==null)
				continue;
			ret.put(it.getKey(), new StringCoder().encode(it.getValue()));
		}
		return ret;
	}

	@Override
	public Map<String, String>[] createArray(int size) {
		throw new RuntimeException("Dont call");
	}

	@Override
	public void setDbValue(DbSetGet db3, Map<String, String> val) throws KnownError {
		db3.setString(toString(val));
	}

	@Override
	protected Map<String, String> inGetDbValue(DbSetGet db3) throws KnownError {
		String s = db3.getString();
		try {
			return fromString(s);
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

	@Override
	public Map<String, String> fromString(String s) throws JSONException {
		if(s==null)
			return null;
		try {
			return new HstoreParser().parse(s);
		} catch (KnownError e) {
			throw new JSONException(e);
		}
		
	}

	@Override
	public String toString(Map<String, String> t) throws KnownError {
		if(t==null)
			return null;
		StringBuilder sb=new StringBuilder();
		int i=0;
		for (Entry<String, String> it : t.entrySet()) {
			sb.append("\"");
			sb.append(it.getKey());
			sb.append("\"");
			sb.append("=>");
			sb.append("\"");
			sb.append(it.getValue());
			sb.append("\"");
			if(++i!=t.size()){
				sb.append(",");
			}
		}
		
		return sb.toString();
	}

}
