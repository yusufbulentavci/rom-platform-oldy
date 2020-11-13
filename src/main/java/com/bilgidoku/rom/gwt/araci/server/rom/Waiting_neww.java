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


public class Waiting_neww extends DbDaoBase{

	private final String callProto="select * from rom.waiting_new(?,?,?,?,?,?,?)";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder contactsqlType=new StringCoder();
		TypeCoder appsqlType=new StringCoder();
		TypeCoder codesqlType=new StringCoder();
		TypeCoder inrefsqlType=new StringCoder();
		TypeCoder titlesqlType=new StringCoder();
		TypeCoder usernamesqlType=new StringCoder();


	TypeCoder coder=new StringCoder();


	private static final int rls=RoleMask.user;

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

	request.checkRole(rls);



		try (DbThree db3 = new DbThree(callProto)) {
			String val;

				db3.setInt(request.getHostId());


				{
					String cid=request.getSession().getCid();
					if(cid==null)
						db3.setNull(java.sql.Types.VARCHAR);
					else
						db3.setString(cid);
				}
				val=request.getParam("app", null, null, false);
				appsqlType.setDbValue(db3, appsqlType.decode(val));

				val=request.getParam("code", null, null, false);
				codesqlType.setDbValue(db3, codesqlType.decode(val));

				val=request.getParam("inref", null, null, false);
				inrefsqlType.setDbValue(db3, inrefsqlType.decode(val));

				val=request.getParam("title", null, null, false);
				titlesqlType.setDbValue(db3, titlesqlType.decode(val));

				val=request.getParam("username", null, null, false);
				usernamesqlType.setDbValue(db3, usernamesqlType.decode(val));


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
