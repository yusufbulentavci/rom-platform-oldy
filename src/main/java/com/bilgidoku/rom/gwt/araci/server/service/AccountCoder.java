package com.bilgidoku.rom.gwt.araci.server.service;
//servertypecoder


import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.*;

import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;

public class AccountCoder extends
		TypeCoder<Account> {


	public AccountCoder(){
		super(false,null);
	}


	@Override
	public  Account decode(Object js) throws JSONException  {
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
	public Object encode( Account obj) throws JSONException{
		if(obj==null)
			return null;
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

	@Override
	public void setDbValue(DbSetGet db3, Account val) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public Account inGetDbValue(DbSetGet db3) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Shouldnt be called");
	}

	public Account fromString(String s) throws JSONException{
		throw new RuntimeException("Shouldnt be called");
	}
	public String toString(Account t) throws KnownError{
		throw new RuntimeException("Shouldnt be called");
	}

}
