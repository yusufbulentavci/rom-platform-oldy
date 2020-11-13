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



public class ContentsCoder extends
		TypeCoder<Contents> {

	public ContentsCoder(){
		super(true,new String[]{"ri","host_id","uri","container","html_file","modified_date","creation_date","delegated","ownercid","gid","relatedcids","mask","nesting","dbfs","weight","lexemes","rtags","aa","title","summary","tip","icon","medium_icon","large_icon","multilang_icon","sound","langcodes","viewy"});
	}

	@Override
	public  Contents decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Contents c=new Contents();

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
		c.title=new ArrayCoder<String>(new StringCoder()).decode(json.get("title"));
		c.summary=new ArrayCoder<String>(new StringCoder()).decode(json.get("summary"));
		c.tip=new ArrayCoder<String>(new StringCoder()).decode(json.get("tip"));
		c.icon=new StringCoder().decode(json.get("icon"));
		c.medium_icon=new StringCoder().decode(json.get("medium_icon"));
		c.large_icon=new StringCoder().decode(json.get("large_icon"));
		c.multilang_icon=new ArrayCoder<String>(new StringCoder()).decode(json.get("multilang_icon"));
		c.sound=new StringCoder().decode(json.get("sound"));
		c.langcodes=new ArrayCoder<String>(new StringCoder()).decode(json.get("langcodes"));
		c.viewy=new JsonCoder().decode(json.get("viewy"));


		return c;
	}

	@Override
	public Object encode(Contents obj) throws JSONException {
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
		js.put("title",new ArrayCoder<String>(new StringCoder()).encode(obj.title));
		js.put("summary",new ArrayCoder<String>(new StringCoder()).encode(obj.summary));
		js.put("tip",new ArrayCoder<String>(new StringCoder()).encode(obj.tip));
		js.put("icon",new StringCoder().encode(obj.icon));
		js.put("medium_icon",new StringCoder().encode(obj.medium_icon));
		js.put("large_icon",new StringCoder().encode(obj.large_icon));
		js.put("multilang_icon",new ArrayCoder<String>(new StringCoder()).encode(obj.multilang_icon));
		js.put("sound",new StringCoder().encode(obj.sound));
		js.put("langcodes",new ArrayCoder<String>(new StringCoder()).encode(obj.langcodes));
		js.put("viewy",new JsonCoder().encode(obj.viewy));

		return js;
	}

	@Override
	public  Contents[] createArray(int size) {
		return new  Contents[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Contents val) throws KnownError {
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
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.title);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.summary);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.tip);
		new StringCoder().setDbValue(db3,val.icon);
		new StringCoder().setDbValue(db3,val.medium_icon);
		new StringCoder().setDbValue(db3,val.large_icon);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.multilang_icon);
		new StringCoder().setDbValue(db3,val.sound);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.langcodes);
		new JsonCoder().setDbValue(db3,val.viewy);

	}

	@Override
	public String toString(Contents val) throws KnownError {

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
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.title));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.summary));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.tip));
		sb.append(',');sb.append(new StringCoder().quoted(val.icon));
		sb.append(',');sb.append(new StringCoder().quoted(val.medium_icon));
		sb.append(',');sb.append(new StringCoder().quoted(val.large_icon));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.multilang_icon));
		sb.append(',');sb.append(new StringCoder().quoted(val.sound));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.langcodes));
		sb.append(',');sb.append(new JsonCoder().quoted(val.viewy));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Contents inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Contents val=new Contents();
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
		val.title=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.summary=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.tip=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.icon=new StringCoder().getDbValue(db3);
		val.medium_icon=new StringCoder().getDbValue(db3);
		val.large_icon=new StringCoder().getDbValue(db3);
		val.multilang_icon=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.sound=new StringCoder().getDbValue(db3);
		val.langcodes=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.viewy=new JsonCoder().getDbValue(db3);

		return val;
	}

	public Contents fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Contents val=new Contents();
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
		val.title=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.summary=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.tip=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.icon=new StringCoder().fromString(ms[i++]);
		val.medium_icon=new StringCoder().fromString(ms[i++]);
		val.large_icon=new StringCoder().fromString(ms[i++]);
		val.multilang_icon=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.sound=new StringCoder().fromString(ms[i++]);
		val.langcodes=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.viewy=new JsonCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
