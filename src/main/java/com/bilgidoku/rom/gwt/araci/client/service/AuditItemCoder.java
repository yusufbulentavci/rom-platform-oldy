package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;

import com.bilgidoku.rom.gwt.shared.*;
import com.google.gwt.json.client.*;

public class AuditItemCoder extends
		TypeCoder<AuditItem> {


	@Override
	public  AuditItem decode(JSONValue js)   {
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
	public JSONValue encode( AuditItem obj) {
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

}
