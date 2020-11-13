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


public class Contacts_neww extends DbDaoBase{

	private final String callProto="select * from rom.contacts_new(?,?::rom.langs,?::rom.langs,?::rom.countrycode,?,?,?,?,?,?,?,?)";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder langsqlType=new StringCoder();
		TypeCoder lngsqlType=new StringCoder();
		TypeCoder ccsqlType=new StringCoder();
		TypeCoder emailsqlType=new StringCoder();
		TypeCoder ciphersqlType=new StringCoder();
		TypeCoder firstnamesqlType=new StringCoder();
		TypeCoder lastnamesqlType=new StringCoder();
		TypeCoder fidsqlType=new StringCoder();
		TypeCoder twittersqlType=new StringCoder();
		TypeCoder mobilesqlType=new StringCoder();
		TypeCoder remote_addrsqlType=new StringCoder();


	TypeCoder coder=new StringCoder();

			private static String[] auditParams=new String[]{"email"};

	private static final int rls=RoleMask.user;

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

		DenetimGorevlisi.tek().write(request.getHostId(),request.getSession().getCid(),request.getUri(),"rom.contacts.new", auditParams, request.getAuditParams(auditParams));
	request.checkRole(rls);



		try (DbThree db3 = new DbThree(callProto)) {
			String val;

				db3.setInt(request.getHostId());


				String lang=request.getParam("lng", null, null, false);
				if(lang==null){
						lang=request.getReqLang();
				}
				db3.setString(lang);

				val=request.getParam("lng", null, null, false);
				lngsqlType.setDbValue(db3, lngsqlType.decode(val));

				val=request.getParam("cc", null, null, false);
				ccsqlType.setDbValue(db3, ccsqlType.decode(val));

				val=request.getParam("email", null, null, false);
				emailsqlType.setDbValue(db3, emailsqlType.decode(val));

				val=request.getParam("cipher", null, null, false);
				ciphersqlType.setDbValue(db3, ciphersqlType.decode(val));

				val=request.getParam("firstname", null, null, false);
				firstnamesqlType.setDbValue(db3, firstnamesqlType.decode(val));

				val=request.getParam("lastname", null, null, false);
				lastnamesqlType.setDbValue(db3, lastnamesqlType.decode(val));

				val=request.getParam("fid", null, null, false);
				fidsqlType.setDbValue(db3, fidsqlType.decode(val));

				val=request.getParam("twitter", null, null, false);
				twittersqlType.setDbValue(db3, twittersqlType.decode(val));

				val=request.getParam("mobile", null, null, false);
				mobilesqlType.setDbValue(db3, mobilesqlType.decode(val));

				String x = request.getSession().getIpAddress();
				db3.setString(x);


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
	public Long getCachePeriod(){
		return 600L;
	}

}
