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


public class Yetkilendirme_forgetpass extends ServiceDaoBase{

	String val;

		
		TypeCoder emailsqlType=new StringCoder();



	TypeCoder coder=new BooleanCoder();



			private static String[] auditParams=new String[]{"mail"};
	private final Method method;
	public Yetkilendirme_forgetpass(){
		method=getMethod(com.bilgidoku.rom.web.authorize.YetkilendirmeGorevlisi.class,"forgetpass");
	}

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

		DenetimGorevlisi.tek().write(request.getHostId(),request.getSession().getCid(),request.getUri(),"/_auth.forgetpass", auditParams, request.getAuditParams(auditParams));

		try{
		int ind=0;
		Object args[]=new Object[4];
		args[ind++]=request.getSession();

		String lang=request.getParam("lng", null, null, false);
		if(lang==null){
				lang=request.getReqLang();
		}
		args[ind++]=lang;

		args[ind++]=request.getRid();

		val=request.getParam("email", null, null, false);
		args[ind++]=emailsqlType.decode(val);


		Object obj=method.invoke(com.bilgidoku.rom.web.authorize.YetkilendirmeGorevlisi.tek(),args);
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
