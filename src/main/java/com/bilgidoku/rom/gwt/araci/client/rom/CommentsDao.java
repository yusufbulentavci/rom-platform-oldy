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


public class CommentsDao extends DaoBase{
	// dbmethodrender
	public static void approve(String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);




			methodResp.postNow(self+"/approve.rom");
		}
			// dbmethodrender
	public static void change(String lng,String dialog_id,String contact,String comment,Json cmd,Json mime,String bymail,Boolean approved,String refer_comment,String[] likes,String[] dislikes,Boolean onpage,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("dialog_id",new StringCoder().encode(dialog_id));
					methodResp.addParam("contact",new StringCoder().encode(contact));
					methodResp.addParam("comment",new StringCoder().encode(comment));
					methodResp.addParam("cmd",new JsonCoder().encode(cmd));
					methodResp.addParam("mime",new JsonCoder().encode(mime));
					methodResp.addParam("bymail",new StringCoder().encode(bymail));
					methodResp.addParam("approved",new BooleanCoder().encode(approved));
					methodResp.addParam("refer_comment",new StringCoder().encode(refer_comment));
					methodResp.addParam("likes",new ArrayCoder(new StringCoder()).encode(likes));
					methodResp.addParam("dislikes",new ArrayCoder(new StringCoder()).encode(dislikes));
					methodResp.addParam("onpage",new BooleanCoder().encode(onpage));


			methodResp.postNow(self+"");
		}
			// dbmethodrender
	public static void get(String self  , 
	CommentsResponse methodResp
	) {

			methodResp.setCoder(
			new CommentsCoder()
			);




			methodResp.getNow(self+"");
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
	public static void listwaitingapproval(String dialog_id,String self  , 
	CommentsResponse methodResp
	) {

			methodResp.setCoder(
			new CommentsCoder()
			);

			methodResp.enableArray();


					methodResp.addParam("dialog_id",new StringCoder().encode(dialog_id));


			methodResp.postNow(self+"/listwaitingapproval.rom");
		}
			
}
