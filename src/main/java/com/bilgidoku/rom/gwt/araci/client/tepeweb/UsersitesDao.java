package com.bilgidoku.rom.gwt.araci.client.tepeweb;
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


public class UsersitesDao extends DaoBase{
	// dbmethodrender
	public static void neww(String title,String lang,Integer remotehost,String email,String adminname,String password,String lastcountry,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("title",new StringCoder().encode(title));
					methodResp.addParam("lang",new StringCoder().encode(lang));
					methodResp.addParam("remotehost",new IntegerCoder().encode(remotehost));
					methodResp.addParam("email",new StringCoder().encode(email));
					methodResp.addParam("adminname",new StringCoder().encode(adminname));
					methodResp.addParam("password",new StringCoder().encode(password));
					methodResp.addParam("lastcountry",new StringCoder().encode(lastcountry));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void list(String self  , 
	UsersitesResponse methodResp
	) {

			methodResp.setCoder(
			new UsersitesCoder()
			);

			methodResp.enableArray();



			methodResp.getNow(self+"");
		}
			
}
