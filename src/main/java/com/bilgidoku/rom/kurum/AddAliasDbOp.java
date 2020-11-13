package com.bilgidoku.rom.kurum;

import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class AddAliasDbOp extends DbOp{
	private static final MC mc=new MC(AddAliasDbOp.class);
	

//update dict.hosts set domainalias=domainalias||'sweet.com'::text where host_id=1
	private static final String query="update dict.hosts "
			+ "set domainalias=domainalias||?::text "
			+ "where host_id=?";
	

	public void doit(int hostId, String alias) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setString(alias);
			db3.setInt(hostId);
			db3.execute();
		}
	}
	
}
