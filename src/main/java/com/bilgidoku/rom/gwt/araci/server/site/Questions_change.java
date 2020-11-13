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


public class Questions_change extends DbDaoBase{

	private final String callProto="select * from site.questions_change(?,?,?::rom.langs,?::rom.langs,?,?,?,?,?,?,?)";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder selfsqlType=new StringCoder();
		TypeCoder langsqlType=new StringCoder();
		TypeCoder lngsqlType=new StringCoder();
		TypeCoder optionasqlType=new StringCoder();
		TypeCoder optionbsqlType=new StringCoder();
		TypeCoder optioncsqlType=new StringCoder();
		TypeCoder optiondsqlType=new StringCoder();
		TypeCoder optionesqlType=new StringCoder();
		TypeCoder optioncountsqlType=new IntegerCoder();
		TypeCoder correctsqlType=new IntegerCoder();


	TypeCoder coder=new StringCoder();


	private static final int rls=RoleMask.user;

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

				val=request.getParam("optiona", null, null, false);
				optionasqlType.setDbValue(db3, optionasqlType.decode(val));

				val=request.getParam("optionb", null, null, false);
				optionbsqlType.setDbValue(db3, optionbsqlType.decode(val));

				val=request.getParam("optionc", null, null, false);
				optioncsqlType.setDbValue(db3, optioncsqlType.decode(val));

				val=request.getParam("optiond", null, null, false);
				optiondsqlType.setDbValue(db3, optiondsqlType.decode(val));

				val=request.getParam("optione", null, null, false);
				optionesqlType.setDbValue(db3, optionesqlType.decode(val));

				val=request.getParam("optioncount", null, null, false);
				optioncountsqlType.setDbValue(db3, optioncountsqlType.decode(val));

				val=request.getParam("correct", null, null, false);
				correctsqlType.setDbValue(db3, correctsqlType.decode(val));


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
