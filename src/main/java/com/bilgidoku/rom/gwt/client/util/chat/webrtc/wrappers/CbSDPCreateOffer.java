package com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers;

import com.google.gwt.core.client.JavaScriptObject;

public interface CbSDPCreateOffer {
	void RTCSessionDescriptionCallback(String type, String sdp);
	void RTCPeerConnectionErrorCallback(JavaScriptObject error);

}
