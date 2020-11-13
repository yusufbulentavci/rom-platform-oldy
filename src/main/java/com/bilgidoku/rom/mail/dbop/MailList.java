package com.bilgidoku.rom.mail.dbop;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.mail.MailOnDb;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class MailList extends DbOp {
	private static final String query="select uri,mime::text,state,dbfs "
			+ "from rom.mails_list(?,?)";
	public List<MailOnDb> list(int hostId, String uri) throws KnownError{
		List<MailOnDb> lst=new ArrayList<MailOnDb>();
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.setString(uri);
			db3.executeQuery();
			while(db3.next()){
				lst.add(new MailOnDb(db3.getString(),
						new JSONObject(db3.getString()),
						db3.getInt(),
						db3.getStringArray()
						));
			}
		} catch (JSONException e) {
			throw err(e);
		}
		return lst;
	}


	
}