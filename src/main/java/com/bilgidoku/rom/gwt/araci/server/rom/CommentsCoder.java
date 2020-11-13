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



public class CommentsCoder extends
		TypeCoder<Comments> {

	public CommentsCoder(){
		super(true,new String[]{"ri","host_id","uri","container","html_file","modified_date","creation_date","delegated","ownercid","gid","relatedcids","mask","nesting","dbfs","weight","lexemes","rtags","aa","dialog_id","contact","lang_id","comment","cmd","mime","bymail","approved","refer_comment","likes","dislikes","onpage"});
	}

	@Override
	public  Comments decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Comments c=new Comments();

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
		c.dialog_id=new StringCoder().decode(json.get("dialog_id"));
		c.contact=new StringCoder().decode(json.get("contact"));
		c.lang_id=new StringCoder().decode(json.get("lang_id"));
		c.comment=new StringCoder().decode(json.get("comment"));
		c.cmd=new JsonCoder().decode(json.get("cmd"));
		c.mime=new JsonCoder().decode(json.get("mime"));
		c.bymail=new StringCoder().decode(json.get("bymail"));
		c.approved=new BooleanCoder().decode(json.get("approved"));
		c.refer_comment=new StringCoder().decode(json.get("refer_comment"));
		c.likes=new ArrayCoder<String>(new StringCoder()).decode(json.get("likes"));
		c.dislikes=new ArrayCoder<String>(new StringCoder()).decode(json.get("dislikes"));
		c.onpage=new BooleanCoder().decode(json.get("onpage"));


		return c;
	}

	@Override
	public Object encode(Comments obj) throws JSONException {
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
		js.put("dialog_id",new StringCoder().encode(obj.dialog_id));
		js.put("contact",new StringCoder().encode(obj.contact));
		js.put("lang_id",new StringCoder().encode(obj.lang_id));
		js.put("comment",new StringCoder().encode(obj.comment));
		js.put("cmd",new JsonCoder().encode(obj.cmd));
		js.put("mime",new JsonCoder().encode(obj.mime));
		js.put("bymail",new StringCoder().encode(obj.bymail));
		js.put("approved",new BooleanCoder().encode(obj.approved));
		js.put("refer_comment",new StringCoder().encode(obj.refer_comment));
		js.put("likes",new ArrayCoder<String>(new StringCoder()).encode(obj.likes));
		js.put("dislikes",new ArrayCoder<String>(new StringCoder()).encode(obj.dislikes));
		js.put("onpage",new BooleanCoder().encode(obj.onpage));

		return js;
	}

	@Override
	public  Comments[] createArray(int size) {
		return new  Comments[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Comments val) throws KnownError {
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
		new StringCoder().setDbValue(db3,val.dialog_id);
		new StringCoder().setDbValue(db3,val.contact);
		new StringCoder().setDbValue(db3,val.lang_id);
		new StringCoder().setDbValue(db3,val.comment);
		new JsonCoder().setDbValue(db3,val.cmd);
		new JsonCoder().setDbValue(db3,val.mime);
		new StringCoder().setDbValue(db3,val.bymail);
		new BooleanCoder().setDbValue(db3,val.approved);
		new StringCoder().setDbValue(db3,val.refer_comment);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.likes);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.dislikes);
		new BooleanCoder().setDbValue(db3,val.onpage);

	}

	@Override
	public String toString(Comments val) throws KnownError {

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
		sb.append(',');sb.append(new StringCoder().quoted(val.dialog_id));
		sb.append(',');sb.append(new StringCoder().quoted(val.contact));
		sb.append(',');sb.append(new StringCoder().quoted(val.lang_id));
		sb.append(',');sb.append(new StringCoder().quoted(val.comment));
		sb.append(',');sb.append(new JsonCoder().quoted(val.cmd));
		sb.append(',');sb.append(new JsonCoder().quoted(val.mime));
		sb.append(',');sb.append(new StringCoder().quoted(val.bymail));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.approved));
		sb.append(',');sb.append(new StringCoder().quoted(val.refer_comment));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.likes));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.dislikes));
		sb.append(',');sb.append(new BooleanCoder().quoted(val.onpage));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Comments inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Comments val=new Comments();
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
		val.dialog_id=new StringCoder().getDbValue(db3);
		val.contact=new StringCoder().getDbValue(db3);
		val.lang_id=new StringCoder().getDbValue(db3);
		val.comment=new StringCoder().getDbValue(db3);
		val.cmd=new JsonCoder().getDbValue(db3);
		val.mime=new JsonCoder().getDbValue(db3);
		val.bymail=new StringCoder().getDbValue(db3);
		val.approved=new BooleanCoder().getDbValue(db3);
		val.refer_comment=new StringCoder().getDbValue(db3);
		val.likes=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.dislikes=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.onpage=new BooleanCoder().getDbValue(db3);

		return val;
	}

	public Comments fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Comments val=new Comments();
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
		val.dialog_id=new StringCoder().fromString(ms[i++]);
		val.contact=new StringCoder().fromString(ms[i++]);
		val.lang_id=new StringCoder().fromString(ms[i++]);
		val.comment=new StringCoder().fromString(ms[i++]);
		val.cmd=new JsonCoder().fromString(ms[i++]);
		val.mime=new JsonCoder().fromString(ms[i++]);
		val.bymail=new StringCoder().fromString(ms[i++]);
		val.approved=new BooleanCoder().fromString(ms[i++]);
		val.refer_comment=new StringCoder().fromString(ms[i++]);
		val.likes=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.dislikes=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.onpage=new BooleanCoder().fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
