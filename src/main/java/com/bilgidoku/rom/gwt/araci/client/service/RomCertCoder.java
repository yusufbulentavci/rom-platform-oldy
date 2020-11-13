package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;

import com.bilgidoku.rom.gwt.shared.*;
import com.google.gwt.json.client.*;

public class RomCertCoder extends
		TypeCoder<RomCert> {


	@Override
	public  RomCert decode(JSONValue js)   {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 RomCert c=new RomCert();

		c.hostId=new IntegerCoder().decode(json.get("hostId"));
		c.alias=new StringCoder().decode(json.get("alias"));
		c.issuer=new StringCoder().decode(json.get("issuer"));
		c.test=new BooleanCoder().decode(json.get("test"));
		c.notBefore=new LongCoder().decode(json.get("notBefore"));
		c.notAfter=new LongCoder().decode(json.get("notAfter"));
		c.serial=new StringCoder().decode(json.get("serial"));


		return c;
	}

	@Override
	public JSONValue encode( RomCert obj) {
		JSONObject js=new JSONObject();
		js.put("hostId",new IntegerCoder().encode(obj.hostId));
		js.put("alias",new StringCoder().encode(obj.alias));
		js.put("issuer",new StringCoder().encode(obj.issuer));
		js.put("test",new BooleanCoder().encode(obj.test));
		js.put("notBefore",new LongCoder().encode(obj.notBefore));
		js.put("notAfter",new LongCoder().encode(obj.notAfter));
		js.put("serial",new StringCoder().encode(obj.serial));

		return js;
	}

	@Override
	public  RomCert[] createArray(int size) {
		return new  RomCert[size];
	}

}
