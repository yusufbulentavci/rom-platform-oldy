package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;


import com.google.gwt.json.client.JSONString;

import com.bilgidoku.rom.gwt.shared.*;

public class YonetimDao extends DaoBase{
	//srvmethodrender
	public static void keys(   
	ArrayResponse<String> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<String>(new StringCoder())
			);



			methodResp.postNow("/_admin/keys.rom");
	}//srvmethodrender
	public static void getHostCerts(   
	ArrayResponse<RomCert> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<RomCert>(new RomCertCoder())
			);



			methodResp.postNow("/_admin/getHostCerts.rom");
	}//srvmethodrender
	public static void removeCert(String alias  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);


					methodResp.addParam("alias",new StringCoder().encode(alias));


			methodResp.postNow("/_admin/removeCert.rom");
	}//srvmethodrender
	public static void updateKeys(String alias  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);


					methodResp.addParam("alias",new StringCoder().encode(alias));


			methodResp.postNow("/_admin/updateKeys.rom");
	}
}
