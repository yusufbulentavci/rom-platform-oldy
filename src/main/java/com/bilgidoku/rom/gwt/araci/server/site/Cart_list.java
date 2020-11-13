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


public class Cart_list extends DbDaoBase{

	private final String callProto="select * from site.cart_list(?)";



		TypeCoder hostsqlType=new IntegerCoder();


	TypeCoder coder=new CartCoder();



	@Override
	public Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError {




		try (DbThree db3 = new DbThree(callProto)) {
			String val;

				db3.setInt(request.getHostId());



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

}
