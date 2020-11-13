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


public class Contacts_change extends DbDaoBase{

	private final String callProto="select * from rom.contacts_change(?,?,?::rom.langs,?::rom.langs,?,?,?,?,?,?,?,?,?,?,?,?,?::rom.countrycode,?,?,?,?,?,?::text[],?::text[])";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder selfsqlType=new StringCoder();
		TypeCoder langsqlType=new StringCoder();
		TypeCoder lngsqlType=new StringCoder();
		TypeCoder ciphersqlType=new StringCoder();
		TypeCoder first_namesqlType=new StringCoder();
		TypeCoder last_namesqlType=new StringCoder();
		TypeCoder iconsqlType=new StringCoder();
		TypeCoder emailsqlType=new StringCoder();
		TypeCoder fb_idsqlType=new StringCoder();
		TypeCoder twittersqlType=new StringCoder();
		TypeCoder websqlType=new StringCoder();
		TypeCoder confirmedsqlType=new BooleanCoder();
		TypeCoder addresssqlType=new StringCoder();
		TypeCoder statesqlType=new StringCoder();
		TypeCoder citysqlType=new StringCoder();
		TypeCoder country_codesqlType=new StringCoder();
		TypeCoder postal_codesqlType=new StringCoder();
		TypeCoder organizationsqlType=new StringCoder();
		TypeCoder phonesqlType=new StringCoder();
		TypeCoder mobilesqlType=new StringCoder();
		TypeCoder faxsqlType=new StringCoder();
		TypeCoder tagssqlType=new ArrayCoder(new StringCoder());
		TypeCoder gidssqlType=new ArrayCoder(new StringCoder());


	TypeCoder coder=new StringCoder();


	private static final int rls=RoleMask.owner|RoleMask.user;

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

	request.checkRole(rls);



		try (DbThree db3 = new DbThree(callProto)) {
			String val;

				db3.setInt(request.getHostId());


				db3.setString(request.getUri());

				String lang=request.getParam("lng", null, null, false);
				if(lang==null){
						lang=request.getReqLang();
				}
				db3.setString(lang);

				val=request.getParam("lng", null, null, false);
				lngsqlType.setDbValue(db3, lngsqlType.decode(val));

				val=request.getParam("cipher", null, null, false);
				ciphersqlType.setDbValue(db3, ciphersqlType.decode(val));

				val=request.getParam("first_name", null, null, false);
				first_namesqlType.setDbValue(db3, first_namesqlType.decode(val));

				val=request.getParam("last_name", null, null, false);
				last_namesqlType.setDbValue(db3, last_namesqlType.decode(val));

				val=request.getParam("icon", null, null, false);
				iconsqlType.setDbValue(db3, iconsqlType.decode(val));

				val=request.getParam("email", null, null, false);
				emailsqlType.setDbValue(db3, emailsqlType.decode(val));

				val=request.getParam("fb_id", null, null, false);
				fb_idsqlType.setDbValue(db3, fb_idsqlType.decode(val));

				val=request.getParam("twitter", null, null, false);
				twittersqlType.setDbValue(db3, twittersqlType.decode(val));

				val=request.getParam("web", null, null, false);
				websqlType.setDbValue(db3, websqlType.decode(val));

				val=request.getParam("confirmed", null, null, false);
				confirmedsqlType.setDbValue(db3, confirmedsqlType.decode(val));

				val=request.getParam("address", null, null, false);
				addresssqlType.setDbValue(db3, addresssqlType.decode(val));

				val=request.getParam("state", null, null, false);
				statesqlType.setDbValue(db3, statesqlType.decode(val));

				val=request.getParam("city", null, null, false);
				citysqlType.setDbValue(db3, citysqlType.decode(val));

				val=request.getParam("country_code", null, null, false);
				country_codesqlType.setDbValue(db3, country_codesqlType.decode(val));

				val=request.getParam("postal_code", null, null, false);
				postal_codesqlType.setDbValue(db3, postal_codesqlType.decode(val));

				val=request.getParam("organization", null, null, false);
				organizationsqlType.setDbValue(db3, organizationsqlType.decode(val));

				val=request.getParam("phone", null, null, false);
				phonesqlType.setDbValue(db3, phonesqlType.decode(val));

				val=request.getParam("mobile", null, null, false);
				mobilesqlType.setDbValue(db3, mobilesqlType.decode(val));

				val=request.getParam("fax", null, null, false);
				faxsqlType.setDbValue(db3, faxsqlType.decode(val));

				val=request.getParam("tags", null, null, false);
				tagssqlType.setDbValue(db3, tagssqlType.decode(val));

				val=request.getParam("gids", null, null, false);
				gidssqlType.setDbValue(db3, gidssqlType.decode(val));


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
