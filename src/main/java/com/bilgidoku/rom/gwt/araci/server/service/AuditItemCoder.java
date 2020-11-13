package com.bilgidoku.rom.gwt.araci.server.service;
//servertypecoder


import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.*;

import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;

public class AuditItemCoder extends
		TypeCoder<AuditItem> {


	public AuditItemCoder(){
		super(false,null);
	}


	@Override
	public  AuditItem decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 AuditItem c=new AuditItem();

		c.hostId=new IntegerCoder().decode(json.get("hostId"));
		c.cid=new StringCoder().decode(json.get("cid"));
		c.method=new StringCoder().decode(json.get("method"));
		c.uri=new StringCoder().decode(json.get("uri"));
		c.fieldNames=new ArrayCoder<String>(new StringCoder()).decode(json.get("fieldNames"));
		c.fieldValues=new ArrayCoder<String>(new StringCoder()).decode(json.get("fieldValues"));
		c.aid=new IntegerCoder().decode(json.get("aid"));
		c.time=new LongCoder().decode(json.get("time"));


		return c;
	}

	@Override
	public Object encode( AuditItem obj) throws JSONException{
		if(obj==null)
			return null;
		JSONObject js=new JSONObject();
		js.put("hostId",new IntegerCoder().encode(obj.hostId));
		js.put("cid",new StringCoder().encode(obj.cid));
		js.put("method",new StringCoder().encode(obj.method));
		js.put("uri",new StringCoder().encode(obj.uri));
		js.put("fieldNames",new ArrayCoder<String>(new StringCoder()).encode(obj.fieldNames));
		js.put("fieldValues",new ArrayCoder<String>(new StringCoder()).encode(obj.fieldValues));
		js.put("aid",new IntegerCoder().encode(obj.aid));
		js.put("time",new LongCoder().encode(obj.time));

		return js;
	}

	@Override
	public  AuditItem[] createArray(int size) {
		return new  AuditItem[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, AuditItem val) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public AuditItem inGetDbValue(DbSetGet db3) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Shouldnt be called");
	}

	public AuditItem fromString(String s) throws JSONException{
		throw new RuntimeException("Shouldnt be called");
	}
	public String toString(AuditItem t) throws KnownError{
		throw new RuntimeException("Shouldnt be called");
	}

}
