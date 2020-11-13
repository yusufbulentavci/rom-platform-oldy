package com.bilgidoku.rom.web.db;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.dbop.Info;

public class DbDao {
	
	public static Info getInfo(int hostId, String uri, String lang) throws KnownError, JSONException, KnownError {
		try (DbThree db = new DbThree("select * from site.info_get(?, ?::rom.langs, ?::rom.langs)")) {
			db.setInt(hostId);
			// db.setString(uri);
			db.setString(lang);
			db.setString(lang);
			db.executeQuery();
			db.checkedNext();

			InfoCoder ic = new InfoCoder();
			// in.langcodes = db.getStringArray();
			return ic.getDbValue(db);
		}
	}
	
	public static Org getOrg(int hostId) throws KnownError, JSONException, KnownError {
		try (DbThree db = new DbThree("select * from rom.org where host_id=?)")) {
			db.setInt(hostId);
			db.executeQuery();
			db.checkedNext();

			OrgCoder ic = new OrgCoder();
			return ic.getDbValue(db);
		}
	}


}
