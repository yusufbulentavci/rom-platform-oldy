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



public class OrgCoder extends
		TypeCoder<Org> {

	public OrgCoder(){
		super(true,new String[]{"ri","host_id","uri","container","html_file","modified_date","creation_date","delegated","ownercid","gid","relatedcids","mask","nesting","dbfs","weight","lexemes","rtags","aa","issue_year","issue_nextid","shipstyle","paystyle","langcodes","cpic","crs","fbappid","nfs","startssl","ga","forcehttps"});
	}

	@Override
	public  Org decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Org c=new Org();

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
		c.issue_year=new IntegerCoder().decode(json.get("issue_year"));
		c.issue_nextid=new IntegerCoder().decode(json.get("issue_nextid"));
		c.shipstyle=new JsonCoder().decode(json.get("shipstyle"));
		c.paystyle=new JsonCoder().decode(json.get("paystyle"));
		c.langcodes=new ArrayCoder<String>(new StringCoder()).decode(json.get("langcodes"));
		c.cpic=new ArrayCoder<String>(new StringCoder()).decode(json.get("cpic"));
		c.crs=new ArrayCoder<String>(new StringCoder()).decode(json.get("crs"));
		c.fbappid=new StringCoder().decode(json.get("fbappid"));
		c.nfs=new JsonCoder().decode(json.get("nfs"));
		c.startssl=new StringCoder().decode(json.get("startssl"));
		c.ga=new StringCoder().decode(json.get("ga"));
		c.forcehttps=new BooleanCoder().decode(json.get("forcehttps"));


		return c;
	}

	@Override
	public Object encode(Org obj) throws JSONException {
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
		js.put("issue_year",new IntegerCoder().encode(obj.issue_year));
		js.put("issue_nextid",new IntegerCoder().encode(obj.issue_nextid));
		js.put("shipstyle",new JsonCoder().encode(obj.shipstyle));
		js.put("paystyle",new JsonCoder().encode(obj.paystyle));
		js.put("langcodes",new ArrayCoder<String>(new StringCoder()).encode(obj.langcodes));
		js.put("cpic",new ArrayCoder<String>(new StringCoder()).encode(obj.cpic));
		js.put("crs",new ArrayCoder<String>(new StringCoder()).encode(obj.crs));
		js.put("fbappid",new StringCoder().encode(obj.fbappid));
		js.put("nfs",new JsonCoder().encode(obj.nfs));
		js.put("startssl",new StringCoder().encode(obj.startssl));
		js.put("ga",new StringCoder().encode(obj.ga));
		js.put("forcehttps",new BooleanCoder().encode(obj.forcehttps));

		return js;
	}

	@Override
	public  Org[] createArray(int size) {
		return new  Org[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Org val) throws KnownError {
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
		new IntegerCoder().setDbValue(db3,val.issue_year);
		new IntegerCoder().setDbValue(db3,val.issue_nextid);
		new JsonCoder().setDbValue(db3,val.shipstyle);
		new JsonCoder().setDbValue(db3,val.paystyle);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.langcodes);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.cpic);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.crs);
		new StringCoder().setDbValue(db3,val.fbappid);
		new JsonCoder().setDbValue(db3,val.nfs);
		new StringCoder().setDbValue(db3,val.startssl);
		new StringCoder().setDbValue(db3,val.ga);
		new BooleanCoder().setDbValue(db3,val.forcehttps);

	}

	@Override
	public String toString(Org val) throws KnownError {

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
		sb.append(',');sb.append(new IntegerCoder().quoted(val.issue_year));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.issue_nextid));
		sb.append(',');sb.append(new JsonCoder().quoted(val.shipstyle));
		sb.append(',');sb.append(new JsonCoder().quoted(val.paystyle));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.langcodes));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.cpic));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.crs));
		sb.append(',');sb.append(new StringCoder().quoted(val.fbappid));
		sb.append(',');sb.append(new JsonCoder().quoted(val.nfs));
		sb.append(',');sb.append(new StringCoder().quoted(val.startssl));
		sb.append(',');sb.append(new StringCoder().quoted(val.ga));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.forcehttps));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Org inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Org val=new Org();
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
		val.issue_year=new IntegerCoder().getDbValue(db3);
		val.issue_nextid=new IntegerCoder().getDbValue(db3);
		val.shipstyle=new JsonCoder().getDbValue(db3);
		val.paystyle=new JsonCoder().getDbValue(db3);
		val.langcodes=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.cpic=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.crs=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.fbappid=new StringCoder().getDbValue(db3);
		val.nfs=new JsonCoder().getDbValue(db3);
		val.startssl=new StringCoder().getDbValue(db3);
		val.ga=new StringCoder().getDbValue(db3);
		val.forcehttps=new BooleanCoder().getDbValue(db3);

		return val;
	}

	public Org fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Org val=new Org();
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
		val.issue_year=new IntegerCoder().fromString(ms[i++]);
		val.issue_nextid=new IntegerCoder().fromString(ms[i++]);
		val.shipstyle=new JsonCoder().fromString(ms[i++]);
		val.paystyle=new JsonCoder().fromString(ms[i++]);
		val.langcodes=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.cpic=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.crs=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.fbappid=new StringCoder().fromString(ms[i++]);
		val.nfs=new JsonCoder().fromString(ms[i++]);
		val.startssl=new StringCoder().fromString(ms[i++]);
		val.ga=new StringCoder().fromString(ms[i++]);
		val.forcehttps=new BooleanCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
