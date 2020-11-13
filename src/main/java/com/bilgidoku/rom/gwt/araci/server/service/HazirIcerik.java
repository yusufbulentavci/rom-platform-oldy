package com.bilgidoku.rom.gwt.araci.server.service;

//customserverdao

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import com.bilgidoku.rom.pg.dict.*;

import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.web.http.RomHttpResponse;
import com.bilgidoku.rom.shared.err.*;

import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.shared.*;
import com.bilgidoku.rom.gwt.server.middle.CustomDaoBase;


import com.bilgidoku.rom.gwt.araci.server.rom.*;
import com.bilgidoku.rom.gwt.araci.server.bilgi.*;
import com.bilgidoku.rom.gwt.araci.server.site.*;
import com.bilgidoku.rom.gwt.araci.server.tepeweb.*;
import com.bilgidoku.rom.gwt.araci.server.asset.*;


public class HazirIcerik extends CustomDaoBase{



	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException {

		com.bilgidoku.rom.web.filecontent.HazirIcerikGorevlisi.tek().romHandle((RomHttpResponse)request);
		return null;
	}

}
