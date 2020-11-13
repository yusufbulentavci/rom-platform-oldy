package com.bilgidoku.rom.web.account.dbop;


import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.shared.HostFeature;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class ListHostFeauteDbOp extends DbOp{

//	tepeweb.hostfeature_canceltransaction(a_host int, p_feature text, p_named text, 
//					p_tid int, p_refid text, p_why text) 
//	returns boolean
	private static final String query="select * "
			+ "from "
			+  "tepeweb.hostfeature_list(?)";

	public HostFeature[] doit(int hostId) throws KnownError{
		List<HostFeature> f=new ArrayList<HostFeature>();
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			
			db3.executeQuery();
			while(db3.next()){
				
				Integer host_id = db3.getInt();
				String feature = db3.getString();
				String named = db3.getString();
				boolean disabled = db3.getBoolean();
				java.sql.Date expire = db3.getDate();
				Integer autorenewperiod = db3.getInt();
				Integer usage = db3.getInt();
				Integer[] usage_hourly = (Integer[]) db3.getArray();
				Integer[] usage_daily = (Integer[]) db3.getArray();
				Integer[] usage_monthly = (Integer[]) db3.getArray();
				String[] refid = db3.getStringArray();
				Integer[] reasons = (Integer[]) db3.getArray();
				
				HostFeature hf=new HostFeature(feature, named, disabled, expire==null?null:expire.getTime(), autorenewperiod, usage,
						usage_hourly, usage_daily, usage_monthly, refid, reasons);
				f.add(hf);
				
			}
			return f.toArray(new HostFeature[f.size()]);
		}
		
		
	}
	
}