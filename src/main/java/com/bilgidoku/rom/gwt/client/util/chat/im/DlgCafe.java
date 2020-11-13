package com.bilgidoku.rom.gwt.client.util.chat.im;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.chat.im.repo.CbContacts;
import com.bilgidoku.rom.gwt.client.util.chat.im.repo.ContactBla;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.MediaReady;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.PushToTalkButton;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.TakePhotoPanel;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteToolbarButton;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

interface DlgCmdSender {
	void postCommand(String cmd, String text, JSONObject more);
}

public class DlgCafe extends ActionBarDlg implements DlgCmdSender {

	class PeoplePanel extends Composite implements CbContacts {

		/// _static/img/etc/tv.jpeg

		private final TakePhotoPanel takePhoto;

		private final ContactCell conCell = new ContactCell();
		private final CellList<ContactBla> conList = new CellList<ContactBla>(conCell);
		final ListDataProvider<ContactBla> dataProvider = new ListDataProvider<ContactBla>();
		// private final Image img = new
		// Image("/_public/images/grandstaff.jpg");

		public PeoplePanel() {
			// img.setSize("75px", "75px");

			takePhoto = new TakePhotoPanel(new MediaReady() {

				@Override
				public void mediaReady(String dataUrl) {
					postCommand("*dlg.photo", dataUrl, null);
				}
			});

			RadioButton notReady = new RadioButton("ready", "Not Ready");
			RadioButton ready = new RadioButton("ready", "Ready");
			notReady.setValue(true);

			notReady.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					postCommand("*dlg.presence", "away", null);
				}
			});

			ready.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					postCommand("*dlg.presence", "ready", null);
				}
			});

			conList.setWidth("100%");
			conList.setStyleName("row");

			FlowPanel fp = new FlowPanel();
			fp.add(takePhoto);
			fp.add(notReady);
			fp.add(ready);
			fp.add(conList);

			dataProvider.addDataDisplay(conList);

			initWidget(fp);

			Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {

				@Override
				public boolean execute() {
					conList.redraw();
					return true;
				}
			}, 1500);
		}

		private static final String yellowImg = "src='/_public/images/presence/away.png' alt='Not ready'";
		private static final String redImg = "src='/_public/images/presence/offline.png'";
		private static final String greenImg = "src='/_public/images/presence/online.png' alt='Ready'";
		private static final String whiteImg = "src='/_public/images/presence/white.png'";

		private class ContactCell extends AbstractCell<ContactBla> {
			@Override
			public void render(Context context, ContactBla row, SafeHtmlBuilder sb) {
				if (row == null) {
					return;
				}
				long now = System.currentTimeMillis();
				String imgUrl = row.getImgUrl();
				String name = row.getContactName();

				// String status = row.getPresenceImg();
				sb.appendHtmlConstant("<div style='float:left;font-size:smaller;text-align:center;'>"
						+ "<img style='width:45px;height:45px;border:1px solid #d2d2d2; padding:1px; margin:1px;' src='"
						+ (imgUrl == null || imgUrl.isEmpty() ? "/_public/images/contact.gif" : imgUrl) + "' alt='"
						+ name + "'></img><br>" + "<img width='10' height='10' " + (row.isBusy(now) ? redImg : whiteImg)
						+ "/>" + "<img width='10' height='10' " + getDlgPrImg(row.getDlgPresence()) + "/>"
						+ ClientUtil.maxNChars(name, 10) + " </div>");

				setTitle(name);

			}

			private String getDlgPrImg(String dlgPresence) {
				switch (dlgPresence) {
				case "online":
					return yellowImg;
				case "ready":
					return greenImg;
				}
				return redImg + " alt='Busy'";
			}

		}

		@Override
		public void contactAdded(ContactBla cba) {

		}

		@Override
		public void contactRemove(ContactBla vla) {

		}

		@Override
		public void resetList() {
			dataProvider.getList().clear();
			conList.redraw();
		}

		@Override
		public void presenceChanged(ContactBla param) {
			conList.redraw();
		}

		@Override
		public void dlgSay(ContactBla param, boolean mine, String text) {
			printSaid(param, mine, text);
			conList.redraw();
		}

		@Override
		public void dlgJoin(ContactBla param, boolean mine) {

			if (mycid.equals(param.contactUri()))
				return;

			if (dataProvider.getList().contains(param))
				return;

			dataProvider.getList().add(param);
			conList.redraw();

			// dataProvider.getList().remove(vla);
			// conList.redraw();

		}

		@Override
		public void dlgContactPhotoChanged(ContactBla param, boolean mine) {
			conList.redraw();
		}

		@Override
		public void dlgVideo(ContactBla param, boolean mine, String src, String docmd) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dlgTvImg(ContactBla param, boolean mine, String src) {
			tv.showImage(src);
			DlgCafe.this.center();
		}

		@Override
		public void dlgTvVideo(ContactBla param, boolean mine, String src) {
			tv.showVideo(src);
			tvCtrl.newVideo();
			DlgCafe.this.show();
			DlgCafe.this.center();
		}

		@Override
		public void dlgTvMark(ContactBla param, boolean mine, int markx, int marky) {
			tv.mark(markx, marky);
			DlgCafe.this.show();
			DlgCafe.this.center();
		}

		@Override
		public void dlgTvVideoCtrl(ContactBla param, boolean mine, Integer secs, String ctrl) {
			tvCtrl.doit(secs, ctrl);
			tv.videoCtrl(secs, ctrl);
		}

		@Override
		public void dlgTvHeader(ContactBla param, boolean mine, String str) {
			tv.showHeader(str);
			DlgCafe.this.show();
			DlgCafe.this.center();

		}

		@Override
		public void dlgTvText(ContactBla param, boolean mine, String str) {
			tv.showText(str);
			DlgCafe.this.show();
			DlgCafe.this.center();

		}

		@Override
		public void dlgTvShow(ContactBla param, boolean mine, char str) {
			Sistem.outln("TVSHOW:" + str);
			tv.show(str);
		}

		@Override
		public void dlgTvPresence(ContactBla param, boolean mine, String text) {
			// TODO Auto-generated method stub

		}

	}

	// private final static ApplicationConstants trans =
	// GWT.create(ApplicationConstants.class);

	private final Tv tv;
	private final TvCtrl tvCtrl;

	private final FlowPanel pnlChatHistory = new FlowPanel();
	private final ScrollPanel chatScroll = new ScrollPanel();
	private final TextArea txtChatMsg = new TextArea();

	private final SiteToolbarButton btnSend = new SiteToolbarButton("/_public/images/bar/send_mail.png", "Send", "Send",
			"");
	// private final TopButton btnSendfile = new
	// TopButton("/_public/images/bar/attachment.png", "", "Send File", "");s
	// private final SiteToolbarButton btnPlayVideo = new
	// SiteToolbarButton("/_public/images/bar/video.png", "", "Play Video", "");
	private final PeoplePanel pp = new PeoplePanel();
	private final SiteToolbarButton btnExam = new SiteToolbarButton("/_public/images/bar/pencil.png", "Exam", "exam",
			"");

	private final PushToTalkButton btnPushToTalk = new PushToTalkButton(new MediaReady() {
		@Override
		public void mediaReady(String dataUrl) {
			pushToTalk(dataUrl);
		}
	});

	private final FlowPanel messagingHolder = new FlowPanel();

	private final String dlgUri;
	private final String mycid;

	public DlgCafe(String dlgUri) {
		super(null, null, null);
		this.dlgUri = dlgUri;
		mycid = RomEntryPoint.com().get("cid");
		this.tv = new Tv(this);
		this.tvCtrl = new TvCtrl(tv, this);
		this.tv.setVideoFb(tvCtrl);

		forSend();
		// forPlayVideo();
		forEnterPress();
		forExam();

		run();
		this.show();
		this.center();
		this.getElement().getStyle().setPosition(Position.FIXED);
	}

	private void forExam() {
		btnExam.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("/_public/exam.html?exam=1", "_blank", "");
			}
		});

	}

	protected void pushToTalk(String dataUrl) {
		postCommand("*dlg.pushtotalk", dataUrl, null);
	}

	protected void hideChat() {
		Sistem.outln("Hide chat");
		DlgCafe.this.setVisible(false);
	}

	public void showChat() {
		Sistem.outln("Show chat");
		DlgCafe.this.setVisible(true);
	}

	// private void forPlayVideo() {
	// btnPlayVideo.addClickHandler(new ClickHandler() {
	// @Override
	// public void onClick(ClickEvent event) {
	// String ref = Window.prompt("Enter video url:", "");
	// if (ref == null || ref.length() == 0) {
	// return;
	// }
	// JSONObject more = new JSONObject();
	// more.put("src", new JSONString("ref"));
	// postCommand("*dlg.video", "play", more);
	// }
	// });
	// }

	private void forEnterPress() {
		txtChatMsg.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					postSay();
				}
			}
		});
	}

	private void forSend() {
		btnSend.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				postSay();
			}
		});
	}

	protected void postSay() {
		String text = txtChatMsg.getValue();

		txtChatMsg.setText("");
		postCommand("*dlg.say", text, null);

	}

	void postReq(String cmd, String text) {
		postCommand(cmd, text, null);
	}

	@Override
	public void postCommand(String cmd, String text, JSONObject more) {
		Sistem.outln("Cafe post command:" + text.length());
		DialogsDao.cmd(cmd, text, more == null ? null : new Json(more), this.dlgUri, new BooleanResponse() {
		});

	}

	// protected interface ChatMsgTemplate extends SafeHtmlTemplates {
	// @Template("<div style='position:relative' class='site-chat-{0}'> " +
	// "<div class='site-arrow-{0}'></div>"
	// + "<div class='site-chat-body'><span
	// style='font-size:smaller;font-weight:bold;'>{2}</span><br>{1}</div>"
	// + "</div>")
	// SafeHtml generate(String type, String text, String contId);
	// }
	//
	// private final ChatMsgTemplate chatMsgTemplate = (ChatMsgTemplate)
	// GWT.create(ChatMsgTemplate.class);

	/**
	 * 
	 * type "incoming", "sent"
	 * 
	 * @param mine
	 * 
	 * @param contId
	 * @param value
	 * @param type
	 */
	public void printSaid(final ContactBla bla, boolean mine, String txt) {
		final String type = mine ? "sent" : "incoming";

		StringBuilder sb = new StringBuilder();
		sb.append("<div style='position:relative' class='site-chat-");
		sb.append(type);
		sb.append("}'> ");
		sb.append("<div class='site-arrow-");
		sb.append(type);
		sb.append("'></div>");
		sb.append("<div class='site-chat-body'><span style='font-size:smaller;font-weight:bold;'>");
		sb.append(bla.getContactName());
		sb.append("</span><br>");
		sb.append(txt);
		sb.append("</div>");

		txt = AnchorAdder.addAnchor(txt);

		// HTML msg = new HTML(chatMsgTemplate.generate(type, txt,
		// bla.getContactName()));
		HTML msg = new HTML(sb.toString());
		msg.setStyleName("site-chatline");
		pnlChatHistory.add(msg);
		chatScroll.scrollToBottom();

	}

	public void call() {
		ClientUtil.startWaiting();
		// showVideoUi("http://www.w3schools.com/html/mov_bbb.mp4");
		// cb.weCall();
	}

	private void hangUp() {
		DlgCafe.this.hide();
		// cb.weHangUp();
	}

	public void photo(String cid, String dataUrl) {
		// pp.photo(cid, dataUrl);

	}

	@Override
	public Widget ui() {
		uiMessaging();

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(tv);
		hp.add(gui());
		return hp;
	}

	@Override
	public void cancel() {
		hangUp();

	}

	@Override
	public void ok() {

	}

	private Widget gui() {

		VerticalPanel hp = new VerticalPanel();
		hp.setStyleName("site-chatdlgin");
		hp.add(pp);
		hp.add(messagingHolder);
		hp.add(tvCtrl);

		return hp;
	}

	private void uiMessaging() {
		txtChatMsg.setSize("96%", "40px");

		chatScroll.add(pnlChatHistory);
		// chatScroll.setWidth("99%");
		chatScroll.setStyleName("site-chat-history");
		chatScroll.setHeight("290px");
		chatScroll.setWidth("100%");

		Widget[] btns = new Widget[] { btnSend, btnPushToTalk, btnExam };
		messagingHolder.add(chatScroll);
		messagingHolder.add(txtChatMsg);
		messagingHolder.add(ClientUtil.getToolbar(btns, 5));
	}

	public CbContacts getCb() {
		return pp;
	}

	public void startSpeak() {
		btnPushToTalk.startSpeak();

	}

	public void endSpeak() {
		btnPushToTalk.endSpeak();
	}

}
