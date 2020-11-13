package com.bilgidoku.rom.web.account.dbop;


import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.shared.Feature;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class ListFeaturesDbOp extends DbOp{

	private static final String query="select * "
			+ "from "
			+  "tepeweb.feature_list()";

	public Feature[] doit() throws KnownError{
		List<Feature> f=new ArrayList<Feature>();
		try(DbThree db3=new DbThree(query)){
			db3.executeQuery();
			while(db3.next()){

//				item text not null,
//				importance int not null,
//				tperiod int not null,
//				invoicetperiod int,
//				invoicetperiodamount int,
//				autorenewoptions int[],
//				usageunit text,
//				description text,

				
				String item=db3.getString();
				Integer importance=db3.getInt();
				Integer tperiod=db3.getInt();
				Integer invoicetperiod=db3.getInt();
				Integer ipamount=db3.getInt();
				Integer[] aro=(Integer[])db3.getArray();
				String usageunit=db3.getString();
				String description=db3.getString();
				
				Feature hf=new Feature(item,importance,tperiod,invoicetperiod,ipamount,aro,usageunit,description);
				
				f.add(hf);
				
			}
			return f.toArray(new Feature[f.size()]);
		}
		
		
	}
	
}