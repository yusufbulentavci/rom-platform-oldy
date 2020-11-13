package com.bilgidoku.rom.mail;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import com.bilgidoku.rom.epostatemel.javam.mail.internet.AttachDeal;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;

public class AttachAdderDbfs implements AttachDeal {

	private int hostId;
	private Set<String> dbfsList;

	public AttachAdderDbfs(int hostId, String[] dbfs) {
		this.hostId = hostId;
		this.dbfsList = new HashSet<String>();
		if(dbfs==null)
			return;
		for (String string : dbfs) {
			dbfsList.add(string);
		}
	}

	@Override
	public File get(String dbfs) throws KnownError {
		if (!dbfsList.contains(dbfs))
			throw new KnownError("Dbfs not found in AttachDeal:" + dbfs);
		return DbfsGorevlisi.tek().get(hostId, dbfs);
	}

	public JSONArray ja;

	public AttachAdderDbfs() {
		ja = new JSONArray();
	}

	@Override
	public String make(InputStream is, String fileName) throws KnownError {
		String nd = DbfsGorevlisi.tek().make(0, is, fileName);
		ja.put(nd);
		return nd;
	}

}
