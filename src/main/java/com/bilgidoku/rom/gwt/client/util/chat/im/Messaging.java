package com.bilgidoku.rom.gwt.client.util.chat.im;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsDao;
import com.bilgidoku.rom.gwt.client.util.Confirmation;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.chat.DlgChat;
import com.bilgidoku.rom.gwt.client.util.chat.RtMsgProcessor;
import com.bilgidoku.rom.gwt.client.util.chat.im.repo.ContactBla;
import com.bilgidoku.rom.gwt.client.util.chat.im.repo.ContactRepo;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.MediaProfile;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.gwt.client.util.common.ActionBar;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.util.AsyncMethodNoParam;
import com.bilgidoku.rom.shared.util.Presence;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Messaging extends DialogBox {

	public static final CompInfo info = new CompInfo("+xmpp", 100,
			new String[] { "*rt.presence", "*rt.say", "*rt.exchange", "*dlg.exists", "*dlg.toggle", "*dlg.join",
					"*dlg.leave", "*dlg.say", "*wnd.closing", "*dlg.pushtotalk", "*dlg.photo", "*dlg.req", "*dlg.tvimg",
					"*dlg.tvvideo", "*dlg.tvvideoctrl", "*dlg.tvmark", "*dlg.tvtext", "*dlg.tvheader", "*wnd.ctrlbegin",
					"*wnd.ctrlend", "*dlg.tvshow", "*dlg.presence" },
			new String[] { "_wndtop", "netonline", "cid" }, null);
	public static final CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return new Messaging().comp;
		}
	};

	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);

	public static final String CONTACT_CONTAINER = "/_/co";

	public final static int FirstTop = 29;
	// private final static int SecondTop = 285;
	public final static int FirstRigth = 220;

	final XmppGui xmppGui = new XmppGui() {

		@Override
		public void askForNewCall(String from, AsyncMethodNoParam asyncMethodNoParam) {
			new Confirmation(trans.heCall(from), asyncMethodNoParam);
		}

		@Override
		public void notice(String note) {
			Sistem.outln(note);
			// Ctrl.setStatus(note);
		}

		@Override
		public void showChatDlg(DlgChat dlg) {
			int left = getDlgLeft();
			int top = getDlgTop();

			dlg.showPopUp(left, top);

		}

		@Override
		public void showChatDlg(ContactBla bla) {
			bla.getCall().dlg().show();
		}

		@Override
		public void deskReady(boolean b) {

		}

		@Override
		public void online(boolean b) {

		}

		@Override
		public void xmmsPresence(String from, int code, String presence) {
		}

		@Override
		public void contactAdd(ContactBla contact) {
		}

		@Override
		public void contactChanged(ContactBla contact) {
		}

		@Override
		public void contactAdded(ContactBla cba) {

		}

		@Override
		public void resetList() {

		}

		@Override
		public void presenceChanged(ContactBla param) {

		}

		@Override
		public void contactRemove(ContactBla rem) {

		}

		@Override
		public void contactRemove(String cid) {

		}

		@Override
		public void showContacts() {
			Messaging.this.show();
		}

		@Override
		public void dlgSay(ContactBla param, boolean mine, String text) {

		}

		@Override
		public void dlgJoin(ContactBla param, boolean mine) {

		}

		@Override
		public void dlgContactPhotoChanged(ContactBla param, boolean mine) {

		}

		@Override
		public void dlgVideo(ContactBla param, boolean mine, String src, String docmd) {

		}

		@Override
		public void dlgTvImg(ContactBla param, boolean mine, String src) {

		}

		@Override
		public void dlgTvVideo(ContactBla param, boolean mine, String src) {

		}

		@Override
		public void dlgTvMark(ContactBla param, boolean mine, int markx, int marky) {

		}

		@Override
		public void dlgTvVideoCtrl(ContactBla param, boolean mine, Integer secs, String ctrl) {

		}

		@Override
		public void dlgTvText(ContactBla param, boolean mine, String str) {

		}

		@Override
		public void dlgTvShow(ContactBla param, boolean mine, char str) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dlgTvHeader(ContactBla param, boolean mine, String str) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dlgTvPresence(ContactBla param, boolean mine, String text) {
			// TODO Auto-generated method stub
			
		}

	};

	XmppContacts xmppContacts = new XmppContacts() {

		@Override
		public ContactBla getContactInfo(String cid) {
			return contactRepo.getBla(cid);
		}
	};

	private final PnlContacts pnlContacts = new PnlContacts(xmppGui);

	private String myCid = RomEntryPoint.com().get("cid");

	private final ContactRepo contactRepo = new ContactRepo(myCid, xmppGui, pnlContacts.cb);

	private DlgCafe dlgCafe = null;
	protected String dlgUri;

	private Comp comp = new CompBase() {

		private boolean netOnline;

		@Override
		public void dataChanged(String cmd, String newValue) {

			switch (cmd) {
			case "netonline":
				netOnline = newValue != null;
				break;
			case "cid":
				myCid = newValue;
				contactRepo.setMyCid(myCid);
				break;
			}
		}

		@Override
		public void processNewState() {
			// if (contacts != null)
			// contacts.online(netOnline);
			if (xmppGui != null)
				xmppGui.online(netOnline);
		}

		@Override
		public CompInfo compInfo() {
			return info;
		}

		@Override
		public Object getInterface(String name) {
			if (name == null) {
				return this;
			}
			if (name.equals("gui"))
				return xmppGui;
			if (name.equals("contacts"))
				return xmppContacts;
			return null;
		}

		@Override
		public boolean handle(String cmd, JSONObject cjo) throws RunException {

			String from = cjo.optString("from");
			boolean mine = from != null && from.equals(myCid);
			RtMsgProcessor ice;

			switch (cmd) {
			case "*rt.exchange":
				String subcmd = cjo.getString("subcmd");
				ice = contactRepo.getDialogMsgProcessor(from);
				if (ice == null) {
					Sistem.errln("Unexpected rt message from:" + from + " Msg:");
					// Utils.consoleJs(cjo.getJavaScriptObject());
					return true;
				}
				if (subcmd.equals("*rt:call")) {
					ding();
					ice.rtCall();
				} else if (subcmd.equals("*rt:call_confirmed")) {
					ice.rtCallConfirmed();
				}
				// else if (subcmd.equals("b_party_declined")) {
				// handle_b_party_declined();
				// } else if (subcmd.equals("b_party_confirmation_timeout")) {
				// handle_b_party_confirmation_timeout();
				// }
				else if (subcmd.equals("*rt:endcall")) {
					ice.rtEndCall();
				} else if (subcmd.equals("*rt:relay")) {
					JSONObject val = cjo.getObject("ext");
					ice.rtRelay((com.google.gwt.json.client.JSONObject) val.ntv);
				} else {
					Sistem.errln("Unhandled message" + subcmd);
					// Utils.consoleJs(cjo.getJavaScriptObject());
				}

				break;
			case "*rt.say":
				ding();

				ice = contactRepo.getDialogMsgProcessor(from);
				if (ice == null)
					return true;

				String text = cjo.getString("text");

				ice.xmmsSay(text);

				break;
			case "*rt.presence":
				String presence = cjo.getString("visible");
				int code = cjo.getInt("code");
				Sistem.outln("Presence code:" + code + " " + presence);
				contactRepo.getMsgProc().xmmsPresence(from, code, presence);
				break;
			case "*dlg.exists":
				dlgUri = cjo.getString("dlg");
				dlgCafe = new DlgCafe(dlgUri);
				contactRepo.addCb(dlgCafe.getCb());
				DialogsDao.join(dlgUri, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						contactRepo.fillDialog(dlgUri);
					}
				});
				break;

			case "*dlg.toggle":
				if (!dlgCafe.isShowing()) {
					dlgCafe.show();
				} else {
					dlgCafe.hide();
				}
				break;
			case "*dlg.join":
				String dlg = cjo.getString("dlg");
				Sistem.outln("Dlg join dlg:" + dlg);
				if (!dlg.equals(dlgUri))
					return true;

				contactRepo.getMsgProc().dlgJoin(from, mine);
				break;
			case "*dlg.say":
				Sistem.outln("Dlg say:");
				dlg = cjo.getString("dlg");
				if (!dlg.equals(dlgUri))
					return true;

				text = cjo.getString("str");

				contactRepo.getMsgProc().dlgSay(from, mine, text);
				break;
			case "*dlg.photo":
				Sistem.outln("Dlg photo:");
				dlg = cjo.getString("dlg");
				if (!dlg.equals(dlgUri))
					return true;

				text = cjo.getString("str");

				contactRepo.getMsgProc().dlgPhoto(from, mine, text);
				break;

			case "*dlg.pushtotalk":
				dlg = cjo.getString("dlg");
				if (!dlg.equals(dlgUri))
					return true;

				text = cjo.getString("str");

				MediaProfile p = MediaProfile.talkOnly();
				p.playAudio(text);

				contactRepo.getMsgProc().dlgTalking(from, mine);

				break;

			// case "*dlg.video":
			// Sistem.outln("Dlg photo:");
			// dlg = cjo.getString("dlg");
			// if (!dlg.equals(dlgUri))
			// return true;
			// boolean frame = false;
			// String src = cjo.optString("src");
			// if (src != null) {
			// frame = src.indexOf("youtube.com") >= 0;
			// if (frame) {
			// String yid = getVideoId(src);
			// src = Location.getProtocol() + "//www.youtube.com/embed/" + yid +
			// "?rel=0&autoplay=1";
			// }
			//
			// }
			//
			// String docmd = cjo.optString("do");
			// if (docmd == null) {
			// docmd = "play";
			// }
			//
			// contactRepo.getMsgProc().dlgVideo(from, mine, src, docmd);
			//
			// break;

			case "*dlg.req":
				if (dlgCafe == null)
					return true;
				String req = cjo.getString("reqcmd");
				String optstr = cjo.optString("str");
				dlgCafe.postReq(req, optstr);

				break;

			case "*dlg.tvimg":
				if (dlgCafe == null)
					return true;
				text = cjo.getString("str");

				contactRepo.getMsgProc().dlgTvImg(from, mine, text);

				break;

			case "*dlg.tvvideo":
				if (dlgCafe == null)
					return true;
				text = cjo.getString("str");

				contactRepo.getMsgProc().dlgTvVideo(from, mine, text);

				break;

			case "*dlg.tvvideoctrl":
				if (dlgCafe == null)
					return true;
				text = cjo.getString("str");
				Integer secs = null;
				try {
					secs = Integer.parseInt(text);
				} catch (Exception e) {
				}

				contactRepo.getMsgProc().dlgTvVideoCtrl(from, mine, secs, text);

				break;

			case "*dlg.tvmark":
				if (dlgCafe == null)
					return true;
				text = cjo.getString("str");
				String[] pnts = text.split(",");

				contactRepo.getMsgProc().dlgTvMark(from, mine, Integer.parseInt(pnts[0]), Integer.parseInt(pnts[1]));

				break;

			case "*dlg.tvtext":
				if (dlgCafe == null)
					return true;
				text = cjo.getString("str");

				contactRepo.getMsgProc().dlgTvText(from, mine, text);
				break;
			case "*dlg.tvheader":
				if (dlgCafe == null)
					return true;
				text = cjo.getString("str");

				contactRepo.getMsgProc().dlgTvHeader(from, mine, text);
				break;

			case "*dlg.tvshow":
				if (dlgCafe == null)
					return true;
				char t = cjo.getString("str").charAt(0);

				contactRepo.getMsgProc().dlgTvShow(from, mine, t);
				break;

			case "*window.closing":
				if (dlgUri != null) {
					DialogsDao.leave(dlgUri, new BooleanResponse() {
					});
				}
				break;
			case "*wnd.ctrlbegin":
				if (dlgCafe == null)
					return true;
				dlgCafe.startSpeak();
				break;
			case "*wnd.ctrlend":
				if (dlgCafe == null)
					return true;
				dlgCafe.endSpeak();
				break;
			case "*dlg.presence":
				if (dlgCafe == null)
					return true;
				text = cjo.getString("str");
				contactRepo.getMsgProc().dlgPresence(from, mine, text);
				break;
			}

			return true;

		}

	};

	public Messaging() {
		Button btnCloseDlg = new Button("close");
		btnCloseDlg.setStyleName("site-closebutton");
		btnCloseDlg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hideAllDlgs();
				Messaging.this.hide();
			}

			private void hideAllDlgs() {
			}
		});

		Button btnSettings = new Button("settings");
		btnSettings.setStyleName("site-settings");
		btnSettings.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new DlgMyStatus(event.getClientX(), event.getClientY(), Messaging.this);
			}
		});

		final VerticalPanel vp = new VerticalPanel();
		vp.add(pnlContacts);
		vp.add(btnCloseDlg);
		vp.add(btnSettings);

		this.setWidget(vp);
		this.addStyleName("site-chatdlg");
		this.getElement().getStyle().setZIndex(ActionBar.enarka);
		updateStatusInfo();
		this.setModal(false);
		this.setAutoHideEnabled(false);
		this.setPopupPosition(Window.getClientWidth() - Messaging.FirstRigth, Messaging.FirstTop);

		contactRepo.fillOnlines();

	}

	public void updateStatusInfo() {
		int code = RomEntryPoint.com().optInt("xmpp.presence", Presence.FREE);
		this.setHTML(ActionBarDlg.getDlgCaption(Presence.img(code), "Messaging"));
		this.setTitle("Messaging");
	}

	public static int getDlgLeft() {

		// int k = chatDlgs.keySet().size() + 1;
		// int left = Window.getClientWidth() - (k * 220);
		return (int) (400 + (Math.random() * 500));

	}

	public static int getDlgTop() {
		return Window.getClientHeight() - 400;
	}

	protected void ding() {
		Audio au = Audio.createIfSupported();
		au.setSrc("/_public/sounds/incoming.mp3");
		au.play();
	}

	public static String getVideoId(String val) {
		String id = val;
		if (val.indexOf("youtube") < 0)
			return id;

		if (val.indexOf("watch") > 0) {
			if (val.indexOf("&") > 0)
				id = val.substring(val.indexOf("v=") + 2, val.indexOf("&"));
			else
				id = val.substring(val.indexOf("v=") + 2);

		} else if (val.indexOf("embed") > 0) {

			id = val.substring(val.lastIndexOf("/") + 1);

		}

		return id;
	}

}
