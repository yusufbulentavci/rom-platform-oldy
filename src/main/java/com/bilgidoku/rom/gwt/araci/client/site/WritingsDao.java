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


public class WritingsDao extends DaoBase{
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
	public static void body(String lng,Json body,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("body",new JsonCoder().encode(body));


			methodResp.postNow(self+"/body.rom");
		}
			// dbmethodrender
	public static void setstock(String stock,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("stock",new StringCoder().encode(stock));


			methodResp.postNow(self+"/setstock.rom");
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
	public static void setmask(Long mask,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("mask",new LongCoder().encode(mask));


			methodResp.postNow(self+"/setmask.rom");
		}
			// dbmethodrender
	public static void get(String lng,String self  , 
	WritingsResponse methodResp
	) {

			methodResp.setCoder(
			new WritingsCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void nostock(Boolean del,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("del",new BooleanCoder().encode(del));


			methodResp.postNow(self+"/nostock.rom");
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
	public static void setcarray(String[] carray,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("carray",new ArrayCoder(new StringCoder()).encode(carray));


			methodResp.postNow(self+"/setcarray.rom");
		}
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
	public static void deletedialog(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/deletedialog.rom");
		}
			// dbmethodrender
	public static void copylangcontent(String lng,String fromlang,Boolean spot,Boolean body,Boolean header,Boolean footer,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("fromlang",new StringCoder().encode(fromlang));
					methodResp.addParam("spot",new BooleanCoder().encode(spot));
					methodResp.addParam("body",new BooleanCoder().encode(body));
					methodResp.addParam("header",new BooleanCoder().encode(header));
					methodResp.addParam("footer",new BooleanCoder().encode(footer));


			methodResp.postNow(self+"/copylangcontent.rom");
		}
			// dbmethodrender
	public static void change(String lng,Json spot,Json body,String menu1,String[] carray,String[] tags,String stock_uri,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("spot",new JsonCoder().encode(spot));
					methodResp.addParam("body",new JsonCoder().encode(body));
					methodResp.addParam("menu1",new StringCoder().encode(menu1));
					methodResp.addParam("carray",new ArrayCoder(new StringCoder()).encode(carray));
					methodResp.addParam("tags",new ArrayCoder(new StringCoder()).encode(tags));
					methodResp.addParam("stock_uri",new StringCoder().encode(stock_uri));


			methodResp.postNow(self+"");
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
	public static void getbydialog(String lng,String dialogid,String self  , 
	WritingsResponse methodResp
	) {

			methodResp.setCoder(
			new WritingsCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("dialogid",new StringCoder().encode(dialogid));


			methodResp.getNow(self+"/getbydialog.rom");
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
	public static void menu(String menu1,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("menu1",new StringCoder().encode(menu1));


			methodResp.postNow(self+"/menu.rom");
		}
			// dbmethodrender
	public static void getbystockuri(String stock,String lng,String self  , 
	WritingsResponse methodResp
	) {

			methodResp.setCoder(
			new WritingsCoder()
			);



					methodResp.addParam("stock",new StringCoder().encode(stock));
					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"/getbystockuri.rom");
		}
			// dbmethodrender
	public static void createdialog(Boolean allow_attach,Boolean approval,Boolean deletable,Boolean updatable,Boolean likeable,Boolean dislikable,Boolean sharable,Boolean closed,String[] contacts,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("allow_attach",new BooleanCoder().encode(allow_attach));
					methodResp.addParam("approval",new BooleanCoder().encode(approval));
					methodResp.addParam("deletable",new BooleanCoder().encode(deletable));
					methodResp.addParam("updatable",new BooleanCoder().encode(updatable));
					methodResp.addParam("likeable",new BooleanCoder().encode(likeable));
					methodResp.addParam("dislikable",new BooleanCoder().encode(dislikable));
					methodResp.addParam("sharable",new BooleanCoder().encode(sharable));
					methodResp.addParam("closed",new BooleanCoder().encode(closed));
					methodResp.addParam("contacts",new ArrayCoder(new StringCoder()).encode(contacts));


			methodResp.postNow(self+"/createdialog.rom");
		}
			// dbmethodrender
	public static void breed(String uri,String lng,Long mask,String uri_prefix,String defaulthtml,String title,String parent  , 
	ContainersResponse methodResp
	) {

			methodResp.setCoder(
			new ContainersCoder()
			);


				final String self="/_/c";
				final String schema="site";
				final String table="writings";
				methodResp.addParam("table",new JSONString("writings"));

					methodResp.addParam("uri",new StringCoder().encode(uri));
					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("mask",new LongCoder().encode(mask));
					methodResp.addParam("uri_prefix",new StringCoder().encode(uri_prefix));
					methodResp.addParam("defaulthtml",new StringCoder().encode(defaulthtml));
					methodResp.addParam("title",new StringCoder().encode(title));
					methodResp.addParam("parent",new StringCoder().encode(parent));
					methodResp.addParam("schema",new StringCoder().encode(schema));
					methodResp.addParam("table",new StringCoder().encode(table));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void tags(String[] tags,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("tags",new ArrayCoder(new StringCoder()).encode(tags));


			methodResp.postNow(self+"/tags.rom");
		}
			// dbmethodrender
	public static void textsearch(String lng,String search,String self  , 
	ContentsResponse methodResp
	) {

			methodResp.setCoder(
			new ContentsCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("search",new StringCoder().encode(search));


			methodResp.getNow(self+"/textsearch.rom");
		}
			// dbmethodrender
	public static void spot(String lng,Json spot,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("spot",new JsonCoder().encode(spot));


			methodResp.postNow(self+"/spot.rom");
		}
			// dbmethodrender
	public static void containerreuri(String uri,String uriprefix,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("uri",new StringCoder().encode(uri));
					methodResp.addParam("uriprefix",new StringCoder().encode(uriprefix));


			methodResp.getNow(self+"/containerreuri.rom");
		}
			// dbmethodrender
	public static void breadcrumbs(String lng,String self  , 
	ContentsResponse methodResp
	) {

			methodResp.setCoder(
			new ContentsCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"/breadcrumbs.rom");
		}
			// dbmethodrender
	public static void setmaskrecursive(Long mask,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("mask",new LongCoder().encode(mask));


			methodResp.postNow(self+"/setmaskrecursive.rom");
		}
			
}
