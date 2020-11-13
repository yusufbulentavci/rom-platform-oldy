package com.bilgidoku.rom.session.dbop;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class GetCidByFbId extends DbOp{
	private static final String query="select uri,confirmed "
			+ "from rom.appserver_getcidbyfbid(?,?)";
	

	/**
	 * 
	 * 
	 * 
	 * @param hostId
	 * @param fbid
	 * @return 
	 * @throws KnownError
	 */
	public ConfirmedCid doit(int hostId,String fbid) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.setString(fbid);
			db3.executeQuery();
			if(!db3.next()){
				return null;
			}
			
			return new ConfirmedCid(db3.getString(),db3.getBoolean());
		}
	}
	
}
