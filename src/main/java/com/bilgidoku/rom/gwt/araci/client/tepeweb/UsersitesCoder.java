package com.bilgidoku.rom.gwt.araci.client.tepeweb;

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



public class UsersitesCoder extends
		TypeCoder<Usersites> {


	@Override
	public  Usersites decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Usersites c=new Usersites();

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
		c.title=new StringCoder().decode(json.get("title"));
		c.remotehost=new IntegerCoder().decode(json.get("remotehost"));
		c.remotesite=new StringCoder().decode(json.get("remotesite"));
		c.remoteworld=new StringCoder().decode(json.get("remoteworld"));
		c.lastip=new StringCoder().decode(json.get("lastip"));
		c.lastcountry=new StringCoder().decode(json.get("lastcountry"));
		c.lastactivity=new StringCoder().decode(json.get("lastactivity"));


		return c;
	}

	@Override
	public JSONValue encode( Usersites obj) {
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
		js.put("title",new StringCoder().encode(obj.title));
		js.put("remotehost",new IntegerCoder().encode(obj.remotehost));
		js.put("remotesite",new StringCoder().encode(obj.remotesite));
		js.put("remoteworld",new StringCoder().encode(obj.remoteworld));
		js.put("lastip",new StringCoder().encode(obj.lastip));
		js.put("lastcountry",new StringCoder().encode(obj.lastcountry));
		js.put("lastactivity",new StringCoder().encode(obj.lastactivity));

		return js;
	}

	@Override
	public  Usersites[] createArray(int size) {
		return new  Usersites[size];
	}

}
