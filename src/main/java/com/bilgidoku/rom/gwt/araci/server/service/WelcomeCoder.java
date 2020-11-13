package com.bilgidoku.rom.gwt.araci.server.service;
//servertypecoder


import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.*;

import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;

public class WelcomeCoder extends
		TypeCoder<Welcome> {


	public WelcomeCoder(){
		super(false,null);
	}


	@Override
	public  Welcome decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Welcome c=new Welcome();

		c.email=new StringCoder().decode(json.get("email"));
		c.hostid=new IntegerCoder().decode(json.get("hostid"));
		c.site=new StringCoder().decode(json.get("site"));
		c.ip=new StringCoder().decode(json.get("ip"));
		c.country=new StringCoder().decode(json.get("country"));
		c.lastactivity=new LongCoder().decode(json.get("lastactivity"));


		return c;
	}

	@Override
	public Object encode( Welcome obj) throws JSONException{
		if(obj==null)
			return null;
		JSONObject js=new JSONObject();
		js.put("email",new StringCoder().encode(obj.email));
		js.put("hostid",new IntegerCoder().encode(obj.hostid));
		js.put("site",new StringCoder().encode(obj.site));
		js.put("ip",new StringCoder().encode(obj.ip));
		js.put("country",new StringCoder().encode(obj.country));
		js.put("lastactivity",new LongCoder().encode(obj.lastactivity));

		return js;
	}

	@Override
	public  Welcome[] createArray(int size) {
		return new  Welcome[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Welcome val) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public Welcome inGetDbValue(DbSetGet db3) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Shouldnt be called");
	}

	public Welcome fromString(String s) throws JSONException{
		throw new RuntimeException("Shouldnt be called");
	}
	public String toString(Welcome t) throws KnownError{
		throw new RuntimeException("Shouldnt be called");
	}

}
