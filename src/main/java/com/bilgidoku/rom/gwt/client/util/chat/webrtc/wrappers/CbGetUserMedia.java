package com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers;

import com.google.gwt.core.client.JavaScriptObject;

public interface CbGetUserMedia {
	void navigatorUserMediaSuccessCallback(MediaStream localStream);

	void navigatorUserMediaErrorCallback(JavaScriptObject error);
}
