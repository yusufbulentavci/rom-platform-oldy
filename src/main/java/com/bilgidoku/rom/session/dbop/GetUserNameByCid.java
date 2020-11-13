package com.bilgidoku.rom.session.dbop;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class GetUserNameByCid extends DbOp{
	private static final String query="select * "
			+ "from dict.appserver_usernamebycid(?,?)";
	

	public String doit(int hostId,String cid) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.setString(cid);
			db3.executeQuery();
			if(!db3.next()){
				return null;
			}
			return db3.getString();
		}
	}
	
}
