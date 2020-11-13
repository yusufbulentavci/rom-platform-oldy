package com.bilgidoku.rom.gwt.client.util.common;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.Waiting;
import com.bilgidoku.rom.gwt.araci.client.rom.WaitingDao;
import com.bilgidoku.rom.gwt.araci.client.rom.WaitingResponse;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.chat.im.XmppGui;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.gwt.client.util.ecommerce.Ecommerce;
import com.bilgidoku.rom.gwt.client.util.help.HelpScene;
import com.bilgidoku.rom.gwt.client.util.panels.StatusBar;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ActionBar extends Composite {

	public static final CompInfo info = new CompInfo("+actionbar", 500,
			new String[] { "isauth", "cid", "wndwaiting", "wndloading", "*waiting.changed", "*dgl.exists" },
			new String[] { "_wndtop", "+topwindow" }, new String[] {});

	public static final CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return new ActionBar().comp;
		}

	};

	Comp comp = new CompBase() {

		private boolean userContact;
		private String userCid;

		@Override
		public CompInfo compInfo() {
			return info;
		}

		@Override
		public void dataChanged(String key, String val) {

			switch (key) {
			case "isauth":
				userContact = (val != null && val.equals("1"));
				break;
			case "cid":
				userCid = val;
				break;

			default:
				break;
			}
		}

		@Override
		public void processNewState() {
			if (userContact) {
				switch (viewMode) {
				case 1:
					// closeLogOutView();
				case 0:
					loggedInView();
					break;
				default:
					break;
				}
			} else if (userCid == null) {
				switch (viewMode) {
				case 2:
					// closeLogInView();
				case 0:
					loggedOutView();
					break;
				default:
					break;
				}
			} else if (userCid != null) {
				hasCidView();
			}

		}

		@Override
		public boolean handle(String cmd, com.bilgidoku.rom.shared.json.JSONObject cjo) throws RunException {
			switch (cmd) {
			case "*dlg.exists":
				String dlg = cjo.getString("dlg");
				enableCafe(dlg);
				break;
			case "*waiting.changed":
				processWaiting(cjo);
				break;
			}

			return false;
		}

		public Object getInterface(String name) {
			return ActionBar.this;
		}

	};

	public static int enarka = 1000000;
	public static int enarkaUstu = 1000001;
	public static int enarkaUstu2 = 1000002;

	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);

	// Messaging chatPnl;
	public HelpScene help = new HelpScene();

	private String cafeDlgUri;

	private final StatusBar statusBar = StatusBar.getOne();
	private final FlowPanel rightSide = new FlowPanel();
	public final FlowPanel extension = new FlowPanel();
	private final WaitingButton btnIssues = new WaitingButton("issue", "/_public/images/bar/iss_white.png");
	private final WaitingButton btnMyMails = new WaitingButton("mail", "/_public/images/bar/mail_white.png");
	private final TopButton btnCafe = new TopButton("/_public/images/bar/cafe_white.png", null, trans.cafe(), null);
	private final TimesButton btnCart = new TimesButton("/_public/images/bar/sepet_white.png");
	private final TopButton btnLogout = new TopButton("/_public/images/bar/logout_white.png", null, trans.logout(),
			"arama.mp4");
	private final TopButton btnChat = new TopButton("/_public/images/bar/chat_white.png", null, trans.chat(),
			"arama.mp4");
	private final TopButton btnPref = new TopButton("/_public/images/bar/pref_white.png", null, trans.preferences(),
			null);
	private final TopButton btnLoginFace = new TopButton("/_public/images/bar/facebook_white.png", null,
			trans.loginFacebook(), "arama.mp4");
	private final TopButton btnLogin = new TopButton("/_public/images/bar/login_white.png", null, trans.login(),
			"arama.mp4");
	private final HTML pipe = new HTML(
			"<span style='font-weight:bolder; color:white;padding: 0 2px; font-size:16px;'>|</span>");

	private final HTML usrLabel = new HTML();

	Widget[] btnsLoggedIn = { usrLabel, btnMyMails, btnIssues, btnCart, pipe, btnChat, btnCafe, btnPref, btnLogout };
	Widget[] btnsHasCid = { btnCart, btnLogin, btnLoginFace };
	Widget[] btnsLoggedOut = { btnLogin, btnLoginFace };

	// final RtUi rtUi = new RtUi() {
	//
	// @Override
	// public void photo(String cid, String dataUrl) {
	// if (dlgCafe == null)
	// return;
	// dlgCafe.photo(cid, dataUrl);
	// }
	//
	// };

	private int viewMode = 0;

	protected ActionBar() {
		btnCafe.setVisible(false);
		btnMyMails.setVisible(false);
		btnIssues.setVisible(false);
		btnCart.setVisible(false);
		usrLabel.setStyleName("site-tabheader-default-selected");
		statusBar.setStyleName("site-statusbar");
		statusBar.setVisible(false);

		forLogin();
		forLoginFace();
		forLogout();
		forChat();
		forPref();
		forMails();
		forIssues();
		forShowCart();
		forCafe();

		FlowPanel holder = new FlowPanel();
		holder.setWidth("960px");
		holder.getElement().getStyle().setProperty("margin", "0 auto");

		rightSide.getElement().getStyle().setFloat(Float.RIGHT);
		extension.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		usrLabel.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		pipe.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);

		// if (getLogo()!=null)
		// holder.add(getLogo());

		holder.add(extension);
		holder.add(statusBar);
		holder.add(rightSide);

		SimplePanel sp = new SimplePanel();
		sp.setStyleName("site-actionbar");
		sp.add(holder);
		sp.setWidth("100%");

		initWidget(sp);
		getElement().getStyle().setZIndex(ActionBar.enarkaUstu);
		getElement().getStyle().setPosition(Position.FIXED);

		RomEntryPoint.one.addToRootPanel(this, 0, 0);
		// hasCidView();
	}

	
	protected void processWaiting(com.bilgidoku.rom.shared.json.JSONObject cjo) {
		try {
			String app = cjo.getString("app");

			String code = cjo.getString("code");
			String[] inref = cjo.getStringArray("inref");
			int times = cjo.getInt("times");

			final Waiting w = new Waiting();
			String cid = RomEntryPoint.com().get("cid");
			w.uri = "/_/waiting" + cid + "/" + app + "/" + code;
			w.app = app;
			w.code = code;
			w.inref = inref;
			w.times = times;
			processWaiting(false, w);

		} catch (RunException e) {
			Sistem.printStackTrace(e);
		}
	}

	public void processWaiting(final boolean initial, Waiting w) {
		if (w.app.equals("issue")) {
			btnIssues.waiting(w);
		} else if (w.app.equals("mail")) {
			btnMyMails.waiting(w);
		} else if (w.app.equals("dialogs")) {
			// if (w.code.equals("comment")) {
			// if (cafeDlgUri != null) {
			// CommentsDao.get(w.inref[0], new CommentsResponse() {
			// @Override
			// public void ready(Comments value) {
			// if (dlgCafe != null) {
			// if (value.cmd != null && value.cmd.getValue() != null) {
			// JSONObject jo = value.cmd.getValue().isObject();
			// try {
			// CommandRepo.one().getCommandRt(jo.get("cmd").isString().stringValue()).exec(
			// initial, null, value.contact, null, rtUi,
			// new com.bilgidoku.rom.shared.json.JSONObject(jo));
			// } catch (RunException e) {
			// Sistem.printStackTrace(e);
			// }
			// } else {
			// dlgCafe.printSaid(value.ownercid, value.comment, "incoming");
			// }
			// }
			// }
			// });
			//
			// }
			// }
		}
	}

	public void enableCafe(String cafeDlgUri) {
		if (this.cafeDlgUri != null)
			return;
		this.cafeDlgUri = cafeDlgUri;
		btnCafe.setVisible(true);
		toggleCafe();
	}

	protected void hasCidView() {
		Sistem.outln("HASCID");
		viewMode = 3;
		rightSide.clear();
		for (int i = 0; i < btnsHasCid.length; i++) {
			rightSide.add(btnsHasCid[i]);
		}

	}

	protected void loggedOutView() {
		viewMode = 1;
		rightSide.clear();
		extension.clear();
		for (int i = 0; i < btnsLoggedOut.length; i++) {
			rightSide.add(btnsLoggedOut[i]);
		}
	}

	protected void loggedInView() {
		Sistem.outln("LOGIN");
		viewMode = 2;
		String userName = RomEntryPoint.com().get("user");
		usrLabel.setHTML(userName);
		Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
			@Override
			public boolean execute() {
				getWaitingMine();
				return false;
			}
		}, 1000);

		rightSide.clear();
		extension.clear();
		for (int i = 0; i < btnsLoggedIn.length; i++) {
			rightSide.add(btnsLoggedIn[i]);
		}
		initExtension();
	}

	protected void initExtension() {

	}

	private void forShowCart() {
		btnCart.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Ecommerce e = (Ecommerce) RomEntryPoint.cm().comp("+ecommerce", null);
				e.showCart();
			}
		});
	}

	private void forIssues() {
		btnIssues.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RomEntryPoint.one.getTopCb().gotoNotifications();
				btnIssues.clear();
			}
		});
	}

	private void forMails() {
		btnMyMails.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				btnMyMails.gotIt();
				RomEntryPoint.one.getTopCb().gotoMails();
			}
		});
	}

	private void forCafe() {
		btnCafe.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				toggleCafe();
			}
		});
	}

	private void forLoginFace() {
		btnLoginFace.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RomEntryPoint.com().post("*userneed", "mode", "contact", "auther", "fb");
			}
		});
	}

	private void forLogin() {
		btnLogin.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RomEntryPoint.com().post("*userneed", "mode", "contact");
			}
		});

	}

	private void forPref() {
		btnPref.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int btnx = event.getClientX();

				DlgPrefs dlg = new DlgPrefs();
				dlg.setPopupPosition(btnx - 200, 24);
			}
		});
	}

	public void getWaitingMine() {
		WaitingDao.list("/_/waiting", new WaitingResponse() {
			@Override
			public void array(List<Waiting> myarr) {
				for (int i = 0; i < myarr.size(); i++) {
					Waiting myWall = myarr.get(i);
					processWaiting(true, myWall);
				}
			}
		});

	}

	private void forChat() {
		btnChat.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				XmppGui gui = (XmppGui) RomEntryPoint.cm().comp("+xmpp", "gui");
				if (gui != null)
					gui.showContacts();
				RomEntryPoint.one.getTopCb().gotoMessaging();
			}
		});
	}

	private void forLogout() {
		btnLogout.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RomEntryPoint.com().post("*userneed", "mode", "logout");
			}
		});
	}

	public void setStatus(String stat) {
		Window.scrollTo(0, 0);
		statusBar.setStatus(stat);
	}

	public void setError(String stat) {
		Window.scrollTo(0, 0);
		statusBar.setError(stat);
	}

	class DlgPrefs extends ActionBarDlg {
		public DlgPrefs() {
			super(trans.preferences(), null, null);
			run();
			this.show();
		}

		@Override
		public Widget ui() {
			String curLocale = LocaleInfo.getCurrentLocale().getLocaleName();

			FlexTable ft = new FlexTable();
			ft.setStyleName("site-chatdlgin");
			ft.setText(0, 0, "Version");
			ft.setText(0, 1, RomEntryPoint.one.getVersion());
			ft.setText(1, 0, trans.language(""));
			ft.setWidget(1, 1, new SwitchLocale(curLocale));

			ft.setText(2, 0, trans.clientWidth());
			ft.setText(2, 1, Window.getClientWidth() + "");

			JSONObject ua = RomEntryPoint.userAgentObj();

			if (ua != null) {
				ft.setText(3, 0, "User agent");
				ft.setText(3, 1,
						ua.get("name").isString().stringValue() + " Version:" + ua.get("ver").isString().stringValue());
			}

			return ft;
		}

		@Override
		public void cancel() {
		}

		@Override
		public void ok() {
		}

	}

	private void toggleCafe() {
		Sistem.outln("CAFFEEE");
		RomEntryPoint.com().post("*dlg.toggle");
	}

	public void cartChanged(int itemCount, boolean showDlg) {
		Window.scrollTo(0, 0);

		if (showDlg)
			statusBar.setStatus("Sepetiniz güncellendi.");

		btnCart.setTimes(itemCount);

		if (!showDlg)
			return;
		final Ecommerce e = (Ecommerce) RomEntryPoint.cm().comp("+ecommerce", null);
		if (e.isShowing())
			return;

		e.showCart();
		Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
			@Override
			public boolean execute() {
				e.hideCart();
				return false;
			}
		}, 5000);

	}

	public void startWaiting(String text) {
		Window.scrollTo(0, 0);
		statusBar.setStatus(text
				+ "&nbsp;&nbsp;<img src='/_public/images/bar/progressbar.gif' style='vertical-align:bottom;'></img>");
	}

	public void showLoading() {
		DOM.getElementById("loading").getStyle().setDisplay(Display.BLOCK);
		Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
			@Override
			public boolean execute() {
				DOM.getElementById("loading").getStyle().setDisplay(Display.NONE);
				return false;
			}
		}, 5000);
	}

	public void hideLoading() {
		DOM.getElementById("loading").getStyle().setDisplay(Display.NONE);
	}

	private class DlgIssues extends ActionBarDlg {

		public DlgIssues() {
			super("Waiting Issues", null, null);
			run();
			show();
			center();
		}

		@Override
		public Widget ui() {

			Map<String, Waiting> waitings = btnIssues.getWaitings();
			VerticalPanel vp = new VerticalPanel();

			Set<String> keySet = waitings.keySet();
			for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				Waiting w = waitings.get(key);
				final String[] inref = w.inref;
				for (int i = 0; i < inref.length; i++) {
					final String ref = inref[i];

					Anchor a = new Anchor(ref);
					String text = "";
					if (w.title != null && w.title[i] != null)
						text = w.title[i];

					if (w.username != null && w.username[i] != null)
						text = text + ">>" + w.username[i];

					a.setText(text);
					a.setTitle(ref);
					a.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							RomEntryPoint.one.getTopCb().gotoIssue(ref);
						}
					});
					vp.add(a);
				}

			}

			btnIssues.gotIt();
			return vp;
		}

		@Override
		public void cancel() {
			// TODO Auto-generated method stub

		}

		@Override
		public void ok() {
			// TODO Auto-generated method stub

		}

	}

	protected HTML getLogo() {
		// if (RomEntryPoint.one.getAp().equals(ApDict.One) ||
		// RomEntryPoint.one.getAp().equals(ApDict.Boxer)) {
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		sb.appendHtmlConstant("" + "<img src='/_local/images/hill.png' style='height:18px;padding-left:7px;'/>");

		HTML logo = new HTML(sb.toSafeHtml());
		logo.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		logo.setTitle(RomEntryPoint.one.name);
		logo.setHeight("22px");
		logo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.replace("/");

			}
		});
		return logo;
		// }
		// return null;

	}

	// help butonlarının çalışması ile ilintili idi
	// @Override
	// public void add(Widget w) {
	// holder.add(w);
	// }
	//
	// @Override
	// public void clear() {
	// holder.clear();
	// }
	//
	// @Override
	// public Iterator<Widget> iterator() {
	// return holder.iterator();
	// }
	//
	// @Override
	// public boolean remove(Widget w) {
	// return holder.remove(w);
	// }

}
