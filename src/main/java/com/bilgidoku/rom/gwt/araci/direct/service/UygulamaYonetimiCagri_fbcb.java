	
package com.bilgidoku.rom.gwt.araci.direct.service;

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







public class UygulamaYonetimiCagri_fbcb extends ServiceDaoBase{

	String val;

		
		TypeCoder cbidsqlType=new StringCoder();
		
		TypeCoder codesqlType=new StringCoder();
		
		TypeCoder error_codesqlType=new IntegerCoder();
		
		TypeCoder error_messagesqlType=new StringCoder();



	TypeCoder coder=new StringCoder();



	private final Method method;
	public UygulamaYonetimiCagri_fbcb(){
		method=getMethod(com.bilgidoku.rom.web.uygulamayonetim.UygulamaYonetimiCagriGorevlisi.class,"fbcb");
	}

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {


		try{
		int ind=0;
		Object args[]=new Object[4];
		val=request.getParam("cbid", null, null, false);
		args[ind++]=cbidsqlType.decode(val);

		val=request.getParam("code", null, null, false);
		args[ind++]=codesqlType.decode(val);

		val=request.getParam("error_code", null, null, false);
		args[ind++]=error_codesqlType.decode(val);

		val=request.getParam("error_message", null, null, false);
		args[ind++]=error_messagesqlType.decode(val);


		Object obj=method.invoke(com.bilgidoku.rom.web.uygulamayonetim.UygulamaYonetimiCagriGorevlisi.tek(),args);
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
