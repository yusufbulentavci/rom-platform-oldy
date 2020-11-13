package com.bilgidoku.rom.gwt.client.util.chat;

public interface IDlgChat {

	void said(String text);

	void remoteVideoSetSrc(String mediaBlobUrl);
	void localVideoSetSrc(String mediaBlobUrl);
	

	void callTerminated();
	
	void show();

	void setPresence(int code, String online);

}
