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



public class DialogsCoder extends
		TypeCoder<Dialogs> {

	public DialogsCoder(){
		super(true,new String[]{"ri","host_id","uri","container","html_file","modified_date","creation_date","delegated","ownercid","gid","relatedcids","mask","nesting","dbfs","weight","lexemes","rtags","aa","allow_attach","approval","deletable","updatable","likeable","dislikable","sharable","closed","contacts","cafe"});
	}

	@Override
	public  Dialogs decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Dialogs c=new Dialogs();

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
		c.allow_attach=new BooleanCoder().decode(json.get("allow_attach"));
		c.approval=new BooleanCoder().decode(json.get("approval"));
		c.deletable=new BooleanCoder().decode(json.get("deletable"));
		c.updatable=new BooleanCoder().decode(json.get("updatable"));
		c.likeable=new BooleanCoder().decode(json.get("likeable"));
		c.dislikable=new BooleanCoder().decode(json.get("dislikable"));
		c.sharable=new BooleanCoder().decode(json.get("sharable"));
		c.closed=new BooleanCoder().decode(json.get("closed"));
		c.contacts=new ArrayCoder<String>(new StringCoder()).decode(json.get("contacts"));
		c.cafe=new BooleanCoder().decode(json.get("cafe"));


		return c;
	}

	@Override
	public Object encode(Dialogs obj) throws JSONException {
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
		js.put("allow_attach",new BooleanCoder().encode(obj.allow_attach));
		js.put("approval",new BooleanCoder().encode(obj.approval));
		js.put("deletable",new BooleanCoder().encode(obj.deletable));
		js.put("updatable",new BooleanCoder().encode(obj.updatable));
		js.put("likeable",new BooleanCoder().encode(obj.likeable));
		js.put("dislikable",new BooleanCoder().encode(obj.dislikable));
		js.put("sharable",new BooleanCoder().encode(obj.sharable));
		js.put("closed",new BooleanCoder().encode(obj.closed));
		js.put("contacts",new ArrayCoder<String>(new StringCoder()).encode(obj.contacts));
		js.put("cafe",new BooleanCoder().encode(obj.cafe));

		return js;
	}

	@Override
	public  Dialogs[] createArray(int size) {
		return new  Dialogs[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Dialogs val) throws KnownError {
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
		new BooleanCoder().setDbValue(db3,val.allow_attach);
		new BooleanCoder().setDbValue(db3,val.approval);
		new BooleanCoder().setDbValue(db3,val.deletable);
		new BooleanCoder().setDbValue(db3,val.updatable);
		new BooleanCoder().setDbValue(db3,val.likeable);
		new BooleanCoder().setDbValue(db3,val.dislikable);
		new BooleanCoder().setDbValue(db3,val.sharable);
		new BooleanCoder().setDbValue(db3,val.closed);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.contacts);
		new BooleanCoder().setDbValue(db3,val.cafe);

	}

	@Override
	public String toString(Dialogs val) throws KnownError {

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
		sb.append(',');sb.append(new BooleanCoder().quoted(val.allow_attach));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.approval));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.deletable));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.updatable));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.likeable));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.dislikable));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.sharable));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.closed));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.contacts));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.cafe));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Dialogs inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Dialogs val=new Dialogs();
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
		val.allow_attach=new BooleanCoder().getDbValue(db3);
		val.approval=new BooleanCoder().getDbValue(db3);
		val.deletable=new BooleanCoder().getDbValue(db3);
		val.updatable=new BooleanCoder().getDbValue(db3);
		val.likeable=new BooleanCoder().getDbValue(db3);
		val.dislikable=new BooleanCoder().getDbValue(db3);
		val.sharable=new BooleanCoder().getDbValue(db3);
		val.closed=new BooleanCoder().getDbValue(db3);
		val.contacts=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.cafe=new BooleanCoder().getDbValue(db3);

		return val;
	}

	public Dialogs fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Dialogs val=new Dialogs();
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
		val.allow_attach=new BooleanCoder().fromString(ms[i++]);
		val.approval=new BooleanCoder().fromString(ms[i++]);
		val.deletable=new BooleanCoder().fromString(ms[i++]);
		val.updatable=new BooleanCoder().fromString(ms[i++]);
		val.likeable=new BooleanCoder().fromString(ms[i++]);
		val.dislikable=new BooleanCoder().fromString(ms[i++]);
		val.sharable=new BooleanCoder().fromString(ms[i++]);
		val.closed=new BooleanCoder().fromString(ms[i++]);
		val.contacts=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.cafe=new BooleanCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
