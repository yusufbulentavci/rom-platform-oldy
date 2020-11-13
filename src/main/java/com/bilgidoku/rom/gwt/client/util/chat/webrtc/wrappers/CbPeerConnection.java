package com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers;

import com.google.gwt.core.client.JavaScriptObject;

public interface CbPeerConnection {
	public void onicecandidate(JavaScriptObject jso);
	public void onconnecting(JavaScriptObject jso);
	public void onopen(JavaScriptObject jso);
	public void onaddstream(JavaScriptObject jso);
	public void onremovestream(JavaScriptObject jso);
}
