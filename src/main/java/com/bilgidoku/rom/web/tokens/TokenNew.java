package com.bilgidoku.rom.web.tokens;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class TokenNew extends DbOp {
	private static final String query = "select * from rom.tokens_new(?,?,?,?,?::json,?::json,?)";

	// rom.tokens_new(a_host integer, p_rid text, p_world text, p_cmd text,
	// p_dataclient json,p_dataserver json, p_expires timestamp) returns
	// text
	public String create(int hostId, String rid, String world, String cmd, 
			JSONObject clientJo, JSONObject serverJo, int mins) throws KnownError {

		try (DbThree db3 = new DbThree(query)) {
			db3.setInt(hostId);
			db3.setString(rid);
			db3.setString(world);
			db3.setString(cmd);
			db3.setString(clientJo==null?null:clientJo.toString());
			db3.setString(serverJo==null?null:serverJo.toString());
			
			Calendar expire = GregorianCalendar.getInstance();
			expire.add(Calendar.MINUTE, mins);

			db3.setTimestamp(new Timestamp(expire.getTimeInMillis()));
			
			db3.executeQuery();
			db3.checkedNext();
			
			return db3.getString();
		}
	}

}
