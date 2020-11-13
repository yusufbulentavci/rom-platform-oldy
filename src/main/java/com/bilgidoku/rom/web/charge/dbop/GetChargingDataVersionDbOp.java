package com.bilgidoku.rom.web.charge.dbop;


import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class GetChargingDataVersionDbOp extends DbOp{

	private static final String query="select chargingdataversion "
			+ "from dict.envo";

	public int doit() throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			
			db3.executeQuery();
			hasNext(db3);

			return db3.getInt();
		}
	}

	
}