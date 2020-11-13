package com.bilgidoku.rom.gwt.araci.server.service;


// serverdao
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import com.bilgidoku.rom.pg.dict.*;

import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.pg.veritabani.DbSetGet;
import com.bilgidoku.rom.shared.err.*;


import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.gwt.shared.*;
import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.gwt.server.middle.ServiceDaoBase;
import com.bilgidoku.rom.web.audit.DenetimGorevlisi;






import com.bilgidoku.rom.gwt.araci.server.rom.*;
import com.bilgidoku.rom.gwt.araci.server.bilgi.*;
import com.bilgidoku.rom.gwt.araci.server.site.*;
import com.bilgidoku.rom.gwt.araci.server.tepeweb.*;
import com.bilgidoku.rom.gwt.araci.server.asset.*;


public class OturumIciCagri_rtsay extends ServiceDaoBase{

	String val;

		
		TypeCoder cidsqlType=new StringCoder();
		
		TypeCoder msgsqlType=new StringCoder();



	TypeCoder coder=new BooleanCoder();



	private final Method method;
	public OturumIciCagri_rtsay(){
		method=getMethod(com.bilgidoku.rom.web.sessionfuncs.OturumIciCagriGorevlisi.class,"rtsay");
	}

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {


		try{
		int ind=0;
		Object args[]=new Object[3];
		args[ind++]=request.getSession();

		val=request.getParam("cid", null, null, false);
		args[ind++]=cidsqlType.decode(val);

		val=request.getParam("msg", null, null, false);
		args[ind++]=msgsqlType.decode(val);


		Object obj=method.invoke(com.bilgidoku.rom.web.sessionfuncs.OturumIciCagriGorevlisi.tek(),args);
				JSONObject ret=new JSONObject();
				Object json=coder.encode(obj);
				ret.put("def",json);
				return ret;
				}catch (JSONException e) {
				throw errJson(e);

		} catch (IllegalAccessException e) {
			throw errIllegalAccess(e);
		} catch (IllegalArgumentException e) {
			throw errIllegalArgument(e);
		} catch (InvocationTargetException e) {
			throw errInvocationTarget(e);
		}
	}

}
