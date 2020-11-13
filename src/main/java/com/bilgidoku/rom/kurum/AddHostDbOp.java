package com.bilgidoku.rom.kurum;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class AddHostDbOp extends DbOp{
	private static final MC mc=new MC(AddHostDbOp.class);
	

//update dict.hosts set domainalias=domainalias||'sweet.com'::text where host_id=1
	private static final String query="insert into dict.hosts "
			+ " (host_name,mainlang,domainalias,contact) values"
			+ "(?,?,?,?::json) returning host_id";
	

	public Integer doit(String hostName, String mainLang, String alias, JSONObject con) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setString(hostName);
			db3.setString(mainLang);
			db3.setStringArray(new String[]{alias});
			db3.setString(con.toString());
			db3.executeQuery();
			hasNext(db3);
			return db3.getInt();
		}
	}
	
}
