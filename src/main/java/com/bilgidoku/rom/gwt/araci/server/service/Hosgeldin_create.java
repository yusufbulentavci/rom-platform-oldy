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


public class Hosgeldin_create extends ServiceDaoBase{

	String val;

		
		TypeCoder emailsqlType=new StringCoder();
		
		TypeCoder usernamesqlType=new StringCoder();
		
		TypeCoder passwordsqlType=new StringCoder();



	TypeCoder coder=new IntegerCoder();



	private final Method method;
	public Hosgeldin_create(){
		method=getMethod(com.bilgidoku.rom.web.welcome.HosgeldinGorevlisi.class,"create");
	}

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {


		try{
		int ind=0;
		Object args[]=new Object[5];
		args[ind++]=request.getSession();

		String lang=request.getParam("lng", null, null, false);
		if(lang==null){
				lang=request.getReqLang();
		}
		args[ind++]=lang;

		val=request.getParam("email", null, null, false);
		args[ind++]=emailsqlType.decode(val);

		val=request.getParam("username", null, null, false);
		args[ind++]=usernamesqlType.decode(val);

		val=request.getParam("password", null, null, false);
		args[ind++]=passwordsqlType.decode(val);


		Object obj=method.invoke(com.bilgidoku.rom.web.welcome.HosgeldinGorevlisi.tek(),args);
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
