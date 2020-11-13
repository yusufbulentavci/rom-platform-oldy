package com.bilgidoku.rom.gwt.client.util.chat;

import com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.LocalMedia;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.LocalMediaCb;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.MediaProfile;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.CallCb;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.OutBoundPeerConnection;
import com.google.gwt.json.client.JSONObject;

public class OutBoundCall extends PeerCon{
	
	
	final PeerCb dlgChat;
	
	final String cidTo;
	private final String name;
	OutBoundPeerConnection connection;
	LocalMedia localMedia;
	
	LocalMediaCb localMediaCb=new LocalMediaCb(){

		@Override
		public void busy() {
		}

		@Override
		public void mediaReady() {
			event("localReady", localMedia.getLocalMedia());
		}

		@Override
		public void error(String err) {
			err("Local media error:"+err);
		}

		@Override
		public void snapShotReady(String dataUrl) {
		}

		@Override
		public void talking() {
		}

		@Override
		public void talkReady(String dataUrl) {
		}

		@Override
		public void notSupported() {
			OutBoundCall.this.err("Local media not supported");
		}
		
	};

	
	public OutBoundCall(final String cidTo, final String name, final PeerCb chat) {
		super("OutBoundCall");
		this.cidTo=cidTo;
		this.dlgChat=chat;
		this.name=name;
		t(new T("*/error"){

			@Override
			public String to(String from, String event, Object... params) {
				if(connection!=null)
					connection.close();
				if(localMedia!=null)
					localMedia.close();
				return "failed";
			}
			
		});

		t(new T("*/error,heClose,weClose"){
			@Override
			public String to(String from, String event, Object... params) {
				if(localMedia!=null){
					localMedia.close();
					localMedia=null;
				}
				if(connection!=null){
					connection.close();
					connection=null;
					dlgChat.callTerminated();
				}
				if(isInState("remoteOffered","connection","run")){
					RtPoster.one().postEndCall(cidTo);
				}
				return "closed";
			}
			
		});
		
		t(new T("initial/start"){

			@Override
			public String to(String from, String event, Object... params) {
				localMedia=new LocalMedia(localMediaCb, MediaProfile.video());
				localMedia.start();
				return "localWaiting";
			}
			
		});
		
		t(new T("localWaiting/localReady"){
			@Override
			public String to(String from, String event, Object... params) {
				String blobUrl=(String) params[0];
				dlgChat.localVideo(blobUrl);
				RtPoster.one().postCall(cidTo);
				return "remoteOffered";
			}
			
		});
		
		t(new T("remoteOffered/remoteAccepted"){
			@Override
			public String to(String from, String event, Object... params) {
				connection=new OutBoundPeerConnection(cidTo, localMedia.getLocalMediaStream(), new CallCb() {
					
					@Override
					public void talking(String mediaBlobUrl) {
						dlgChat.talking(mediaBlobUrl);
					}
					
					@Override
					public void failed() {
						err("Connection failed");
					}

					@Override
					public void closed() {
					}
				});
				return "connection";
			}
			
		});
		
		
		tDone();
		
		event("start");
	}
	

	


	public void weClose(){
		event("weClose");
	}
	
	@Override
	public void rtRelay(JSONObject payload) {
		if(connection!=null)
			connection.rtRelay(payload);		
	}

	@Override
	public void rtEndCall() {
		event("heClose");
	}
	
	@Override
	public void rtCallConfirmed() {
		event("remoteAccepted");
	}
}
