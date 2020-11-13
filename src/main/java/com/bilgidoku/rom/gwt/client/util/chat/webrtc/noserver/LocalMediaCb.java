package com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver;

public interface LocalMediaCb {

	void busy();

	void mediaReady();

	void error(String string);

	void snapShotReady(String dataUrl);
	
	void talking();
	
	void talkReady(String dataUrl);

	void notSupported();

}
