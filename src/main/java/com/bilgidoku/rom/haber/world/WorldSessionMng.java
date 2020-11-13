package com.bilgidoku.rom.haber.world;

import com.bilgidoku.rom.haber.fastQueue.Cmd;
import com.bilgidoku.rom.ilk.json.JSONObject;


public interface WorldSessionMng {

	WorldSession newSession(String name);

	void endCon(WorldConnection talkSession);

	void newCon(WorldConnection worldConnection);
	void newMsg(WorldConnection worldConnection, JSONObject msg);
	void sent(WorldConnection worldConnection, Cmd msg);
	void sentFailed(WorldConnection worldConnection, Cmd msg);

}
