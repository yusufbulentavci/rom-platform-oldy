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



public class MailsCoder extends
		TypeCoder<Mails> {

	public MailsCoder(){
		super(true,new String[]{"ri","host_id","uri","container","html_file","modified_date","creation_date","delegated","ownercid","gid","relatedcids","mask","nesting","dbfs","weight","lexemes","rtags","aa","state","mime","remotehost","remoteaddr"});
	}

	@Override
	public  Mails decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Mails c=new Mails();

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
		c.state=new IntegerCoder().decode(json.get("state"));
		c.mime=new JsonCoder().decode(json.get("mime"));
		c.remotehost=new StringCoder().decode(json.get("remotehost"));
		c.remoteaddr=new StringCoder().decode(json.get("remoteaddr"));


		return c;
	}

	@Override
	public Object encode(Mails obj) throws JSONException {
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
		js.put("state",new IntegerCoder().encode(obj.state));
		js.put("mime",new JsonCoder().encode(obj.mime));
		js.put("remotehost",new StringCoder().encode(obj.remotehost));
		js.put("remoteaddr",new StringCoder().encode(obj.remoteaddr));

		return js;
	}

	@Override
	public  Mails[] createArray(int size) {
		return new  Mails[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Mails val) throws KnownError {
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
		new IntegerCoder().setDbValue(db3,val.state);
		new JsonCoder().setDbValue(db3,val.mime);
		new StringCoder().setDbValue(db3,val.remotehost);
		new StringCoder().setDbValue(db3,val.remoteaddr);

	}

	@Override
	public String toString(Mails val) throws KnownError {

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
		sb.append(',');sb.append(new IntegerCoder().quoted(val.state));
		sb.append(',');sb.append(new JsonCoder().quoted(val.mime));
		sb.append(',');sb.append(new StringCoder().quoted(val.remotehost));
		sb.append(',');sb.append(new StringCoder().quoted(val.remoteaddr));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Mails inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Mails val=new Mails();
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
		val.state=new IntegerCoder().getDbValue(db3);
		val.mime=new JsonCoder().getDbValue(db3);
		val.remotehost=new StringCoder().getDbValue(db3);
		val.remoteaddr=new StringCoder().getDbValue(db3);

		return val;
	}

	public Mails fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Mails val=new Mails();
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
		val.state=new IntegerCoder().fromString(ms[i++]);
		val.mime=new JsonCoder().fromString(ms[i++]);
		val.remotehost=new StringCoder().fromString(ms[i++]);
		val.remoteaddr=new StringCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
