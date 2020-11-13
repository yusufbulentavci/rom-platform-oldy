package com.bilgidoku.rom.session.dbop;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class GetDeskDbOp extends DbOp{
	private static final String query="select user_name "
			+ "from dict.users_desk(?)";
	

	public String doit(int hostId) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.executeQuery();
			if(!db3.next()){
				return null;
			}
			return db3.getString();
		}
	}
	
}
