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



public class StocksCoder extends
		TypeCoder<Stocks> {


	@Override
	public  Stocks decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Stocks c=new Stocks();

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
		c.writing=new StringCoder().decode(json.get("writing"));
		c.tariff=new JsonCoder().decode(json.get("tariff"));
		c.physical=new JsonCoder().decode(json.get("physical"));
		c.reserved=new IntegerCoder().decode(json.get("reserved"));
		c.amount=new IntegerCoder().decode(json.get("amount"));
		c.alertonleft=new IntegerCoder().decode(json.get("alertonleft"));
		c.onsale=new BooleanCoder().decode(json.get("onsale"));
		c.options=new JsonCoder().decode(json.get("options"));
		c.firststock=new StringCoder().decode(json.get("firststock"));
		c.alternatives=new ArrayCoder<String>(new StringCoder()).decode(json.get("alternatives"));
		c.virtualsparent=new StringCoder().decode(json.get("virtualsparent"));
		c.title=new StringCoder().decode(json.get("title"));
		c.summary=new StringCoder().decode(json.get("summary"));
		c.tariffmodel=new StringCoder().decode(json.get("tariffmodel"));
		c.icon=new StringCoder().decode(json.get("icon"));


		return c;
	}

	@Override
	public JSONValue encode( Stocks obj) {
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
		js.put("writing",new StringCoder().encode(obj.writing));
		js.put("tariff",new JsonCoder().encode(obj.tariff));
		js.put("physical",new JsonCoder().encode(obj.physical));
		js.put("reserved",new IntegerCoder().encode(obj.reserved));
		js.put("amount",new IntegerCoder().encode(obj.amount));
		js.put("alertonleft",new IntegerCoder().encode(obj.alertonleft));
		js.put("onsale",new BooleanCoder().encode(obj.onsale));
		js.put("options",new JsonCoder().encode(obj.options));
		js.put("firststock",new StringCoder().encode(obj.firststock));
		js.put("alternatives",new ArrayCoder<String>(new StringCoder()).encode(obj.alternatives));
		js.put("virtualsparent",new StringCoder().encode(obj.virtualsparent));
		js.put("title",new StringCoder().encode(obj.title));
		js.put("summary",new StringCoder().encode(obj.summary));
		js.put("tariffmodel",new StringCoder().encode(obj.tariffmodel));
		js.put("icon",new StringCoder().encode(obj.icon));

		return js;
	}

	@Override
	public  Stocks[] createArray(int size) {
		return new  Stocks[size];
	}

}
