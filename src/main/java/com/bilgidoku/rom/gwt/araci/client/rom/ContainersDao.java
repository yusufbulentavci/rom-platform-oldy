package com.bilgidoku.rom.gwt.araci.client.rom;
// daorender
import com.bilgidoku.rom.gwt.client.common.*;
import com.bilgidoku.rom.gwt.client.common.resp.*;
import com.bilgidoku.rom.gwt.client.common.coders.*;

import com.google.gwt.json.client.JSONString;
import com.bilgidoku.rom.gwt.araci.client.rom.*;
import com.bilgidoku.rom.gwt.araci.client.bilgi.*;
import com.bilgidoku.rom.gwt.araci.client.site.*;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.*;
import com.bilgidoku.rom.gwt.araci.client.asset.*;


public class ContainersDao extends DaoBase{
	// dbmethodrender
	public static void listsub(String container,String self  , 
	ContainersResponse methodResp
	) {

			methodResp.setCoder(
			new ContainersCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("container",new StringCoder().encode(container));


			methodResp.getNow(self+"/listsub.rom");
		}
			// dbmethodrender
	public static void listing(String space,String name,String self  , 
	ContainersResponse methodResp
	) {

			methodResp.setCoder(
			new ContainersCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("space",new StringCoder().encode(space));
					methodResp.addParam("name",new StringCoder().encode(name));


			methodResp.getNow(self+"/listing.rom");
		}
			// dbmethodrender
	public static void reuri(String uri,String uriprefix,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("uri",new StringCoder().encode(uri));
					methodResp.addParam("uriprefix",new StringCoder().encode(uriprefix));


			methodResp.postNow(self+"/reuri.rom");
		}
			
}
