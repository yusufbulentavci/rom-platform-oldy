package com.bilgidoku.rom.web.dbop;

import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class CidByEmailDbOp extends DbOp{
	private static final MC mc=new MC(CidByEmailDbOp.class);
	

//	rom.contacts_cidbyemail(
//			a_host int, p_email text
//		)
	private static final String query="select * "
			+ "from rom.contacts_cidbyemail(?, ?)";
	

	public String doit(int hostId, String email) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.setString(email);
			db3.executeQuery();
			if(!db3.next()){
				return null;
			}
			return db3.getString();
		}
	}
	
}
