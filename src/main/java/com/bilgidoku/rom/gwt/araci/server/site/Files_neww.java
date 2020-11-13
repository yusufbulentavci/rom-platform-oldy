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


public class Files_neww extends DbDaoBase{

	private final String callProto="select * from site.files_new(?,?,?::rom.langs,?::rom.langs,?,?,?::text[],?,?::text[],?,?)";

	private final BeforeHook beforeHook=getBeforeHook("com.bilgidoku.rom.web.db.files.newfile");


		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder selfsqlType=new StringCoder();
		TypeCoder langsqlType=new StringCoder();
		TypeCoder lngsqlType=new StringCoder();
		TypeCoder urisqlType=new StringCoder();
		TypeCoder contactsqlType=new StringCoder();
		TypeCoder metassqlType=new ArrayCoder(new StringCoder());
		TypeCoder download_urisqlType=new StringCoder();
		TypeCoder mulfnsqlType=new ArrayCoder(new StringCoder());
		TypeCoder filenamesqlType=new StringCoder();
		TypeCoder textcontentsqlType=new StringCoder();


	TypeCoder coder=new StringCoder();

			private static String[] auditParams=new String[]{"uri"};

	private static final int rls=RoleMask.author|RoleMask.contact|RoleMask.admin|RoleMask.designer;

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

		DenetimGorevlisi.tek().write(request.getHostId(),request.getSession().getCid(),request.getUri(),"site.files.new", auditParams, request.getAuditParams(auditParams));
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


				db3.setString(request.getUri());

				String lang=request.getParam("lng", null, null, false);
				if(lang==null){
						lang=request.getReqLang();
				}
				db3.setString(lang);

				val=request.getParam("lng", null, null, false);
				lngsqlType.setDbValue(db3, lngsqlType.decode(val));

				val=request.getParam("uri", null, null, false);
				urisqlType.setDbValue(db3, urisqlType.decode(val));

				{
					String cid=request.getSession().getCid();
					if(cid==null)
						db3.setNull(java.sql.Types.VARCHAR);
					else
						db3.setString(cid);
				}
				val=request.getParam("metas", null, null, false);
				metassqlType.setDbValue(db3, metassqlType.decode(val));

				val=request.getParam("download_uri", null, null, false);
				download_urisqlType.setDbValue(db3, download_urisqlType.decode(val));

				val=request.getParam("mulfn", null, null, false);
				mulfnsqlType.setDbValue(db3, mulfnsqlType.decode(val));

				val=request.getParam("filename", null, null, false);
				filenamesqlType.setDbValue(db3, filenamesqlType.decode(val));

				val=request.getParam("textcontent", null, null, false);
				textcontentsqlType.setDbValue(db3, textcontentsqlType.decode(val));


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
