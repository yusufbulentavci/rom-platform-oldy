package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;


import com.google.gwt.json.client.JSONString;

import com.bilgidoku.rom.gwt.shared.*;

public class HosgeldinDao extends DaoBase{
	//srvmethodrender
	public static void create(String email,String username,String password  , 
	IntegerResponse methodResp
	) {

			methodResp.setCoder(
			new IntegerCoder()
			);


					methodResp.addParam("email",new StringCoder().encode(email));
					methodResp.addParam("username",new StringCoder().encode(username));
					methodResp.addParam("password",new StringCoder().encode(password));


			methodResp.postNow("/_welcome/create.rom");
	}//srvmethodrender
	public static void sitesof(String email  , 
	ArrayResponse<Welcome> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<Welcome>(new WelcomeCoder())
			);


					methodResp.addParam("email",new StringCoder().encode(email));


			methodResp.postNow("/_welcome/sitesof.rom");
	}
}
