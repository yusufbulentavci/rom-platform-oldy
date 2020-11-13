package com.bilgidoku.rom.web.info.dbop;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class OrgGetStartssl extends DbOp{


	private static final String query = "select startssl from rom.org where host_id=?";

	public String doit(Integer a_host) throws KnownError {
		try (DbThree db3 = new DbThree(query)) {

			db3.setInt(a_host);
			db3.executeQuery();
			hasNext(db3);

			String startssl=db3.getString();

			return startssl;
		}

	}
}