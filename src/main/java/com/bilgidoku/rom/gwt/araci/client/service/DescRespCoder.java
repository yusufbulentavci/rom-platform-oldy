package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;

import com.bilgidoku.rom.gwt.shared.*;
import com.google.gwt.json.client.*;

public class DescRespCoder extends
		TypeCoder<DescResp> {


	@Override
	public  DescResp decode(JSONValue js)   {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 DescResp c=new DescResp();

		c.romerror=new StringCoder().decode(json.get("romerror"));
		c.resp=new StringCoder().decode(json.get("resp"));


		return c;
	}

	@Override
	public JSONValue encode( DescResp obj) {
		JSONObject js=new JSONObject();
		js.put("romerror",new StringCoder().encode(obj.romerror));
		js.put("resp",new StringCoder().encode(obj.resp));

		return js;
	}

	@Override
	public  DescResp[] createArray(int size) {
		return new  DescResp[size];
	}

}
