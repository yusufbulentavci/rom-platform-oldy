package com.bilgidoku.rom.gwt.araci.client.bilgi;

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



public class MenuCoder extends
		TypeCoder<Menu> {


	@Override
	public  Menu decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Menu c=new Menu();

		c.id=new StringCoder().decode(json.get("id"));
		c.ust=new StringCoder().decode(json.get("ust"));
		c.devam=new StringCoder().decode(json.get("devam"));


		return c;
	}

	@Override
	public JSONValue encode( Menu obj) {
		JSONObject js=new JSONObject();
		js.put("id",new StringCoder().encode(obj.id));
		js.put("ust",new StringCoder().encode(obj.ust));
		js.put("devam",new StringCoder().encode(obj.devam));

		return js;
	}

	@Override
	public  Menu[] createArray(int size) {
		return new  Menu[size];
	}

}
