package com.bilgidoku.rom.mail.dbop;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

//  rom.mails_delivered(>!{resourcegetparams}!<, a_contact text, p_mime json, p_dbfs text)
// returns text
public class MailDestroy extends DbOp {
	private static final String query="select * "
			+ "from rom.mails_destroy(?,?)";
	public String destroy(int hostId, String uri) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.setString(uri);
			db3.executeQuery();
			
			db3.checkedNext();
			
			return db3.getString();
		}
	}


	
}