package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;


import com.google.gwt.json.client.JSONString;

import com.bilgidoku.rom.gwt.shared.*;

public class HesapDao extends DaoBase{
	//srvmethodrender
	public static void hostfeatures(   
	ArrayResponse<HostFeature> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<HostFeature>(new HostFeatureCoder())
			);



			methodResp.postNow("/_account/hostfeatures.rom");
	}//srvmethodrender
	public static void tariffs(   
	ArrayResponse<Tariff> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<Tariff>(new TariffCoder())
			);



			methodResp.postNow("/_account/tariffs.rom");
	}//srvmethodrender
	public static void account(   
	AccountResponse methodResp
	) {

			methodResp.setCoder(
			new AccountCoder()
			);



			methodResp.postNow("/_account/account.rom");
	}//srvmethodrender
	public static void features(   
	ArrayResponse<Feature> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<Feature>(new FeatureCoder())
			);



			methodResp.postNow("/_account/features.rom");
	}
}
