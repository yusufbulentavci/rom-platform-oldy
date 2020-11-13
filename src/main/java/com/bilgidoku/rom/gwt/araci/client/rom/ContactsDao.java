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


public class ContactsDao extends DaoBase{
	// dbmethodrender
	public static void neww(String lng,String cc,String email,String cipher,String firstname,String lastname,String fid,String twitter,String mobile,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("cc",new StringCoder().encode(cc));
					methodResp.addParam("email",new StringCoder().encode(email));
					methodResp.addParam("cipher",new StringCoder().encode(cipher));
					methodResp.addParam("firstname",new StringCoder().encode(firstname));
					methodResp.addParam("lastname",new StringCoder().encode(lastname));
					methodResp.addParam("fid",new StringCoder().encode(fid));
					methodResp.addParam("twitter",new StringCoder().encode(twitter));
					methodResp.addParam("mobile",new StringCoder().encode(mobile));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void relset(String email,Integer tone,String self  , 
	IntegerResponse methodResp
	) {

			methodResp.setCoder(
			new IntegerCoder()
			);



					methodResp.addParam("email",new StringCoder().encode(email));
					methodResp.addParam("tone",new IntegerCoder().encode(tone));


			methodResp.getNow(self+"/relset.rom");
		}
			// dbmethodrender
	public static void change(String lng,String cipher,String first_name,String last_name,String icon,String email,String fb_id,String twitter,String web,Boolean confirmed,String address,String state,String city,String country_code,String postal_code,String organization,String phone,String mobile,String fax,String[] tags,String[] gids,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("cipher",new StringCoder().encode(cipher));
					methodResp.addParam("first_name",new StringCoder().encode(first_name));
					methodResp.addParam("last_name",new StringCoder().encode(last_name));
					methodResp.addParam("icon",new StringCoder().encode(icon));
					methodResp.addParam("email",new StringCoder().encode(email));
					methodResp.addParam("fb_id",new StringCoder().encode(fb_id));
					methodResp.addParam("twitter",new StringCoder().encode(twitter));
					methodResp.addParam("web",new StringCoder().encode(web));
					methodResp.addParam("confirmed",new BooleanCoder().encode(confirmed));
					methodResp.addParam("address",new StringCoder().encode(address));
					methodResp.addParam("state",new StringCoder().encode(state));
					methodResp.addParam("city",new StringCoder().encode(city));
					methodResp.addParam("country_code",new StringCoder().encode(country_code));
					methodResp.addParam("postal_code",new StringCoder().encode(postal_code));
					methodResp.addParam("organization",new StringCoder().encode(organization));
					methodResp.addParam("phone",new StringCoder().encode(phone));
					methodResp.addParam("mobile",new StringCoder().encode(mobile));
					methodResp.addParam("fax",new StringCoder().encode(fax));
					methodResp.addParam("tags",new ArrayCoder(new StringCoder()).encode(tags));
					methodResp.addParam("gids",new ArrayCoder(new StringCoder()).encode(gids));


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
	public static void adminchangepwd(String cid,String cipher,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("cid",new StringCoder().encode(cid));
					methodResp.addParam("cipher",new StringCoder().encode(cipher));


			methodResp.postNow(self+"/adminchangepwd.rom");
		}
			// dbmethodrender
	public static void list(String search,String self  , 
	ContactsResponse methodResp
	) {

			methodResp.setCoder(
			new ContactsCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("search",new StringCoder().encode(search));


			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void getworks(String self  , 
	ArrayResponse<Json> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<Json>(new JsonCoder())
			);




			methodResp.getNow(self+"/getworks.rom");
		}
			// dbmethodrender
	public static void setworks(Json[] works,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("works",new ArrayCoder(new JsonCoder()).encode(works));


			methodResp.postNow(self+"/setworks.rom");
		}
			// dbmethodrender
	public static void changepwd(String cipher,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("cipher",new StringCoder().encode(cipher));


			methodResp.postNow(self+"/changepwd.rom");
		}
			// dbmethodrender
	public static void setnestingvalue(String key,String value,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("key",new StringCoder().encode(key));
					methodResp.addParam("value",new StringCoder().encode(value));


			methodResp.postNow(self+"/setnestingvalue.rom");
		}
			// dbmethodrender
	public static void getnestingvalue(String key,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("key",new StringCoder().encode(key));


			methodResp.getNow(self+"/getnestingvalue.rom");
		}
			// dbmethodrender
	public static void get(String self  , 
	ContactsResponse methodResp
	) {

			methodResp.setCoder(
			new ContactsCoder()
			);




			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void relget(String email,String self  , 
	IntegerResponse methodResp
	) {

			methodResp.setCoder(
			new IntegerCoder()
			);



					methodResp.addParam("email",new StringCoder().encode(email));


			methodResp.getNow(self+"/relget.rom");
		}
			// dbmethodrender
	public static void useremail(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.getNow(self+"/useremail.rom");
		}
			// dbmethodrender
	public static void username(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.getNow(self+"/username.rom");
		}
			
}
