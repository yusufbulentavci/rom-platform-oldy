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


public class ExamsDao extends DaoBase{
	// dbmethodrender
	public static void neww(String lng,String title,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("title",new StringCoder().encode(title));


			methodResp.postNow(self+"/new.rom");
		}
			// dbmethodrender
	public static void start(String lng,String self  , 
	ExamsResponse methodResp
	) {

			methodResp.setCoder(
			new ExamsCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"/start.rom");
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
	public static void setelimination(Integer elim,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("elim",new IntegerCoder().encode(elim));


			methodResp.postNow(self+"/setelimination.rom");
		}
			// dbmethodrender
	public static void setduration(Integer duration,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("duration",new IntegerCoder().encode(duration));


			methodResp.postNow(self+"/setduration.rom");
		}
			// dbmethodrender
	public static void finished(Integer[] responses,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("responses",new ArrayCoder(new IntegerCoder()).encode(responses));


			methodResp.postNow(self+"/finished.rom");
		}
			// dbmethodrender
	public static void list(String lng,String self  , 
	ContentsResponse methodResp
	) {

			methodResp.setCoder(
			new ContentsCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void setpage(String page,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("page",new StringCoder().encode(page));


			methodResp.postNow(self+"/setpage.rom");
		}
			// dbmethodrender
	public static void setrequirements(String[] requirements,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("requirements",new ArrayCoder(new StringCoder()).encode(requirements));


			methodResp.postNow(self+"/setrequirements.rom");
		}
			// dbmethodrender
	public static void listresults(String contact,String self  , 
	JsonResponse methodResp
	) {

			methodResp.setCoder(
			new JsonCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("contact",new StringCoder().encode(contact));


			methodResp.getNow(self+"/listresults.rom");
		}
			// dbmethodrender
	public static void getexambypage(String page,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("page",new StringCoder().encode(page));


			methodResp.postNow(self+"/getexambypage.rom");
		}
			// dbmethodrender
	public static void myresults(String self  , 
	JsonResponse methodResp
	) {

			methodResp.setCoder(
			new JsonCoder()
			);

			methodResp.enableArray();



			methodResp.getNow(self+"/myresults.rom");
		}
			// dbmethodrender
	public static void get(String lng,String self  , 
	ExamsResponse methodResp
	) {

			methodResp.setCoder(
			new ExamsCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void open(String lng,String contact,String self  , 
	ExamsResponse methodResp
	) {

			methodResp.setCoder(
			new ExamsCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("contact",new StringCoder().encode(contact));


			methodResp.getNow(self+"/open.rom");
		}
			// dbmethodrender
	public static void setquestions(String[] questions,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("questions",new ArrayCoder(new StringCoder()).encode(questions));


			methodResp.postNow(self+"/setquestions.rom");
		}
			
}
