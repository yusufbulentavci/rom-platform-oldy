package com.bilgidoku.rom.haber;

import com.bilgidoku.rom.haber.fastQueue.Cmd;
import com.bilgidoku.rom.ilk.json.JSONObject;

public interface MsgStatus {

	void sendFailed(Cmd msg);

	void sendSuc(Cmd msg);

	void newMsg(JSONObject msg, String rid);

}
