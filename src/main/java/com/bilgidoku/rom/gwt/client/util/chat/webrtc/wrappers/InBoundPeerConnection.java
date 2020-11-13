package com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers;

import com.bilgidoku.rom.gwt.client.util.chat.RtPoster;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.state.StateMachine;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class InBoundPeerConnection extends StateMachine {

	private final String cidTo;
	private final PeerConnectionWrapper wrapper;
	private final CallCb cb;

	/**
	 * start-/sendSdpOffer/->offerResponseWaiting-/sdpAnswer/->waitRemote-/
	 * remoteReady/->run
	 * 
	 * -/closeRequest->closed -/error/->closed
	 * 
	 * 
	 */


	CbPeerConnection peerConCb = new CbPeerConnection() {
		@Override
		public void onicecandidate(JavaScriptObject jso) {
//			Utils.consoleStr("ICE > onicecandidate");

			JSONObject candidate = null;
			try {
				candidate = new JSONObject(Utils.toJSON(jso));
			} catch (Exception e) {
				candidate = new JSONObject(jso);
			}
			if (candidate.toString().equals("{}")) {
//				Utils.consoleStr("No more ice candidate");
				return;
			}
//			Utils.consoleStr("IceCandidate:" + candidate.toString());
			JSONObject toRelay = new JSONObject();
			toRelay.put("cadidate", candidate);
			RtPoster.one().postRelay(cidTo, "iceCandidate", toRelay);
		}

		@Override
		public void onconnecting(JavaScriptObject jso) {
//			Utils.consoleStr("ICE > onconnecting");
//			Utils.consoleJs(jso);
		}

		@Override
		public void onopen(JavaScriptObject jso) {
//			Utils.consoleStr("ICE > onopen");
//			Utils.consoleJs(jso);

		}

		/**
		 * Called after setremotedescription
		 */
		@Override
		public void onaddstream(JavaScriptObject stream) {
//			Utils.consoleStr("ICE > onaddstream");
//			Utils.consoleJs(stream);

			// JSONObject(jso)).get("stream")).getJavaScriptObject();
			String mediaBlobUrl = Utils.createMediaObjectBlobUrl(stream);
			// Utils.consoleStr("mediaBlobUrl:" + mediaBlobUrl);
			Sistem.errln("------------------------------------");
			cb.talking(mediaBlobUrl);

		}

		@Override
		public void onremovestream(JavaScriptObject jso) {
//			Utils.consoleStr("ICE > onremovestream");
			event("error");

		}
	};

	public void rtRelay(JSONObject payload) {
		String type = ((JSONString) payload.get("icmd")).stringValue();
		if (type.equals("iceCandidate")) {
			event("iceCandidate", payload);
		} else if (type.equals("sdpOffer")) {
			event("sdpOffer", payload);
		} else if (type.equals("sdpAnswer")) {
			err("sdpAnswer came");
		} else {
			err("Unhnadled relay message");
//			Utils.consoleErr("Unhnadled relay message");
//			Utils.consoleJs(payload.getJavaScriptObject());
		}

	}

	public InBoundPeerConnection(final String cidTo, final JavaScriptObject localMediaStream, final CallCb cb) {
		super("InBoundPeerConnection");
		this.cb = cb;
		this.cidTo = cidTo;
		this.wrapper = new PeerConnectionWrapper(peerConCb, localMediaStream);

		t(new T("initial/sdpOffer") {

			@Override
			public String to(String from, String event, Object... params) {
				
				JSONObject payload = (JSONObject) params[0];
				JSONString sdp = (JSONString) payload.get("sdp");
				JSONString type = (JSONString) payload.get("type");

				wrapper.setRemoteDescription(type.stringValue(), sdp.stringValue(), new CbSetRemoteDesc() {

					@Override
					public void CbSetRemoteDescSuc() {
						Sistem.errln("+++++++++++++++++++++++++++++++++++++++");
						event("remoteReady");
					}

					@Override
					public void CbSetRemoteDescErr(String error) {
						event("error");
					}
				});

				return "setRemote";
			}

		});

		t(new T("setRemote/remoteReady") {
			@Override
			public String to(final String from, final String event, final Object... params) {
				wrapper.createAnswer(true, true, new CbSDPCreateOffer() {

					@Override
					public void RTCSessionDescriptionCallback(String type, String sdp) {
//						Utils.consoleStr("PeerConnection.setLocalDescription:" + " sdp:" + sdp);

						wrapper.setLocalDescription(type, sdp);
						
						JSONObject jso = new JSONObject();
						jso.put("sdp", new JSONString(sdp));
						jso.put("type", new JSONString(type));

						RtPoster.one().postRelay(cidTo, "sdpAnswer", jso);
						
						
						event("sdpAnswerSent");
					}

					@Override
					public void RTCPeerConnectionErrorCallback(JavaScriptObject error) {
//						Utils.consoleJs(error);

						err("Sending SDP answer error");

					}
				});

				return "sendingSdpAnswer";
			}
		});
		
		t(new T("run,setRemote/iceCandidate") {
			@Override
			public String to(String from, String event, final Object... params) {
				JSONObject payload=(JSONObject) params[0];
				JSONValue v = payload.get("cadidate");

				JSONObject cadidate = v.isObject();
				if (cadidate == null) {
					cadidate = new JSONObject();
//					Utils.consoleErr("Relayed null candidate");
					return "setRemote";
				}
				// Utils.consoleDebug(payload.getJavaScriptObject());

				wrapper.addIceCandidate(cadidate.getJavaScriptObject());

				return from;
			}
		});

		t(new T("sendingSdpAnswer/sdpAnswerSent") {

			@Override
			public String to(String from, String event, Object... params) {
				return "run";
			}
		});
		
		t(new T("*/error,close") {
			@Override
			public String to(String from, String event, Object... params) {
				if (wrapper != null) {
					wrapper.close();
				}
				if (event.equals("error")) {
					cb.failed();
				}
				cb.closed();

				return "failed";
			}

		});

		tDone();
	}

	public void close() {
		event("close");
	}

}
