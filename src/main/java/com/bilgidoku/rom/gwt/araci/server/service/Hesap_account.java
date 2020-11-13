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


public class Hesap_account extends ServiceDaoBase{

	String val;



	TypeCoder coder=new AccountCoder();


	private static final int rls=RoleMask.author|RoleMask.admin|RoleMask.designer;

	private final Method method;
	public Hesap_account(){
		method=getMethod(com.bilgidoku.rom.web.account.HesapGorevlisi.class,"account");
	}

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {


		request.checkRole(rls);
		try{
		int ind=0;
		Object args[]=new Object[1];
		args[ind++]=request.getHostId();


		Object obj=method.invoke(com.bilgidoku.rom.web.account.HesapGorevlisi.tek(),args);
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
