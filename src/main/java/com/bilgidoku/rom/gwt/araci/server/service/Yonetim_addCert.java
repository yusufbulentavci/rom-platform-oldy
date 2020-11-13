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


public class Yonetim_addCert extends ServiceDaoBase{

	String val;

		
		TypeCoder aliassqlType=new StringCoder();



	TypeCoder coder=new RomCertCoder();



	private final Method method;
	public Yonetim_addCert(){
		method=getMethod(com.bilgidoku.rom.web.admin.YonetimGorevlisi.class,"addCert");
	}

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {


		try{
		int ind=0;
		Object args[]=new Object[3];
		args[ind++]=request.getHostId();

		val=request.getParam("alias", null, null, false);
		args[ind++]=aliassqlType.decode(val);

		io.netty.handler.codec.http.multipart.FileUpload item = request.getFileParam("cert", false);
		try{
		args[ind++]=(item==null)?null:item.getFile();
		}catch(java.io.IOException e){
			throw new KnownError(e);
		}


		Object obj=method.invoke(com.bilgidoku.rom.web.admin.YonetimGorevlisi.tek(),args);
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
