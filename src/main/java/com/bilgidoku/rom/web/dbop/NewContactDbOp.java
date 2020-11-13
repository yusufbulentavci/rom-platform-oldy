package com.bilgidoku.rom.web.dbop;

import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class NewContactDbOp extends DbOp {
	private static final MC mc = new MC(NewContactDbOp.class);

//	a_host integer,a_lang rom.langs,p_lng rom.langs,p_cc rom.countrycode,p_email text,p_cipher text,p_firstname text, p_lastname text, p_fid text, p_twitter text
	
	private static final String query = "select * from rom.contacts_new(?,?::rom.langs,?::rom.langs,?::rom.countrycode,?,?,?,?,?,?,?,?)";



	public String doIt(int intraHostId, String a_lang, String p_cc,
			String p_email,
			String p_credential,
			String p_firstname,
			String p_lastname, 
			String p_fid, 
			String p_twitter,
			String p_mobile,
			String p_ip) throws KnownError {
		try (DbThree db3 = new DbThree(query);) {

			db3.setInt(intraHostId);
			db3.setString(a_lang);
			db3.setString(a_lang);
			db3.setString(p_cc);
			db3.setString(p_email);
			db3.setString(p_credential);
			db3.setString(p_firstname);
			db3.setString(p_lastname);
			db3.setString(p_fid);
			db3.setString(p_twitter);
			db3.setString(p_mobile);
			db3.setString(p_ip);
			db3.executeQuery();
			if(!db3.next()){
				return null;
			}
			return db3.getString();
		}
	}

}
