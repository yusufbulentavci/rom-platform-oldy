package com.bilgidoku.rom.kurum;

import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class GetFavIconDbOp extends DbOp {
	private static final MC mc = new MC(GetFavIconDbOp.class);

	private static final String query = "select browser_icon from site.info where host_id=?";

	public String doit(Integer hostid) throws KnownError {

		try (DbThree db3 = new DbThree(query)) {
			db3.setInt(hostid);
			db3.executeQuery();
			db3.checkedNext();
			return db3.getString();
		}
	}

}
