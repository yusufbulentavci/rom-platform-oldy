package com.bilgidoku.rom.gwt.araci.server.site;

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



public class HitsCoder extends
		TypeCoder<Hits> {

	public HitsCoder(){
		super(true,new String[]{"ri","host_id","uri","container","html_file","modified_date","creation_date","delegated","ownercid","gid","relatedcids","mask","nesting","dbfs","weight","lexemes","rtags","aa","timebegin","timeend","ops","browsers","referrers","inhits","countries","langs","pageperiods","bringingwords","cpu","netread","netwrite"});
	}

	@Override
	public  Hits decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Hits c=new Hits();

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
		c.timebegin=new LongCoder().decode(json.get("timebegin"));
		c.timeend=new LongCoder().decode(json.get("timeend"));
		c.ops=new JsonCoder().decode(json.get("ops"));
		c.browsers=new JsonCoder().decode(json.get("browsers"));
		c.referrers=new JsonCoder().decode(json.get("referrers"));
		c.inhits=new JsonCoder().decode(json.get("inhits"));
		c.countries=new JsonCoder().decode(json.get("countries"));
		c.langs=new JsonCoder().decode(json.get("langs"));
		c.pageperiods=new JsonCoder().decode(json.get("pageperiods"));
		c.bringingwords=new JsonCoder().decode(json.get("bringingwords"));
		c.cpu=new LongCoder().decode(json.get("cpu"));
		c.netread=new LongCoder().decode(json.get("netread"));
		c.netwrite=new LongCoder().decode(json.get("netwrite"));


		return c;
	}

	@Override
	public Object encode(Hits obj) throws JSONException {
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
		js.put("timebegin",new LongCoder().encode(obj.timebegin));
		js.put("timeend",new LongCoder().encode(obj.timeend));
		js.put("ops",new JsonCoder().encode(obj.ops));
		js.put("browsers",new JsonCoder().encode(obj.browsers));
		js.put("referrers",new JsonCoder().encode(obj.referrers));
		js.put("inhits",new JsonCoder().encode(obj.inhits));
		js.put("countries",new JsonCoder().encode(obj.countries));
		js.put("langs",new JsonCoder().encode(obj.langs));
		js.put("pageperiods",new JsonCoder().encode(obj.pageperiods));
		js.put("bringingwords",new JsonCoder().encode(obj.bringingwords));
		js.put("cpu",new LongCoder().encode(obj.cpu));
		js.put("netread",new LongCoder().encode(obj.netread));
		js.put("netwrite",new LongCoder().encode(obj.netwrite));

		return js;
	}

	@Override
	public  Hits[] createArray(int size) {
		return new  Hits[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Hits val) throws KnownError {
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
		new LongCoder().setDbValue(db3,val.timebegin);
		new LongCoder().setDbValue(db3,val.timeend);
		new JsonCoder().setDbValue(db3,val.ops);
		new JsonCoder().setDbValue(db3,val.browsers);
		new JsonCoder().setDbValue(db3,val.referrers);
		new JsonCoder().setDbValue(db3,val.inhits);
		new JsonCoder().setDbValue(db3,val.countries);
		new JsonCoder().setDbValue(db3,val.langs);
		new JsonCoder().setDbValue(db3,val.pageperiods);
		new JsonCoder().setDbValue(db3,val.bringingwords);
		new LongCoder().setDbValue(db3,val.cpu);
		new LongCoder().setDbValue(db3,val.netread);
		new LongCoder().setDbValue(db3,val.netwrite);

	}

	@Override
	public String toString(Hits val) throws KnownError {

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
		sb.append(',');sb.append(new LongCoder().quoted(val.timebegin));
		sb.append(',');sb.append(new LongCoder().quoted(val.timeend));
		sb.append(',');sb.append(new JsonCoder().quoted(val.ops));
		sb.append(',');sb.append(new JsonCoder().quoted(val.browsers));
		sb.append(',');sb.append(new JsonCoder().quoted(val.referrers));
		sb.append(',');sb.append(new JsonCoder().quoted(val.inhits));
		sb.append(',');sb.append(new JsonCoder().quoted(val.countries));
		sb.append(',');sb.append(new JsonCoder().quoted(val.langs));
		sb.append(',');sb.append(new JsonCoder().quoted(val.pageperiods));
		sb.append(',');sb.append(new JsonCoder().quoted(val.bringingwords));
		sb.append(',');sb.append(new LongCoder().quoted(val.cpu));
		sb.append(',');sb.append(new LongCoder().quoted(val.netread));
		sb.append(',');sb.append(new LongCoder().quoted(val.netwrite));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Hits inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Hits val=new Hits();
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
		val.timebegin=new LongCoder().getDbValue(db3);
		val.timeend=new LongCoder().getDbValue(db3);
		val.ops=new JsonCoder().getDbValue(db3);
		val.browsers=new JsonCoder().getDbValue(db3);
		val.referrers=new JsonCoder().getDbValue(db3);
		val.inhits=new JsonCoder().getDbValue(db3);
		val.countries=new JsonCoder().getDbValue(db3);
		val.langs=new JsonCoder().getDbValue(db3);
		val.pageperiods=new JsonCoder().getDbValue(db3);
		val.bringingwords=new JsonCoder().getDbValue(db3);
		val.cpu=new LongCoder().getDbValue(db3);
		val.netread=new LongCoder().getDbValue(db3);
		val.netwrite=new LongCoder().getDbValue(db3);

		return val;
	}

	public Hits fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Hits val=new Hits();
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
		val.timebegin=new LongCoder().fromString(ms[i++]);
		val.timeend=new LongCoder().fromString(ms[i++]);
		val.ops=new JsonCoder().fromString(ms[i++]);
		val.browsers=new JsonCoder().fromString(ms[i++]);
		val.referrers=new JsonCoder().fromString(ms[i++]);
		val.inhits=new JsonCoder().fromString(ms[i++]);
		val.countries=new JsonCoder().fromString(ms[i++]);
		val.langs=new JsonCoder().fromString(ms[i++]);
		val.pageperiods=new JsonCoder().fromString(ms[i++]);
		val.bringingwords=new JsonCoder().fromString(ms[i++]);
		val.cpu=new LongCoder().fromString(ms[i++]);
		val.netread=new LongCoder().fromString(ms[i++]);
		val.netwrite=new LongCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
