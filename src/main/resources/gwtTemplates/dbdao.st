dbdao(schema,table,method,modules)::=
<<package com.bilgidoku.rom.gwt.araci.server.%method.schemaName%;
// dbdao
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.netty.handler.codec.http.HttpResponseStatus;

import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;
import com.bilgidoku.rom.gwt.server.common.resp.*;

import com.bilgidoku.rom.shared.err.*;
import com.bilgidoku.rom.ilk.json.*;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.*;
import com.bilgidoku.rom.gwt.server.middle.DbDaoBase;
import com.bilgidoku.rom.web.audit.DenetimGorevlisi;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;

%modules:moduleimport(false);wrap%

public class %table.nameFirstUpper%_%method.nameJavaForm% extends DbDaoBase{
	
	private final String callProto="%method.callProto%";
	
	%if(method.beforeStr)%
	private final BeforeHook beforeHook=getBeforeHook("%method.beforeStr%");
	%endif%
	
	%if(method.afterStr)%
	private final AfterHook afterHook=getAfterHook("%method.afterStr%");
	%endif%
	
	%method.serverArgs:dbargdefs(); wrap%
	
	%if(method.retType.array)%
	TypeCoder coder=new ArrayCoder<%method.retType.simpleJavaTypeName%>(new %method.retType.simpleJavaTypeName%Coder());
	%elseif(method.retType.map)%
	TypeCoder coder=new MapCoder<%method.retType.simpleJavaTypeName%>(new %method.retType.simpleJavaTypeName%Coder());
	%else%
	TypeCoder coder=new %method.retType.simpleJavaTypeName%Coder();
	%endif%
	
	%if(method.audit)%
		%if(method.hasAuditParam)%
			private static String[] auditParams=new String[]{%method.auditparamnames%};
		%else%
			private static String[] auditParams=new String[]{};
		%endif%
	%endif%
	
	%if(method.roles)%
	private static final int rls=%method.roles:rolecheck();separator="|"%;
	%endif%
	
	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

	%if(method.audit)%
		DenetimGorevlisi.tek().write(request.getHostId(),request.getSession().getCid(),request.getUri(),"%method.auditname%", auditParams, request.getAuditParams(auditParams));
	%endif%
	%if(method.roles)%
	request.checkRole(rls);
	%endif%
	

%if(method.hook)%
		HookScope hookScope = new HookScope(request);
		boolean chargeBeforeHook = false;
		boolean acceptable = false;
%endif%
		
%if(method.beforeStr)%
		if (!beforeHook.hook(hookScope)) {
			acceptable = true;
			throw new KnownError().internalError();
		}
		chargeBeforeHook = true;
%endif%
	
		try (DbThree db3 = new DbThree(callProto)) {
			String val;
			%method.serverArgs:dbargmapper(); wrap%
			if(!db3.executeQuery()){
				return null;
			}
			
			
%if(method.retset)%
			JSONArray ret=new JSONArray();
			while (db3.next()) {
				Object o=coder.getDbValue(db3);
				Object json=coder.encode(o);
				ret.put(json);
			}
%else%
			Object ret=null;
			if (db3.next()) {
		%if(method.retFile)%
				String fn=db3.getString();
				File f=DbfsGorevlisi.tek().get(request.getSession().getIntraHostId(), fn);
				ret=new RetStream(f, fn);
		%elseif(method.retType.primitive)%
				Object o=coder.getDbValue(db3);
				JSONObject reto=new JSONObject();
				Object json=coder.encode(o);
				reto.put("def",json);
				ret=reto;
		%else%
				Object o=coder.getDbValue(db3);
				ret=coder.encode(o);
		%endif%
			}
%endif%

%if(method.beforeStr)%
			acceptable=true;
%endif%

%if(method.afterStr)%
			afterHook.hook(hookScope, ret);
%endif%
			
			return ret;
		}catch (JSONException e) {
			throw errJson(e);
		}
%if(method.beforeStr)%
		finally {
			if (!acceptable) {
					beforeHook.undo(hookScope);
			}
		}
%endif%
	
	}
%if(method.cache)%
	public Long getCachePeriod(){
		return %method.cache%L;
	}
%endif%
	
}

>>