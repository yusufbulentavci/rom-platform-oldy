package com.bilgidoku.rom.web.tokens;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class TokenGet extends DbOp {
	private static final String query="select rid,world,cmd,dataclient,dataserver,expires "
			+ "from rom.tokens where host_id=? and uri=?";
	public Token get(int hostId, String uri) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.setString(uri);
			db3.executeQuery();
			if(!db3.next())
				return null;
			return new Token(db3.getString(), db3.getString(),db3.getString(),db3.getString(),db3.getString(),db3.getTimestamp());
		} catch (JSONException e) {
			throw err(e);
		}
	}


	
}
