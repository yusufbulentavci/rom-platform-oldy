package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;

import com.bilgidoku.rom.gwt.shared.*;
import com.google.gwt.json.client.*;

public class FeatureCoder extends
		TypeCoder<Feature> {


	@Override
	public  Feature decode(JSONValue js)   {
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
	public JSONValue encode( Feature obj) {
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

}
