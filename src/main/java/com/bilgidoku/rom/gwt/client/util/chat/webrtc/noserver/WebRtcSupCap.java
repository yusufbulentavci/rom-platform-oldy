package com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

/**
 * 
 * 
 * Chrome: { "deviceId": true, "echoCancellation": true, "frameRate": true,
 * "height": true, "width": true
 * 
 * "aspectRatio": true, "channelCount": true, "facingMode": true, "groupId":
 * true, "latency": true, "sampleRate": true, "sampleSize": true, "volume":
 * true, }
 *
 * 
 * Firefox: { "deviceId": true, "echoCancellation": true, "frameRate": true,
 * "height": true, "width": true
 * 
 * "browserWindow": true, "facingMode": true, "mediaSource": true,
 * "mozAutoGainControl": true, "mozNoiseSuppression": true, "scrollWithPage":
 * true, "viewportHeight": true, "viewportOffsetX": true, "viewportOffsetY":
 * true, "viewportWidth": true, }
 * 
 * 
 * 
 * @author bulent.avci
 *
 */
public class WebRtcSupCap {

	public static JSONObject support = new JSONObject(getSupport());
//	public static JSONObject cap = new JSONObject(getCap());

	final private static native JavaScriptObject getSupport() /*-{
		return navigator.mediaDevices.getSupportedConstraints();
	}-*/;

//	final private static native JavaScriptObject getCap() /*-{
//		if(MediaStreamTrack.getCapabilities)
//			return  MediaStreamTrack.getCapabilities();
//		return {};
//	}-*/;
	
	
	
	public static native void getUserMedia(boolean audio, boolean video, boolean screen, int widthPx, int heightPx,
			CbGetUserMedia callback) /*-{

		//

		var cb = function(stream) {
			callback.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.CbGetUserMedia::navigatorUserMediaSuccessCallback(Lcom/bilgidoku/rom/gwt/client/util/chat/webrtc/noserver/MediaStream;)(stream);
		}

		var ecb = function(error) {
			//			navigator.mediaDevices
			//					.getUserMedia(spec)
			//					.then(
			//							function(stream) {
			//								callback.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.CbGetUserMedia::navigatorUserMediaSuccessCallback(Lcom/bilgidoku/rom/gwt/client/util/chat/webrtc/noserver/MediaStream;)(stream);
			//							},
			//							function(error) {
			//								callback.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.CbGetUserMedia::navigatorUserMediaErrorCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(error);
			//							});

			callback.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.CbGetUserMedia::navigatorUserMediaErrorCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(error);
		}
		
		var videoparam=video;

		if (screen) {
			videoparam={ mediaSource: "screen"};
		}
		
		if(video){
			videoparam={ width: widthPx, height: heightPx};
		}
		
		

		navigator.getUserMedia = navigator.getUserMedia
				|| navigator.webkitGetUserMedia || navigator.mozGetUserMedia;

		try {
			navigator.getUserMedia({
				audio : audio,
				video : videoparam
			}, cb, ecb);

		} catch (err) {
			ecb(err);
		}
	}-*/;

}
