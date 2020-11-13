package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;


import com.google.gwt.json.client.JSONString;

import com.bilgidoku.rom.gwt.shared.*;

public class SiteInfoDao extends DaoBase{
	//srvmethodrender
	public static void sitemap(   
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



			methodResp.getNow("/_info//sitemap.rom");
	}//srvmethodrender
	public static void robots(   
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



			methodResp.getNow("/_info//robots.rom");
	}//srvmethodrender
	public static void startssl(   
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



			methodResp.getNow("/_info//startssl.rom");
	}
}
