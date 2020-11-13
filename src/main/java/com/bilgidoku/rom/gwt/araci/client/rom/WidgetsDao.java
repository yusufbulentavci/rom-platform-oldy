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


public class WidgetsDao extends DaoBase{
	// dbmethodrender
	public static void change(String title,Json codes,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("title",new StringCoder().encode(title));
					methodResp.addParam("codes",new JsonCoder().encode(codes));


			methodResp.postNow(self+"");
		}
			// dbmethodrender
	public static void get(String self  , 
	WidgetsResponse methodResp
	) {

			methodResp.setCoder(
			new WidgetsCoder()
			);




			methodResp.getNow(self+"");
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
	WidgetsResponse methodResp
	) {

			methodResp.setCoder(
			new WidgetsCoder()
			);

			methodResp.enableArray();



			methodResp.getNow(self+"");
		}
			
}
