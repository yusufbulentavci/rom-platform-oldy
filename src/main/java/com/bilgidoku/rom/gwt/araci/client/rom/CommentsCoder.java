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



public class CommentsCoder extends
		TypeCoder<Comments> {


	@Override
	public  Comments decode(JSONValue js){
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
	public JSONValue encode( Comments obj) {
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

}
