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



public class MeasureCoder extends
		TypeCoder<Measure> {


	@Override
	public  Measure decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Measure c=new Measure();

		c.id=new IntegerCoder().decode(json.get("id"));
		c.host_id=new IntegerCoder().decode(json.get("host_id"));
		c.uri=new StringCoder().decode(json.get("uri"));
		c.code=new StringCoder().decode(json.get("code"));
		c.reading_at=new StringCoder().decode(json.get("reading_at"));
		c.amount=new IntegerCoder().decode(json.get("amount"));


		return c;
	}

	@Override
	public JSONValue encode( Measure obj) {
		JSONObject js=new JSONObject();
		js.put("id",new IntegerCoder().encode(obj.id));
		js.put("host_id",new IntegerCoder().encode(obj.host_id));
		js.put("uri",new StringCoder().encode(obj.uri));
		js.put("code",new StringCoder().encode(obj.code));
		js.put("reading_at",new StringCoder().encode(obj.reading_at));
		js.put("amount",new IntegerCoder().encode(obj.amount));

		return js;
	}

	@Override
	public  Measure[] createArray(int size) {
		return new  Measure[size];
	}

}
