package com.bilgidoku.rom.gwt.araci.server.rom;

//dbcoder
import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;

import com.bilgidoku.rom.shared.err.*;
import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.pg.dict.*;



import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;


import com.bilgidoku.rom.gwt.araci.server.rom.*;
import com.bilgidoku.rom.gwt.araci.server.bilgi.*;
import com.bilgidoku.rom.gwt.araci.server.site.*;
import com.bilgidoku.rom.gwt.araci.server.tepeweb.*;
import com.bilgidoku.rom.gwt.araci.server.asset.*;



public class MoneyCoder extends
		TypeCoder<Money> {

	public MoneyCoder(){
		super(false,new String[]{"amount","currency"});
	}

	@Override
	public  Money decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Money c=new Money();

		c.amount=new LongCoder().decode(json.get("amount"));
		c.currency=new StringCoder().decode(json.get("currency"));


		return c;
	}

	@Override
	public Object encode(Money obj) throws JSONException {
		JSONObject js=new JSONObject();
		js.put("amount",new LongCoder().encode(obj.amount));
		js.put("currency",new StringCoder().encode(obj.currency));

		return js;
	}

	@Override
	public  Money[] createArray(int size) {
		return new  Money[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Money val) throws KnownError {
		if (val == null) {
			db3.setNull(java.sql.Types.VARCHAR);
			return;
		}
		new LongCoder().setDbValue(db3,val.amount);
		new StringCoder().setDbValue(db3,val.currency);

	}

	@Override
	public String toString(Money val) throws KnownError {

		StringBuilder sb=new StringBuilder();
		int i=0;
		sb.append('(');
		sb.append(new LongCoder().quoted(val.amount));
		sb.append(',');sb.append(new StringCoder().quoted(val.currency));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Money inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Money val=new Money();
		val.amount=new LongCoder().getDbValue(db3);
		val.currency=new StringCoder().getDbValue(db3);

		return val;
	}

	public Money fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Money val=new Money();
		int i=0;
		val.amount=new LongCoder().fromString(ms[i++]);
		val.currency=new StringCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
