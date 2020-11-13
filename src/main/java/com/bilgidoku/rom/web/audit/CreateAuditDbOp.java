package com.bilgidoku.rom.web.audit;

import com.bilgidoku.rom.gwt.shared.AuditItem;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class CreateAuditDbOp extends DbOp{
	private static final MC mc=new MC(CreateAuditDbOp.class);
	

//	rom.audits_new(a_host integer,
//		p_cid text,p_amethod text,p_turi text,p_params text[],p_pvalues text[])
	private static final String query="select * "
			+ "from rom.audits_new(?,"
			+ "?,?,?,?,?)";
	

	public void doit(AuditItem a) throws KnownError{
		
		try(DbThree db3=new DbThree(query)){
			db3.setInt(a.hostId);
			db3.setString(a.cid);
			db3.setString(a.method);
			db3.setString(a.uri);
			db3.setStringArray(a.fieldNames);
			db3.setStringArray(a.fieldValues);
			db3.executeQuery();
			if(!db3.next()){
				throw new KnownError();
			}
			db3.getInt();
		}
	}
	
}