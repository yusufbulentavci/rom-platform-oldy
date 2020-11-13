package com.bilgidoku.rom.gwt.araci.server.service;
//servertypecoder


import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.*;

import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;

public class DescRespCoder extends
		TypeCoder<DescResp> {


	public DescRespCoder(){
		super(false,null);
	}


	@Override
	public  DescResp decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 DescResp c=new DescResp();

		c.romerror=new StringCoder().decode(json.get("romerror"));
		c.resp=new StringCoder().decode(json.get("resp"));


		return c;
	}

	@Override
	public Object encode( DescResp obj) throws JSONException{
		if(obj==null)
			return null;
		JSONObject js=new JSONObject();
		js.put("romerror",new StringCoder().encode(obj.romerror));
		js.put("resp",new StringCoder().encode(obj.resp));

		return js;
	}

	@Override
	public  DescResp[] createArray(int size) {
		return new  DescResp[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, DescResp val) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public DescResp inGetDbValue(DbSetGet db3) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Shouldnt be called");
	}

	public DescResp fromString(String s) throws JSONException{
		throw new RuntimeException("Shouldnt be called");
	}
	public String toString(DescResp t) throws KnownError{
		throw new RuntimeException("Shouldnt be called");
	}

}
