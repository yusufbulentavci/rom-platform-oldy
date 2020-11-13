package com.bilgidoku.rom.mail.dbop;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.mail.MailOnDb;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class MailGet extends DbOp {
	private static final String query="select mime::text,state,dbfs "
			+ "from rom.mails where host_id=? and uri=?";
	public MailOnDb get(int hostId, String uri) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.setString(uri);
			db3.executeQuery();
			if(!db3.next())
				return null;
			
			return new MailOnDb(uri,
					new JSONObject(db3.getString()),
					db3.getInt(),
					db3.getStringArray()
					);
		} catch (JSONException e) {
			throw err(e);
		}
	}


	
}