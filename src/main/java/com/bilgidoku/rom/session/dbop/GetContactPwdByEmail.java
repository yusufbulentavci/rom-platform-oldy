package com.bilgidoku.rom.session.dbop;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class GetContactPwdByEmail extends DbOp {

	private static final String query = "select * from rom.appserver_pwdofemail(?,?)";

	public String doit(int hostId, String email) throws KnownError {
		try (DbThree db3 = new DbThree(query)) {
			db3.setInt(hostId);
			db3.setString(email);
			db3.executeQuery();
			db3.checkedNext();
			return db3.getString();
		}
	}

}
