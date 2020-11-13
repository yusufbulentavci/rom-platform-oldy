dbcoder(schema,type,modules)::=
<<package com.bilgidoku.rom.gwt.araci.server.%schema%;

//dbcoder
import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;

import com.bilgidoku.rom.shared.err.*;
import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.pg.dict.*;



import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;


%modules:moduleimport(false);wrap%


public class %type.nameFirstUpper%Coder extends
		TypeCoder<%type.nameFirstUpper%> {

	public %type.nameFirstUpper%Coder(){
		super(%type.tbl%,new String[]{%type.atts:{att| "%att.name%"};separator=","%});
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
	public Object encode(%type.nameFirstUpper% obj) throws JSONException {
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
		if (val == null) {
			db3.setNull(java.sql.Types.VARCHAR);
			return;
		}
		%type.atts:dbcoderfieldset();wrap%
	}
	
	@Override
	public String toString(%type.nameFirstUpper% val) throws KnownError {
		
		StringBuilder sb=new StringBuilder();
		int i=0;
		sb.append('(');
		%type.atts:dbcoderfieldtostring();separator="sb.append(',');"%
		sb.append(')');
		return sb.toString();
	}
	

	@Override
	protected %type.nameFirstUpper% inGetDbValue(DbSetGet db3) throws KnownError, JSONException {
		%type.nameFirstUpper% val=new %type.nameFirstUpper%();
		%type.atts:dbcoderfieldget();wrap%
		return val;
	}
	
	public %type.nameFirstUpper% fromString(String str) throws JSONException{
		if(str==null)
			return null;
		
		String[] ms = SiteUtil.splitDbType(str);
		if(ms==null)
			return null;
		%type.nameFirstUpper% val=new %type.nameFirstUpper%();
		int i=0;
		%type.atts:dbcoderfieldfromstring();wrap%
		return val;
	}

	@Override
	public String getSqlName() {
		throw new RuntimeException("Dont call");
	}

}

>>