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



public class CartCoder extends
		TypeCoder<Cart> {


	@Override
	public  Cart decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Cart c=new Cart();

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
		c.sid=new StringCoder().decode(json.get("sid"));
		c.active=new BooleanCoder().decode(json.get("active"));
		c.design=new BooleanCoder().decode(json.get("design"));
		c.confirmed=new BooleanCoder().decode(json.get("confirmed"));
		c.paysatisfied=new BooleanCoder().decode(json.get("paysatisfied"));
		c.payconfirmed=new BooleanCoder().decode(json.get("payconfirmed"));
		c.cancelled=new BooleanCoder().decode(json.get("cancelled"));
		c.shipdate=new LongCoder().decode(json.get("shipdate"));
		c.invoicesent=new BooleanCoder().decode(json.get("invoicesent"));
		c.items=new JsonCoder().decode(json.get("items"));
		c.itemsprice=new JsonCoder().decode(json.get("itemsprice"));
		c.paystyle=new StringCoder().decode(json.get("paystyle"));
		c.payments=new ArrayCoder<Json>(new JsonCoder()).decode(json.get("payments"));
		c.calcdetails=new JsonCoder().decode(json.get("calcdetails"));
		c.shipstyle=new StringCoder().decode(json.get("shipstyle"));
		c.shipaddr=new JsonCoder().decode(json.get("shipaddr"));
		c.shipref=new StringCoder().decode(json.get("shipref"));
		c.shipdays=new IntegerCoder().decode(json.get("shipdays"));
		c.shipprice=new JsonCoder().decode(json.get("shipprice"));
		c.vatprice=new JsonCoder().decode(json.get("vatprice"));
		c.discountprice=new JsonCoder().decode(json.get("discountprice"));
		c.totalprice=new JsonCoder().decode(json.get("totalprice"));
		c.invoiceaddr=new JsonCoder().decode(json.get("invoiceaddr"));
		c.notice=new StringCoder().decode(json.get("notice"));
		c.issue=new StringCoder().decode(json.get("issue"));
		c.lang_id=new StringCoder().decode(json.get("lang_id"));
		c.validity=new IntegerCoder().decode(json.get("validity"));


		return c;
	}

	@Override
	public JSONValue encode( Cart obj) {
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
		js.put("sid",new StringCoder().encode(obj.sid));
		js.put("active",new BooleanCoder().encode(obj.active));
		js.put("design",new BooleanCoder().encode(obj.design));
		js.put("confirmed",new BooleanCoder().encode(obj.confirmed));
		js.put("paysatisfied",new BooleanCoder().encode(obj.paysatisfied));
		js.put("payconfirmed",new BooleanCoder().encode(obj.payconfirmed));
		js.put("cancelled",new BooleanCoder().encode(obj.cancelled));
		js.put("shipdate",new LongCoder().encode(obj.shipdate));
		js.put("invoicesent",new BooleanCoder().encode(obj.invoicesent));
		js.put("items",new JsonCoder().encode(obj.items));
		js.put("itemsprice",new JsonCoder().encode(obj.itemsprice));
		js.put("paystyle",new StringCoder().encode(obj.paystyle));
		js.put("payments",new ArrayCoder<Json>(new JsonCoder()).encode(obj.payments));
		js.put("calcdetails",new JsonCoder().encode(obj.calcdetails));
		js.put("shipstyle",new StringCoder().encode(obj.shipstyle));
		js.put("shipaddr",new JsonCoder().encode(obj.shipaddr));
		js.put("shipref",new StringCoder().encode(obj.shipref));
		js.put("shipdays",new IntegerCoder().encode(obj.shipdays));
		js.put("shipprice",new JsonCoder().encode(obj.shipprice));
		js.put("vatprice",new JsonCoder().encode(obj.vatprice));
		js.put("discountprice",new JsonCoder().encode(obj.discountprice));
		js.put("totalprice",new JsonCoder().encode(obj.totalprice));
		js.put("invoiceaddr",new JsonCoder().encode(obj.invoiceaddr));
		js.put("notice",new StringCoder().encode(obj.notice));
		js.put("issue",new StringCoder().encode(obj.issue));
		js.put("lang_id",new StringCoder().encode(obj.lang_id));
		js.put("validity",new IntegerCoder().encode(obj.validity));

		return js;
	}

	@Override
	public  Cart[] createArray(int size) {
		return new  Cart[size];
	}

}
