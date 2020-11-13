package com.bilgidoku.rom.gwt.araci.client.tepeweb;
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


public class InitialsDao extends DaoBase{
	// dbmethodrender
	public static void changeroles(String username,Integer roles,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("username",new StringCoder().encode(username));
					methodResp.addParam("roles",new IntegerCoder().encode(roles));


			methodResp.postNow(self+"/changeroles.rom");
		}
			// dbmethodrender
	public static void getcontact(String uri,String self  , 
	ContactsResponse methodResp
	) {

			methodResp.setCoder(
			new ContactsCoder()
			);



					methodResp.addParam("uri",new StringCoder().encode(uri));


			methodResp.getNow(self+"/getcontact.rom");
		}
			// dbmethodrender
	public static void adduser(String lng,String cc,String username,String cipher,String email,String first_name,String last_name,String mobile,String fid,String twitter,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("cc",new StringCoder().encode(cc));
					methodResp.addParam("username",new StringCoder().encode(username));
					methodResp.addParam("cipher",new StringCoder().encode(cipher));
					methodResp.addParam("email",new StringCoder().encode(email));
					methodResp.addParam("first_name",new StringCoder().encode(first_name));
					methodResp.addParam("last_name",new StringCoder().encode(last_name));
					methodResp.addParam("mobile",new StringCoder().encode(mobile));
					methodResp.addParam("fid",new StringCoder().encode(fid));
					methodResp.addParam("twitter",new StringCoder().encode(twitter));


			methodResp.postNow(self+"/adduser.rom");
		}
			// dbmethodrender
	public static void removeuser(String username,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("username",new StringCoder().encode(username));


			methodResp.postNow(self+"/removeuser.rom");
		}
			// dbmethodrender
	public static void getusers(String self  , 
	ArrayResponse<String> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<String>(new StringCoder())
			);

			methodResp.enableArray();



			methodResp.postNow(self+"/getusers.rom");
		}
			// dbmethodrender
	public static void changepass(String username,String credential,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("username",new StringCoder().encode(username));
					methodResp.addParam("credential",new StringCoder().encode(credential));


			methodResp.postNow(self+"/changepass.rom");
		}
			// dbmethodrender
	public static void getfbappid(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.getNow(self+"/getfbappid.rom");
		}
			
}
