package com.bilgidoku.rom.session.dbop;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class GetContactEmail extends DbOp {

	private static final String query = "select * from rom.appserver_emailofcid(?,?)";

	public String doit(int hostId, String cid) throws KnownError {
		try (DbThree db3 = new DbThree(query)) {
			db3.setInt(hostId);
			db3.setString(cid);
			db3.executeQuery();
			db3.checkedNext();
			return db3.getString();
		}
	}

}
