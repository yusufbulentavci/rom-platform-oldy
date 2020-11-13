package com.bilgidoku.rom.web.richweb;

import java.sql.Date;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class NewMedia extends DbOp{

//	tepeweb.mediastore_new(p_pr int, p_pid text, p_tags text[], p_uses int, p_weight int, p_format text) 
//	returns int
	private static final String query="select * from tepeweb.medias_new(?,?,?,?,?,?,?)";

	public int doit(int hostId, String contact, 
			String feature, String named, String refid,
			Integer autorenewperiod, Long fromDate, Long toDate, Integer amount,
			String desc, String remotid) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			db3.setString(contact);
			
			db3.setString(feature);
			db3.setString(named);
			db3.setString(refid);
			
			db3.setInt(autorenewperiod);
			if(fromDate==null){
				db3.setNull(java.sql.Types.DATE);
			}else{
				db3.setDate(new Date(fromDate));
			}
			if(toDate==null){
				db3.setNull(java.sql.Types.DATE);
			}else{
				db3.setDate(new Date(toDate));
			}
			db3.setInt(amount);
			
			db3.setString(desc);
			db3.setString(remotid);
			
			db3.executeQuery();
			if(!db3.next()){
				throw new KnownError();
			}
			return db3.getInt();
		}
	}
	
}