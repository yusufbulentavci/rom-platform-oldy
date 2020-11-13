package com.bilgidoku.rom.gwt.araci.server.site;
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


public class Writings_breed extends DbDaoBase{

	private final String callProto="select * from site.writings_breed(?,?,?::rom.langs,?::rom.langs,?,?,?,?,?,?)";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder urisqlType=new StringCoder();
		TypeCoder langsqlType=new StringCoder();
		TypeCoder lngsqlType=new StringCoder();
		TypeCoder contactsqlType=new StringCoder();
		TypeCoder masksqlType=new LongCoder();
		TypeCoder uri_prefixsqlType=new StringCoder();
		TypeCoder defaulthtmlsqlType=new StringCoder();
		TypeCoder titlesqlType=new StringCoder();
		TypeCoder parentsqlType=new StringCoder();


	TypeCoder coder=new ContainersCoder();

			private static String[] auditParams=new String[]{"uri"};

	private static final int rls=RoleMask.user;

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

		DenetimGorevlisi.tek().write(request.getHostId(),request.getSession().getCid(),request.getUri(),"site.writings.breed", auditParams, request.getAuditParams(auditParams));
	request.checkRole(rls);



		try (DbThree db3 = new DbThree(callProto)) {
			String val;

				db3.setInt(request.getHostId());


				val=request.getParam("uri", null, null, false);
				urisqlType.setDbValue(db3, urisqlType.decode(val));

				String lang=request.getParam("lng", null, null, false);
				if(lang==null){
						lang=request.getReqLang();
				}
				db3.setString(lang);

				val=request.getParam("lng", null, null, false);
				lngsqlType.setDbValue(db3, lngsqlType.decode(val));

				{
					String cid=request.getSession().getCid();
					if(cid==null)
						db3.setNull(java.sql.Types.VARCHAR);
					else
						db3.setString(cid);
				}
				val=request.getParam("mask", null, null, false);
				masksqlType.setDbValue(db3, masksqlType.decode(val));

				val=request.getParam("uri_prefix", null, null, false);
				uri_prefixsqlType.setDbValue(db3, uri_prefixsqlType.decode(val));

				val=request.getParam("defaulthtml", null, null, false);
				defaulthtmlsqlType.setDbValue(db3, defaulthtmlsqlType.decode(val));

				val=request.getParam("title", null, null, false);
				titlesqlType.setDbValue(db3, titlesqlType.decode(val));

				val=request.getParam("parent", null, null, false);
				parentsqlType.setDbValue(db3, parentsqlType.decode(val));


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
