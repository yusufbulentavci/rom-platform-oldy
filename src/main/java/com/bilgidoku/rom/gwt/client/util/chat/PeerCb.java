package com.bilgidoku.rom.gwt.client.util.chat;

public interface PeerCb {
	
	void callTerminated();

	void talking(String mediaBlobUrl);

	void localVideo(String params);

}
