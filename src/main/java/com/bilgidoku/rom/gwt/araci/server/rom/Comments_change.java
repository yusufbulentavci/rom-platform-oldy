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


public class Comments_change extends DbDaoBase{

	private final String callProto="select * from rom.comments_change(?,?,?::rom.langs,?::rom.langs,?,?,?,?::json,?::json,?,?,?,?::text[],?::text[],?)";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder selfsqlType=new StringCoder();
		TypeCoder langsqlType=new StringCoder();
		TypeCoder lngsqlType=new StringCoder();
		TypeCoder dialog_idsqlType=new StringCoder();
		TypeCoder contactsqlType=new StringCoder();
		TypeCoder commentsqlType=new StringCoder();
		TypeCoder cmdsqlType=new JsonCoder();
		TypeCoder mimesqlType=new JsonCoder();
		TypeCoder bymailsqlType=new StringCoder();
		TypeCoder approvedsqlType=new BooleanCoder();
		TypeCoder refer_commentsqlType=new StringCoder();
		TypeCoder likessqlType=new ArrayCoder(new StringCoder());
		TypeCoder dislikessqlType=new ArrayCoder(new StringCoder());
		TypeCoder onpagesqlType=new BooleanCoder();


	TypeCoder coder=new StringCoder();


	private static final int rls=RoleMask.contact;

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

				val=request.getParam("dialog_id", null, null, false);
				dialog_idsqlType.setDbValue(db3, dialog_idsqlType.decode(val));

				val=request.getParam("contact", null, null, false);
				contactsqlType.setDbValue(db3, contactsqlType.decode(val));

				val=request.getParam("comment", null, null, false);
				commentsqlType.setDbValue(db3, commentsqlType.decode(val));

				val=request.getParam("cmd", null, null, false);
				cmdsqlType.setDbValue(db3, cmdsqlType.decode(val));

				val=request.getParam("mime", null, null, false);
				mimesqlType.setDbValue(db3, mimesqlType.decode(val));

				val=request.getParam("bymail", null, null, false);
				bymailsqlType.setDbValue(db3, bymailsqlType.decode(val));

				val=request.getParam("approved", null, null, false);
				approvedsqlType.setDbValue(db3, approvedsqlType.decode(val));

				val=request.getParam("refer_comment", null, null, false);
				refer_commentsqlType.setDbValue(db3, refer_commentsqlType.decode(val));

				val=request.getParam("likes", null, null, false);
				likessqlType.setDbValue(db3, likessqlType.decode(val));

				val=request.getParam("dislikes", null, null, false);
				dislikessqlType.setDbValue(db3, dislikessqlType.decode(val));

				val=request.getParam("onpage", null, null, false);
				onpagesqlType.setDbValue(db3, onpagesqlType.decode(val));


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
		return 0L;
	}

}
