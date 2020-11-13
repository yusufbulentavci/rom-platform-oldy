package com.bilgidoku.rom.gwt.araci.client.rom;

// factoryrender

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;
import com.google.gwt.json.client.*;
import com.bilgidoku.rom.gwt.shared.*;

import com.bilgidoku.rom.gwt.araci.client.rom.*;
import com.bilgidoku.rom.gwt.araci.client.bilgi.*;
import com.bilgidoku.rom.gwt.araci.client.site.*;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.*;
import com.bilgidoku.rom.gwt.araci.client.asset.*;



public class WorlduriCoder extends
		TypeCoder<Worlduri> {


	@Override
	public  Worlduri decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Worlduri c=new Worlduri();

		c.hid=new IntegerCoder().decode(json.get("hid"));
		c.wuri=new StringCoder().decode(json.get("wuri"));


		return c;
	}

	@Override
	public JSONValue encode( Worlduri obj) {
		JSONObject js=new JSONObject();
		js.put("hid",new IntegerCoder().encode(obj.hid));
		js.put("wuri",new StringCoder().encode(obj.wuri));

		return js;
	}

	@Override
	public  Worlduri[] createArray(int size) {
		return new  Worlduri[size];
	}

}
