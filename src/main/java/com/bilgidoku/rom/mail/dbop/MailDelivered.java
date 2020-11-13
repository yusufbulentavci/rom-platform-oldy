package com.bilgidoku.rom.mail.dbop;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Genel;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

//  rom.mails_delivered(>!{resourcegetparams}!<, a_contact text, p_mime json, p_dbfs text)
// returns text
public class MailDelivered extends DbOp {
	private static final String query="select * "
			+ "from rom.mails_delivered(?,?,?,?::json,?,?,?)";
	public String deliver(int hostId, String uri, String contact, JSONObject mime, String[] dbfs, String remoteHost, String remoteAddr) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.setString(uri);
			db3.setString(contact);
			db3.setString(mime.toStringPassKey("bcc"));
			db3.setStringArray(dbfs);
			db3.setString(remoteHost);
			db3.setString(remoteAddr);
			if(!db3.executeQuery()){
				throw new KnownError("Can not deliver to hostId:"+hostId+" uri:"+Genel.notNull(uri)+ "contact:" +Genel.notNull(contact)+ Genel.notNull(mime));
			}
			
			db3.checkedNext();
			
			return db3.getString();
		}
	}


	
}