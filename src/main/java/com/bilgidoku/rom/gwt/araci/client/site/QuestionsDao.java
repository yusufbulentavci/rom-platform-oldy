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


public class QuestionsDao extends DaoBase{
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
	public static void get(String lng,String self  , 
	QuestionsResponse methodResp
	) {

			methodResp.setCoder(
			new QuestionsCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));


			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void change(String lng,String optiona,String optionb,String optionc,String optiond,String optione,Integer optioncount,Integer correct,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("optiona",new StringCoder().encode(optiona));
					methodResp.addParam("optionb",new StringCoder().encode(optionb));
					methodResp.addParam("optionc",new StringCoder().encode(optionc));
					methodResp.addParam("optiond",new StringCoder().encode(optiond));
					methodResp.addParam("optione",new StringCoder().encode(optione));
					methodResp.addParam("optioncount",new IntegerCoder().encode(optioncount));
					methodResp.addParam("correct",new IntegerCoder().encode(correct));


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
	public static void list(String lng,String[] tags,String self  , 
	ContentsResponse methodResp
	) {

			methodResp.setCoder(
			new ContentsCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("tags",new ArrayCoder(new StringCoder()).encode(tags));


			methodResp.getNow(self+"");
		}
			
}
