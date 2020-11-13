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


public class Files_setdetail extends DbDaoBase{

	private final String callProto="select * from site.files_setdetail(?,?,?::rom.langs,?::rom.langs,?,?,?,?,?,?,?,?,?::json,?::text[])";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder selfsqlType=new StringCoder();
		TypeCoder langsqlType=new StringCoder();
		TypeCoder lngsqlType=new StringCoder();
		TypeCoder titlesqlType=new StringCoder();
		TypeCoder summarysqlType=new StringCoder();
		TypeCoder tipsqlType=new StringCoder();
		TypeCoder iconsqlType=new StringCoder();
		TypeCoder medium_iconsqlType=new StringCoder();
		TypeCoder large_iconsqlType=new StringCoder();
		TypeCoder multilang_iconsqlType=new StringCoder();
		TypeCoder soundsqlType=new StringCoder();
		TypeCoder viewysqlType=new JsonCoder();
		TypeCoder metassqlType=new ArrayCoder(new StringCoder());


	TypeCoder coder=new StringCoder();


	private static final int rls=RoleMask.author|RoleMask.admin|RoleMask.designer;

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

				val=request.getParam("title", null, null, false);
				titlesqlType.setDbValue(db3, titlesqlType.decode(val));

				val=request.getParam("summary", null, null, false);
				summarysqlType.setDbValue(db3, summarysqlType.decode(val));

				val=request.getParam("tip", null, null, false);
				tipsqlType.setDbValue(db3, tipsqlType.decode(val));

				val=request.getParam("icon", null, null, false);
				iconsqlType.setDbValue(db3, iconsqlType.decode(val));

				val=request.getParam("medium_icon", null, null, false);
				medium_iconsqlType.setDbValue(db3, medium_iconsqlType.decode(val));

				val=request.getParam("large_icon", null, null, false);
				large_iconsqlType.setDbValue(db3, large_iconsqlType.decode(val));

				val=request.getParam("multilang_icon", null, null, false);
				multilang_iconsqlType.setDbValue(db3, multilang_iconsqlType.decode(val));

				val=request.getParam("sound", null, null, false);
				soundsqlType.setDbValue(db3, soundsqlType.decode(val));

				val=request.getParam("viewy", null, null, false);
				viewysqlType.setDbValue(db3, viewysqlType.decode(val));

				val=request.getParam("metas", null, null, false);
				metassqlType.setDbValue(db3, metassqlType.decode(val));


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

}
