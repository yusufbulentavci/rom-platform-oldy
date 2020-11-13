package com.bilgidoku.rom.session;

import com.bilgidoku.rom.kurum.KurumGorevlisi;
import com.bilgidoku.rom.pg.dict.CommonSession;
import com.bilgidoku.rom.pg.dict.RomDomain;
import com.bilgidoku.rom.pg.dict.RomUser;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class LoadUser extends DbOp {

	private static final String query = "select credentials,roles,contact from dict.appserver_load_user(?,?)";
	

//	public RomUser doit(CommonSession session, String userName) throws KnownError {
//		try (DbThree db3 = new DbThree(query)) {
//			db3.setInt(session.getIntraHostId());
//			db3.setString(userName);
//			db3.executeQuery();
//			if(!db3.next())
//				return null;
//			String credentials = db3.getString();
//			int rolNames = db3.getInt();
//			String contact = db3.getString();
//			return new RomUser(session, userName, credentials, rolNames, contact);
//		}
//	}
	
	public RomUser doit(CommonSession session, int intraHostId, String userName) throws KnownError {
		try (DbThree db3 = new DbThree(query)) {
			db3.setInt(intraHostId);
			db3.setString(userName);
			db3.executeQuery();
			if(!db3.next())
				return null;
			String credentials = db3.getString();
			int rolNames = db3.getInt();
			String contact = db3.getString();
			
			RomDomain rd=KurumGorevlisi.tek().getDomain(intraHostId);
			
			return new RomUser(intraHostId, session, rd, userName, credentials, rolNames, contact);
		}
	}

}
