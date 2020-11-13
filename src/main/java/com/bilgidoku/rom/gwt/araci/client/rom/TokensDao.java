package com.bilgidoku.rom.gwt.araci.client.rom;
// daorender
import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;

import com.google.gwt.json.client.JSONString;
import com.bilgidoku.rom.gwt.araci.client.rom.*;
import com.bilgidoku.rom.gwt.araci.client.bilgi.*;
import com.bilgidoku.rom.gwt.araci.client.site.*;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.*;
import com.bilgidoku.rom.gwt.araci.client.asset.*;


public class TokensDao extends DaoBase{
	// dbmethodrender
	public static void ready(String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);




			methodResp.getNow(self+"/ready.rom");
		}
			// dbmethodrender
	public static void get(String self  , 
	JsonResponse methodResp
	) {

			methodResp.setCoder(
			new JsonCoder()
			);




			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void change(Json dataclient,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("dataclient",new JsonCoder().encode(dataclient));


			methodResp.postNow(self+"");
		}
			
}
