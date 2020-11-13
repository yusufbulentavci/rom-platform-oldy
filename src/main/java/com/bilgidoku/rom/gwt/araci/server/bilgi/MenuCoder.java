package com.bilgidoku.rom.gwt.araci.server.bilgi;

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



public class MenuCoder extends
		TypeCoder<Menu> {

	public MenuCoder(){
		super(true,new String[]{"id","ust","devam"});
	}

	@Override
	public  Menu decode(Object js) throws JSONException  {
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
	public Object encode(Menu obj) throws JSONException {
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

	@Override
	public void setDbValue(DbSetGet db3, Menu val) throws KnownError {
		if (val == null) {
			db3.setNull(java.sql.Types.VARCHAR);
			return;
		}
		new StringCoder().setDbValue(db3,val.id);
		new StringCoder().setDbValue(db3,val.ust);
		new StringCoder().setDbValue(db3,val.devam);

	}

	@Override
	public String toString(Menu val) throws KnownError {

		StringBuilder sb=new StringBuilder();
		int i=0;
		sb.append('(');
		sb.append(new StringCoder().quoted(val.id));
		sb.append(',');sb.append(new StringCoder().quoted(val.ust));
		sb.append(',');sb.append(new StringCoder().quoted(val.devam));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Menu inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Menu val=new Menu();
		val.id=new StringCoder().getDbValue(db3);
		val.ust=new StringCoder().getDbValue(db3);
		val.devam=new StringCoder().getDbValue(db3);

		return val;
	}

	public Menu fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Menu val=new Menu();
		int i=0;
		val.id=new StringCoder().fromString(ms[i++]);
		val.ust=new StringCoder().fromString(ms[i++]);
		val.devam=new StringCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
