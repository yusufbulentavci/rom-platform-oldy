package com.bilgidoku.rom.web.maintain.dbop;

import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class CronMonthlyDbOp extends DbOp {
	private static final MC mc = new MC(CronMonthlyDbOp.class);

	
	private static final String query = "select tepeweb.cron_hourly(?,?)";



	public boolean doIt(int year, int month) throws KnownError {
		
		
		try (DbThree db3 = new DbThree(query);) {

			db3.setInt(year);
			db3.setInt(month);
			db3.executeQuery();
			if(!db3.next()){
				return false;
			}
			return db3.getBoolean();
		}
	}

}
