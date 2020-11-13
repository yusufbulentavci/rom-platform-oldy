package com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers;

public interface CallCb {

	void failed();

	void talking(String params);

	void closed();

}
