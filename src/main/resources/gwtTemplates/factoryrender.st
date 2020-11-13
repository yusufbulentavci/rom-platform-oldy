factoryrender(schema,type,modules)::=
<<package com.bilgidoku.rom.gwt.araci.client.%schema%;

// factoryrender

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;
import com.google.gwt.json.client.*;
import com.bilgidoku.rom.gwt.shared.*;

%modules:moduleimport(true);wrap%


public class %type.nameFirstUpper%Coder extends
		TypeCoder<%type.nameFirstUpper%> {

	
	@Override
	public  %type.nameFirstUpper% decode(JSONValue js){
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 %type.nameFirstUpper% c=new %type.nameFirstUpper%();

		%type.atts:factoryfielddecode();wrap%

		return c;
	}
	
	@Override
	public JSONValue encode( %type.nameFirstUpper% obj) {
		JSONObject js=new JSONObject();
		%type.atts:factoryfieldencode();wrap%
		return js;
	}

	@Override
	public  %type.nameFirstUpper%[] createArray(int size) {
		return new  %type.nameFirstUpper%[size];
	}

}

>>