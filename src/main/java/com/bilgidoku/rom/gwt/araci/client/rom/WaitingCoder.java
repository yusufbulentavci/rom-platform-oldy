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



public class WaitingCoder extends
		TypeCoder<Waiting> {


	@Override
	public  Waiting decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Waiting c=new Waiting();

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
		c.app=new StringCoder().decode(json.get("app"));
		c.code=new StringCoder().decode(json.get("code"));
		c.inref=new ArrayCoder<String>(new StringCoder()).decode(json.get("inref"));
		c.valid_after=new StringCoder().decode(json.get("valid_after"));
		c.valid_before=new StringCoder().decode(json.get("valid_before"));
		c.times=new IntegerCoder().decode(json.get("times"));
		c.title=new ArrayCoder<String>(new StringCoder()).decode(json.get("title"));
		c.username=new ArrayCoder<String>(new StringCoder()).decode(json.get("username"));


		return c;
	}

	@Override
	public JSONValue encode( Waiting obj) {
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
		js.put("app",new StringCoder().encode(obj.app));
		js.put("code",new StringCoder().encode(obj.code));
		js.put("inref",new ArrayCoder<String>(new StringCoder()).encode(obj.inref));
		js.put("valid_after",new StringCoder().encode(obj.valid_after));
		js.put("valid_before",new StringCoder().encode(obj.valid_before));
		js.put("times",new IntegerCoder().encode(obj.times));
		js.put("title",new ArrayCoder<String>(new StringCoder()).encode(obj.title));
		js.put("username",new ArrayCoder<String>(new StringCoder()).encode(obj.username));

		return js;
	}

	@Override
	public  Waiting[] createArray(int size) {
		return new  Waiting[size];
	}

}
