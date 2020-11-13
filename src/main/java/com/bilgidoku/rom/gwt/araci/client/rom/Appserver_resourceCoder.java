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



public class Appserver_resourceCoder extends
		TypeCoder<Appserver_resource> {


	@Override
	public  Appserver_resource decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 Appserver_resource c=new Appserver_resource();

		c.uri=new StringCoder().decode(json.get("uri"));
		c.container=new StringCoder().decode(json.get("container"));
		c.html_file=new StringCoder().decode(json.get("html_file"));
		c.modified_date=new StringCoder().decode(json.get("modified_date"));
		c.owner_role=new StringCoder().decode(json.get("owner_role"));
		c.schema_name=new StringCoder().decode(json.get("schema_name"));
		c.type_name=new StringCoder().decode(json.get("type_name"));
		c.is_container=new BooleanCoder().decode(json.get("is_container"));


		return c;
	}

	@Override
	public JSONValue encode( Appserver_resource obj) {
		JSONObject js=new JSONObject();
		js.put("uri",new StringCoder().encode(obj.uri));
		js.put("container",new StringCoder().encode(obj.container));
		js.put("html_file",new StringCoder().encode(obj.html_file));
		js.put("modified_date",new StringCoder().encode(obj.modified_date));
		js.put("owner_role",new StringCoder().encode(obj.owner_role));
		js.put("schema_name",new StringCoder().encode(obj.schema_name));
		js.put("type_name",new StringCoder().encode(obj.type_name));
		js.put("is_container",new BooleanCoder().encode(obj.is_container));

		return js;
	}

	@Override
	public  Appserver_resource[] createArray(int size) {
		return new  Appserver_resource[size];
	}

}
