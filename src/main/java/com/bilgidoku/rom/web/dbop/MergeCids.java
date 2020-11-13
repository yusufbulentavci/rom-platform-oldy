package com.bilgidoku.rom.web.dbop;

import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class MergeCids extends DbOp {
	private static final MC mc = new MC(MergeCids.class);

	private static final String query = "select * from tepeweb.mergecids(?,?,?)";



	public boolean doIt(int intraHostId, String cidToDel, String mainCid) throws KnownError {
		try (DbThree db3 = new DbThree(query);) {

			db3.setInt(intraHostId);
			db3.setString(cidToDel);
			db3.setString(mainCid);
			
			db3.checkedNext();
			
			return db3.getBoolean();
		}
	}

}
