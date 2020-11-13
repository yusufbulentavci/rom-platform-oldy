package com.bilgidoku.rom.pg.dict.mapper;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.ArgMapper;
import com.bilgidoku.rom.pg.dict.CallInteraction;
import com.bilgidoku.rom.pg.dict.CookieFinder;

public class LangMapper extends ArgMapper {
	private final CookieFinder service;
	private String conversion = null;

	public LangMapper(CookieFinder cf, short i) {
		super(i, true);
		this.service=cf;
		conversion = "rom.langs";
	}

	public String toString() {
		return "ParamMapper:" + " name:a_lang";
	}

	public String getConversion() {
		return conversion;
	}

	@Override
	public void setValue(CallInteraction request, String self, DbThree ps) throws KnownError, KnownError,
			ParameterError {

		String lang = getLang(request);
		ps.setString(lang);
		// syso("Param assignment ;paramName:a_lang Value:"+lang);
	}

	private String getLang(CallInteraction request) throws KnownError, ParameterError {
		return request.getReqLang();
	}

	@Override
	public Object getValue(CallInteraction request, String self) throws KnownError, ParameterError {
		return getLang(request);
	}
}
