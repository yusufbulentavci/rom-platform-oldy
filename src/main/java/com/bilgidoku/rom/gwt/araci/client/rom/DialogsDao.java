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


public class DialogsDao extends DaoBase{
	// dbmethodrender
	public static void comments(String self  , 
	CommentsResponse methodResp
	) {

			methodResp.setCoder(
			new CommentsCoder()
			);

			methodResp.enableArray();



			methodResp.getNow(self+"/comments.rom");
		}
			// dbmethodrender
	public static void leave(String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);




			methodResp.postNow(self+"/leave.rom");
		}
			// dbmethodrender
	public static void change(Boolean allow_attach,Boolean approval,Boolean deletable,Boolean updatable,Boolean likeable,Boolean dislikable,Boolean sharable,Boolean closed,String[] contacts,String self  , 
	DialogsResponse methodResp
	) {

			methodResp.setCoder(
			new DialogsCoder()
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


			methodResp.postNow(self+"");
		}
			// dbmethodrender
	public static void get(String self  , 
	DialogsResponse methodResp
	) {

			methodResp.setCoder(
			new DialogsCoder()
			);




			methodResp.getNow(self+"");
		}
			// dbmethodrender
	public static void setcafe(Boolean cafe,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("cafe",new BooleanCoder().encode(cafe));


			methodResp.postNow(self+"/setcafe.rom");
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
	public static void comment(String lng,String comment,Json cmd,Json mime,String bymail,Boolean onpage,String self  , 
	StringResponse methodResp
	) {

			methodResp.setCoder(
			new StringCoder()
			);



					methodResp.addParam("lng",new StringCoder().encode(lng));
					methodResp.addParam("comment",new StringCoder().encode(comment));
					methodResp.addParam("cmd",new JsonCoder().encode(cmd));
					methodResp.addParam("mime",new JsonCoder().encode(mime));
					methodResp.addParam("bymail",new StringCoder().encode(bymail));
					methodResp.addParam("onpage",new BooleanCoder().encode(onpage));


			methodResp.postNow(self+"/comment.rom");
		}
			// dbmethodrender
	public static void join(String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);




			methodResp.postNow(self+"/join.rom");
		}
			// dbmethodrender
	public static void cmd(String cmd,String str,Json more,String self  , 
	BooleanResponse methodResp
	) {

			methodResp.setCoder(
			new BooleanCoder()
			);



					methodResp.addParam("cmd",new StringCoder().encode(cmd));
					methodResp.addParam("str",new StringCoder().encode(str));
					methodResp.addParam("more",new JsonCoder().encode(more));


			methodResp.postNow(self+"/cmd.rom");
		}
			
}
