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


public class IssuesDao extends DaoBase{
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
	public static void set_ozne(Long[] val,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("val",new ArrayCoder(new LongCoder()).encode(val));


			methodResp.postNow(self+"/set_ozne.rom");
		}
			// dbmethodrender
	public static void resolve(String html_file,String delegated,String ownercid,String gid,String[] relatedcids,Long mask,String lng,String resolvedesc,Integer resolvecode,String self  , 
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
					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("resolvedesc",new StringCoder().encode(resolvedesc));
					methodResp.addParam("resolvecode",new IntegerCoder().encode(resolvecode));


			methodResp.postNow(self+"/resolve.rom");
		}
			// dbmethodrender
	public static void start(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/start.rom");
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
	public static void setcls(String cls,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("cls",new StringCoder().encode(cls));


			methodResp.postNow(self+"/setcls.rom");
		}
			// dbmethodrender
	public static void list(Boolean notclosed,Boolean notresolved,String self  , 
	IssuesResponse methodResp
	) {

			methodResp.setCoder(
			new IssuesCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("notclosed",new BooleanCoder().encode(notclosed));
					methodResp.addParam("notresolved",new BooleanCoder().encode(notresolved));


			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void changetitle(String title,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("title",new StringCoder().encode(title));


			methodResp.postNow(self+"/changetitle.rom");
		}
			// dbmethodrender
	public static void listmine(Boolean notclosed,Boolean notresolved,String since,String totime,String self  , 
	IssuesResponse methodResp
	) {

			methodResp.setCoder(
			new IssuesCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("notclosed",new BooleanCoder().encode(notclosed));
					methodResp.addParam("notresolved",new BooleanCoder().encode(notresolved));
					methodResp.addParam("since",new StringCoder().encode(since));
					methodResp.addParam("totime",new StringCoder().encode(totime));


			methodResp.getNow(self+"/listmine.rom");
		}
			// dbmethodrender
	public static void set_oznetags(String[] val,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("val",new ArrayCoder(new StringCoder()).encode(val));


			methodResp.postNow(self+"/set_oznetags.rom");
		}
			// dbmethodrender
	public static void changetags(String[] tags,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("tags",new ArrayCoder(new StringCoder()).encode(tags));


			methodResp.postNow(self+"/changetags.rom");
		}
			// dbmethodrender
	public static void stop(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/stop.rom");
		}
			// dbmethodrender
	public static void changedesc(String description,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("description",new StringCoder().encode(description));


			methodResp.postNow(self+"/changedesc.rom");
		}
			// dbmethodrender
	public static void reopen(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/reopen.rom");
		}
			// dbmethodrender
	public static void set_nesnetags(String[] val,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("val",new ArrayCoder(new StringCoder()).encode(val));


			methodResp.postNow(self+"/set_nesnetags.rom");
		}
			// dbmethodrender
	public static void get(String self  , 
	IssuesResponse methodResp
	) {

			methodResp.setCoder(
			new IssuesCoder()
			);




			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void set_nesne(Long[] val,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("val",new ArrayCoder(new LongCoder()).encode(val));


			methodResp.postNow(self+"/set_nesne.rom");
		}
			// dbmethodrender
	public static void setduedate(String ts,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("ts",new StringCoder().encode(ts));


			methodResp.postNow(self+"/setduedate.rom");
		}
			// dbmethodrender
	public static void setduestart(String ts,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("ts",new StringCoder().encode(ts));


			methodResp.postNow(self+"/setduestart.rom");
		}
			// dbmethodrender
	public static void close(String html_file,String delegated,String ownercid,String gid,String[] relatedcids,Long mask,String self  , 
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


			methodResp.postNow(self+"/close.rom");
		}
			// dbmethodrender
	public static void assignto(String contact,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("contact",new StringCoder().encode(contact));


			methodResp.postNow(self+"/assignto.rom");
		}
			// dbmethodrender
	public static void setcreator(String contact,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("contact",new StringCoder().encode(contact));


			methodResp.postNow(self+"/setcreator.rom");
		}
			
}
