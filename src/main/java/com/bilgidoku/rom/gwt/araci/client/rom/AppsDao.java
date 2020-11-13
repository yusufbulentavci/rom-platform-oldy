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


public class AppsDao extends DaoBase{
	// dbmethodrender
	public static void neww(String title,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("title",new StringCoder().encode(title));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void change(String title,Json codes,Integer cls,Json state,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("title",new StringCoder().encode(title));
					methodResp.addParam("codes",new JsonCoder().encode(codes));
					methodResp.addParam("cls",new IntegerCoder().encode(cls));
					methodResp.addParam("state",new JsonCoder().encode(state));


			methodResp.postNow(self+"");
		}
			// dbmethodrender
	public static void get(String self  , 
	AppsResponse methodResp
	) {

			methodResp.setCoder(
			new AppsCoder()
			);




			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void destroy(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.deleteNow(self);
		}
			// dbmethodrender
	public static void list(String self  , 
	AppsResponse methodResp
	) {

			methodResp.setCoder(
			new AppsCoder()
			);

			methodResp.enableArray();



			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void breed(String uri,Long mask,String parent  , 
	ContainersResponse methodResp
	) {

			methodResp.setCoder(
			new ContainersCoder()
			);


				final String self="/_/c";
				final String schema="rom";
				final String table="apps";
				methodResp.addParam("table",new JSONString("apps"));

					methodResp.addParam("uri",new StringCoder().encode(uri));
					methodResp.addParam("mask",new LongCoder().encode(mask));
					methodResp.addParam("parent",new StringCoder().encode(parent));
					methodResp.addParam("schema",new StringCoder().encode(schema));
					methodResp.addParam("table",new StringCoder().encode(table));


			methodResp.postNow(self+"/new.rom");
		}
			
}
