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


public class Dialogs_change extends DbDaoBase{

	private final String callProto="select * from rom.dialogs_change(?,?,?,?,?,?,?,?,?,?,?::text[])";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder selfsqlType=new StringCoder();
		TypeCoder allow_attachsqlType=new BooleanCoder();
		TypeCoder approvalsqlType=new BooleanCoder();
		TypeCoder deletablesqlType=new BooleanCoder();
		TypeCoder updatablesqlType=new BooleanCoder();
		TypeCoder likeablesqlType=new BooleanCoder();
		TypeCoder dislikablesqlType=new BooleanCoder();
		TypeCoder sharablesqlType=new BooleanCoder();
		TypeCoder closedsqlType=new BooleanCoder();
		TypeCoder contactssqlType=new ArrayCoder(new StringCoder());


	TypeCoder coder=new DialogsCoder();


	private static final int rls=RoleMask.user;

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

	request.checkRole(rls);



		try (DbThree db3 = new DbThree(callProto)) {
			String val;

				db3.setInt(request.getHostId());


				db3.setString(request.getUri());

				val=request.getParam("allow_attach", null, null, false);
				allow_attachsqlType.setDbValue(db3, allow_attachsqlType.decode(val));

				val=request.getParam("approval", null, null, false);
				approvalsqlType.setDbValue(db3, approvalsqlType.decode(val));

				val=request.getParam("deletable", null, null, false);
				deletablesqlType.setDbValue(db3, deletablesqlType.decode(val));

				val=request.getParam("updatable", null, null, false);
				updatablesqlType.setDbValue(db3, updatablesqlType.decode(val));

				val=request.getParam("likeable", null, null, false);
				likeablesqlType.setDbValue(db3, likeablesqlType.decode(val));

				val=request.getParam("dislikable", null, null, false);
				dislikablesqlType.setDbValue(db3, dislikablesqlType.decode(val));

				val=request.getParam("sharable", null, null, false);
				sharablesqlType.setDbValue(db3, sharablesqlType.decode(val));

				val=request.getParam("closed", null, null, false);
				closedsqlType.setDbValue(db3, closedsqlType.decode(val));

				val=request.getParam("contacts", null, null, false);
				contactssqlType.setDbValue(db3, contactssqlType.decode(val));


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
