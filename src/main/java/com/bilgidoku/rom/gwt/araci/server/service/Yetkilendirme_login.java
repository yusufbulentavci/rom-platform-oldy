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


public class Yetkilendirme_login extends ServiceDaoBase{

	String val;

		
		TypeCoder usersqlType=new StringCoder();
		
		TypeCoder credentialsqlType=new StringCoder();



	TypeCoder coder=new DescRespCoder();



			private static String[] auditParams=new String[]{"ser"};
	private final Method method;
	public Yetkilendirme_login(){
		method=getMethod(com.bilgidoku.rom.web.authorize.YetkilendirmeGorevlisi.class,"login");
	}

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

		DenetimGorevlisi.tek().write(request.getHostId(),request.getSession().getCid(),request.getUri(),"/_auth.login", auditParams, request.getAuditParams(auditParams));

		try{
		int ind=0;
		Object args[]=new Object[4];
		args[ind++]=request.getSession();

		args[ind++]=request.getDomain();

		val=request.getParam("user", null, null, false);
		args[ind++]=usersqlType.decode(val);

		val=request.getParam("credential", null, null, false);
		args[ind++]=credentialsqlType.decode(val);


		Object obj=method.invoke(com.bilgidoku.rom.web.authorize.YetkilendirmeGorevlisi.tek(),args);
				return coder.encode(obj);
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
