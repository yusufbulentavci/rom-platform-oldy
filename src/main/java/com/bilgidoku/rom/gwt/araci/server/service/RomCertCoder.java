package com.bilgidoku.rom.gwt.araci.server.service;
//servertypecoder


import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.*;

import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;

public class RomCertCoder extends
		TypeCoder<RomCert> {


	public RomCertCoder(){
		super(false,null);
	}


	@Override
	public  RomCert decode(Object js) throws JSONException  {
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
	public Object encode( RomCert obj) throws JSONException{
		if(obj==null)
			return null;
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

	@Override
	public void setDbValue(DbSetGet db3, RomCert val) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public RomCert inGetDbValue(DbSetGet db3) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Shouldnt be called");
	}

	public RomCert fromString(String s) throws JSONException{
		throw new RuntimeException("Shouldnt be called");
	}
	public String toString(RomCert t) throws KnownError{
		throw new RuntimeException("Shouldnt be called");
	}

}
