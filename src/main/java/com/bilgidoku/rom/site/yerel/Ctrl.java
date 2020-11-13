package com.bilgidoku.rom.site.yerel;

import java.util.Set;

import com.bilgidoku.rom.gwt.araci.client.site.Info;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.browse.image.SearchDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.site.yerel.comment.TabComments;
import com.bilgidoku.rom.site.yerel.constants.OneTrans;
import com.bilgidoku.rom.site.yerel.links.TabLink;
import com.bilgidoku.rom.site.yerel.lists.TabList;
import com.bilgidoku.rom.site.yerel.medias.TabMedia;
import com.bilgidoku.rom.site.yerel.styles.Style;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class Ctrl {
	public static Ctrl two = new Ctrl();
	public final static OneTrans trans = GWT.create(OneTrans.class);
	private final DockLayoutPanel left = new DockLayoutPanel(Unit.PX);

	private NavTabs navTabs;
	private TabbedPanel tabs;
	private Info info;
	private Set<Style> copyStyle = null;
	private JSONObject infoJson;
	public boolean mobileMode = false;

	private Ctrl() {

	}

	public static boolean isMultiLang() {
		if (Ctrl.info().langcodes.length > 1)
			return true;
		return false;
	}

	public static Ctrl two() {
		return two;
	}

	public static void copy(Set<Style> incoming) {
		two.copyStyle = incoming;
	}

	public static Set<Style> paste() {
		return two.copyStyle;
	}

	public static void clearCache() {
		two.copyStyle = null;
	}

	public static String infoLang() {
		return two.info.langcodes[0];
	}

	public static Info info() {
		return two.info;
	}

	public static JSONObject objInfo() {
		return two.infoJson;
	}

	public static void setInfoObj(JSONObject infoJson) {
		two.infoJson = infoJson;
	}

	public static void setInfo(Info info) {
		two.info = info;
	}

	public SplitLayoutPanel ui() {
		SplitLayoutPanel holder = new SplitLayoutPanel(10);
		holder.setStyleName("holder");
		this.tabs = new TabbedPanel();
		this.navTabs = new NavTabs();

		left.add(navTabs);

		holder.addWest(left, 290);
		holder.add(tabs);

		return holder;

	}

	public static void openTab(String id, String named, Widget theTab, String color) {
		if (two.mobileMode) {
			LayoutPanel lp = (LayoutPanel) RomEntryPoint.one.rootPanelGetWidget(0);
			lp.setWidgetLeftWidth(two.left, 0, Unit.PX, 0, Unit.PX);
			lp.setWidgetLeftRight(two.tabs, 0, Unit.PX, 0, Unit.PX);
		}

		two.tabs.openTab(id, named, theTab, color);

	}

	public static void closeTab(String id) {
		two.tabs.closeTab(id);
	}

	public static void tabClosed() {
		if (two.mobileMode) {
			if (two.tabs.getTabs().getWidgetCount() <= 0) {
				// tüm tablar kapatılmışsa navigasyonu göster
				LayoutPanel lp = (LayoutPanel) RomEntryPoint.one.rootPanelGetWidget(0);
				lp.setWidgetLeftRight(two.left, 0, Unit.PX, 0, Unit.PX);
				lp.setWidgetRightWidth(two.tabs, 0, Unit.PX, 0, Unit.PX);
			}
		}
	}

	public static void closeAndOpenTab(String id, String named, Widget theTab, String color) {
		two.tabs.closeAndOpenTab(id, named, theTab, color);
	}

	public static void setStatus(String stat) {
		RomEntryPoint.one.setStatus(stat);
		RomEntryPoint.com().post("*wnd.status", "str", stat);
	}

	public static void startWaiting() {
		DOM.getElementById("spinner").getStyle().setDisplay(Display.BLOCK);
		Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
			@Override
			public boolean execute() {
				DOM.getElementById("spinner").getStyle().setDisplay(Display.NONE);
				return false;
			}
		}, 1500);
	}

	public static void stopWaiting() {
		// do nothing
		/*
		 * Element e = DOM.getElementById("spinner"); e.setAttribute("style",
		 * "display:ntwo");
		 */
	}

	public static String getUriDateFix() {
		return "d=" + ClientUtil.getNow();
	}

	public static void reloadUsers() {
		two.navTabs.reloadUsers();
	}

	public static void gotoAdmin() {
		two.navTabs.gotoAdmin();
	}

	public static void gotoContent() {
		two.navTabs.gotoContent();
	}

	public static void gotoMail() {
		two.navTabs.gotoMail();
		// refreshHelpPers();
	}

	public static void gotoDesign() {
		two.navTabs.gotoApplication();
		// refreshHelpPers();
	}
	
	public static void gotoHouse() {
		two.navTabs.gotoHouse();
		// refreshHelpPers();
	}

	public static void editHome() {
		two.navTabs.getNavWriting().getToolbar().editItem("/");
	}

	public static void editPage(String uri) {
		two.navTabs.getNavWriting().getToolbar().editItem(uri);
	}

	public static void setError(RunException e) {
		Sistem.printStackTrace(e);
		// RomEntryPoint.one.getTop().setError(e.getMessage());
	}

	public static void setError(String e) {
		RomEntryPoint.com().post("*wnd.error", "str", e);
	}

	public static void reloadMailNav() {
		two.navTabs.reloadMailFolders();

	}

	public static void focusSearch() {
		new SearchDlg();
	}

	public static void focusList(String uri) {
		TabList tw = new TabList(uri);
		Ctrl.openTab(uri, ClientUtil.getTitleFromUri(uri), tw, Data.CONTENT_COLOR);
		gotoContent();
		two.navTabs.selectPageTab();
	}

	public static void focusLink(String uri) {
		TabLink tw = new TabLink(uri);
		Ctrl.openTab(uri, ClientUtil.getTitleFromUri(uri), tw, Data.CONTENT_COLOR);

		gotoContent();
		two.navTabs.selectLinkTab();
	}

	public static void focusPageContainer(String uri) {
		gotoContent();
		two.navTabs.selectPageTab();
	}

	public static void focusIssues() {
		gotoMail();
		two.navTabs.selectIssueTab();
	}

	public static void focusMedia(String uri) {
		TabMedia tw = new TabMedia(uri);
		Ctrl.openTab(uri, ClientUtil.getTitleFromUri(uri), tw, Data.CONTENT_COLOR);

		gotoContent();
		two.navTabs.selectFileTab();
	}

	public static void focusWaitingComments(String uri) {
		String[] params = uri.split("!!");

		String topic = params[1];
		if (topic.length() > 15)
			topic = topic.substring(0, 15);

		topic = Ctrl.trans.commentsWaiting() + ":" + topic;

		TabComments tw = new TabComments(params[0], topic, "waitingapproval");
		Ctrl.openTab("waitingapproval" + uri, topic, tw, Data.DEFAULT_COLOR);

		gotoAdmin();

	}

	public static void focusAllComments(String uri) {
		String[] params = uri.split("!!");
		String topic = params[1];
		if (topic.length() > 15)
			topic = topic.substring(0, 15);

		topic = Ctrl.trans.comments() + ":" + topic;

		TabComments tw = new TabComments(params[0], topic, "all");
		Ctrl.openTab("all" + uri, topic, tw, Data.DEFAULT_COLOR);

		gotoAdmin();
	}

	public static void initHelp() {
		// addToHelp(RomEntryPoint.one.getTop()., null);
		// addToHelp(two.perspective.getPerspective(), null);
		// addToHelp(two.perspective.getPersButtons(), null);
		//
		// two.navTabs.getTabPanel().addSelectionHandler(new
		// SelectionHandler<Integer>() {
		// @Override
		// public void onSelection(SelectionEvent<Integer> event) {
		// int ind = event.getSelectedItem();
		// Widget w = two.navTabs.getTabPanel().getWidget(ind);
		// two.top.help.clearNavToolbar();
		// addToHelp(w, "navtoolbar");
		//
		// }
		// });
		//
		// two.tabs.getTabs().addBeforeSelectionHandler(new
		// BeforeSelectionHandler<Integer>() {
		// @Override
		// public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
		// Integer ind = (Integer) event.getItem();
		// Widget currTab = two.tabs.getTabs().getWidget(ind);
		//// two.top.help.clearTabToolbar();
		// addToHelp(currTab, "tabtoolbar");
		//
		// }
		// });

	}

	public static void addToHelp(Widget f, String type) {
		// List<Widget> found = new ArrayList<Widget>();
		// ClientUtil.findButtons(f, found);
		// if (found.size() > 0) {
		// for (int i = 0; i < found.size(); i++) {
		// Widget wdt = found.get(i);
		// if (wdt != null && wdt instanceof SiteToolbarButton &&
		// wdt.isVisible()) {
		// SiteToolbarButton btn = (SiteToolbarButton) wdt;
		// if (f instanceof ActionBarOne) {
		// two.top.help.addToTopVisibles(btn.getHelpy());
		// } else if (f instanceof SwitchPerspective) {
		// two.top.help.addToPerspVisibles(btn.getHelpy());
		// } else if (f instanceof PerspectiveButtons) {
		// two.top.help.addToPerspBtnsVisibles(btn.getHelpy());
		// } else if (type.equals("tabtoolbar")) {
		// two.top.help.addToTabTbVisibles(btn.getHelpy());
		// } else if (type.equals("navtoolbar")) {
		// two.top.help.addToNavTbVisibles(btn.getHelpy());
		// } else if (type.equals("navtabs")) {
		// two.top.help.addToNavVisibles(btn.getHelpy());
		// }
		//
		// }
		// }
		// }
	}

	private static void refreshHelpPers() {
		// two.top.help.clearPerBut();
		// addToHelp(two.perspective.getPersButtons(), null);
		//
		// // nav tab headers
		// two.top.help.clearNav();

		for (int i = 0; i < two.navTabs.getTabPanel().getWidgetCount(); i++) {
			Widget w = two.navTabs.getTabPanel().getTabWidget(i);
			addToHelp(w, "navtabs");
		}

	}

	public static void reloadIssuesNav() {
		two.navTabs.navIssues.filterAgain();

	}

	public static void activateTagChart() {
		two.navTabs.navTags.activateChart();

	}

	public static void focusInbox() {
		Ctrl.gotoMail();
	}

	public static void toggleMobile() {
		LayoutPanel lp = (LayoutPanel) RomEntryPoint.one.rootPanelGetWidget(0);
		if (!two.mobileMode) {
			two.mobileMode = true;
			lp.setWidgetLeftRight(two.left, 0, Unit.PX, 0, Unit.PX);
			lp.setWidgetRightWidth(two.tabs, 0, Unit.PX, 0, Unit.PX);
		} else {
			two.mobileMode = false;
			lp.setWidgetLeftWidth(two.left, 0, Unit.PX, 270, Unit.PX);
			lp.setWidgetLeftRight(two.tabs, 270, Unit.PX, 0, Unit.PX);
		}

	}

	public static void gotoNotifications() {
		gotoMail();
		two.navTabs.selectNotificationTab();
	}

}
