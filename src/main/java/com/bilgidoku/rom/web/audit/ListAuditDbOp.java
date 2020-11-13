package com.bilgidoku.rom.web.audit;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.shared.AuditItem;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class ListAuditDbOp extends DbOp{
	private static final MC mc=new MC(ListAuditDbOp.class);
	

//	rom.audits_list(a_host integer, p_fromtime timestamp, p_totime timestamp, p_cid text) returns setof rom.audits
	private static final String query="select * "
			+ "from rom.audits_list(?,?,?,?)";
	
/**
 * 
 *  host_id integer,
    aid serial,
    creation_date timestamp without time zone default now(),
    cid text,
    amethod text,
    turi text,
    params text[],
    pvalues text[],
 */
	
	public List<AuditItem> doit(int hostId, Long p_fromtime , Long p_totime, String p_cid ) throws KnownError{
		List<AuditItem> ret=new ArrayList<AuditItem>();
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			
			if(p_fromtime==null)
				db3.setNull(java.sql.Types.TIMESTAMP);
			else
				db3.setTimestamp(new Timestamp(p_fromtime));
			
			if(p_totime==null)
				db3.setNull(java.sql.Types.TIMESTAMP);
			else
				db3.setTimestamp(new Timestamp(p_totime));
			
			db3.setString(p_cid);
			
			db3.executeQuery();
			while(db3.next()){
				ret.add(new AuditItem(db3.getInt(), db3.getInt(), db3.getTimestamp().getTime(), db3.getString(), db3.getString(), db3.getString(), db3.getStringArray(), db3.getStringArray()));
			}
			return ret;
		}
	}
	
}