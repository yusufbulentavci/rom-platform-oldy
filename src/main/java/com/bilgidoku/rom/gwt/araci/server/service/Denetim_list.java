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


public class Denetim_list extends ServiceDaoBase{

	String val;

		
		TypeCoder fromtimesqlType=new LongCoder();
		
		TypeCoder totimesqlType=new LongCoder();
		
		TypeCoder cidsqlType=new StringCoder();



	TypeCoder coder=new ArrayCoder<AuditItem>(new AuditItemCoder());


	private final Method method;
	public Denetim_list(){
		method=getMethod(com.bilgidoku.rom.web.audit.DenetimGorevlisi.class,"list");
	}

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {


		try{
		int ind=0;
		Object args[]=new Object[4];
		args[ind++]=request.getHostId();

		val=request.getParam("fromtime", null, null, false);
		args[ind++]=fromtimesqlType.decode(val);

		val=request.getParam("totime", null, null, false);
		args[ind++]=totimesqlType.decode(val);

		val=request.getParam("cid", null, null, false);
		args[ind++]=cidsqlType.decode(val);


		Object obj=method.invoke(com.bilgidoku.rom.web.audit.DenetimGorevlisi.tek(),args);
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
