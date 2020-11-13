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



public class MoneyCoder extends
		TypeCoder<Money> {


	@Override
	public  Money decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Money c=new Money();

		c.amount=new LongCoder().decode(json.get("amount"));
		c.currency=new StringCoder().decode(json.get("currency"));


		return c;
	}

	@Override
	public JSONValue encode( Money obj) {
		JSONObject js=new JSONObject();
		js.put("amount",new LongCoder().encode(obj.amount));
		js.put("currency",new StringCoder().encode(obj.currency));

		return js;
	}

	@Override
	public  Money[] createArray(int size) {
		return new  Money[size];
	}

}
