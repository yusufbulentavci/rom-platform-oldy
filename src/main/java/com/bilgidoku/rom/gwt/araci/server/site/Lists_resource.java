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


public class Lists_resource extends DbDaoBase{

	private final String callProto="select * from site.lists_resource(?,?,?,?,?,?,?::text[],?)";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder selfsqlType=new StringCoder();
		TypeCoder html_filesqlType=new StringCoder();
		TypeCoder delegatedsqlType=new StringCoder();
		TypeCoder ownercidsqlType=new StringCoder();
		TypeCoder gidsqlType=new StringCoder();
		TypeCoder relatedcidssqlType=new ArrayCoder(new StringCoder());
		TypeCoder masksqlType=new LongCoder();


	TypeCoder coder=new StringCoder();



	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {




		try (DbThree db3 = new DbThree(callProto)) {
			String val;

				db3.setInt(request.getHostId());


				db3.setString(request.getUri());

				val=request.getParam("html_file", null, null, false);
				html_filesqlType.setDbValue(db3, html_filesqlType.decode(val));

				val=request.getParam("delegated", null, null, false);
				delegatedsqlType.setDbValue(db3, delegatedsqlType.decode(val));

				val=request.getParam("ownercid", null, null, false);
				ownercidsqlType.setDbValue(db3, ownercidsqlType.decode(val));

				val=request.getParam("gid", null, null, false);
				gidsqlType.setDbValue(db3, gidsqlType.decode(val));

				val=request.getParam("relatedcids", null, null, false);
				relatedcidssqlType.setDbValue(db3, relatedcidssqlType.decode(val));

				val=request.getParam("mask", null, null, false);
				masksqlType.setDbValue(db3, masksqlType.decode(val));


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
