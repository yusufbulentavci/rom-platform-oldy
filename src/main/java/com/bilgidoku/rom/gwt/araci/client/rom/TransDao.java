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


public class TransDao extends DaoBase{
	// dbmethodrender
	public static void getall(String self  , 
	TransResponse methodResp
	) {

			methodResp.setCoder(
			new TransCoder()
			);




			methodResp.getNow(self+"/getall.rom");
		}
			// dbmethodrender
	public static void change(String lng,Json title,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("title",new JsonCoder().encode(title));


			methodResp.postNow(self+"");
		}
			// dbmethodrender
	public static void get(String lng,String self  , 
	TransResponse methodResp
	) {

			methodResp.setCoder(
			new TransCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


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
	public static void changeall(String[] langs,Json[] title,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("langs",new ArrayCoder(new StringCoder()).encode(langs));
					methodResp.addParam("title",new ArrayCoder(new JsonCoder()).encode(title));


			methodResp.postNow(self+"/changeall.rom");
		}
			// dbmethodrender
	public static void changeconstants(Json constants,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("constants",new JsonCoder().encode(constants));


			methodResp.postNow(self+"/changeconstants.rom");
		}
			
}
