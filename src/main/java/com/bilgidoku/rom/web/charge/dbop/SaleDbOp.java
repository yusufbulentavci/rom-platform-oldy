package com.bilgidoku.rom.web.charge.dbop;


import java.sql.Date;

import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class SaleDbOp extends DbOp{

//	 tepeweb.hostfeature_sale(a_host int, a_contact text, 
//				p_feature text, p_named text, p_refid text,
//				p_autorenewperiod int, p_fromdate time, p_todate date, p_amount int,
//				p_desc text, p_ftid text)
	private static final String query="select * "
			+ "from "
			+  "tepeweb.hostfeature_sale(?, ?, "
			+ 		"?, ?, ?,"
			+		"?, ?, ?, ?,"
			+		"?, ?)";

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