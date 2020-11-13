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


public class TariffmodelDao extends DaoBase{
	// dbmethodrender
	public static void neww(String title,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("title",new StringCoder().encode(title));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void setcode(String code,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("code",new StringCoder().encode(code));


			methodResp.postNow(self+"/setcode.rom");
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
	public static void setvatpercentage(Integer vatpercentage,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("vatpercentage",new IntegerCoder().encode(vatpercentage));


			methodResp.postNow(self+"/setvatpercentage.rom");
		}
			// dbmethodrender
	public static void setcoefficient(String coefficient,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("coefficient",new StringCoder().encode(coefficient));


			methodResp.postNow(self+"/setcoefficient.rom");
		}
			// dbmethodrender
	public static void list(String self  , 
	TariffmodelResponse methodResp
	) {

			methodResp.setCoder(
			new TariffmodelCoder()
			);

			methodResp.enableArray();



			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void setbaseprice(String baseprice,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("baseprice",new StringCoder().encode(baseprice));


			methodResp.postNow(self+"/setbaseprice.rom");
		}
			
}
