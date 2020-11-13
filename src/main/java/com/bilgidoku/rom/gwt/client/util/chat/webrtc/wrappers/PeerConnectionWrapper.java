package com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers;

import com.google.gwt.core.client.JavaScriptObject;

public class PeerConnectionWrapper {
	JavaScriptObject pc;

	public PeerConnectionWrapper(CbPeerConnection callbacks, JavaScriptObject localMediaStream) {
		init(callbacks);
		addStream(localMediaStream);
	}

	private native void init(CbPeerConnection callbacks) /*-{
		var theInstance = this;
		var c = {
			iceServers : [ {
				url : "stun:stun.l.google.com:19302"
			} ]
		};

		//var c = conf.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.RTCConfiguration::asJavaScriptObject(*)();

		theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc = window.mozRTCPeerConnection ? new mozRTCPeerConnection(
				c)
				: new webkitRTCPeerConnection(c);

		var _onicecandidate = function(e) {
			callbacks.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.CbPeerConnection::onicecandidate(Lcom/google/gwt/core/client/JavaScriptObject;)(e.candidate);
		}
		var _onconnecting = function(e) {
			callbacks.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.CbPeerConnection::onconnecting(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
		}
		var _onopen = function(e) {
			callbacks.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.CbPeerConnection::onopen(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
		}
		var _onaddstream = function(e) {
			callbacks.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.CbPeerConnection::onaddstream(Lcom/google/gwt/core/client/JavaScriptObject;)(e.stream);
		}
		var _onremovestream = function(e) {
			callbacks.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.CbPeerConnection::onremovestream(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
		}

		theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc.onicecandidate = _onicecandidate;
		theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc.onconnecting = _onconnecting;
		theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc.onopen = _onopen;
		theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc.onaddstream = _onaddstream;
		theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc.onremovestream = _onremovestream;

	}-*/;

	native void createAnswer(boolean video, boolean audio, CbSDPCreateOffer callback) /*-{
		var theInstance = this;

		var offerCallback = function(event) {
			callback.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.CbSDPCreateOffer::RTCSessionDescriptionCallback(Ljava/lang/String;Ljava/lang/String;)(event.type, event.sdp);
		}
		var errorCallback = function(event) {
			callback.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.CbSDPCreateOffer::RTCPeerConnectionErrorCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
		}

		var mc = {
			'mandatory' : {
				'OfferToReceiveAudio' : audio,
				'OfferToReceiveVideo' : video
			}
		};

		theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc
				.createAnswer(offerCallback, errorCallback, mc);
	}-*/;

	native void createOffer(boolean video, boolean audio, CbSDPCreateOffer callback) /*-{
		var theInstance = this;

		var offerCallback = function(event) {
			callback.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.CbSDPCreateOffer::RTCSessionDescriptionCallback(Ljava/lang/String;Ljava/lang/String;)(event.type, event.sdp);
		}
		var errorCallback = function(event) {
			callback.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.CbSDPCreateOffer::RTCPeerConnectionErrorCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
		}

		var mc;

		if (navigator.mozGetUserMedia) {
			mc = {
				'OfferToReceiveAudio' : audio,
				'OfferToReceiveVideo' : video,
			}
		} else {
			mc = {
				'mandatory' : {
					'OfferToReceiveAudio' : audio,
					'OfferToReceiveVideo' : video,
				}

			};
		}
		theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc
				.createOffer(offerCallback, errorCallback, mc);
	}-*/;

	private native JavaScriptObject getLocalDescription()/*-{
		var theInstance = this;
		return theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc.localDescription;
	}-*/;

	private native JavaScriptObject getRemoteDescription()/*-{
		var theInstance = this;
		return theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc.remoteDescription;
	}-*/;

	native void setLocalDescription(String type, String sdp)/*-{
		var theInstance = this;
		var sdpo = {
			type : type,
			sdp : sdp
		};
		var sessionDescription = window.RTCSessionDescription
				|| window.mozRTCSessionDescription
				|| window.webkitRTCSessionDescription
				|| window.msRTCSessionDescription;

		theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc
				.setLocalDescription(new sessionDescription(sdpo));
	}-*/;

	private native void addStream(JavaScriptObject jso)/*-{
		var theInstance = this;
		console.debug(jso);
		theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc
				.addStream(jso);
	}-*/;

	native void setRemoteDescription(String type, String sdp, CbSetRemoteDesc callback)/*-{
		var theInstance = this;

		var offerCallback = function() {
			callback.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.CbSetRemoteDesc::CbSetRemoteDescSuc()();
		}
		var errorCallback = function(desc) {
			callback.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.CbSetRemoteDesc::CbSetRemoteDescErr(Ljava/lang/String;)(desc);
		}

		var sdpo = {
			type : type,
			sdp : sdp
		};

		var sessionDescription = window.RTCSessionDescription
				|| window.mozRTCSessionDescription
				|| window.webkitRTCSessionDescription
				|| window.msRTCSessionDescription;

		theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc
				.setRemoteDescription(new sessionDescription(sdpo),
						offerCallback, errorCallback);
	}-*/;

	native void close()/*-{
		var theInstance = this;
		try {
			theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc
					.close();
		} catch (err) {
			console.log('Exeption while closing peer connection');
			console.debug(err);
		}
	}-*/;

	private native void dumpPCToConsole() /*-{
		console
				.debug(this.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc);
	}-*/;

	native void addIceCandidate(JavaScriptObject jso) /*-{
		var theInstance = this;

		var iceCandidate = window.RTCIceCandidate || window.mozRTCIceCandidate
				|| window.webkitRTCIceCandidate || window.msRTCIceCandidate;

		theInstance.@com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.PeerConnectionWrapper::pc
				.addIceCandidate(new iceCandidate(jso));
	}-*/;

}
