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


public class Contacts_get extends DbDaoBase{

	private final String callProto="select * from rom.contacts_get(?,?)";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder selfsqlType=new StringCoder();


	TypeCoder coder=new ContactsCoder();


	private static final int rls=RoleMask.owner|RoleMask.user;

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

	request.checkRole(rls);



		try (DbThree db3 = new DbThree(callProto)) {
			String val;

				db3.setInt(request.getHostId());


				db3.setString(request.getUri());


			if(!db3.executeQuery()){
				return null;
			}


			Object ret=null;
			if (db3.next()) {
				Object o=coder.getDbValue(db3);
				ret=coder.encode(o);
			}


			return ret;
		}catch (JSONException e) {
			throw errJson(e);
		}

	}
	public Long getCachePeriod(){
		return 600L;
	}

}
