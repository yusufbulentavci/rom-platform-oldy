package com.bilgidoku.rom.gwt.araci.server.asset;

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



public class AssetsCoder extends
		TypeCoder<Assets> {

	public AssetsCoder(){
		super(true,new String[]{"ri","host_id","uri","container","html_file","modified_date","creation_date","delegated","ownercid","gid","relatedcids","mask","nesting","dbfs","weight","lexemes","rtags","aa","writing","tariff","physical","reserved","amount","alertonleft","onsale","options","firststock","alternatives","virtualsparent","title","summary","tariffmodel","icon","simple_id"});
	}

	@Override
	public  Assets decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Assets c=new Assets();

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
		c.writing=new StringCoder().decode(json.get("writing"));
		c.tariff=new JsonCoder().decode(json.get("tariff"));
		c.physical=new JsonCoder().decode(json.get("physical"));
		c.reserved=new IntegerCoder().decode(json.get("reserved"));
		c.amount=new IntegerCoder().decode(json.get("amount"));
		c.alertonleft=new IntegerCoder().decode(json.get("alertonleft"));
		c.onsale=new BooleanCoder().decode(json.get("onsale"));
		c.options=new JsonCoder().decode(json.get("options"));
		c.firststock=new StringCoder().decode(json.get("firststock"));
		c.alternatives=new ArrayCoder<String>(new StringCoder()).decode(json.get("alternatives"));
		c.virtualsparent=new StringCoder().decode(json.get("virtualsparent"));
		c.title=new StringCoder().decode(json.get("title"));
		c.summary=new StringCoder().decode(json.get("summary"));
		c.tariffmodel=new StringCoder().decode(json.get("tariffmodel"));
		c.icon=new StringCoder().decode(json.get("icon"));
		c.simple_id=new IntegerCoder().decode(json.get("simple_id"));


		return c;
	}

	@Override
	public Object encode(Assets obj) throws JSONException {
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
		js.put("writing",new StringCoder().encode(obj.writing));
		js.put("tariff",new JsonCoder().encode(obj.tariff));
		js.put("physical",new JsonCoder().encode(obj.physical));
		js.put("reserved",new IntegerCoder().encode(obj.reserved));
		js.put("amount",new IntegerCoder().encode(obj.amount));
		js.put("alertonleft",new IntegerCoder().encode(obj.alertonleft));
		js.put("onsale",new BooleanCoder().encode(obj.onsale));
		js.put("options",new JsonCoder().encode(obj.options));
		js.put("firststock",new StringCoder().encode(obj.firststock));
		js.put("alternatives",new ArrayCoder<String>(new StringCoder()).encode(obj.alternatives));
		js.put("virtualsparent",new StringCoder().encode(obj.virtualsparent));
		js.put("title",new StringCoder().encode(obj.title));
		js.put("summary",new StringCoder().encode(obj.summary));
		js.put("tariffmodel",new StringCoder().encode(obj.tariffmodel));
		js.put("icon",new StringCoder().encode(obj.icon));
		js.put("simple_id",new IntegerCoder().encode(obj.simple_id));

		return js;
	}

	@Override
	public  Assets[] createArray(int size) {
		return new  Assets[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Assets val) throws KnownError {
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
		new StringCoder().setDbValue(db3,val.writing);
		new JsonCoder().setDbValue(db3,val.tariff);
		new JsonCoder().setDbValue(db3,val.physical);
		new IntegerCoder().setDbValue(db3,val.reserved);
		new IntegerCoder().setDbValue(db3,val.amount);
		new IntegerCoder().setDbValue(db3,val.alertonleft);
		new BooleanCoder().setDbValue(db3,val.onsale);
		new JsonCoder().setDbValue(db3,val.options);
		new StringCoder().setDbValue(db3,val.firststock);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.alternatives);
		new StringCoder().setDbValue(db3,val.virtualsparent);
		new StringCoder().setDbValue(db3,val.title);
		new StringCoder().setDbValue(db3,val.summary);
		new StringCoder().setDbValue(db3,val.tariffmodel);
		new StringCoder().setDbValue(db3,val.icon);
		new IntegerCoder().setDbValue(db3,val.simple_id);

	}

	@Override
	public String toString(Assets val) throws KnownError {

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
		sb.append(',');sb.append(new StringCoder().quoted(val.writing));
		sb.append(',');sb.append(new JsonCoder().quoted(val.tariff));
		sb.append(',');sb.append(new JsonCoder().quoted(val.physical));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.reserved));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.amount));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.alertonleft));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.onsale));
		sb.append(',');sb.append(new JsonCoder().quoted(val.options));
		sb.append(',');sb.append(new StringCoder().quoted(val.firststock));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.alternatives));
		sb.append(',');sb.append(new StringCoder().quoted(val.virtualsparent));
		sb.append(',');sb.append(new StringCoder().quoted(val.title));
		sb.append(',');sb.append(new StringCoder().quoted(val.summary));
		sb.append(',');sb.append(new StringCoder().quoted(val.tariffmodel));
		sb.append(',');sb.append(new StringCoder().quoted(val.icon));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.simple_id));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Assets inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Assets val=new Assets();
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
		val.writing=new StringCoder().getDbValue(db3);
		val.tariff=new JsonCoder().getDbValue(db3);
		val.physical=new JsonCoder().getDbValue(db3);
		val.reserved=new IntegerCoder().getDbValue(db3);
		val.amount=new IntegerCoder().getDbValue(db3);
		val.alertonleft=new IntegerCoder().getDbValue(db3);
		val.onsale=new BooleanCoder().getDbValue(db3);
		val.options=new JsonCoder().getDbValue(db3);
		val.firststock=new StringCoder().getDbValue(db3);
		val.alternatives=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.virtualsparent=new StringCoder().getDbValue(db3);
		val.title=new StringCoder().getDbValue(db3);
		val.summary=new StringCoder().getDbValue(db3);
		val.tariffmodel=new StringCoder().getDbValue(db3);
		val.icon=new StringCoder().getDbValue(db3);
		val.simple_id=new IntegerCoder().getDbValue(db3);

		return val;
	}

	public Assets fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Assets val=new Assets();
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
		val.writing=new StringCoder().fromString(ms[i++]);
		val.tariff=new JsonCoder().fromString(ms[i++]);
		val.physical=new JsonCoder().fromString(ms[i++]);
		val.reserved=new IntegerCoder().fromString(ms[i++]);
		val.amount=new IntegerCoder().fromString(ms[i++]);
		val.alertonleft=new IntegerCoder().fromString(ms[i++]);
		val.onsale=new BooleanCoder().fromString(ms[i++]);
		val.options=new JsonCoder().fromString(ms[i++]);
		val.firststock=new StringCoder().fromString(ms[i++]);
		val.alternatives=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.virtualsparent=new StringCoder().fromString(ms[i++]);
		val.title=new StringCoder().fromString(ms[i++]);
		val.summary=new StringCoder().fromString(ms[i++]);
		val.tariffmodel=new StringCoder().fromString(ms[i++]);
		val.icon=new StringCoder().fromString(ms[i++]);
		val.simple_id=new IntegerCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
