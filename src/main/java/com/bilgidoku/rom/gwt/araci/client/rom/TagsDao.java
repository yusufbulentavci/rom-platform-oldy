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


public class TagsDao extends DaoBase{
	// dbmethodrender
	public static void neww(String lng,String title,String[] parenttags,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("title",new StringCoder().encode(title));
					methodResp.addParam("parenttags",new ArrayCoder(new StringCoder()).encode(parenttags));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void change(String lng,String title,String summary,String[] parenttags,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("title",new StringCoder().encode(title));
					methodResp.addParam("summary",new StringCoder().encode(summary));
					methodResp.addParam("parenttags",new ArrayCoder(new StringCoder()).encode(parenttags));


			methodResp.postNow(self+"");
		}
			// dbmethodrender
	public static void get(String lng,String self  , 
	TagsResponse methodResp
	) {

			methodResp.setCoder(
			new TagsCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


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
	public static void list(String lng,String self  , 
	TagsResponse methodResp
	) {

			methodResp.setCoder(
			new TagsCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"");
		}
			
}
