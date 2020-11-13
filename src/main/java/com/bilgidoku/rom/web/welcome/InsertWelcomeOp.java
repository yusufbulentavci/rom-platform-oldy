package com.bilgidoku.rom.web.welcome;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class InsertWelcomeOp extends DbOp {

	
//	tepeweb.welcome_new(p_email text, p_hostid int, p_ip text, p_country text)
	private static final String query = "select * "
			+ "from tepeweb.welcome_new(?,?,?,?)";

	public void doit(String p_email, Integer hostId, String p_ip, String p_country) throws KnownError {
		try (DbThree db3 = new DbThree(query)) {
			db3.setString(p_email);
			db3.setInt(hostId);
			db3.setString(p_ip);
			db3.setString(p_country);
			db3.executeQuery();
			if (!db3.next()) {
				throw new KnownError("Unable to insert welcome for:"+p_email+" hostId:"+hostId+",ip:"+p_ip+" country:"+p_country);
			}
		}
	}

}
