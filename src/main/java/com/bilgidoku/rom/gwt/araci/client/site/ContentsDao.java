package com.bilgidoku.rom.gwt.araci.client.site;
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


public class ContentsDao extends DaoBase{
	// dbmethodrender
	public static void summary(String lng,String summary,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("summary",new StringCoder().encode(summary));


			methodResp.postNow(self+"/summary.rom");
		}
			// dbmethodrender
	public static void dellang(String lng,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.postNow(self+"/dellang.rom");
		}
			// dbmethodrender
	public static void changelang(String lng,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.postNow(self+"/changelang.rom");
		}
			// dbmethodrender
	public static void newlang(String lng,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.postNow(self+"/newlang.rom");
		}
			// dbmethodrender
	public static void icon(String icon,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("icon",new StringCoder().encode(icon));


			methodResp.postNow(self+"/icon.rom");
		}
			// dbmethodrender
	public static void tip(String lng,String tip,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("tip",new StringCoder().encode(tip));


			methodResp.postNow(self+"/tip.rom");
		}
			// dbmethodrender
	public static void multilangicon(String lng,String icon,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("icon",new StringCoder().encode(icon));


			methodResp.postNow(self+"/multilangicon.rom");
		}
			// dbmethodrender
	public static void title(String lng,String title,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("title",new StringCoder().encode(title));


			methodResp.postNow(self+"/title.rom");
		}
			// dbmethodrender
	public static void content(String lng,String title,String summary,String tip,String icon,String medium_icon,String large_icon,String multilang_icon,String sound,Json viewy,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("title",new StringCoder().encode(title));
					methodResp.addParam("summary",new StringCoder().encode(summary));
					methodResp.addParam("tip",new StringCoder().encode(tip));
					methodResp.addParam("icon",new StringCoder().encode(icon));
					methodResp.addParam("medium_icon",new StringCoder().encode(medium_icon));
					methodResp.addParam("large_icon",new StringCoder().encode(large_icon));
					methodResp.addParam("multilang_icon",new StringCoder().encode(multilang_icon));
					methodResp.addParam("sound",new StringCoder().encode(sound));
					methodResp.addParam("viewy",new JsonCoder().encode(viewy));


			methodResp.postNow(self+"/content.rom");
		}
			// dbmethodrender
	public static void largeicon(String icon,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("icon",new StringCoder().encode(icon));


			methodResp.postNow(self+"/largeicon.rom");
		}
			// dbmethodrender
	public static void mediumicon(String icon,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("icon",new StringCoder().encode(icon));


			methodResp.postNow(self+"/mediumicon.rom");
		}
			
}
