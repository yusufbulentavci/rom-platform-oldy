package com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers;

import com.google.gwt.core.client.JavaScriptObject;

public class MediaStream extends JavaScriptObject {
	protected MediaStream() {
	}

	public final native String createMediaObjectBlobUrl() /*-{
		var theInstance = this;
		return URL.createObjectURL(this);
	}-*/;

	public final native void close() /*-{
		this.getTracks().forEach(function(track) {
			track.stop();
		});
	}-*/;

}
