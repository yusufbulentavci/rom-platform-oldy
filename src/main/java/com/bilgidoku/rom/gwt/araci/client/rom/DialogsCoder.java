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



public class DialogsCoder extends
		TypeCoder<Dialogs> {


	@Override
	public  Dialogs decode(JSONValue js){
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
	public JSONValue encode( Dialogs obj) {
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

}
