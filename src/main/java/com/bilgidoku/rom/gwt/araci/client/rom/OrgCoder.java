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



public class OrgCoder extends
		TypeCoder<Org> {


	@Override
	public  Org decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Org c=new Org();

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
		c.issue_year=new IntegerCoder().decode(json.get("issue_year"));
		c.issue_nextid=new IntegerCoder().decode(json.get("issue_nextid"));
		c.shipstyle=new JsonCoder().decode(json.get("shipstyle"));
		c.paystyle=new JsonCoder().decode(json.get("paystyle"));
		c.langcodes=new ArrayCoder<String>(new StringCoder()).decode(json.get("langcodes"));
		c.cpic=new ArrayCoder<String>(new StringCoder()).decode(json.get("cpic"));
		c.crs=new ArrayCoder<String>(new StringCoder()).decode(json.get("crs"));
		c.fbappid=new StringCoder().decode(json.get("fbappid"));
		c.nfs=new JsonCoder().decode(json.get("nfs"));
		c.startssl=new StringCoder().decode(json.get("startssl"));
		c.ga=new StringCoder().decode(json.get("ga"));
		c.forcehttps=new BooleanCoder().decode(json.get("forcehttps"));


		return c;
	}

	@Override
	public JSONValue encode( Org obj) {
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
		js.put("issue_year",new IntegerCoder().encode(obj.issue_year));
		js.put("issue_nextid",new IntegerCoder().encode(obj.issue_nextid));
		js.put("shipstyle",new JsonCoder().encode(obj.shipstyle));
		js.put("paystyle",new JsonCoder().encode(obj.paystyle));
		js.put("langcodes",new ArrayCoder<String>(new StringCoder()).encode(obj.langcodes));
		js.put("cpic",new ArrayCoder<String>(new StringCoder()).encode(obj.cpic));
		js.put("crs",new ArrayCoder<String>(new StringCoder()).encode(obj.crs));
		js.put("fbappid",new StringCoder().encode(obj.fbappid));
		js.put("nfs",new JsonCoder().encode(obj.nfs));
		js.put("startssl",new StringCoder().encode(obj.startssl));
		js.put("ga",new StringCoder().encode(obj.ga));
		js.put("forcehttps",new BooleanCoder().encode(obj.forcehttps));

		return js;
	}

	@Override
	public  Org[] createArray(int size) {
		return new  Org[size];
	}

}
