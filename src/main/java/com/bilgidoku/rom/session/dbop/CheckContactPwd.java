package com.bilgidoku.rom.session.dbop;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class CheckContactPwd extends DbOp {

	private static final String query = "select * from site.appserver_contact_chkpwd(?, ?, ?)";

	public String[] doit(int hostId, String email, String password) throws KnownError {
		try (DbThree db3 = new DbThree(query)) {
			db3.setInt(hostId);
			db3.setString(email);
			db3.setString(password);
			db3.executeQuery();
			if (!db3.next()) {
				throw new RuntimeException("Unexpected empty response");
			}
			return db3.getStringArray();
		}
	}

}
