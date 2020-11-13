package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;


import com.google.gwt.json.client.JSONString;

import com.bilgidoku.rom.gwt.shared.*;

public class OturumIciCagriDao extends DaoBase{
	//srvmethodrender
	public static void hostName(   
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



			methodResp.postNow("/_sesfuncs/hostName.rom");
	}//srvmethodrender
	public static void userAgent(   
	JsonResponse methodResp
	) {

			methodResp.setCoder(
			new JsonCoder()
			);



			methodResp.postNow("/_sesfuncs/userAgent.rom");
	}//srvmethodrender
	public static void rtpresence(String presence,Integer code  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);


					methodResp.addParam("presence",new StringCoder().encode(presence));
					methodResp.addParam("code",new IntegerCoder().encode(code));


			methodResp.postNow("/_sesfuncs/rtpresence.rom");
	}//srvmethodrender
	public static void rtonlines(   
	ArrayResponse<String> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<String>(new StringCoder())
			);



			methodResp.postNow("/_sesfuncs/rtonlines.rom");
	}//srvmethodrender
	public static void rtexchange(String cid,String subcmd,String text,Json ext  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);


					methodResp.addParam("cid",new StringCoder().encode(cid));
					methodResp.addParam("subcmd",new StringCoder().encode(subcmd));
					methodResp.addParam("text",new StringCoder().encode(text));
					methodResp.addParam("ext",new JsonCoder().encode(ext));


			methodResp.postNow("/_sesfuncs/rtexchange.rom");
	}//srvmethodrender
	public static void rtsay(String cid,String msg  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);


					methodResp.addParam("cid",new StringCoder().encode(cid));
					methodResp.addParam("msg",new StringCoder().encode(msg));


			methodResp.postNow("/_sesfuncs/rtsay.rom");
	}//srvmethodrender
	public static void akil(String eylem,Json json  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);


					methodResp.addParam("eylem",new StringCoder().encode(eylem));
					methodResp.addParam("json",new JsonCoder().encode(json));


			methodResp.postNow("/_sesfuncs/akil.rom");
	}//srvmethodrender
	public static void hasDomain(   
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



			methodResp.postNow("/_sesfuncs/hasDomain.rom");
	}
}
