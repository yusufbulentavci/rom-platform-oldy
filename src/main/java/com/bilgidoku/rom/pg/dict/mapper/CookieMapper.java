package com.bilgidoku.rom.pg.dict.mapper;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.ArgMapper;
import com.bilgidoku.rom.pg.dict.CallInteraction;
import com.bilgidoku.rom.pg.dict.CookieFinder;

public class CookieMapper extends ArgMapper{
	private final CookieFinder service;

	protected String cookieName;
	private String conversion;
	
	public CookieMapper(CookieFinder cf, short i, String varName, boolean canBeNull) {
		super(i,canBeNull);
		this.service=cf;
		this.cookieName=varName;
		if(this.cookieName.equals("lang")){
			this.conversion="rom.langs";
		}

	}

	public String toString(){
		return "CookieMapper name:"+this.cookieName+" sqlType:String";
	}

	@Override
	public void setValue(CallInteraction  request, String self, DbThree ps) throws KnownError, KnownError {
		ps.setString(service.getCookieByName(request.getCookie(), cookieName));
	}
	
	public String getConversion(){
		return this.conversion;
	}

	@Override
	public Object getValue(CallInteraction request, String self) throws KnownError {
		return service.getCookieByName(request.getCookie(), cookieName);
	}
}
