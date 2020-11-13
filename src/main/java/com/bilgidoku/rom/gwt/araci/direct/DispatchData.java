package com.bilgidoku.rom.gwt.araci.direct;

import com.bilgidoku.rom.gwt.araci.direct.service.*;
//appdistpatch

import com.bilgidoku.rom.gwt.server.common.*;
import com.bilgidoku.rom.gwt.server.common.coders.*;

import com.bilgidoku.rom.type.DispatchBase;

import com.bilgidoku.rom.gwt.shared.*;

import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.*;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;




public class DispatchData extends DispatchBase{

	public DispatchData(){
		//appadddispatch
		add(true,false,"/_directmethods","fbcb",0,new String[]{},
			HttpMethod.POST,false,"service","UygulamaYonetimiCagriGorevlisi",null,
			UriHierarychy.SINGLE,Net.INTRA,new UygulamaYonetimiCagri_fbcb(),1000,null); 


		resolveInheritance();
	}
}
