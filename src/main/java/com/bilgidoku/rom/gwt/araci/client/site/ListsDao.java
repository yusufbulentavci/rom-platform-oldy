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


public class ListsDao extends DaoBase{
	// dbmethodrender
	public static void neww(String lng,String title,String uri,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("title",new StringCoder().encode(title));
					methodResp.addParam("uri",new StringCoder().encode(uri));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void contains(String lng,String uri,String self  , 
	ContentsResponse methodResp
	) {

			methodResp.setCoder(
			new ContentsCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("uri",new StringCoder().encode(uri));


			methodResp.getNow(self+"/contains.rom");
		}
			// dbmethodrender
	public static void resource(String html_file,String delegated,String ownercid,String gid,String[] relatedcids,Long mask,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("html_file",new StringCoder().encode(html_file));
					methodResp.addParam("delegated",new StringCoder().encode(delegated));
					methodResp.addParam("ownercid",new StringCoder().encode(ownercid));
					methodResp.addParam("gid",new StringCoder().encode(gid));
					methodResp.addParam("relatedcids",new ArrayCoder(new StringCoder()).encode(relatedcids));
					methodResp.addParam("mask",new LongCoder().encode(mask));


			methodResp.postNow(self+"/resource.rom");
		}
			// dbmethodrender
	public static void content_list(String lng,String self  , 
	ContentsResponse methodResp
	) {

			methodResp.setCoder(
			new ContentsCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"/content_list.rom");
		}
			// dbmethodrender
	public static void change(String[] content_ids,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("content_ids",new ArrayCoder(new StringCoder()).encode(content_ids));


			methodResp.postNow(self+"");
		}
			// dbmethodrender
	public static void get(String lng,String self  , 
	ListsResponse methodResp
	) {

			methodResp.setCoder(
			new ListsCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void extinct(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/extinct.rom");
		}
			// dbmethodrender
	public static void addtolist(String lng,String uri,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("uri",new StringCoder().encode(uri));


			methodResp.postNow(self+"/addtolist.rom");
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
	public static void removefromlist(String lng,String uri,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("uri",new StringCoder().encode(uri));


			methodResp.postNow(self+"/removefromlist.rom");
		}
			// dbmethodrender
	public static void list(String lng,String search,String self  , 
	ContentsResponse methodResp
	) {

			methodResp.setCoder(
			new ContentsCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("search",new StringCoder().encode(search));


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
				final String schema="site";
				final String table="lists";
				methodResp.addParam("table",new JSONString("lists"));

					methodResp.addParam("uri",new StringCoder().encode(uri));
					methodResp.addParam("mask",new LongCoder().encode(mask));
					methodResp.addParam("parent",new StringCoder().encode(parent));
					methodResp.addParam("schema",new StringCoder().encode(schema));
					methodResp.addParam("table",new StringCoder().encode(table));


			methodResp.postNow(self+"/new.rom");
		}
			
}
