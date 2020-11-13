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


public class Files_imagemaketransparent extends DbDaoBase{

	private final String callProto="select * from site.files_imagemaketransparent(?,?,?,?,?)";

	private final BeforeHook beforeHook=getBeforeHook("com.bilgidoku.rom.web.db.image.maketransparent");


		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder selfsqlType=new StringCoder();
		TypeCoder leftsqlType=new IntegerCoder();
		TypeCoder topsqlType=new IntegerCoder();
		TypeCoder closeindsqlType=new IntegerCoder();


	TypeCoder coder=new StringCoder();



	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {


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

				val=request.getParam("left", null, null, false);
				leftsqlType.setDbValue(db3, leftsqlType.decode(val));

				val=request.getParam("top", null, null, false);
				topsqlType.setDbValue(db3, topsqlType.decode(val));

				val=request.getParam("closeind", null, null, false);
				closeindsqlType.setDbValue(db3, closeindsqlType.decode(val));


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
