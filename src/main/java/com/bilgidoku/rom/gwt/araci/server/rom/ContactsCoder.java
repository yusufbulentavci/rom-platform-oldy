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



public class ContactsCoder extends
		TypeCoder<Contacts> {

	public ContactsCoder(){
		super(true,new String[]{"ri","host_id","uri","container","html_file","modified_date","creation_date","delegated","ownercid","gid","relatedcids","mask","nesting","dbfs","weight","lexemes","rtags","aa","lang_id","cipher","first_name","last_name","icon","email","fb_id","twitter","web","confirmed","address","state","city","country_code","postal_code","organization","phone","mobile","fax","tags","gids","works"});
	}

	@Override
	public  Contacts decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Contacts c=new Contacts();

		c.ri=new LongCoder().decode(json.get("ri"));
		c.host_id=new IntegerCoder().decode(json.get("host_id"));
		c.uri=new StringCoder().decode(json.get("uri"));
		c.container=new StringCoder().decode(json.get("container"));
		c.html_file=new StringCoder().decode(json.get("html_file"));
		c.modified_date=new StringCoder().decode(json.get("modified_date"));
		c.creation_date=new StringCoder().decode(json.get("creation_date"));
		c.delegated=new StringCoder().decode(json.get("delegated"));
		c.ownercid=new StringCoder().decode(json.get("ownercid"));
		c.gid=new StringCoder().decode(json.get("gid"));
		c.relatedcids=new ArrayCoder<String>(new StringCoder()).decode(json.get("relatedcids"));
		c.mask=new LongCoder().decode(json.get("mask"));
		c.nesting=new MapCoder().decode(json.get("nesting"));
		c.dbfs=new ArrayCoder<String>(new StringCoder()).decode(json.get("dbfs"));
		c.weight=new IntegerCoder().decode(json.get("weight"));
		c.lexemes=new StringCoder().decode(json.get("lexemes"));
		c.rtags=new ArrayCoder<String>(new StringCoder()).decode(json.get("rtags"));
		c.aa=new StringCoder().decode(json.get("aa"));
		c.lang_id=new StringCoder().decode(json.get("lang_id"));
		c.cipher=new StringCoder().decode(json.get("cipher"));
		c.first_name=new StringCoder().decode(json.get("first_name"));
		c.last_name=new StringCoder().decode(json.get("last_name"));
		c.icon=new StringCoder().decode(json.get("icon"));
		c.email=new StringCoder().decode(json.get("email"));
		c.fb_id=new StringCoder().decode(json.get("fb_id"));
		c.twitter=new StringCoder().decode(json.get("twitter"));
		c.web=new StringCoder().decode(json.get("web"));
		c.confirmed=new BooleanCoder().decode(json.get("confirmed"));
		c.address=new StringCoder().decode(json.get("address"));
		c.state=new StringCoder().decode(json.get("state"));
		c.city=new StringCoder().decode(json.get("city"));
		c.country_code=new StringCoder().decode(json.get("country_code"));
		c.postal_code=new StringCoder().decode(json.get("postal_code"));
		c.organization=new StringCoder().decode(json.get("organization"));
		c.phone=new StringCoder().decode(json.get("phone"));
		c.mobile=new StringCoder().decode(json.get("mobile"));
		c.fax=new StringCoder().decode(json.get("fax"));
		c.tags=new ArrayCoder<String>(new StringCoder()).decode(json.get("tags"));
		c.gids=new ArrayCoder<String>(new StringCoder()).decode(json.get("gids"));
		c.works=new ArrayCoder<Json>(new JsonCoder()).decode(json.get("works"));


		return c;
	}

	@Override
	public Object encode(Contacts obj) throws JSONException {
		JSONObject js=new JSONObject();
		js.put("ri",new LongCoder().encode(obj.ri));
		js.put("host_id",new IntegerCoder().encode(obj.host_id));
		js.put("uri",new StringCoder().encode(obj.uri));
		js.put("container",new StringCoder().encode(obj.container));
		js.put("html_file",new StringCoder().encode(obj.html_file));
		js.put("modified_date",new StringCoder().encode(obj.modified_date));
		js.put("creation_date",new StringCoder().encode(obj.creation_date));
		js.put("delegated",new StringCoder().encode(obj.delegated));
		js.put("ownercid",new StringCoder().encode(obj.ownercid));
		js.put("gid",new StringCoder().encode(obj.gid));
		js.put("relatedcids",new ArrayCoder<String>(new StringCoder()).encode(obj.relatedcids));
		js.put("mask",new LongCoder().encode(obj.mask));
		js.put("nesting",new MapCoder().encode(obj.nesting));
		js.put("dbfs",new ArrayCoder<String>(new StringCoder()).encode(obj.dbfs));
		js.put("weight",new IntegerCoder().encode(obj.weight));
		js.put("lexemes",new StringCoder().encode(obj.lexemes));
		js.put("rtags",new ArrayCoder<String>(new StringCoder()).encode(obj.rtags));
		js.put("aa",new StringCoder().encode(obj.aa));
		js.put("lang_id",new StringCoder().encode(obj.lang_id));
		js.put("cipher",new StringCoder().encode(obj.cipher));
		js.put("first_name",new StringCoder().encode(obj.first_name));
		js.put("last_name",new StringCoder().encode(obj.last_name));
		js.put("icon",new StringCoder().encode(obj.icon));
		js.put("email",new StringCoder().encode(obj.email));
		js.put("fb_id",new StringCoder().encode(obj.fb_id));
		js.put("twitter",new StringCoder().encode(obj.twitter));
		js.put("web",new StringCoder().encode(obj.web));
		js.put("confirmed",new BooleanCoder().encode(obj.confirmed));
		js.put("address",new StringCoder().encode(obj.address));
		js.put("state",new StringCoder().encode(obj.state));
		js.put("city",new StringCoder().encode(obj.city));
		js.put("country_code",new StringCoder().encode(obj.country_code));
		js.put("postal_code",new StringCoder().encode(obj.postal_code));
		js.put("organization",new StringCoder().encode(obj.organization));
		js.put("phone",new StringCoder().encode(obj.phone));
		js.put("mobile",new StringCoder().encode(obj.mobile));
		js.put("fax",new StringCoder().encode(obj.fax));
		js.put("tags",new ArrayCoder<String>(new StringCoder()).encode(obj.tags));
		js.put("gids",new ArrayCoder<String>(new StringCoder()).encode(obj.gids));
		js.put("works",new ArrayCoder<Json>(new JsonCoder()).encode(obj.works));

		return js;
	}

	@Override
	public  Contacts[] createArray(int size) {
		return new  Contacts[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Contacts val) throws KnownError {
		if (val == null) {
			db3.setNull(java.sql.Types.VARCHAR);
			return;
		}
		new LongCoder().setDbValue(db3,val.ri);
		new IntegerCoder().setDbValue(db3,val.host_id);
		new StringCoder().setDbValue(db3,val.uri);
		new StringCoder().setDbValue(db3,val.container);
		new StringCoder().setDbValue(db3,val.html_file);
		new StringCoder().setDbValue(db3,val.modified_date);
		new StringCoder().setDbValue(db3,val.creation_date);
		new StringCoder().setDbValue(db3,val.delegated);
		new StringCoder().setDbValue(db3,val.ownercid);
		new StringCoder().setDbValue(db3,val.gid);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.relatedcids);
		new LongCoder().setDbValue(db3,val.mask);
		new MapCoder().setDbValue(db3,val.nesting);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.dbfs);
		new IntegerCoder().setDbValue(db3,val.weight);
		new StringCoder().setDbValue(db3,val.lexemes);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.rtags);
		new StringCoder().setDbValue(db3,val.aa);
		new StringCoder().setDbValue(db3,val.lang_id);
		new StringCoder().setDbValue(db3,val.cipher);
		new StringCoder().setDbValue(db3,val.first_name);
		new StringCoder().setDbValue(db3,val.last_name);
		new StringCoder().setDbValue(db3,val.icon);
		new StringCoder().setDbValue(db3,val.email);
		new StringCoder().setDbValue(db3,val.fb_id);
		new StringCoder().setDbValue(db3,val.twitter);
		new StringCoder().setDbValue(db3,val.web);
		new BooleanCoder().setDbValue(db3,val.confirmed);
		new StringCoder().setDbValue(db3,val.address);
		new StringCoder().setDbValue(db3,val.state);
		new StringCoder().setDbValue(db3,val.city);
		new StringCoder().setDbValue(db3,val.country_code);
		new StringCoder().setDbValue(db3,val.postal_code);
		new StringCoder().setDbValue(db3,val.organization);
		new StringCoder().setDbValue(db3,val.phone);
		new StringCoder().setDbValue(db3,val.mobile);
		new StringCoder().setDbValue(db3,val.fax);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.tags);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.gids);
		new ArrayCoder<Json>(new JsonCoder()).setDbValue(db3,val.works);

	}

	@Override
	public String toString(Contacts val) throws KnownError {

		StringBuilder sb=new StringBuilder();
		int i=0;
		sb.append('(');
		sb.append(new LongCoder().quoted(val.ri));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.host_id));
		sb.append(',');sb.append(new StringCoder().quoted(val.uri));
		sb.append(',');sb.append(new StringCoder().quoted(val.container));
		sb.append(',');sb.append(new StringCoder().quoted(val.html_file));
		sb.append(',');sb.append(new StringCoder().quoted(val.modified_date));
		sb.append(',');sb.append(new StringCoder().quoted(val.creation_date));
		sb.append(',');sb.append(new StringCoder().quoted(val.delegated));
		sb.append(',');sb.append(new StringCoder().quoted(val.ownercid));
		sb.append(',');sb.append(new StringCoder().quoted(val.gid));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.relatedcids));
		sb.append(',');sb.append(new LongCoder().quoted(val.mask));
		sb.append(',');if(true)throw new RuntimeException("dont call");
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.dbfs));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.weight));
		sb.append(',');sb.append(new StringCoder().quoted(val.lexemes));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.rtags));
		sb.append(',');sb.append(new StringCoder().quoted(val.aa));
		sb.append(',');sb.append(new StringCoder().quoted(val.lang_id));
		sb.append(',');sb.append(new StringCoder().quoted(val.cipher));
		sb.append(',');sb.append(new StringCoder().quoted(val.first_name));
		sb.append(',');sb.append(new StringCoder().quoted(val.last_name));
		sb.append(',');sb.append(new StringCoder().quoted(val.icon));
		sb.append(',');sb.append(new StringCoder().quoted(val.email));
		sb.append(',');sb.append(new StringCoder().quoted(val.fb_id));
		sb.append(',');sb.append(new StringCoder().quoted(val.twitter));
		sb.append(',');sb.append(new StringCoder().quoted(val.web));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.confirmed));
		sb.append(',');sb.append(new StringCoder().quoted(val.address));
		sb.append(',');sb.append(new StringCoder().quoted(val.state));
		sb.append(',');sb.append(new StringCoder().quoted(val.city));
		sb.append(',');sb.append(new StringCoder().quoted(val.country_code));
		sb.append(',');sb.append(new StringCoder().quoted(val.postal_code));
		sb.append(',');sb.append(new StringCoder().quoted(val.organization));
		sb.append(',');sb.append(new StringCoder().quoted(val.phone));
		sb.append(',');sb.append(new StringCoder().quoted(val.mobile));
		sb.append(',');sb.append(new StringCoder().quoted(val.fax));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.tags));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.gids));
		sb.append(',');sb.append(new ArrayCoder<Json>(new JsonCoder()).quoted(val.works));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Contacts inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Contacts val=new Contacts();
		val.ri=new LongCoder().getDbValue(db3);
		val.host_id=new IntegerCoder().getDbValue(db3);
		val.uri=new StringCoder().getDbValue(db3);
		val.container=new StringCoder().getDbValue(db3);
		val.html_file=new StringCoder().getDbValue(db3);
		val.modified_date=new StringCoder().getDbValue(db3);
		val.creation_date=new StringCoder().getDbValue(db3);
		val.delegated=new StringCoder().getDbValue(db3);
		val.ownercid=new StringCoder().getDbValue(db3);
		val.gid=new StringCoder().getDbValue(db3);
		val.relatedcids=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.mask=new LongCoder().getDbValue(db3);
		val.nesting=new MapCoder().getDbValue(db3);
		val.dbfs=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.weight=new IntegerCoder().getDbValue(db3);
		val.lexemes=new StringCoder().getDbValue(db3);
		val.rtags=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.aa=new StringCoder().getDbValue(db3);
		val.lang_id=new StringCoder().getDbValue(db3);
		val.cipher=new StringCoder().getDbValue(db3);
		val.first_name=new StringCoder().getDbValue(db3);
		val.last_name=new StringCoder().getDbValue(db3);
		val.icon=new StringCoder().getDbValue(db3);
		val.email=new StringCoder().getDbValue(db3);
		val.fb_id=new StringCoder().getDbValue(db3);
		val.twitter=new StringCoder().getDbValue(db3);
		val.web=new StringCoder().getDbValue(db3);
		val.confirmed=new BooleanCoder().getDbValue(db3);
		val.address=new StringCoder().getDbValue(db3);
		val.state=new StringCoder().getDbValue(db3);
		val.city=new StringCoder().getDbValue(db3);
		val.country_code=new StringCoder().getDbValue(db3);
		val.postal_code=new StringCoder().getDbValue(db3);
		val.organization=new StringCoder().getDbValue(db3);
		val.phone=new StringCoder().getDbValue(db3);
		val.mobile=new StringCoder().getDbValue(db3);
		val.fax=new StringCoder().getDbValue(db3);
		val.tags=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.gids=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.works=new ArrayCoder<Json>(new JsonCoder()).getDbValue(db3);

		return val;
	}

	public Contacts fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Contacts val=new Contacts();
		int i=0;
		val.ri=new LongCoder().fromString(ms[i++]);
		val.host_id=new IntegerCoder().fromString(ms[i++]);
		val.uri=new StringCoder().fromString(ms[i++]);
		val.container=new StringCoder().fromString(ms[i++]);
		val.html_file=new StringCoder().fromString(ms[i++]);
		val.modified_date=new StringCoder().fromString(ms[i++]);
		val.creation_date=new StringCoder().fromString(ms[i++]);
		val.delegated=new StringCoder().fromString(ms[i++]);
		val.ownercid=new StringCoder().fromString(ms[i++]);
		val.gid=new StringCoder().fromString(ms[i++]);
		val.relatedcids=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.mask=new LongCoder().fromString(ms[i++]);
		if(true)throw new RuntimeException("dont call");
		val.dbfs=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.weight=new IntegerCoder().fromString(ms[i++]);
		val.lexemes=new StringCoder().fromString(ms[i++]);
		val.rtags=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.aa=new StringCoder().fromString(ms[i++]);
		val.lang_id=new StringCoder().fromString(ms[i++]);
		val.cipher=new StringCoder().fromString(ms[i++]);
		val.first_name=new StringCoder().fromString(ms[i++]);
		val.last_name=new StringCoder().fromString(ms[i++]);
		val.icon=new StringCoder().fromString(ms[i++]);
		val.email=new StringCoder().fromString(ms[i++]);
		val.fb_id=new StringCoder().fromString(ms[i++]);
		val.twitter=new StringCoder().fromString(ms[i++]);
		val.web=new StringCoder().fromString(ms[i++]);
		val.confirmed=new BooleanCoder().fromString(ms[i++]);
		val.address=new StringCoder().fromString(ms[i++]);
		val.state=new StringCoder().fromString(ms[i++]);
		val.city=new StringCoder().fromString(ms[i++]);
		val.country_code=new StringCoder().fromString(ms[i++]);
		val.postal_code=new StringCoder().fromString(ms[i++]);
		val.organization=new StringCoder().fromString(ms[i++]);
		val.phone=new StringCoder().fromString(ms[i++]);
		val.mobile=new StringCoder().fromString(ms[i++]);
		val.fax=new StringCoder().fromString(ms[i++]);
		val.tags=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.gids=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.works=new ArrayCoder<Json>(new JsonCoder()).fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
