package com.bilgidoku.rom.gwt.client.util.chat;

import com.bilgidoku.rom.shared.state.StateMachine;
import com.google.gwt.json.client.JSONObject;

public abstract class PeerCon extends StateMachine{

	protected PeerCon(String name) {
		super(name);
	}

	public abstract void rtEndCall();

	public abstract void rtCallConfirmed();

	public abstract void rtRelay(JSONObject payload);

}
