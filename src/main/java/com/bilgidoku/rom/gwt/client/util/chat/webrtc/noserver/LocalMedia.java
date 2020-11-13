package com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver;

import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.media.client.Audio;

class CidCmdJo {
	final String cid, cmd;
	final JSONObject jo;

	public CidCmdJo(String cid, String cmd, JSONObject jo) {
		super();
		this.cid = cid;
		this.cmd = cmd;
		this.jo = jo;
	}
}

/**
 * In about:config, please enable media.getusermedia.screensharing.enabled and
 * add this site's domain name to
 * media.getusermedia.screensharing.allowed_domains in about:config
 * 
 * @author avci
 *
 */
class GetUserMediaUtils {

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

		var spec = {
			audio : audio,
			video : video
		};
		if (video) {
			spec = {
				audio : audio,
				video : {
					width : {
						ideal : widthPx,
						max : widthPx
					},
					height : {
						ideal : heightPx,
						max : widthPx
					},
				}
			};
		}

		navigator.getUserMedia = navigator.getUserMedia
				|| navigator.webkitGetUserMedia || navigator.mozGetUserMedia;

		try {
			navigator.getUserMedia({
				audio : audio,
				video : video
			}, cb, ecb);

		} catch (err) {
			ecb(err);
		}
	}-*/;

}

// audio : false,
// video : {
// mandatory : {
// chromeMediaSource : 'screen',
// maxWidth : 1280,
// maxHeight : 720
// },
// optional : []
// }

public class LocalMedia implements CbGetUserMedia {

	private Audio au = Audio.createIfSupported();

	private boolean error = false;

	private Boolean accepted = null;

	private boolean busy;

	private final LocalMediaCb run;

	protected MediaStream localMedia = null;
	protected JavaScriptObject mediaRecorder;

	private final MediaProfile mediaProfile;

	public LocalMedia(LocalMediaCb run, MediaProfile mediaProfile) {
		this.mediaProfile = mediaProfile;
		this.run = run;
	}

	private static native boolean isSupported() /*-{

		window.AudioContext = window.AudioContext || window.webkitAudioContext;

		return !!(navigator.getUserMedia || navigator.webkitGetUserMedia
				|| navigator.mozGetUserMedia || navigator.msGetUserMedia);
	}-*/;



	private static native boolean isAudioSupported() /*-{
		return !!(window.AudioContext || window.webkitAudioContext);
	}-*/;

	public void start() {
		Sistem.outln("WebRTC getUserMedia " + mediaProfile.toString());
		if (!isSupported()) {
			this.error = true;
			Sistem.outln("WebRTC configuration not supported");
			run.notSupported();
			mediaProfile.error();
			return;
		}
		if (this.busy) {
			Sistem.outln("WebRTC busy");
			mediaProfile.error();
			run.busy();
		}

		try {
			busy = true;

			if (accepted != null && accepted) {
				Sistem.outln("WebRTC already accepted");
				return;
			}
			accepted = null;
			Sistem.outln("WebRTC getUserMedia call");
			WebRtcSupCap.getUserMedia(mediaProfile.audio, mediaProfile.camera, mediaProfile.screen, mediaProfile.width,
					mediaProfile.height, this);
		} finally {
			busy = false;
		}

	}

	public String getLocalMedia() {
		return localMedia.createMediaObjectBlobUrl();
	}

	public JavaScriptObject getLocalMediaStream() {
		return localMedia;
	}

	public void close() {
		Sistem.outln("WebRTC close");
		try {
			if (localMedia != null)
				localMedia.close();
		} catch (Exception e) {
			Sistem.printStackTrace(e);
		} finally {
			localMedia = null;
			error = false;
			accepted = null;
			busy = false;
		}
	}

	@Override
	public synchronized void navigatorUserMediaSuccessCallback(MediaStream localStream) {
		this.accepted = true;
		this.localMedia = localStream;
		Sistem.outln("WebRTC LocalMedia ready");
		// preview.setVideo(localStream);
		this.busy = false;
		mediaProfile.ready();

		mediaReady();
	}

	private void mediaReady() {
		mediaProfile.setSrc(getLocalMedia());
		run.mediaReady();
	}

	@Override
	public void navigatorUserMediaErrorCallback(JavaScriptObject error) {
		this.accepted = false;
		this.error = true;
		this.busy = false;
		String str = (new JSONObject(error)).toString();
		Sistem.outln("WebRTC LocalMedia ready error:" + str);

		mediaProfile.error();
		run.error(str);
	}

	public void talkBegin() {
		if (error)
			return;
		Sistem.outln("WebRTC talk begin");
		startTalking();
	}

	public void talkEnd() {
		Sistem.outln("WebRTC talk end");
		endTalking();
	}

	private native void startTalking() /*-{
		//		var chunks = "data:audio/ogg;base64,";

		var mediaRecorder = new MediaRecorder(
				this.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.LocalMedia::localMedia, {mimeType:'audio/ogg;codecs=opus', audioBitsPerSecond:10000});

		//mediaRecorder.mimeType = ;

		this.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.LocalMedia::mediaRecorder = mediaRecorder;

		var that = this;

		mediaRecorder.ondataavailable = function(e) {
			console.log("ondata");
			//			console.log(window.btoa(e.data));
			//			chunks+=window.btoa(e.data);
			//			
			var reader = new FileReader();
			reader
					.addEventListener(
							"load",
							function() {
								console.log('-->' + reader.result);
								that.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.LocalMedia::talkReady(Ljava/lang/String;)(reader.result);
							}, false);
			reader.readAsDataURL(e.data);

		}

		mediaRecorder.onstop = function(e) {
			console.log("onstop");

			//			chunks = "";
		}

		mediaRecorder.start(10000);
	}-*/;

	private native void endTalking() /*-{
		this.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.LocalMedia::mediaRecorder
				.stop();
	}-*/;

	public void snapshot() {
		run.snapShotReady(mediaProfile.snapShot());
	}

	public void talkReady(String datUrl) {
		if (datUrl.startsWith("data:;base64,")) {
			datUrl = datUrl.replaceFirst("data:;base64,", "data:audio/ogg;base64,");
		}
		run.talkReady(datUrl);
	}

}