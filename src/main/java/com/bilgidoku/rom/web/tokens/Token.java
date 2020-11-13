package com.bilgidoku.rom.web.tokens;

import java.sql.Timestamp;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;

public class Token {

	final String rid;
	public final String cmd;
	public final JSONObject dataClient;
	public final JSONObject dataServer;
	final Timestamp expires;
	public final String world;

	// rid,world,cmd,dataclient,dataserver,expires
	public Token(String rid, String world, String cmd, String dataClient, String dataServer, Timestamp expires)
			throws JSONException {
		this.rid = rid;
		this.world=world;
		this.cmd = cmd;
		this.dataClient = dataClient == null ? null : new JSONObject(dataClient);
		this.dataServer = dataServer == null ? null : new JSONObject(dataServer);
		this.expires = expires;
	}

}
