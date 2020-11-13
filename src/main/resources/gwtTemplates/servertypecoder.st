servertypecoder(schema,type,direct)::=
<<
%if(direct)%
package com.bilgidoku.rom.gwt.araci.direct.%schema%;
%else%
package com.bilgidoku.rom.gwt.araci.server.%schema%;
%endif%
//servertypecoder


import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.*;

import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;

public class %type.nameFirstUpper%Coder extends
		TypeCoder<%type.nameFirstUpper%> {


	public %type.nameFirstUpper%Coder(){
		super(false,null);
	}
	
	
	@Override
	public  %type.nameFirstUpper% decode(Object js) throws JSONException  {
		JSONObject json = getObject(js);
		if(json==null)
			return null;
		 %type.nameFirstUpper% c=new %type.nameFirstUpper%();

		%type.atts:factoryfielddecode();wrap%

		return c;
	}
	
	@Override
	public Object encode( %type.nameFirstUpper% obj) throws JSONException{
		if(obj==null)
			return null;
		JSONObject js=new JSONObject();
		%type.atts:factoryfieldencode();wrap%
		return js;
	}

	@Override
	public  %type.nameFirstUpper%[] createArray(int size) {
		return new  %type.nameFirstUpper%[size];
	}

	@Override
	public void setDbValue(DbSetGet db3, %type.nameFirstUpper% val) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public %type.nameFirstUpper% inGetDbValue(DbSetGet db3) throws KnownError {
		throw new RuntimeException("Shouldnt be called");
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Shouldnt be called");
	}
	
	public %type.nameFirstUpper% fromString(String s) throws JSONException{
		throw new RuntimeException("Shouldnt be called");
	}
	public String toString(%type.nameFirstUpper% t) throws KnownError{
		throw new RuntimeException("Shouldnt be called");
	}
	

}

>>