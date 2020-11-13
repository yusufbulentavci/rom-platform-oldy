package com.bilgidoku.rom.gwt.araci.server.service;
//servertypecoder


import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.*;

import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;

public class HostFeatureCoder extends
		TypeCoder<HostFeature> {


	public HostFeatureCoder(){
		super(false,null);
	}


	@Override
	public  HostFeature decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 HostFeature c=new HostFeature();

		c.feature=new StringCoder().decode(json.get("feature"));
		c.named=new StringCoder().decode(json.get("named"));
		c.expire=new LongCoder().decode(json.get("expire"));
		c.disabled=new BooleanCoder().decode(json.get("disabled"));
		c.autorenewperiod=new IntegerCoder().decode(json.get("autorenewperiod"));
		c.usage=new IntegerCoder().decode(json.get("usage"));
		c.usage_hourly=new ArrayCoder<Integer>(new IntegerCoder()).decode(json.get("usage_hourly"));
		c.usage_daily=new ArrayCoder<Integer>(new IntegerCoder()).decode(json.get("usage_daily"));
		c.usage_monthly=new ArrayCoder<Integer>(new IntegerCoder()).decode(json.get("usage_monthly"));
		c.refid=new ArrayCoder<String>(new StringCoder()).decode(json.get("refid"));
		c.reasons=new ArrayCoder<Integer>(new IntegerCoder()).decode(json.get("reasons"));


		return c;
	}

	@Override
	public Object encode( HostFeature obj) throws JSONException{
		if(obj==null)
			return null;
		JSONObject js=new JSONObject();
		js.put("feature",new StringCoder().encode(obj.feature));
		js.put("named",new StringCoder().encode(obj.named));
		js.put("expire",new LongCoder().encode(obj.expire));
		js.put("disabled",new BooleanCoder().encode(obj.disabled));
		js.put("autorenewperiod",new IntegerCoder().encode(obj.autorenewperiod));
		js.put("usage",new IntegerCoder().encode(obj.usage));
		js.put("usage_hourly",new ArrayCoder<Integer>(new IntegerCoder()).encode(obj.usage_hourly));
		js.put("usage_daily",new ArrayCoder<Integer>(new IntegerCoder()).encode(obj.usage_daily));
		js.put("usage_monthly",new ArrayCoder<Integer>(new IntegerCoder()).encode(obj.usage_monthly));
		js.put("refid",new ArrayCoder<String>(new StringCoder()).encode(obj.refid));
		js.put("reasons",new ArrayCoder<Integer>(new IntegerCoder()).encode(obj.reasons));

		return js;
	}

	@Override
	public  HostFeature[] createArray(int size) {
		return new  HostFeature[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, HostFeature val) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public HostFeature inGetDbValue(DbSetGet db3) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Shouldnt be called");
	}

	public HostFeature fromString(String s) throws JSONException{
		throw new RuntimeException("Shouldnt be called");
	}
	public String toString(HostFeature t) throws KnownError{
		throw new RuntimeException("Shouldnt be called");
	}

}
