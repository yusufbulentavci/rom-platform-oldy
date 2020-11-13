package com.bilgidoku.rom.web.dbop.hosts;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class NestedOfHostDbOp extends DbOp{
	private static final MC mc=new MC(NestedOfHostDbOp.class);
	

//	rom.contacts_cidbyemail(
//			a_host int, p_email text
//		)
	private static final String query="select nested "
			+ "from dict.hosts where host_id=?";
	

	public JSONObject doit(int hostId) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.executeQuery();
			if(!db3.next()){
				return null;
			}
			try {
				return new JSONObject(db3.getString());
			} catch (JSONException e) {
				Sistem.printStackTrace(e);
				return null;
			}
		}
	}
	
}
