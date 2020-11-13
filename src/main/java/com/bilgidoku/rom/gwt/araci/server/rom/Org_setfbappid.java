package com.bilgidoku.rom.gwt.araci.server.rom;
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

import com.bilgidoku.rom.gwt.araci.server.rom.*;
import com.bilgidoku.rom.gwt.araci.server.bilgi.*;
import com.bilgidoku.rom.gwt.araci.server.site.*;
import com.bilgidoku.rom.gwt.araci.server.tepeweb.*;
import com.bilgidoku.rom.gwt.araci.server.asset.*;


public class Org_setfbappid extends DbDaoBase{

	private final String callProto="select * from rom.org_setfbappid(?,?)";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder fbappidsqlType=new StringCoder();


	TypeCoder coder=new BooleanCoder();

			private static String[] auditParams=new String[]{"fbappid"};


	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

		DenetimGorevlisi.tek().write(request.getHostId(),request.getSession().getCid(),request.getUri(),"rom.org.setfbappid", auditParams, request.getAuditParams(auditParams));



		try (DbThree db3 = new DbThree(callProto)) {
			String val;

				db3.setInt(request.getHostId());


				val=request.getParam("fbappid", null, null, false);
				fbappidsqlType.setDbValue(db3, fbappidsqlType.decode(val));


			if(!db3.executeQuery()){
				return null;
			}


			Object ret=null;
			if (db3.next()) {
				Object o=coder.getDbValue(db3);
				JSONObject reto=new JSONObject();
				Object json=coder.encode(o);
				reto.put("def",json);
				ret=reto;
			}


			return ret;
		}catch (JSONException e) {
			throw errJson(e);
		}

	}

}
