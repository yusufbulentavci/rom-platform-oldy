serverdao(table,method,modules,roles,direct)::=
<<
%if(direct)%	
package com.bilgidoku.rom.gwt.araci.direct.service;
%else%
package com.bilgidoku.rom.gwt.araci.server.service;
%endif%


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






%modules:moduleimport(false);wrap%

public class %table.nameFirstUpper%_%method.nameJavaForm% extends ServiceDaoBase{
	
	String val;
	
	%method.serverArgs:srvargdefs(); wrap%
	
	%if(method.retType)%
	
	%if(method.retType.array)%
	TypeCoder coder=new ArrayCoder<%method.retType.simpleJavaTypeName%>(new %method.retType.simpleJavaTypeName%Coder());
	%elseif(method.retType.map)%
	TypeCoder coder=new MapCoder<%method.retType.simpleJavaTypeName%>(new %method.retType.simpleJavaTypeName%Coder());
	%else%
	TypeCoder coder=new %method.retType.simpleJavaTypeName%Coder();
	%endif%

	%endif%
	
	%if(method.roles)%
	private static final int rls=%method.roles:rolecheck();separator="|"%;
	%endif%
	
	%if(method.audit)%
		%if(method.hasAuditParam)%
			private static String[] auditParams=new String[]{%method.auditparamnames%};
		%else%
			private static String[] auditParams=new String[]{};
		%endif%
	%endif%

	private final Method method;
	public %table.nameFirstUpper%_%method.nameJavaForm%(){
		method=getMethod(%table.pack%.%table.name%Gorevlisi.class,"%method.javaMethodName%");
	}
	
	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

		%if(method.audit)%
		DenetimGorevlisi.tek().write(request.getHostId(),request.getSession().getCid(),request.getUri(),"%method.auditname%", auditParams, request.getAuditParams(auditParams));
		%endif%
		
		%if(method.roles)%
		request.checkRole(rls);
		%endif%
		try{
		int ind=0;
		Object args[]=new Object[%method.argLen%];
	%method.serverArgs:srvargmapper(); wrap%
		Object obj=method.invoke(%table.pack%.%table.name%Gorevlisi.tek(),args);
	%if(method.retType)%
		%if(method.returnJust)%
				return obj;
		%elseif(method.retType.primitive)%
				JSONObject ret=new JSONObject();
				Object json=coder.encode(obj);
				ret.put("def",json);
				return ret;
				}catch (JSONException e) {
				throw errJson(e);
		%else%
				return coder.encode(obj);
				}catch (JSONException e) {
				throw errJson(e);
		%endif%
	%elseif(method.retFile)%
		return (RetStream)obj;
		}catch (JSONException e) {
				throw errJson(e);
	%else%
		return obj;	
	%endif%
		
		} catch (IllegalAccessException e) {
			throw errIllegalAccess(e);
		} catch (IllegalArgumentException e) {
			throw errIllegalArgument(e);
		} catch (InvocationTargetException e) {
			throw errInvocationTarget(e);
		}
	}
	
}

>>