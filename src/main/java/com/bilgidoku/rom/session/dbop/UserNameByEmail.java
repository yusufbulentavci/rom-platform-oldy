package com.bilgidoku.rom.session.dbop;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class UserNameByEmail extends DbOp {

	private static final String query = "select * from rom.appserver_usernamebyemail(?,?)";

	public String doit(Integer a_host, String p_email) throws KnownError {
		try (DbThree db3 = new DbThree(query)) {
			db3.setInt(a_host);
			db3.setString(p_email);
			db3.executeQuery();
			if (!db3.next()) {
				return null;
			}
			return db3.getString();
		}
	}

}
