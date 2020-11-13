package com.bilgidoku.rom.gwt.client.util.chat;

import com.google.gwt.json.client.JSONObject;

public interface IceMsgProcessor {
	
	void rtCallConfirmed();

	void rtEndCall();

	void rtRelay(JSONObject val);


}
