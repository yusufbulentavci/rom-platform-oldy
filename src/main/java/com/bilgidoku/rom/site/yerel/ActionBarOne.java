package com.bilgidoku.rom.site.yerel;

import java.util.Iterator;
import java.util.Map;

import com.bilgidoku.rom.gwt.araci.client.rom.Waiting;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.gwt.client.util.common.ActionBar;
import com.bilgidoku.rom.gwt.client.util.common.TopButton;
import com.bilgidoku.rom.gwt.client.util.common.TopCb;
import com.bilgidoku.rom.site.yerel.exam.PnlExams;
import com.bilgidoku.rom.site.yerel.perspective.SwitchPerspective;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

public class ActionBarOne extends ActionBar implements HasWidgets {
	public static final CompInfo info = new CompInfo("+actionbar", 500,
			new String[] { "isauth", "cid", "wndwaiting", "wndloading" }, new String[] { "_wndtop", "+topwindow" },
			new String[] {});

	public final static CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return new ActionBarOne().comp;
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

		public Object getInterface(String name) {
			return ActionBarOne.this;
		}
	};

	int viewMode;

	private final TopButton btnPlay = new TopButton("/_local/images/play_white.png", "", Ctrl.trans.play(), "");
	private final Image onImg = new Image("/_local/images/desktop_white.png");
	private final Image offImg = new Image("/_local/images/mobile_white.png");
	private final ToggleButton mobileMode = new ToggleButton(offImg, onImg);
	private final SwitchPerspective perspective = new SwitchPerspective();

	public ActionBarOne() {
		onImg.setTitle(Ctrl.trans.switchToDesktopMode());
		offImg.setTitle(Ctrl.trans.switchToMobileMode());

		mobileMode.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		mobileMode.getElement().getStyle().setBorderWidth(0, Unit.PX);
		mobileMode.getElement().getStyle().setProperty("background", "transparent");
		mobileMode.getElement().getStyle().setProperty("padding", "1px 4px !important");

		forPlay();
		forMenu(mobileMode);


	}

	@Override
	protected void initExtension() {
		addToButtons(perspective);
		addToButtons(mobileMode);
		addToButtons(btnPlay);
		perspective.update();
	}

	protected void gotoFirstPerspective() {
		perspective.selectMailPerspective();
	}	
	
	public void addToButtons(Widget wdt) {
		extension.add(wdt);
	}

	private void forMenu(ToggleButton mobileMode) {
		mobileMode.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Ctrl.toggleMobile();
			}
		});
	}

	protected TopCb createTopCb() {
		return new TopCb() {

			@Override
			public void gotoMessaging() {
				// chatPnl.show();
				// Window.alert("show messaging");
			}

			@Override
			public void gotoMails() {
				Ctrl.reloadMailNav();
			}

			@Override
			public void gotoIssue(String uri) {
			}

			@Override
			public void gotoMyInfos(String[] mine) {
			}

			@Override
			public void gotoNotifications() {			
				Ctrl.gotoNotifications();
			}
		};
	}

	private void forPlay() {
		btnPlay.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("/", "_blank", "");
			}
		});
	}

	@Override
	public void add(Widget w) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<Widget> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Widget w) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeButton(Widget btn) {
		extension.remove(btn);
	}

}
