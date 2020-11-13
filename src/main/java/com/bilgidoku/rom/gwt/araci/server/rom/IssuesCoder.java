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



public class IssuesCoder extends
		TypeCoder<Issues> {

	public IssuesCoder(){
		super(true,new String[]{"ri","host_id","uri","container","html_file","modified_date","creation_date","delegated","ownercid","gid","relatedcids","mask","nesting","dbfs","weight","lexemes","rtags","aa","created_by","assigned_to","assign_date","resolve_date","close_date","dialog_uri","tags","lang_id","title","description","resolve_lang","resolve_desc","resolve_code","duplicate_issue","related_issues","start_date","stop_date","due_date","due_start","cls","ozne","nesne","oznetags","nesnetags"});
	}

	@Override
	public  Issues decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Issues c=new Issues();

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
		c.created_by=new StringCoder().decode(json.get("created_by"));
		c.assigned_to=new StringCoder().decode(json.get("assigned_to"));
		c.assign_date=new StringCoder().decode(json.get("assign_date"));
		c.resolve_date=new StringCoder().decode(json.get("resolve_date"));
		c.close_date=new StringCoder().decode(json.get("close_date"));
		c.dialog_uri=new StringCoder().decode(json.get("dialog_uri"));
		c.tags=new ArrayCoder<String>(new StringCoder()).decode(json.get("tags"));
		c.lang_id=new StringCoder().decode(json.get("lang_id"));
		c.title=new StringCoder().decode(json.get("title"));
		c.description=new StringCoder().decode(json.get("description"));
		c.resolve_lang=new StringCoder().decode(json.get("resolve_lang"));
		c.resolve_desc=new StringCoder().decode(json.get("resolve_desc"));
		c.resolve_code=new IntegerCoder().decode(json.get("resolve_code"));
		c.duplicate_issue=new StringCoder().decode(json.get("duplicate_issue"));
		c.related_issues=new ArrayCoder<String>(new StringCoder()).decode(json.get("related_issues"));
		c.start_date=new StringCoder().decode(json.get("start_date"));
		c.stop_date=new StringCoder().decode(json.get("stop_date"));
		c.due_date=new StringCoder().decode(json.get("due_date"));
		c.due_start=new StringCoder().decode(json.get("due_start"));
		c.cls=new StringCoder().decode(json.get("cls"));
		c.ozne=new ArrayCoder<Long>(new LongCoder()).decode(json.get("ozne"));
		c.nesne=new ArrayCoder<Long>(new LongCoder()).decode(json.get("nesne"));
		c.oznetags=new ArrayCoder<String>(new StringCoder()).decode(json.get("oznetags"));
		c.nesnetags=new ArrayCoder<String>(new StringCoder()).decode(json.get("nesnetags"));


		return c;
	}

	@Override
	public Object encode(Issues obj) throws JSONException {
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
		js.put("created_by",new StringCoder().encode(obj.created_by));
		js.put("assigned_to",new StringCoder().encode(obj.assigned_to));
		js.put("assign_date",new StringCoder().encode(obj.assign_date));
		js.put("resolve_date",new StringCoder().encode(obj.resolve_date));
		js.put("close_date",new StringCoder().encode(obj.close_date));
		js.put("dialog_uri",new StringCoder().encode(obj.dialog_uri));
		js.put("tags",new ArrayCoder<String>(new StringCoder()).encode(obj.tags));
		js.put("lang_id",new StringCoder().encode(obj.lang_id));
		js.put("title",new StringCoder().encode(obj.title));
		js.put("description",new StringCoder().encode(obj.description));
		js.put("resolve_lang",new StringCoder().encode(obj.resolve_lang));
		js.put("resolve_desc",new StringCoder().encode(obj.resolve_desc));
		js.put("resolve_code",new IntegerCoder().encode(obj.resolve_code));
		js.put("duplicate_issue",new StringCoder().encode(obj.duplicate_issue));
		js.put("related_issues",new ArrayCoder<String>(new StringCoder()).encode(obj.related_issues));
		js.put("start_date",new StringCoder().encode(obj.start_date));
		js.put("stop_date",new StringCoder().encode(obj.stop_date));
		js.put("due_date",new StringCoder().encode(obj.due_date));
		js.put("due_start",new StringCoder().encode(obj.due_start));
		js.put("cls",new StringCoder().encode(obj.cls));
		js.put("ozne",new ArrayCoder<Long>(new LongCoder()).encode(obj.ozne));
		js.put("nesne",new ArrayCoder<Long>(new LongCoder()).encode(obj.nesne));
		js.put("oznetags",new ArrayCoder<String>(new StringCoder()).encode(obj.oznetags));
		js.put("nesnetags",new ArrayCoder<String>(new StringCoder()).encode(obj.nesnetags));

		return js;
	}

	@Override
	public  Issues[] createArray(int size) {
		return new  Issues[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, Issues val) throws KnownError {
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
		new StringCoder().setDbValue(db3,val.created_by);
		new StringCoder().setDbValue(db3,val.assigned_to);
		new StringCoder().setDbValue(db3,val.assign_date);
		new StringCoder().setDbValue(db3,val.resolve_date);
		new StringCoder().setDbValue(db3,val.close_date);
		new StringCoder().setDbValue(db3,val.dialog_uri);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.tags);
		new StringCoder().setDbValue(db3,val.lang_id);
		new StringCoder().setDbValue(db3,val.title);
		new StringCoder().setDbValue(db3,val.description);
		new StringCoder().setDbValue(db3,val.resolve_lang);
		new StringCoder().setDbValue(db3,val.resolve_desc);
		new IntegerCoder().setDbValue(db3,val.resolve_code);
		new StringCoder().setDbValue(db3,val.duplicate_issue);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.related_issues);
		new StringCoder().setDbValue(db3,val.start_date);
		new StringCoder().setDbValue(db3,val.stop_date);
		new StringCoder().setDbValue(db3,val.due_date);
		new StringCoder().setDbValue(db3,val.due_start);
		new StringCoder().setDbValue(db3,val.cls);
		new ArrayCoder<Long>(new LongCoder()).setDbValue(db3,val.ozne);
		new ArrayCoder<Long>(new LongCoder()).setDbValue(db3,val.nesne);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.oznetags);
		new ArrayCoder<String>(new StringCoder()).setDbValue(db3,val.nesnetags);

	}

	@Override
	public String toString(Issues val) throws KnownError {

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
		sb.append(',');sb.append(new StringCoder().quoted(val.created_by));
		sb.append(',');sb.append(new StringCoder().quoted(val.assigned_to));
		sb.append(',');sb.append(new StringCoder().quoted(val.assign_date));
		sb.append(',');sb.append(new StringCoder().quoted(val.resolve_date));
		sb.append(',');sb.append(new StringCoder().quoted(val.close_date));
		sb.append(',');sb.append(new StringCoder().quoted(val.dialog_uri));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.tags));
		sb.append(',');sb.append(new StringCoder().quoted(val.lang_id));
		sb.append(',');sb.append(new StringCoder().quoted(val.title));
		sb.append(',');sb.append(new StringCoder().quoted(val.description));
		sb.append(',');sb.append(new StringCoder().quoted(val.resolve_lang));
		sb.append(',');sb.append(new StringCoder().quoted(val.resolve_desc));
		sb.append(',');sb.append(new IntegerCoder().quoted(val.resolve_code));
		sb.append(',');sb.append(new StringCoder().quoted(val.duplicate_issue));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.related_issues));
		sb.append(',');sb.append(new StringCoder().quoted(val.start_date));
		sb.append(',');sb.append(new StringCoder().quoted(val.stop_date));
		sb.append(',');sb.append(new StringCoder().quoted(val.due_date));
		sb.append(',');sb.append(new StringCoder().quoted(val.due_start));
		sb.append(',');sb.append(new StringCoder().quoted(val.cls));
		sb.append(',');sb.append(new ArrayCoder<Long>(new LongCoder()).quoted(val.ozne));
		sb.append(',');sb.append(new ArrayCoder<Long>(new LongCoder()).quoted(val.nesne));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.oznetags));
		sb.append(',');sb.append(new ArrayCoder<String>(new StringCoder()).quoted(val.nesnetags));

		sb.append(')');
		return sb.toString();
	}

	@Override
	protected Issues inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		Issues val=new Issues();
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
		val.created_by=new StringCoder().getDbValue(db3);
		val.assigned_to=new StringCoder().getDbValue(db3);
		val.assign_date=new StringCoder().getDbValue(db3);
		val.resolve_date=new StringCoder().getDbValue(db3);
		val.close_date=new StringCoder().getDbValue(db3);
		val.dialog_uri=new StringCoder().getDbValue(db3);
		val.tags=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.lang_id=new StringCoder().getDbValue(db3);
		val.title=new StringCoder().getDbValue(db3);
		val.description=new StringCoder().getDbValue(db3);
		val.resolve_lang=new StringCoder().getDbValue(db3);
		val.resolve_desc=new StringCoder().getDbValue(db3);
		val.resolve_code=new IntegerCoder().getDbValue(db3);
		val.duplicate_issue=new StringCoder().getDbValue(db3);
		val.related_issues=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.start_date=new StringCoder().getDbValue(db3);
		val.stop_date=new StringCoder().getDbValue(db3);
		val.due_date=new StringCoder().getDbValue(db3);
		val.due_start=new StringCoder().getDbValue(db3);
		val.cls=new StringCoder().getDbValue(db3);
		val.ozne=new ArrayCoder<Long>(new LongCoder()).getDbValue(db3);
		val.nesne=new ArrayCoder<Long>(new LongCoder()).getDbValue(db3);
		val.oznetags=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);
		val.nesnetags=new ArrayCoder<String>(new StringCoder()).getDbValue(db3);

		return val;
	}

	public Issues fromString(String str) throws JSONException{
		if(str==null)
			return null;

		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		Issues val=new Issues();
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
		val.created_by=new StringCoder().fromString(ms[i++]);
		val.assigned_to=new StringCoder().fromString(ms[i++]);
		val.assign_date=new StringCoder().fromString(ms[i++]);
		val.resolve_date=new StringCoder().fromString(ms[i++]);
		val.close_date=new StringCoder().fromString(ms[i++]);
		val.dialog_uri=new StringCoder().fromString(ms[i++]);
		val.tags=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.lang_id=new StringCoder().fromString(ms[i++]);
		val.title=new StringCoder().fromString(ms[i++]);
		val.description=new StringCoder().fromString(ms[i++]);
		val.resolve_lang=new StringCoder().fromString(ms[i++]);
		val.resolve_desc=new StringCoder().fromString(ms[i++]);
		val.resolve_code=new IntegerCoder().fromString(ms[i++]);
		val.duplicate_issue=new StringCoder().fromString(ms[i++]);
		val.related_issues=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.start_date=new StringCoder().fromString(ms[i++]);
		val.stop_date=new StringCoder().fromString(ms[i++]);
		val.due_date=new StringCoder().fromString(ms[i++]);
		val.due_start=new StringCoder().fromString(ms[i++]);
		val.cls=new StringCoder().fromString(ms[i++]);
		val.ozne=new ArrayCoder<Long>(new LongCoder()).fromString(ms[i++]);
		val.nesne=new ArrayCoder<Long>(new LongCoder()).fromString(ms[i++]);
		val.oznetags=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);
		val.nesnetags=new ArrayCoder<String>(new StringCoder()).fromString(ms[i++]);

		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}
