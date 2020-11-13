package com.bilgidoku.rom.gwt.araci.client.service;

import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;


import com.google.gwt.json.client.JSONString;

import com.bilgidoku.rom.gwt.shared.*;

public class DenetimDao extends DaoBase{
	//srvmethodrender
	public static void list(Long fromtime,Long totime,String cid  , 
	ArrayResponse<AuditItem> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<AuditItem>(new AuditItemCoder())
			);


					methodResp.addParam("fromtime",new LongCoder().encode(fromtime));
					methodResp.addParam("totime",new LongCoder().encode(totime));
					methodResp.addParam("cid",new StringCoder().encode(cid));


			methodResp.postNow("/_audit/list.rom");
	}
}
