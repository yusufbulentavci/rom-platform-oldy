package com.bilgidoku.rom.web.grow;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class NewHostDbOp extends DbOp{
	private static final MC mc=new MC(NewHostDbOp.class);
	
	
//	1				2					3				4
//tepeweb.newhost(p_hostname text, p_lang rom.langs, p_contact json, p_payerid text,
//p_admin text, p_credential text, p_remotetid text, p_money bigint) returns int
//5				6					7				8
	private static final String query="select * "
			+ "from tepeweb.newhost(?,?::rom.langs,?::json,?,?,?,?,?,?,?)";
	
	private static final Astate _create=mc.c("create.f.rm");

	public int create(String hostName, String lang, String contact, String payerId,
			String adminName, String credential, String rid, long money, String facebookId, String twitterId) throws KnownError{
		_create.more();
		
		try(DbThree db3=new DbThree(query)){
			db3.setString(hostName);
			db3.setString(lang);
			db3.setString(contact);
			db3.setString(payerId);
			db3.setString(adminName);
			db3.setString(credential);
			db3.setString(rid);
			db3.setLong(money);
			db3.setString(facebookId);
			db3.setString(twitterId);
			db3.executeQuery();
			if(!db3.next()){
				_create.fail(hostName,lang,contact,payerId,adminName,credential,rid,money);
				throw new KnownError();
			}
			return db3.getInt();
		}
		
	}
	
}
