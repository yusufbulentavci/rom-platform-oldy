package com.bilgidoku.rom.gwt.client.util.chat;

import java.util.List;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.chat.im.XmppContacts;
import com.bilgidoku.rom.gwt.client.util.chat.im.repo.ContactBla;
import com.bilgidoku.rom.gwt.client.util.common.ActionBar;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.TopButton;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.util.AsyncMethodNoParam;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.media.client.Video;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DlgChat extends DialogBox {

	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);

	private final Button btnClose = new Button("Close");
	private final Button btnMin = new Button("Min");
	private final Button btnMax = new Button("Max");
	private final FlowPanel pnlChatHistory = new FlowPanel();
	private final ScrollPanel chatScroll = new ScrollPanel();
	private final TextArea txtChatMsg = new TextArea();

	private final TopButton btnSend = new TopButton("/_public/images/bar/send_mail.png", "", "Send", "");
	private final TopButton btnSendfile = new TopButton("/_public/images/bar/attachment.png", "", "Send File",
			"");
	private final TopButton btnVideoCall = new TopButton("/_public/images/bar/video.png", "", "Video Call", "");


	
	private final VerticalPanel videoHolder = new VerticalPanel();
	private final FlowPanel messagingHolder = new FlowPanel();
	private static String smallDlgHeight = "266px";
	private static String bigDlgHeight = "600px";

	private String contactId;

	private final CbChat cb;

	private final ContactBla contactInfo;
	
	private String remoteVideoUrl;


	private String localVideoUrl;

	public DlgChat(CbChat cb, String cid) {

		this.cb = cb;
		XmppContacts contacts=(XmppContacts) RomEntryPoint.cm().comp("+xmpp", "contacts");
		this.contactInfo = contacts.getContactInfo(cid);
		this.contactId = contactInfo.contactUri();

		buildHistory(contactInfo.getChatHistory());
		forSend();
		forSendfile();
		forVideoCall();
		forEnterPress();
		forClose();
		forMin();
		forMax();

		btnClose.setStyleName("site-closebutton");
		btnMin.setStyleName("site-minimize");
		btnMax.setStyleName("site-maximize");

		messagingHolder.setHeight(smallDlgHeight);
		
		FlowPanel fp = new FlowPanel();
		fp.add(ui());
		fp.add(btnMin);
		fp.add(btnMax);
		fp.add(btnClose);

		btnMin.setVisible(false);

		this.setHTML(ActionBarDlg.getDlgCaption(null, contactInfo.getContactName()));
		this.setTitle(contactInfo.getContactName());
		this.addStyleName("site-chatdlg");
		this.getElement().getStyle().setZIndex(ActionBar.enarka);
		this.setWidget(fp);
		this.setAutoHideEnabled(false);
		this.setModal(false);
		this.hide();

	}

	public void showPopUp(int left, int top) {
		posLeft = left;
		posTop = top;
		this.setPopupPosition(left, top);
		this.show();
	}

	private void forMin() {
		btnMin.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Sistem.outln("Minimize chat");
//				removeVideo();
				DlgChat.this.setPopupPosition(posLeft, posTop);
			}
		});

	}

	protected void hideChat() {
		Sistem.outln("Hide chat");
//		removeVideo();
		DlgChat.this.setVisible(false);
	}

	public void showChat() {
		Sistem.outln("Show chat");
		DlgChat.this.setVisible(true);
	}


	protected void showVideoUi() {
		

//		try {
//			if (videoHolder.getWidget() != null)
//				videoHolder.clear();
//		} catch (Exception e) {
//			Sistem.printStackTrace(e);
//		}
//		
//		
//		messagingHolder.setHeight(smallDlgHeight);
//		btnMax.setVisible(true);
//		btnMin.setVisible(false);
//		btnVideoCall.setVisible(true);
//		messagingHolder.removeStyleName("site-dlgchat-video-messages");
//		
		Sistem.outln("ShowVideoUi");
		btnMax.setVisible(false);
		btnMin.setVisible(true);
		btnVideoCall.setVisible(false);
		
		messagingHolder.addStyleName("site-dlgchat-video-messages");
		messagingHolder.setHeight(bigDlgHeight);

		posLeft = DlgChat.this.getPopupLeft();
		posTop = DlgChat.this.getPopupTop();

//		if (videoHolder.getWidget() != null) {
//		}

		videoHolder.clear();
		
		
		if(remoteVideoUrl!=null){
			Sistem.outln("remote:"+remoteVideoUrl);
			Video vd = Video.createIfSupported();
			vd.setControls(false);
			vd.setWidth("800px");
			vd.setSrc(remoteVideoUrl);
			vd.setAutoplay(true);

			videoHolder.add(vd);	
		}
		
		if(localVideoUrl!=null){
			Sistem.outln("local:"+localVideoUrl);
			Video vd = Video.createIfSupported();
			vd.setControls(false);
			vd.setWidth("150px");
			vd.setSrc(localVideoUrl);
			vd.setAutoplay(true);
			
			vd.getElement().getStyle().setPosition(Position.ABSOLUTE);
			vd.getElement().getStyle().setTop(39, Unit.PX);

			videoHolder.add(vd);
		}
		
		
//		HTML ht1 = new HTML("<img src='/_local/images/templates/contactus.png' width='500px' style='border:2px solid black;'/>");
//		videoHolder.add(ht1);
//
//		HTML ht2 = new HTML("<img src='/_local/images/templates/detail_1.png' width='150px' style='border:2px solid red;'/>");
//		ht2.getElement().getStyle().setPosition(Position.ABSOLUTE);
//		ht2.getElement().getStyle().setTop(39, Unit.PX);
//		videoHolder.add(ht2);
		
		DlgChat.this.center();

	}

	private void forMax() {
		btnMax.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				call();
			}
		});
	}

	private void forVideoCall() {
		btnVideoCall.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				call();
			}
		});
	}

	private void forSendfile() {
		btnSendfile.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new DlgFileSend();
			}
		});
	}

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

		RtPoster.one().postSay(contactId, text, new AsyncMethodNoParam() {

			@Override
			public void run() {
				sendMessage(contactId, txtChatMsg.getValue());
			}

			@Override
			public void error() {
//				Xmpp.gui.notice("Remote user is offline.");
			}
		});

	}

	private void forClose() {
		btnClose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hangUp();
			}
		});

	}

	private void buildHistory(List<String> chatHistory) {
		if (chatHistory == null)
			return;
		for (String msg : chatHistory) {
			pnlChatHistory.add(new HTML(msg));
		}

	}

	protected interface ChatMsgTemplate extends SafeHtmlTemplates {
		@Template("<div style='position:relative' class='site-chat-{0}'> " + "<div class='site-arrow-{0}'></div>"
				+ "<div class='site-chat-body'>{1}</div>" + "</div>")
		SafeHtml generate(String type, String text);
	}

	private ChatMsgTemplate chatMsgTemplate = (ChatMsgTemplate) GWT.create(ChatMsgTemplate.class);
	private int posLeft;
	private int posTop;

	private void sendMessage(String contId, String value) {
		if (txtChatMsg.getValue().trim().isEmpty())
			return;

		printSaid(contId, value, "sent");
		txtChatMsg.setValue("");
	}

	private void printSaid(String contId, String value, String type) {
		HTML msg = new HTML(chatMsgTemplate.generate(type, value));
		msg.setStyleName("site-chatline");
		pnlChatHistory.add(msg);
		chatScroll.scrollToBottom();
		// add to history
		// tabMessaging.appendToChatHistory(contId, msg.getHTML());
	}

