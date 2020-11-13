package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;


import com.google.gwt.json.client.JSONString;

import com.bilgidoku.rom.gwt.shared.*;

public class YetkilendirmeDao extends DaoBase{
	//srvmethodrender
	public static void register(String firstname,String lastname,String countrycode,String email,String credential  , 
	DescRespResponse methodResp
	) {

			methodResp.setCoder(
			new DescRespCoder()
			);


					methodResp.addParam("firstname",new StringCoder().encode(firstname));
					methodResp.addParam("lastname",new StringCoder().encode(lastname));
					methodResp.addParam("countrycode",new StringCoder().encode(countrycode));
					methodResp.addParam("email",new StringCoder().encode(email));
					methodResp.addParam("credential",new StringCoder().encode(credential));


			methodResp.postNow("/_auth/register.rom");
	}//srvmethodrender
	public static void check(   
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



			methodResp.postNow("/_auth/check.rom");
	}//srvmethodrender
	public static void login(String user,String credential  , 
	DescRespResponse methodResp
	) {

			methodResp.setCoder(
			new DescRespCoder()
			);


					methodResp.addParam("user",new StringCoder().encode(user));
					methodResp.addParam("credential",new StringCoder().encode(credential));


			methodResp.postNow("/_auth/login.rom");
	}//srvmethodrender
	public static void letmecontact(   
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



			methodResp.postNow("/_auth/letmecontact.rom");
	}//srvmethodrender
	public static void logout(   
	DescRespResponse methodResp
	) {

			methodResp.setCoder(
			new DescRespCoder()
			);



			methodResp.postNow("/_auth/logout.rom");
	}//srvmethodrender
	public static void forgetcontactpass(String email  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);


					methodResp.addParam("email",new StringCoder().encode(email));


			methodResp.postNow("/_auth/forgetcontactpass.rom");
	}//srvmethodrender
	public static void forgetpass(String email  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);


					methodResp.addParam("email",new StringCoder().encode(email));


			methodResp.postNow("/_auth/forgetpass.rom");
	}//srvmethodrender
	public static void extplay(String platform  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);


					methodResp.addParam("platform",new StringCoder().encode(platform));


			methodResp.postNow("/_auth/extplay.rom");
	}//srvmethodrender
	public static void trustedplatformlogin(String platform,String accesstoken  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);


					methodResp.addParam("platform",new StringCoder().encode(platform));
					methodResp.addParam("accesstoken",new StringCoder().encode(accesstoken));


			methodResp.postNow("/_auth/trustedplatformlogin.rom");
	}
}
