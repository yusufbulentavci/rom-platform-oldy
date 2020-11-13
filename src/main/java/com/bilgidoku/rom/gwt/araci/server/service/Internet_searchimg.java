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


public class Internet_searchimg extends ServiceDaoBase{

	String val;

		
		TypeCoder prsqlType=new IntegerCoder();
		
		TypeCoder limitsqlType=new IntegerCoder();
		
		TypeCoder offsetsqlType=new IntegerCoder();
		
		TypeCoder phrasesqlType=new StringCoder();
		
		TypeCoder sizesqlType=new StringCoder();
		
		TypeCoder aspectsqlType=new StringCoder();
		
		TypeCoder stylesqlType=new StringCoder();
		
		TypeCoder colorssqlType=new StringCoder();
		
		TypeCoder facesqlType=new StringCoder();



	TypeCoder coder=new ArrayCoder<ImageResp>(new ImageRespCoder());

	private static final int rls=RoleMask.author|RoleMask.admin|RoleMask.designer;

	private final Method method;
	public Internet_searchimg(){
		method=getMethod(com.bilgidoku.rom.web.richweb.InternetGorevlisi.class,"searchimg");
	}

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {


		request.checkRole(rls);
		try{
		int ind=0;
		Object args[]=new Object[11];
		args[ind++]=request.getHostId();

		args[ind++]=request.getSession().getCid();

		val=request.getParam("pr", null, null, false);
		args[ind++]=prsqlType.decode(val);

		val=request.getParam("limit", null, null, false);
		args[ind++]=limitsqlType.decode(val);

		val=request.getParam("offset", null, null, false);
		args[ind++]=offsetsqlType.decode(val);

		val=request.getParam("phrase", null, null, false);
		args[ind++]=phrasesqlType.decode(val);

		val=request.getParam("size", null, null, false);
		args[ind++]=sizesqlType.decode(val);

		val=request.getParam("aspect", null, null, false);
		args[ind++]=aspectsqlType.decode(val);

		val=request.getParam("style", null, null, false);
		args[ind++]=stylesqlType.decode(val);

		val=request.getParam("colors", null, null, false);
		args[ind++]=colorssqlType.decode(val);

		val=request.getParam("face", null, null, false);
		args[ind++]=facesqlType.decode(val);


		Object obj=method.invoke(com.bilgidoku.rom.web.richweb.InternetGorevlisi.tek(),args);
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
