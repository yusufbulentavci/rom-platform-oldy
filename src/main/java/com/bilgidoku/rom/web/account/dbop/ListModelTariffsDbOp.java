package com.bilgidoku.rom.web.account.dbop;


import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.shared.Tariff;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class ListModelTariffsDbOp extends DbOp{

//	tepeweb.hostfeature_canceltransaction(a_host int, p_feature text, p_named text, 
//					p_tid int, p_refid text, p_why text) 
//	returns boolean
	private static final String query="select * "
			+ "from "
			+  "tepeweb.account_listtariff(?)";

	public Tariff[] doit(int hostId) throws KnownError{
		List<Tariff> f=new ArrayList<Tariff>();
		try(DbThree db3=new DbThree(query)){
			db3.setInt(hostId);
			
			db3.executeQuery();
			while(db3.next()){
				
				String model=db3.getString();
				String feature = db3.getString();
				Integer limitto = db3.getInt();
				Boolean denied=db3.getBoolean();
				Long credits=db3.getLong();
				
				Tariff tariff=new Tariff(feature,limitto,denied,credits);
				
				f.add(tariff);
				
			}
			return f.toArray(new Tariff[f.size()]);
		}
		
		
	}
	
}