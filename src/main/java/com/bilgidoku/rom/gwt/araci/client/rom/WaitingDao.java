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


public class WaitingDao extends DaoBase{
	// dbmethodrender
	public static void neww(String app,String code,String inref,String title,String username,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("app",new StringCoder().encode(app));
					methodResp.addParam("code",new StringCoder().encode(code));
					methodResp.addParam("inref",new StringCoder().encode(inref));
					methodResp.addParam("title",new StringCoder().encode(title));
					methodResp.addParam("username",new StringCoder().encode(username));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void setvalidperiod(String valid_after,String valid_before,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("valid_after",new StringCoder().encode(valid_after));
					methodResp.addParam("valid_before",new StringCoder().encode(valid_before));


			methodResp.postNow(self+"/setvalidperiod.rom");
		}
			// dbmethodrender
	public static void gotit(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/gotit.rom");
		}
			// dbmethodrender
	public static void destroy(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.deleteNow(self);
		}
			// dbmethodrender
	public static void list(String self  , 
	WaitingResponse methodResp
	) {

			methodResp.setCoder(
			new WaitingCoder()
			);

			methodResp.enableArray();



			methodResp.getNow(self+"");
		}
			
}
