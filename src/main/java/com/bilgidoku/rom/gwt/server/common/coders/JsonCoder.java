package com.bilgidoku.rom.gwt.server.common.coders;

import com.bilgidoku.rom.gwt.server.common.Json;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.KnownError;

public class JsonCoder extends TypeCoder<Json>{

	@Override
	public void setDbValue(DbSetGet db3, Json val) throws KnownError {
		if(val==null){
			db3.setNull(java.sql.Types.VARCHAR);
			return;
		}
		db3.setString(val.getAsString());
	}

	@Override
	protected Json inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		String val=db3.getString();
		if(val==null)
			return null;
		return decodeCode(val);
	}

	protected Json decodeCode(String val) throws JSONException {
		if(val.equalsIgnoreCase("NULL")){
			return null;
		}
		
		if(val.startsWith("[")){
			return new Json(new JSONArray(val));
		}
		
		return new Json(new JSONObject(val));
	}

	@Override
	public Json decode(Object json) throws JSONException {
		if(json==null)
			return null;
		
		if(json instanceof String){
			return decodeCode((String)json);
		}
		
		if(json instanceof JSONObject)
			return new Json((JSONObject)json);
		
		return new Json((JSONArray)json);
	}

	@Override
	public Object encode(Json obj) throws JSONException {
		if(obj==null)
			return null;
		return obj.getValue();
	}

	@Override
	public Json[] createArray(int size) {
		return new Json[size];
	}

	@Override
	public String getSqlName() {
		return "json";
	}

	@Override
	public Json fromString(String s) throws JSONException {
		return decodeCode(s);
	}

	@Override
	public String toString(Json t) throws KnownError {
		return t.getValue().toString();
	}
}
