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



public class Appserver_resourceCoder extends
		TypeCoder<Appserver_resource> {

	public Appserver_resourceCoder(){
		super(false,new String[]{"uri","container","html_file","modified_date","owner_role","schema_name","type_name","is_container"});
	}

	@Override
	public  Appserver_resource decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Appserver_resource c=new Appserver_resource();

		c.uri=new StringCoder().decode(json.get("uri"));
		c.container=new StringCoder().decode(json.get("container"));
		c.html_file=new StringCoder().decode(json.get("html_file"));
		c.modified_date=new StringCoder().decode(json.get("modified_date"));
		c.owner_role=new StringCoder().decode(json.get("owner_role"));
		c.schema_name=new StringCoder().decode(json.get("schema_name"));
		c.type_name=new StringCoder().decode(json.get("type_name"));
		c.is_container=new BooleanCoder().decode(json.get("is_container"));


		return c;
	}

	@Override
	public Object encode(Appserver_resource obj) throws JSONException {
		JSONObject js=new JSONObject();
		js.put("uri",new StringCoder().encode(obj.uri));
		js.put("container",new StringCoder().encode(obj.container));
		js.put("html_file",new StringCoder().encode(obj.html_file));
		js.put("modified_date",new StringCoder().encode(obj.modified_date));
		js.put("owner_role",new StringCoder().encode(obj.owner_role));
		js.put("schema_name",new StringCoder().encode(obj.schema_name));
		js.put("type_name",new StringCoder().encode(obj.type_name));
		js.put("is_container",new BooleanCoder().encode(obj.is_container));

		return js;
	}

	@Override
	public  Appserver_resource[] createArray(int size) {
		return new  Appserver_resource[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Appserver_resource val) throws KnownError {
		if (val == null) {
			db3.setNull(java.sql.Types.VARCHAR);
			return;
		}
		new StringCoder().setDbValue(db3,val.uri);
		new StringCoder().setDbValue(db3,val.container);
		new StringCoder().setDbValue(db3,val.html_file);
		new StringCoder().setDbValue(db3,val.modified_date);
		new StringCoder().setDbValue(db3,val.owner_role);
		new StringCoder().setDbValue(db3,val.schema_name);
		new StringCoder().setDbValue(db3,val.type_name);
		new BooleanCoder().setDbValue(db3,val.is_container);

	}

	@Override
	public String toString(Appserver_resource val) throws KnownError {

		StringBuilder sb=new StringBuilder();
		int i=0;
		sb.append('(');
		sb.append(new StringCoder().quoted(val.uri));
		sb.append(',');sb.append(new StringCoder().quoted(val.container));
		sb.append(',');sb.append(new StringCoder().quoted(val.html_file));
		sb.append(',');sb.append(new StringCoder().quoted(val.modified_date));
		sb.append(',');sb.append(new StringCoder().quoted(val.owner_role));
		sb.append(',');sb.append(new StringCoder().quoted(val.schema_name));
		sb.append(',');sb.append(new StringCoder().quoted(val.type_name));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.is_container));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Appserver_resource inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Appserver_resource val=new Appserver_resource();
		val.uri=new StringCoder().getDbValue(db3);
		val.container=new StringCoder().getDbValue(db3);
		val.html_file=new StringCoder().getDbValue(db3);
		val.modified_date=new StringCoder().getDbValue(db3);
		val.owner_role=new StringCoder().getDbValue(db3);
		val.schema_name=new StringCoder().getDbValue(db3);
		val.type_name=new StringCoder().getDbValue(db3);
		val.is_container=new BooleanCoder().getDbValue(db3);

		return val;
	}

	public Appserver_resource fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Appserver_resource val=new Appserver_resource();
		int i=0;
		val.uri=new StringCoder().fromString(ms[i++]);
		val.container=new StringCoder().fromString(ms[i++]);
		val.html_file=new StringCoder().fromString(ms[i++]);
		val.modified_date=new StringCoder().fromString(ms[i++]);
		val.owner_role=new StringCoder().fromString(ms[i++]);
		val.schema_name=new StringCoder().fromString(ms[i++]);
		val.type_name=new StringCoder().fromString(ms[i++]);
		val.is_container=new BooleanCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
