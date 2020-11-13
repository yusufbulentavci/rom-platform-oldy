package com.bilgidoku.rom.gwt.araci.client.site;
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


public class HitsDao extends DaoBase{
	// dbmethodrender
	public static void get(String self  , 
	HitsResponse methodResp
	) {

			methodResp.setCoder(
			new HitsCoder()
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
	public static void list(Long since,Long timeend,String self  , 
	HitsResponse methodResp
	) {

			methodResp.setCoder(
			new HitsCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("since",new LongCoder().encode(since));
					methodResp.addParam("timeend",new LongCoder().encode(timeend));


			methodResp.getNow(self+"");
		}
			
}
