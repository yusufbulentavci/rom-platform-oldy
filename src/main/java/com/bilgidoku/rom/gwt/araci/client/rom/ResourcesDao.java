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


public class ResourcesDao extends DaoBase{
	// dbmethodrender
	public static void removefile(String fn,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("fn",new StringCoder().encode(fn));


			methodResp.postNow(self+"/removefile.rom");
		}
			// dbmethodrender
	public static void measure_del(String id,String self  , 
	IntegerResponse methodResp
	) {

			methodResp.setCoder(
			new IntegerCoder()
			);



					methodResp.addParam("id",new StringCoder().encode(id));


			methodResp.postNow(self+"/measure_del.rom");
		}
			// dbmethodrender
	public static void setrelatedcids(String relatedcids,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("relatedcids",new StringCoder().encode(relatedcids));


			methodResp.postNow(self+"/setrelatedcids.rom");
		}
			// dbmethodrender
	public static void setcontainer(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/setcontainer.rom");
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
	public static void setownercid(String ownercid,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("ownercid",new StringCoder().encode(ownercid));


			methodResp.postNow(self+"/setownercid.rom");
		}
			// dbmethodrender
	public static void setrtag(String[] rtag,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("rtag",new ArrayCoder(new StringCoder()).encode(rtag));


			methodResp.postNow(self+"/setrtag.rom");
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
	public static void leave(String contact,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("contact",new StringCoder().encode(contact));


			methodResp.postNow(self+"/leave.rom");
		}
			// dbmethodrender
	public static void join(String contact,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("contact",new StringCoder().encode(contact));


			methodResp.postNow(self+"/join.rom");
		}
			// dbmethodrender
	public static void effectivetags(String self  , 
	ArrayResponse<String> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<String>(new StringCoder())
			);




			methodResp.getNow(self+"/effectivetags.rom");
		}
			// dbmethodrender
	public static void setgid(String gid,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("gid",new StringCoder().encode(gid));


			methodResp.postNow(self+"/setgid.rom");
		}
			// dbmethodrender
	public static void setweight(Integer weight,String self  , 
	IntegerResponse methodResp
	) {

			methodResp.setCoder(
			new IntegerCoder()
			);



					methodResp.addParam("weight",new IntegerCoder().encode(weight));


			methodResp.postNow(self+"/setweight.rom");
		}
			// dbmethodrender
	public static void getaccess(String self  , 
	JsonResponse methodResp
	) {

			methodResp.setCoder(
			new JsonCoder()
			);




			methodResp.getNow(self+"/getaccess.rom");
		}
			// dbmethodrender
	public static void downloadfile(String download_uri,String fn,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("download_uri",new StringCoder().encode(download_uri));
					methodResp.addParam("fn",new StringCoder().encode(fn));


			methodResp.postNow(self+"/downloadfile.rom");
		}
			// dbmethodrender
	public static void getrtags(String self  , 
	ArrayResponse<String> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<String>(new StringCoder())
			);




			methodResp.getNow(self+"/getrtags.rom");
		}
			// dbmethodrender
	public static void setdelegated(String delegated,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("delegated",new StringCoder().encode(delegated));


			methodResp.postNow(self+"/setdelegated.rom");
		}
			// dbmethodrender
	public static void measure_add(String date,String code,Integer val,String self  , 
	IntegerResponse methodResp
	) {

			methodResp.setCoder(
			new IntegerCoder()
			);



					methodResp.addParam("date",new StringCoder().encode(date));
					methodResp.addParam("code",new StringCoder().encode(code));
					methodResp.addParam("val",new IntegerCoder().encode(val));


			methodResp.postNow(self+"/measure_add.rom");
		}
			// dbmethodrender
	public static void sethtmlfile(String htmlfile,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("htmlfile",new StringCoder().encode(htmlfile));


			methodResp.postNow(self+"/sethtmlfile.rom");
		}
			// dbmethodrender
	public static void reuri(String uri,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("uri",new StringCoder().encode(uri));


			methodResp.postNow(self+"/reuri.rom");
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
	public static void parentaltags(String self  , 
	ArrayResponse<String> methodResp
	) {

			methodResp.setCoder(
			new ArrayCoder<String>(new StringCoder())
			);




			methodResp.getNow(self+"/parentaltags.rom");
		}
			// dbmethodrender
	public static void exists(String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);




			methodResp.getNow(self+"/exists.rom");
		}
			// dbmethodrender
	public static void measure_list(String code,String self  , 
	MeasureResponse methodResp
	) {

			methodResp.setCoder(
			new MeasureCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("code",new StringCoder().encode(code));


			methodResp.getNow(self+"/measure_list.rom");
		}
			// dbmethodrender
	public static void changecontainer(String to,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("to",new StringCoder().encode(to));


			methodResp.postNow(self+"/changecontainer.rom");
		}
			
}
