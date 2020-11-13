package com.bilgidoku.rom.mail.dbop;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class ContactEmailRel extends DbOp {
	private static final String query="select * from rom.contacts_relget(?,?,?)";
	public Integer get(int hostId, String cid, String email) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.setString(cid);
			db3.setString(email);
			db3.executeQuery();
			if(!db3.next())
				return null;
			
			return db3.getInt();
		} 
	}


	
}