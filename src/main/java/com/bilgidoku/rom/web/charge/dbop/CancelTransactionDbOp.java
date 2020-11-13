package com.bilgidoku.rom.web.charge.dbop;


import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class CancelTransactionDbOp extends DbOp{

//	tepeweb.hostfeature_canceltransaction(a_host int, p_feature text, p_named text, 
//					p_tid int, p_refid text, p_why text) 
//	returns boolean
	private static final String query="select * "
			+ "from "
			+  "tepeweb.hostfeature_canceltransaction(?, ?, ?, "
			+ "?, ?, ?)";

	public boolean doit(int hostId, String feature, String named, 
			Integer p_tid, String refid, String why) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.setString(feature);
			db3.setString(named);

			db3.setInt(p_tid);
			db3.setString(refid);
			db3.setString(why);
			
			db3.executeQuery();
			if(!db3.next()){
				throw new KnownError();
			}
			return db3.getBoolean();
		}
	}
	
}