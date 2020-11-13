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



public class ContactsCoder extends
		TypeCoder<Contacts> {


	@Override
	public  Contacts decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Contacts c=new Contacts();

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
		c.lang_id=new StringCoder().decode(json.get("lang_id"));
		c.cipher=new StringCoder().decode(json.get("cipher"));
		c.first_name=new StringCoder().decode(json.get("first_name"));
		c.last_name=new StringCoder().decode(json.get("last_name"));
		c.icon=new StringCoder().decode(json.get("icon"));
		c.email=new StringCoder().decode(json.get("email"));
		c.fb_id=new StringCoder().decode(json.get("fb_id"));
		c.twitter=new StringCoder().decode(json.get("twitter"));
		c.web=new StringCoder().decode(json.get("web"));
		c.confirmed=new BooleanCoder().decode(json.get("confirmed"));
		c.address=new StringCoder().decode(json.get("address"));
		c.state=new StringCoder().decode(json.get("state"));
		c.city=new StringCoder().decode(json.get("city"));
		c.country_code=new StringCoder().decode(json.get("country_code"));
		c.postal_code=new StringCoder().decode(json.get("postal_code"));
		c.organization=new StringCoder().decode(json.get("organization"));
		c.phone=new StringCoder().decode(json.get("phone"));
		c.mobile=new StringCoder().decode(json.get("mobile"));
		c.fax=new StringCoder().decode(json.get("fax"));
		c.tags=new ArrayCoder<String>(new StringCoder()).decode(json.get("tags"));
		c.gids=new ArrayCoder<String>(new StringCoder()).decode(json.get("gids"));
		c.works=new ArrayCoder<Json>(new JsonCoder()).decode(json.get("works"));


		return c;
	}

	@Override
	public JSONValue encode( Contacts obj) {
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
		js.put("lang_id",new StringCoder().encode(obj.lang_id));
		js.put("cipher",new StringCoder().encode(obj.cipher));
		js.put("first_name",new StringCoder().encode(obj.first_name));
		js.put("last_name",new StringCoder().encode(obj.last_name));
		js.put("icon",new StringCoder().encode(obj.icon));
		js.put("email",new StringCoder().encode(obj.email));
		js.put("fb_id",new StringCoder().encode(obj.fb_id));
		js.put("twitter",new StringCoder().encode(obj.twitter));
		js.put("web",new StringCoder().encode(obj.web));
		js.put("confirmed",new BooleanCoder().encode(obj.confirmed));
		js.put("address",new StringCoder().encode(obj.address));
		js.put("state",new StringCoder().encode(obj.state));
		js.put("city",new StringCoder().encode(obj.city));
		js.put("country_code",new StringCoder().encode(obj.country_code));
		js.put("postal_code",new StringCoder().encode(obj.postal_code));
		js.put("organization",new StringCoder().encode(obj.organization));
		js.put("phone",new StringCoder().encode(obj.phone));
		js.put("mobile",new StringCoder().encode(obj.mobile));
		js.put("fax",new StringCoder().encode(obj.fax));
		js.put("tags",new ArrayCoder<String>(new StringCoder()).encode(obj.tags));
		js.put("gids",new ArrayCoder<String>(new StringCoder()).encode(obj.gids));
		js.put("works",new ArrayCoder<Json>(new JsonCoder()).encode(obj.works));

		return js;
	}

	@Override
	public  Contacts[] createArray(int size) {
		return new  Contacts[size];
	}

}
