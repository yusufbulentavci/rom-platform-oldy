package com.bilgidoku.rom.gwt.client.util.chat;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.chat.im.XmppGui;
import com.bilgidoku.rom.gwt.client.util.chat.im.repo.CallWrap;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.wrappers.Utils;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.shared.util.AsyncMethodNoParam;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;

public class IceCallRt implements CallWrap{

	private final static ApplicationConstants cons = GWT.create(ApplicationConstants.class);

	private final String name;
	private final String cidTo;

	private PeerCon con;

	private DlgChat privateGui;
	private IDlgChat dlg;

	private PeerCb feedback = new PeerCb() {

		@Override
		public void callTerminated() {
		}

		@Override
		public void talking(String mediaBlobUrl) {
			dlg();
			privateGui.setRemoteVideoUrl(mediaBlobUrl);
		}

		@Override
		public void localVideo(String mediaBlobUrl) {
			dlg();
			privateGui.setLocalVideoUrl(mediaBlobUrl);
		}
	};

	private XmppGui gui;

	
	public IceCallRt(String name, String cidTo) {
		this.gui=(XmppGui) RomEntryPoint.cm().comp("+xmpp", "gui");
		this.name = name;
		this.cidTo = cidTo;
	}
	
	public IDlgChat dlg() {
		if (dlg == null) {
			privateGui = new DlgChat(getCbChat(), cidTo);
			dlg = privateGui.getDlgChatImpl();
		}
		gui.showChatDlg(privateGui);
		return dlg;
	}

	

	public void setPresence(int code, String online) {
		if (dlg != null) {
			dlg.setPresence(code, online);
		}
	}

	public CbChat getCbChat() {
		return new CbChat() {
			@Override
			public void weCall() {
				con = new OutBoundCall(cidTo, name, feedback);
			}

			@Override
			public void weHangUp() {
				if(con!=null)
					con.rtEndCall();
			}

			@Override
			public String localVideoUrl() {
				return null;
			}

			@Override
			public String remoteVideoUrl() {
				return null;
			}

		};
	}

	private final RtMsgProcessor msgProcessor = new RtMsgProcessor() {

		@Override
		public void rtCall() {
			gui.notice(cons.heCall(name));
			gui.askForNewCall(name, new AsyncMethodNoParam() {

				@Override
				public void run() {

					con = new InBoundCall(name, cidTo, feedback);

				}

				@Override
				public void error() {

				}

			});
		}

		@Override
		public void rtCallConfirmed() {
//			Utils.consoleStr("SIG:>> handle_call_confirmed");
			if (con == null)
				return;

			gui.notice(cons.heCallConfirmed(name));

			con.rtCallConfirmed();

		}

		@Override
		public void rtEndCall() {
//			Utils.consoleStr("SIG:>>rt:endcall");

			if (con == null)
				return;
			con.rtEndCall();
		}

		@Override
		public void rtRelay(JSONObject payload) {
			con.rtRelay(payload);
		}

		@Override
		public void xmmsSay(String text) {
			dlg().said(text);

		}

		

	};

	public RtMsgProcessor getMsgProcessor() {
		return msgProcessor;
	}

}