//	private void printAnnounce(String contId, String value, String type) {
//		HTML msg = new HTML(chatMsgTemplate.generate(type, value));
//		msg.setStyleName("site-chatline");
//		pnlChatHistory.add(msg);
//		chatScroll.scrollToBottom();
//		// add to history
//		// tabMessaging.appendToChatHistory(contId, msg.getHTML());
//	}

	private Widget ui() {
		txtChatMsg.setSize("180px", "40px");

		chatScroll.add(pnlChatHistory);
//		chatScroll.setWidth("99%");
		chatScroll.setStyleName("site-chat-history");

		btnSend.setHeight("24px");
		btnSendfile.setHeight("24px");
		btnVideoCall.setHeight("24px");

		Widget[] btns = new Widget[] { btnSend, btnSendfile, btnVideoCall };

		messagingHolder.setStyleName("site-dlgchat");
		messagingHolder.add(chatScroll);
		messagingHolder.add(txtChatMsg);
		messagingHolder.add(ClientUtil.getToolbar(btns, 4));

		videoHolder.setStyleName("site-dlgchat-video");

		HorizontalPanel holder = new HorizontalPanel();
		holder.setStyleName("site-chatdlgin");
		holder.add(videoHolder);
		holder.add(messagingHolder);

		return holder;
	}

	private class DlgFileSend extends ActionBarDlg {

		public DlgFileSend() {
			super(trans.sendFile(), null, null);
			run();
			this.show();
			this.center();
		}

		private Widget getFileUploadForm() {

			final FormPanel formPanel = new FormPanel();
			formPanel.setAction("/_sesfuncs/rtdlgboardmime");
			formPanel.setMethod(FormPanel.METHOD_POST);
			formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);

			FileUpload fileUpload = new FileUpload();
			fileUpload.setName("msg");
			fileUpload.setWidth("250px");
			
			TopButton btnSendFile = new TopButton("/_public/images/bar/send_mail.png", trans.upload(), "", "");
			btnSendFile.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					formPanel.submit();
				}
			});

			Button btnCloseDlg = new Button("close");
			btnCloseDlg.setStyleName("site-closebutton");
			btnCloseDlg.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DlgFileSend.this.hide();
				}
			});

			Hidden hidden = new Hidden("outform");
			hidden.setValue("json");

			VerticalPanel f = new VerticalPanel();
			f.setSpacing(5);
			f.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			f.add(fileUpload);
			f.add(btnSendFile);
			f.add(hidden);
			f.add(btnCloseDlg);

			formPanel.add(f);
			return formPanel;
		}

		@Override
		public Widget ui() {
			return getFileUploadForm();
		}

		@Override
		public void cancel() {
		}

		@Override
		public void ok() {
		}

	}

	public void call() {

		ClientUtil.startWaiting();
		// showVideoUi("http://www.w3schools.com/html/mov_bbb.mp4");

		cb.weCall();

		// rtCall.call();

		
	}

	private void hangUp() {
		DlgChat.this.hide();
		cb.weHangUp();
	}

	public IDlgChat getDlgChatImpl() {
		return dlgChatImpl;
	}

	private final IDlgChat dlgChatImpl = new DlgChatImpl();

	private class DlgChatImpl implements IDlgChat {

		@Override
		public void said(String text) {
			printSaid(contactId, text, "incoming");
		}

		@Override
		public void remoteVideoSetSrc(String mediaBlobUrl) {
			
			remoteVideoUrl=mediaBlobUrl;
			
			showVideoUi();
		}

		@Override
		public void callTerminated() {
			hideChat();

		}

		@Override
		public void show() {
			DlgChat.this.show();
		}

		@Override
		public void setPresence(int code, String online) {
			printSaid(contactId, "Status:" + online, "incoming");
		}

		@Override
		public void localVideoSetSrc(String mediaBlobUrl) {
			localVideoUrl=mediaBlobUrl;
			

			showVideoUi();
		}

	}
	
	public void setRemoteVideoUrl(String remoteVideoUrl) {
		this.remoteVideoUrl = remoteVideoUrl;
		showVideoUi();
	}

	public void setLocalVideoUrl(String localVideoUrl) {
		this.localVideoUrl = localVideoUrl;
		showVideoUi();
	}

}
