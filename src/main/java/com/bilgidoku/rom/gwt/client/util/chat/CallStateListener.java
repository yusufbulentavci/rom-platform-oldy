package com.bilgidoku.rom.gwt.client.util.chat;

public interface CallStateListener {
	void weRequestedCall();
	void callRequested();
	void weConfirmedCall();
	void callConfirmed();

	void onCallStarted();
	void remoteVideoSetSrc(String mediaBlobUrl);

	void onCallTerminate(String cause);
}
