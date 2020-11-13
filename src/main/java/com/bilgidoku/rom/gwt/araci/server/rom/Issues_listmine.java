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


public class Issues_listmine extends DbDaoBase{

	private final String callProto="select * from rom.issues_listmine(?,?,?,?,?::timestamp,?::timestamp)";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder contactsqlType=new StringCoder();
		TypeCoder notclosedsqlType=new BooleanCoder();
		TypeCoder notresolvedsqlType=new BooleanCoder();
		TypeCoder sincesqlType=new StringCoder();
		TypeCoder totimesqlType=new StringCoder();


	TypeCoder coder=new IssuesCoder();


	private static final int rls=RoleMask.contact;

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
				val=request.getParam("notclosed", null, null, false);
				notclosedsqlType.setDbValue(db3, notclosedsqlType.decode(val));

				val=request.getParam("notresolved", null, null, false);
				notresolvedsqlType.setDbValue(db3, notresolvedsqlType.decode(val));

				val=request.getParam("since", null, null, false);
				sincesqlType.setDbValue(db3, sincesqlType.decode(val));

				val=request.getParam("totime", null, null, false);
				totimesqlType.setDbValue(db3, totimesqlType.decode(val));


			if(!db3.executeQuery()){
				return null;
			}


			JSONArray ret=new JSONArray();
			while (db3.next()) {
				Object o=coder.getDbValue(db3);
				Object json=coder.encode(o);
				ret.put(json);
			}

			return ret;
		}catch (JSONException e) {
			throw errJson(e);
		}

	}
	public Long getCachePeriod(){
		return 60L;
	}

}
