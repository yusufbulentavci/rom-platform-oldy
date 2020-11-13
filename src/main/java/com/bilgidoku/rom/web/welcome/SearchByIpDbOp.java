package com.bilgidoku.rom.web.welcome;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.shared.Welcome;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class SearchByIpDbOp extends DbOp {

	private static final String query = "select email,hostid,site,ip,country,lastactivity "
			+ "from tepeweb.welcome_byip(?)";

	public List<Welcome> doit(String p_ip) throws KnownError {
		List<Welcome> ret = new ArrayList<Welcome>();
		try (DbThree db3 = new DbThree(query)) {
			db3.setString(p_ip);
			db3.executeQuery();
			while (db3.next()) {
				String email=db3.getString();
				Integer hostname=db3.getInt();
				String site=db3.getString();
				String ip=db3.getString();
				String country=db3.getString();
				Timestamp lastactivity = db3.getTimestamp();
				ret.add(new Welcome(email,hostname,site,ip,country,lastactivity));
			}
		}
		return ret;
	}

}
