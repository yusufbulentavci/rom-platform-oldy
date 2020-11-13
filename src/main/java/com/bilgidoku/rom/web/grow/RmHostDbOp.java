package com.bilgidoku.rom.web.grow;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class RmHostDbOp extends DbOp{
	private static final MC mc=new MC(RmHostDbOp.class);
	

	private static final String query="select * "
			+ "from tepeweb.rmhost(?)";
	
	
	private static final Astate _create=mc.c("create.f.norecord");

	public int rm(String hostName) throws KnownError{
		_create.more();
		
		
		try(DbThree db3=new DbThree(query)){
			db3.setString(hostName);
			db3.executeQuery();
			if(!db3.next()){
				_create.fail(hostName);
				throw new KnownError();
			}
			return db3.getInt();
		}
	}
	
}
