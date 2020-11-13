package com.bilgidoku.rom.mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;

public class MailOnDb {
	
	
	public final String uri;
	final JSONObject mime;
	final int state;
	final String[] dbfs;

	public MailOnDb(String uri,JSONObject jsonObject, Integer int1, String[] stringArray) {
		this.uri=uri;
		this.mime = jsonObject;
		this.state = int1;
		this.dbfs = stringArray;
	}

	public long getSize() throws KnownError {
		if (dbfs == null || dbfs.length == 0)
			return 0;
		return DbfsGorevlisi.tek().get(0, dbfs[0]).length();
	}

	public InputStream getInputStream() throws KnownError {
		if (dbfs == null || dbfs.length == 0)
			return null;
		try {
			return new FileInputStream(DbfsGorevlisi.tek().get(0, dbfs[0]));
		} catch (FileNotFoundException e) {
			throw new KnownError(e);
		}
	}

	public long getUid() throws KnownError {
		int ind=uri.lastIndexOf('/');
		if(ind<0 || ind==uri.length()-1)
			throw new KnownError("No uid, invalid uri:"+uri);
		
		return Long.parseLong(uri.substring(ind+1));
	}

	public String[] attachMime(String k) {
		if(dbfs==null)
			return new String[]{k};
		String[] ret=new String[dbfs.length+1];
		ret[0]=k;
		for(int i=0;i<dbfs.length;i++){
			ret[i+1]=dbfs[i];
		}
		return ret;
	}
}
