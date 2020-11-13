package com.bilgidoku.rom.kurum;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.shared.HostStat;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class LoadHostsDbOp extends DbOp {
	private static final MC mc = new MC(LoadHostsDbOp.class);

	protected static final String query = "select h.host_id,h.host_name,h.mainlang,h.domainalias,h.servable,forcehttps is not null and forcehttps"
	+" from dict.hosts h"
	+" left outer join rom.org o on(h.host_id = o.host_id)";

	public HostStat[] doit() throws KnownError {
		List<HostStat> got = loadNow();
		return got.toArray(new HostStat[got.size()]);
	}

	protected List<HostStat> loadNow() throws KnownError {
		try (DbThree db3 = new DbThree(query)) {
			List<HostStat> all = new ArrayList<HostStat>();
			db3.executeQuery();
			while (db3.next()) {
				HostStat hs = new HostStat();
				hs.hostid = db3.getInt();
				hs.hostname = db3.getString();
				hs.mainlang = db3.getString();
				hs.domainalias = db3.getStringArray();
				hs.servable = db3.getBoolean();
				hs.forceHttps = db3.getBoolean();
				all.add(hs);
			}
			return all;
		}
	}

}
