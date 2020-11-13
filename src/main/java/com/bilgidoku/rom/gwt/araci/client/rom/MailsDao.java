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


public class MailsDao extends DaoBase{
	// dbmethodrender
	public static void important(Boolean important,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("important",new BooleanCoder().encode(important));


			methodResp.postNow(self+"/important.rom");
		}
			// dbmethodrender
	public static void neww(Json mime,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("mime",new JsonCoder().encode(mime));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void answered(Boolean answered,String self  , 
	IntegerResponse methodResp
	) {

			methodResp.setCoder(
			new IntegerCoder()
			);



					methodResp.addParam("answered",new BooleanCoder().encode(answered));


			methodResp.postNow(self+"/answered.rom");
		}
			// dbmethodrender
	public static void change(Json mime,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("mime",new JsonCoder().encode(mime));


			methodResp.postNow(self+"");
		}
			// dbmethodrender
	public static void get(String self  , 
	MailsResponse methodResp
	) {

			methodResp.setCoder(
			new MailsCoder()
			);




			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void changemailbox(String to,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("to",new StringCoder().encode(to));


			methodResp.postNow(self+"/changemailbox.rom");
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
	public static void list(Integer offset,Integer limit,String self  , 
	MailsResponse methodResp
	) {

			methodResp.setCoder(
			new MailsCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("offset",new IntegerCoder().encode(offset));
					methodResp.addParam("limit",new IntegerCoder().encode(limit));


			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void changestate(Integer state,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("state",new IntegerCoder().encode(state));


			methodResp.postNow(self+"/changestate.rom");
		}
			// dbmethodrender
	public static void send(String mimedbfs,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("mimedbfs",new StringCoder().encode(mimedbfs));


			methodResp.postNow(self+"/send.rom");
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
				final String table="mails";
				methodResp.addParam("table",new JSONString("mails"));

					methodResp.addParam("uri",new StringCoder().encode(uri));
					methodResp.addParam("mask",new LongCoder().encode(mask));
					methodResp.addParam("parent",new StringCoder().encode(parent));
					methodResp.addParam("schema",new StringCoder().encode(schema));
					methodResp.addParam("table",new StringCoder().encode(table));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void seen(Boolean seen,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("seen",new BooleanCoder().encode(seen));


			methodResp.postNow(self+"/seen.rom");
		}
			
}
