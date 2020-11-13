package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;

import com.bilgidoku.rom.gwt.shared.*;
import com.google.gwt.json.client.*;

public class AccountCoder extends
		TypeCoder<Account> {


	@Override
	public  Account decode(JSONValue js)   {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Account c=new Account();

		c.credits=new LongCoder().decode(json.get("credits"));
		c.limitfeatures=new ArrayCoder<String>(new StringCoder()).decode(json.get("limitfeatures"));
		c.reserved=new LongCoder().decode(json.get("reserved"));
		c.model=new StringCoder().decode(json.get("model"));
		c.modelExpire=new LongCoder().decode(json.get("modelExpire"));
		c.modelNext=new StringCoder().decode(json.get("modelNext"));


		return c;
	}

	@Override
	public JSONValue encode( Account obj) {
		JSONObject js=new JSONObject();
		js.put("credits",new LongCoder().encode(obj.credits));
		js.put("limitfeatures",new ArrayCoder<String>(new StringCoder()).encode(obj.limitfeatures));
		js.put("reserved",new LongCoder().encode(obj.reserved));
		js.put("model",new StringCoder().encode(obj.model));
		js.put("modelExpire",new LongCoder().encode(obj.modelExpire));
		js.put("modelNext",new StringCoder().encode(obj.modelNext));

		return js;
	}

	@Override
	public  Account[] createArray(int size) {
		return new  Account[size];
	}

}
