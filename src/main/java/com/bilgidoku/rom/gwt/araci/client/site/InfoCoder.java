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



public class InfoCoder extends
		TypeCoder<Info> {


	@Override
	public  Info decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Info c=new Info();

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
		c.style=new StringCoder().decode(json.get("style"));
		c.headertext=new ArrayCoder<Json>(new JsonCoder()).decode(json.get("headertext"));
		c.default_app=new StringCoder().decode(json.get("default_app"));
		c.palette=new JsonCoder().decode(json.get("palette"));
		c.text_font=new JsonCoder().decode(json.get("text_font"));
		c.site_footer=new ArrayCoder<Json>(new JsonCoder()).decode(json.get("site_footer"));
		c.address=new JsonCoder().decode(json.get("address"));
		c.browser_title=new ArrayCoder<String>(new StringCoder()).decode(json.get("browser_title"));
		c.banner_img=new StringCoder().decode(json.get("banner_img"));
		c.logo_img=new JsonCoder().decode(json.get("logo_img"));
		c.browser_icon=new StringCoder().decode(json.get("browser_icon"));
		c.text1=new ArrayCoder<String>(new StringCoder()).decode(json.get("text1"));
		c.text2=new ArrayCoder<String>(new StringCoder()).decode(json.get("text2"));
		c.menu1=new StringCoder().decode(json.get("menu1"));
		c.menu2=new StringCoder().decode(json.get("menu2"));
		c.list1=new StringCoder().decode(json.get("list1"));
		c.list2=new StringCoder().decode(json.get("list2"));
		c.ecommerce=new BooleanCoder().decode(json.get("ecommerce"));
		c.login=new BooleanCoder().decode(json.get("login"));


		return c;
	}

	@Override
	public JSONValue encode( Info obj) {
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
		js.put("style",new StringCoder().encode(obj.style));
		js.put("headertext",new ArrayCoder<Json>(new JsonCoder()).encode(obj.headertext));
		js.put("default_app",new StringCoder().encode(obj.default_app));
		js.put("palette",new JsonCoder().encode(obj.palette));
		js.put("text_font",new JsonCoder().encode(obj.text_font));
		js.put("site_footer",new ArrayCoder<Json>(new JsonCoder()).encode(obj.site_footer));
		js.put("address",new JsonCoder().encode(obj.address));
		js.put("browser_title",new ArrayCoder<String>(new StringCoder()).encode(obj.browser_title));
		js.put("banner_img",new StringCoder().encode(obj.banner_img));
		js.put("logo_img",new JsonCoder().encode(obj.logo_img));
		js.put("browser_icon",new StringCoder().encode(obj.browser_icon));
		js.put("text1",new ArrayCoder<String>(new StringCoder()).encode(obj.text1));
		js.put("text2",new ArrayCoder<String>(new StringCoder()).encode(obj.text2));
		js.put("menu1",new StringCoder().encode(obj.menu1));
		js.put("menu2",new StringCoder().encode(obj.menu2));
		js.put("list1",new StringCoder().encode(obj.list1));
		js.put("list2",new StringCoder().encode(obj.list2));
		js.put("ecommerce",new BooleanCoder().encode(obj.ecommerce));
		js.put("login",new BooleanCoder().encode(obj.login));

		return js;
	}

	@Override
	public  Info[] createArray(int size) {
		return new  Info[size];
	}

}
