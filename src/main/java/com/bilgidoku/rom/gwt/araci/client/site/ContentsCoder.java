package com.bilgidoku.rom.gwt.araci.client.site;

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



public class ContentsCoder extends
		TypeCoder<Contents> {


	@Override
	public  Contents decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Contents c=new Contents();

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
		c.title=new ArrayCoder<String>(new StringCoder()).decode(json.get("title"));
		c.summary=new ArrayCoder<String>(new StringCoder()).decode(json.get("summary"));
		c.tip=new ArrayCoder<String>(new StringCoder()).decode(json.get("tip"));
		c.icon=new StringCoder().decode(json.get("icon"));
		c.medium_icon=new StringCoder().decode(json.get("medium_icon"));
		c.large_icon=new StringCoder().decode(json.get("large_icon"));
		c.multilang_icon=new ArrayCoder<String>(new StringCoder()).decode(json.get("multilang_icon"));
		c.sound=new StringCoder().decode(json.get("sound"));
		c.langcodes=new ArrayCoder<String>(new StringCoder()).decode(json.get("langcodes"));
		c.viewy=new JsonCoder().decode(json.get("viewy"));


		return c;
	}

	@Override
	public JSONValue encode( Contents obj) {
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
		js.put("title",new ArrayCoder<String>(new StringCoder()).encode(obj.title));
		js.put("summary",new ArrayCoder<String>(new StringCoder()).encode(obj.summary));
		js.put("tip",new ArrayCoder<String>(new StringCoder()).encode(obj.tip));
		js.put("icon",new StringCoder().encode(obj.icon));
		js.put("medium_icon",new StringCoder().encode(obj.medium_icon));
		js.put("large_icon",new StringCoder().encode(obj.large_icon));
		js.put("multilang_icon",new ArrayCoder<String>(new StringCoder()).encode(obj.multilang_icon));
		js.put("sound",new StringCoder().encode(obj.sound));
		js.put("langcodes",new ArrayCoder<String>(new StringCoder()).encode(obj.langcodes));
		js.put("viewy",new JsonCoder().encode(obj.viewy));

		return js;
	}

	@Override
	public  Contents[] createArray(int size) {
		return new  Contents[size];
	}

}
