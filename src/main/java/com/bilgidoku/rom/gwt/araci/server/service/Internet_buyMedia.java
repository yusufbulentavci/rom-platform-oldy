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


public class Internet_buyMedia extends ServiceDaoBase{

	String val;

		
		TypeCoder providersqlType=new IntegerCoder();
		
		TypeCoder pidsqlType=new StringCoder();



	TypeCoder coder=new StringCoder();


	private static final int rls=RoleMask.author|RoleMask.admin|RoleMask.designer;

			private static String[] auditParams=new String[]{"rovider","id"};
	private final Method method;
	public Internet_buyMedia(){
		method=getMethod(com.bilgidoku.rom.web.richweb.InternetGorevlisi.class,"buyMedia");
	}

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

		DenetimGorevlisi.tek().write(request.getHostId(),request.getSession().getCid(),request.getUri(),"/_richweb.buyMedia", auditParams, request.getAuditParams(auditParams));

		request.checkRole(rls);
		try{
		int ind=0;
		Object args[]=new Object[4];
		args[ind++]=request.getHostId();

		args[ind++]=request.getSession().getCid();

		val=request.getParam("provider", null, null, false);
		args[ind++]=providersqlType.decode(val);

		val=request.getParam("pid", null, null, false);
		args[ind++]=pidsqlType.decode(val);


		Object obj=method.invoke(com.bilgidoku.rom.web.richweb.InternetGorevlisi.tek(),args);
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
