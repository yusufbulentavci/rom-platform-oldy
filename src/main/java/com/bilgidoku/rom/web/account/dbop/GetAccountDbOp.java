package com.bilgidoku.rom.web.account.dbop;

import java.sql.Date;

import com.bilgidoku.rom.gwt.shared.Account;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class GetAccountDbOp extends DbOp {

	private static final String query = "select * " + "from " + "tepeweb.account_get(?)";

	public Account doit(Integer a_host) throws KnownError {
		try (DbThree db3 = new DbThree(query)) {

			db3.setInt(a_host);
			db3.executeQuery();
			hasNext(db3);

			//				credits bigint not null default 0,
			//				reserved bigint not null default 0,
			//				model text not null default 'welcome',
			//				model_expire date,
			//				model_next text,
			//				limitfeatures text[],

			Integer hostId=db3.getInt();
			Long credits=db3.getLong();
			Long reserved=db3.getLong();
			String model=db3.getString();
			Date modelExpire = db3.getDate();
			Long expireTime=modelExpire==null?null:modelExpire.getTime();
			String modelNext=db3.getString();
			String[] limitFeatures = db3.getStringArray();

			return new Account(credits,reserved,model,expireTime, modelNext,limitFeatures);
		}

	}

}