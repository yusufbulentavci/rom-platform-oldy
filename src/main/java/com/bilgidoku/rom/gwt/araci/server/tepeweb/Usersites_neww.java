package com.bilgidoku.rom.gwt.araci.server.tepeweb;
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


public class Usersites_neww extends DbDaoBase{

	private final String callProto="select * from tepeweb.usersites_new(?,?,?,?,?,?,?,?,?,?)";

	private final BeforeHook beforeHook=getBeforeHook("com.bilgidoku.rom.web.db.usersites.createhost");


		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder contactsqlType=new StringCoder();
		TypeCoder remote_addrsqlType=new StringCoder();
		TypeCoder titlesqlType=new StringCoder();
		TypeCoder langsqlType=new StringCoder();
		TypeCoder remotehostsqlType=new IntegerCoder();
		TypeCoder emailsqlType=new StringCoder();
		TypeCoder adminnamesqlType=new StringCoder();
		TypeCoder passwordsqlType=new StringCoder();
		TypeCoder lastcountrysqlType=new StringCoder();


	TypeCoder coder=new StringCoder();

			private static String[] auditParams=new String[]{"title"};

	private static final int rls=RoleMask.contact;

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

		DenetimGorevlisi.tek().write(request.getHostId(),request.getSession().getCid(),request.getUri(),"tepeweb.usersites.new", auditParams, request.getAuditParams(auditParams));
	request.checkRole(rls);

		HookScope hookScope = new HookScope(request);
		boolean chargeBeforeHook = false;
		boolean acceptable = false;

		if (!beforeHook.hook(hookScope)) {
			acceptable = true;
			throw new KnownError().internalError();
		}
		chargeBeforeHook = true;

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
				String x = request.getSession().getIpAddress();
				db3.setString(x);

				val=request.getParam("title", null, null, false);
				titlesqlType.setDbValue(db3, titlesqlType.decode(val));

				val=request.getParam("lang", null, null, false);
				langsqlType.setDbValue(db3, langsqlType.decode(val));

				val=request.getParam("remotehost", null, null, false);
				remotehostsqlType.setDbValue(db3, remotehostsqlType.decode(val));

				val=request.getParam("email", null, null, false);
				emailsqlType.setDbValue(db3, emailsqlType.decode(val));

				val=request.getParam("adminname", null, null, false);
				adminnamesqlType.setDbValue(db3, adminnamesqlType.decode(val));

				val=request.getParam("password", null, null, false);
				passwordsqlType.setDbValue(db3, passwordsqlType.decode(val));

				val=request.getParam("lastcountry", null, null, false);
				lastcountrysqlType.setDbValue(db3, lastcountrysqlType.decode(val));


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

			acceptable=true;


			return ret;
		}catch (JSONException e) {
			throw errJson(e);
		}
		finally {
			if (!acceptable) {
					beforeHook.undo(hookScope);
			}
		}

	}

}
