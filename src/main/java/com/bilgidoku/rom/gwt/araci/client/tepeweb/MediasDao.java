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


public class MediasDao extends DaoBase{
	// dbmethodrender
	public static void getbyprovider(Integer pr,String pid,String self  , 
	MediasResponse methodResp
	) {

			methodResp.setCoder(
			new MediasCoder()
			);



					methodResp.addParam("pr",new IntegerCoder().encode(pr));
					methodResp.addParam("pid",new StringCoder().encode(pid));


			methodResp.getNow(self+"/getbyprovider.rom");
		}
			// dbmethodrender
	public static void get(String self  , 
	MediasResponse methodResp
	) {

			methodResp.setCoder(
			new MediasCoder()
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
	MediasResponse methodResp
	) {

			methodResp.setCoder(
			new MediasCoder()
			);

			methodResp.enableArray();



			methodResp.getNow(self+"");
		}
			
}
