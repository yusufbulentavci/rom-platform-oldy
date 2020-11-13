package com.bilgidoku.rom.gwt.araci.server.service;
//servertypecoder


import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.*;

import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;

public class TariffCoder extends
		TypeCoder<Tariff> {


	public TariffCoder(){
		super(false,null);
	}


	@Override
	public  Tariff decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Tariff c=new Tariff();

		c.feature=new StringCoder().decode(json.get("feature"));
		c.limitto=new IntegerCoder().decode(json.get("limitto"));
		c.credits=new LongCoder().decode(json.get("credits"));
		c.denied=new BooleanCoder().decode(json.get("denied"));


		return c;
	}

	@Override
	public Object encode( Tariff obj) throws JSONException{
		if(obj==null)
			return null;
		JSONObject js=new JSONObject();
		js.put("feature",new StringCoder().encode(obj.feature));
		js.put("limitto",new IntegerCoder().encode(obj.limitto));
		js.put("credits",new LongCoder().encode(obj.credits));
		js.put("denied",new BooleanCoder().encode(obj.denied));

		return js;
	}

	@Override
	public  Tariff[] createArray(int size) {
		return new  Tariff[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Tariff val) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public Tariff inGetDbValue(DbSetGet db3) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Shouldnt be called");
	}

	public Tariff fromString(String s) throws JSONException{
		throw new RuntimeException("Shouldnt be called");
	}
	public String toString(Tariff t) throws KnownError{
		throw new RuntimeException("Shouldnt be called");
	}

}
