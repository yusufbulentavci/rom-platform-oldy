package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;


import com.google.gwt.json.client.JSONString;

import com.bilgidoku.rom.gwt.shared.*;

public class MisafirDao extends DaoBase{
	//srvmethodrender
	public static void contactform(String title,String body,String email,String phone,String addr  , 
	DescRespResponse methodResp
	) {

			methodResp.setCoder(
			new DescRespCoder()
			);


					methodResp.addParam("title",new StringCoder().encode(title));
					methodResp.addParam("body",new StringCoder().encode(body));
					methodResp.addParam("email",new StringCoder().encode(email));
					methodResp.addParam("phone",new StringCoder().encode(phone));
					methodResp.addParam("addr",new StringCoder().encode(addr));


			methodResp.postNow("/_guest/contactform.rom");
	}
}
