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



public class HitsCoder extends
		TypeCoder<Hits> {


	@Override
	public  Hits decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Hits c=new Hits();

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
		c.timebegin=new LongCoder().decode(json.get("timebegin"));
		c.timeend=new LongCoder().decode(json.get("timeend"));
		c.ops=new JsonCoder().decode(json.get("ops"));
		c.browsers=new JsonCoder().decode(json.get("browsers"));
		c.referrers=new JsonCoder().decode(json.get("referrers"));
		c.inhits=new JsonCoder().decode(json.get("inhits"));
		c.countries=new JsonCoder().decode(json.get("countries"));
		c.langs=new JsonCoder().decode(json.get("langs"));
		c.pageperiods=new JsonCoder().decode(json.get("pageperiods"));
		c.bringingwords=new JsonCoder().decode(json.get("bringingwords"));
		c.cpu=new LongCoder().decode(json.get("cpu"));
		c.netread=new LongCoder().decode(json.get("netread"));
		c.netwrite=new LongCoder().decode(json.get("netwrite"));


		return c;
	}

	@Override
	public JSONValue encode( Hits obj) {
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
		js.put("timebegin",new LongCoder().encode(obj.timebegin));
		js.put("timeend",new LongCoder().encode(obj.timeend));
		js.put("ops",new JsonCoder().encode(obj.ops));
		js.put("browsers",new JsonCoder().encode(obj.browsers));
		js.put("referrers",new JsonCoder().encode(obj.referrers));
		js.put("inhits",new JsonCoder().encode(obj.inhits));
		js.put("countries",new JsonCoder().encode(obj.countries));
		js.put("langs",new JsonCoder().encode(obj.langs));
		js.put("pageperiods",new JsonCoder().encode(obj.pageperiods));
		js.put("bringingwords",new JsonCoder().encode(obj.bringingwords));
		js.put("cpu",new LongCoder().encode(obj.cpu));
		js.put("netread",new LongCoder().encode(obj.netread));
		js.put("netwrite",new LongCoder().encode(obj.netwrite));

		return js;
	}

	@Override
	public  Hits[] createArray(int size) {
		return new  Hits[size];
	}

}
