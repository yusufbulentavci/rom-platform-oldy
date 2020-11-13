package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;

import com.bilgidoku.rom.gwt.shared.*;
import com.google.gwt.json.client.*;

public class WelcomeCoder extends
		TypeCoder<Welcome> {


	@Override
	public  Welcome decode(JSONValue js)   {
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
	public JSONValue encode( Welcome obj) {
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

}
