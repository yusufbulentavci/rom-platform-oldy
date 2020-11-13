package com.bilgidoku.rom.gwt.araci.client.rom;

// factoryrender

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;
import com.google.gwt.json.client.*;
import com.bilgidoku.rom.gwt.shared.*;

import com.bilgidoku.rom.gwt.araci.client.rom.*;
import com.bilgidoku.rom.gwt.araci.client.bilgi.*;
import com.bilgidoku.rom.gwt.araci.client.site.*;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.*;
import com.bilgidoku.rom.gwt.araci.client.asset.*;



public class IssuesCoder extends
		TypeCoder<Issues> {


	@Override
	public  Issues decode(JSONValue js){
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
	public JSONValue encode( Issues obj) {
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

}
