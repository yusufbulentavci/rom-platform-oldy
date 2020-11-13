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



public class WorlduriCoder extends
		TypeCoder<Worlduri> {

	public WorlduriCoder(){
		super(false,new String[]{"hid","wuri"});
	}

	@Override
	public  Worlduri decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Worlduri c=new Worlduri();

		c.hid=new IntegerCoder().decode(json.get("hid"));
		c.wuri=new StringCoder().decode(json.get("wuri"));


		return c;
	}

	@Override
	public Object encode(Worlduri obj) throws JSONException {
		JSONObject js=new JSONObject();
		js.put("hid",new IntegerCoder().encode(obj.hid));
		js.put("wuri",new StringCoder().encode(obj.wuri));

		return js;
	}

	@Override
	public  Worlduri[] createArray(int size) {
		return new  Worlduri[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Worlduri val) throws KnownError {
		if (val == null) {
			db3.setNull(java.sql.Types.VARCHAR);
			return;
		}
		new IntegerCoder().setDbValue(db3,val.hid);
		new StringCoder().setDbValue(db3,val.wuri);

	}

	@Override
	public String toString(Worlduri val) throws KnownError {

		StringBuilder sb=new StringBuilder();
		int i=0;
		sb.append('(');
		sb.append(new IntegerCoder().quoted(val.hid));
		sb.append(',');sb.append(new StringCoder().quoted(val.wuri));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Worlduri inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Worlduri val=new Worlduri();
		val.hid=new IntegerCoder().getDbValue(db3);
		val.wuri=new StringCoder().getDbValue(db3);

		return val;
	}

	public Worlduri fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Worlduri val=new Worlduri();
		int i=0;
		val.hid=new IntegerCoder().fromString(ms[i++]);
		val.wuri=new StringCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
