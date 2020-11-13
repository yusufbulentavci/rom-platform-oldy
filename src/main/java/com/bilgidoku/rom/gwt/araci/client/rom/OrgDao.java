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


public class OrgDao extends DaoBase{
	// dbmethodrender
	public static void getforcehttps(String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);




			methodResp.postNow(self+"/getforcehttps.rom");
		}
			// dbmethodrender
	public static void getcrs(String lng,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"/getcrs.rom");
		}
			// dbmethodrender
	public static void changelanged(String lng,String cpic,String crs,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("cpic",new StringCoder().encode(cpic));
					methodResp.addParam("crs",new StringCoder().encode(crs));


			methodResp.postNow(self+"/changelanged.rom");
		}
			// dbmethodrender
	public static void getorderpref(String self  , 
	JsonResponse methodResp
	) {

			methodResp.setCoder(
			new JsonCoder()
			);




			methodResp.postNow(self+"/getorderpref.rom");
		}
			// dbmethodrender
	public static void getga(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/getga.rom");
		}
			// dbmethodrender
	public static void setshipstyle(Json shipstyle,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("shipstyle",new JsonCoder().encode(shipstyle));


			methodResp.postNow(self+"/setshipstyle.rom");
		}
			// dbmethodrender
	public static void getnfs(String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);




			methodResp.postNow(self+"/getnfs.rom");
		}
			// dbmethodrender
	public static void getcpic(String lng,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"/getcpic.rom");
		}
			// dbmethodrender
	public static void getshipstyle(String self  , 
	JsonResponse methodResp
	) {

			methodResp.setCoder(
			new JsonCoder()
			);




			methodResp.getNow(self+"/getshipstyle.rom");
		}
			// dbmethodrender
	public static void dellang(String dellang,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("dellang",new StringCoder().encode(dellang));


			methodResp.postNow(self+"/dellang.rom");
		}
			// dbmethodrender
	public static void setpaystyle(Json paystyle,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("paystyle",new JsonCoder().encode(paystyle));


			methodResp.postNow(self+"/setpaystyle.rom");
		}
			// dbmethodrender
	public static void setga(String ga,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("ga",new StringCoder().encode(ga));


			methodResp.postNow(self+"/setga.rom");
		}
			// dbmethodrender
	public static void setforcehttps(Boolean forcehttps,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("forcehttps",new BooleanCoder().encode(forcehttps));


			methodResp.postNow(self+"/setforcehttps.rom");
		}
			// dbmethodrender
	public static void setfbappid(String fbappid,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("fbappid",new StringCoder().encode(fbappid));


			methodResp.postNow(self+"/setfbappid.rom");
		}
			// dbmethodrender
	public static void setstartssl(String startssl,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("startssl",new StringCoder().encode(startssl));


			methodResp.postNow(self+"/setstartssl.rom");
		}
			// dbmethodrender
	public static void getpaystyle(String self  , 
	JsonResponse methodResp
	) {

			methodResp.setCoder(
			new JsonCoder()
			);




			methodResp.getNow(self+"/getpaystyle.rom");
		}
			// dbmethodrender
	public static void setnfs(Json nfs,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("nfs",new JsonCoder().encode(nfs));


			methodResp.postNow(self+"/setnfs.rom");
		}
			// dbmethodrender
	public static void getstartssl(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/getstartssl.rom");
		}
			
}
