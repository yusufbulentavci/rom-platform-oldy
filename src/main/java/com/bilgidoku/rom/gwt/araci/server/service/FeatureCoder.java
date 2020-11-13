package com.bilgidoku.rom.gwt.araci.server.service;
//servertypecoder


import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.*;

import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;

public class FeatureCoder extends
		TypeCoder<Feature> {


	public FeatureCoder(){
		super(false,null);
	}


	@Override
	public  Feature decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Feature c=new Feature();

		c.item=new StringCoder().decode(json.get("item"));
		c.importance=new IntegerCoder().decode(json.get("importance"));
		c.tperiod=new IntegerCoder().decode(json.get("tperiod"));
		c.invoicetperiod=new IntegerCoder().decode(json.get("invoicetperiod"));
		c.invoicetperiodamount=new IntegerCoder().decode(json.get("invoicetperiodamount"));
		c.autorenewoptions=new ArrayCoder<Integer>(new IntegerCoder()).decode(json.get("autorenewoptions"));
		c.usageunit=new StringCoder().decode(json.get("usageunit"));
		c.description=new StringCoder().decode(json.get("description"));


		return c;
	}

	@Override
	public Object encode( Feature obj) throws JSONException{
		if(obj==null)
			return null;
		JSONObject js=new JSONObject();
		js.put("item",new StringCoder().encode(obj.item));
		js.put("importance",new IntegerCoder().encode(obj.importance));
		js.put("tperiod",new IntegerCoder().encode(obj.tperiod));
		js.put("invoicetperiod",new IntegerCoder().encode(obj.invoicetperiod));
		js.put("invoicetperiodamount",new IntegerCoder().encode(obj.invoicetperiodamount));
		js.put("autorenewoptions",new ArrayCoder<Integer>(new IntegerCoder()).encode(obj.autorenewoptions));
		js.put("usageunit",new StringCoder().encode(obj.usageunit));
		js.put("description",new StringCoder().encode(obj.description));

		return js;
	}

	@Override
	public  Feature[] createArray(int size) {
		return new  Feature[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Feature val) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public Feature inGetDbValue(DbSetGet db3) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Shouldnt be called");
	}

	public Feature fromString(String s) throws JSONException{
		throw new RuntimeException("Shouldnt be called");
	}
	public String toString(Feature t) throws KnownError{
		throw new RuntimeException("Shouldnt be called");
	}

}
