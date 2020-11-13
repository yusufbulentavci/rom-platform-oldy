package com.bilgidoku.rom.session.dbop;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class ChangePassDbOp extends DbOp{

//	tepeweb.initials_changepass(a_host integer, p_username text, p_credential text
//	returns boolean
	private static final String query="select * "
			+ "from tepeweb.initials_changepass(?, ?, ?)";
	

	public void doit(int hostId, String user, String pass) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.setString(user);
			db3.setString(pass);
			db3.executeQuery();
			if(!db3.next()){
				throw new KnownError();
			}
			db3.getString();
		}
	}
	
}
