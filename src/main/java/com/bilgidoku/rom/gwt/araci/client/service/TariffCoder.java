package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;

import com.bilgidoku.rom.gwt.shared.*;
import com.google.gwt.json.client.*;

public class TariffCoder extends
		TypeCoder<Tariff> {


	@Override
	public  Tariff decode(JSONValue js)   {
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
	public JSONValue encode( Tariff obj) {
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

}
