package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;


import com.google.gwt.json.client.JSONString;

import com.bilgidoku.rom.gwt.shared.*;

public class InternetDao extends DaoBase{
	//srvmethodrender
	public static void buyMedia(Integer provider,String pid  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);


					methodResp.addParam("provider",new IntegerCoder().encode(provider));
					methodResp.addParam("pid",new StringCoder().encode(pid));


			methodResp.postNow("/_richweb/buyMedia.rom");
	}//srvmethodrender
	public static void searchimg(Integer pr,Integer limit,Integer offset,String phrase,String size,String aspect,String style,String colors,String face  , 
	ArrayResponse<ImageResp> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<ImageResp>(new ImageRespCoder())
			);


					methodResp.addParam("pr",new IntegerCoder().encode(pr));
					methodResp.addParam("limit",new IntegerCoder().encode(limit));
					methodResp.addParam("offset",new IntegerCoder().encode(offset));
					methodResp.addParam("phrase",new StringCoder().encode(phrase));
					methodResp.addParam("size",new StringCoder().encode(size));
					methodResp.addParam("aspect",new StringCoder().encode(aspect));
					methodResp.addParam("style",new StringCoder().encode(style));
					methodResp.addParam("colors",new StringCoder().encode(colors));
					methodResp.addParam("face",new StringCoder().encode(face));


			methodResp.postNow("/_richweb/searchimg.rom");
	}
}
