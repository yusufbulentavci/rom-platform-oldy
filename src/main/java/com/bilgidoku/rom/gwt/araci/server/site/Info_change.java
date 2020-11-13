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


public class Info_change extends DbDaoBase{

	private final String callProto="select * from site.info_change(?,?::rom.langs,?::rom.langs,?,?::json,?,?::json,?::json,?::json,?::json,?,?,?::json,?,?,?,?,?,?,?)";



		TypeCoder hostsqlType=new IntegerCoder();
		TypeCoder langsqlType=new StringCoder();
		TypeCoder lngsqlType=new StringCoder();
		TypeCoder stylesqlType=new StringCoder();
		TypeCoder headertextsqlType=new JsonCoder();
		TypeCoder default_appsqlType=new StringCoder();
		TypeCoder palettesqlType=new JsonCoder();
		TypeCoder text_fontsqlType=new JsonCoder();
		TypeCoder site_footersqlType=new JsonCoder();
		TypeCoder addresssqlType=new JsonCoder();
		TypeCoder browser_titlesqlType=new StringCoder();
		TypeCoder banner_imgsqlType=new StringCoder();
		TypeCoder logo_imgsqlType=new JsonCoder();
		TypeCoder browser_iconsqlType=new StringCoder();
		TypeCoder text1sqlType=new StringCoder();
		TypeCoder text2sqlType=new StringCoder();
		TypeCoder menu1sqlType=new StringCoder();
		TypeCoder menu2sqlType=new StringCoder();
		TypeCoder list1sqlType=new StringCoder();
		TypeCoder list2sqlType=new StringCoder();


	TypeCoder coder=new StringCoder();


	private static final int rls=RoleMask.author|RoleMask.admin|RoleMask.designer;

	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {

	request.checkRole(rls);



		try (DbThree db3 = new DbThree(callProto)) {
			String val;

				db3.setInt(request.getHostId());


				String lang=request.getParam("lng", null, null, false);
				if(lang==null){
						lang=request.getReqLang();
				}
				db3.setString(lang);

				val=request.getParam("lng", null, null, false);
				lngsqlType.setDbValue(db3, lngsqlType.decode(val));

				val=request.getParam("style", null, null, false);
				stylesqlType.setDbValue(db3, stylesqlType.decode(val));

				val=request.getParam("headertext", null, null, false);
				headertextsqlType.setDbValue(db3, headertextsqlType.decode(val));

				val=request.getParam("default_app", null, null, false);
				default_appsqlType.setDbValue(db3, default_appsqlType.decode(val));

				val=request.getParam("palette", null, null, false);
				palettesqlType.setDbValue(db3, palettesqlType.decode(val));

				val=request.getParam("text_font", null, null, false);
				text_fontsqlType.setDbValue(db3, text_fontsqlType.decode(val));

				val=request.getParam("site_footer", null, null, false);
				site_footersqlType.setDbValue(db3, site_footersqlType.decode(val));

				val=request.getParam("address", null, null, false);
				addresssqlType.setDbValue(db3, addresssqlType.decode(val));

				val=request.getParam("browser_title", null, null, false);
				browser_titlesqlType.setDbValue(db3, browser_titlesqlType.decode(val));

				val=request.getParam("banner_img", null, null, false);
				banner_imgsqlType.setDbValue(db3, banner_imgsqlType.decode(val));

				val=request.getParam("logo_img", null, null, false);
				logo_imgsqlType.setDbValue(db3, logo_imgsqlType.decode(val));

				val=request.getParam("browser_icon", null, null, false);
				browser_iconsqlType.setDbValue(db3, browser_iconsqlType.decode(val));

				val=request.getParam("text1", null, null, false);
				text1sqlType.setDbValue(db3, text1sqlType.decode(val));

				val=request.getParam("text2", null, null, false);
				text2sqlType.setDbValue(db3, text2sqlType.decode(val));

				val=request.getParam("menu1", null, null, false);
				menu1sqlType.setDbValue(db3, menu1sqlType.decode(val));

				val=request.getParam("menu2", null, null, false);
				menu2sqlType.setDbValue(db3, menu2sqlType.decode(val));

				val=request.getParam("list1", null, null, false);
				list1sqlType.setDbValue(db3, list1sqlType.decode(val));

				val=request.getParam("list2", null, null, false);
				list2sqlType.setDbValue(db3, list2sqlType.decode(val));


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
