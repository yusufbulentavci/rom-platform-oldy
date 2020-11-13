package com.bilgidoku.rom.site.yerel;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.Role;
import com.bilgidoku.rom.gwt.client.util.help.Helpy;
import com.bilgidoku.rom.gwt.client.util.help.NeedHelp;
import com.bilgidoku.rom.site.yerel.admin.NavUsers;
import com.bilgidoku.rom.site.yerel.apps.NavApps;
import com.bilgidoku.rom.site.yerel.contacts.NavCon;
import com.bilgidoku.rom.site.yerel.households.NavHouseHolds;
import com.bilgidoku.rom.site.yerel.issues.NavIssues;
import com.bilgidoku.rom.site.yerel.issues.NavNotifs;
import com.bilgidoku.rom.site.yerel.issues.NavTasks;
import com.bilgidoku.rom.site.yerel.links.NavLinks;
import com.bilgidoku.rom.site.yerel.lists.NavLists;
import com.bilgidoku.rom.site.yerel.mail.NavMailBox;
import com.bilgidoku.rom.site.yerel.medias.NavFiles;
import com.bilgidoku.rom.site.yerel.stock.NavStock;
import com.bilgidoku.rom.site.yerel.styles.NavStyles;
import com.bilgidoku.rom.site.yerel.tags.NavTags;
import com.bilgidoku.rom.site.yerel.wgts.NavApp;
import com.bilgidoku.rom.site.yerel.writings.NavWriting;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class NavTabs extends ResizeComposite implements NeedHelp {
	public final NavMailBox navMail = new NavMailBox("inbox");
	private final NavCon navContacts = new NavCon();
	public final NavIssues navIssues = new NavIssues();
	private NavUsers navUsers = null;
	public NavTags navTags = null;
	private NavWriting navWritings = null;
	private NavLists navLists = null;
	private NavLinks navLinks = null;
	private NavFiles navFiles = null;
	private NavStyles navStyle = null;
	private NavApps navQuery = null;
	private NavApp navWgt = null;
	private NavStock navStocks = null;
	private NavNotifs navNotifs = new NavNotifs();
	private NavTasks navTasks = new NavTasks();
	private NavHouseHolds navHouseHolds = new NavHouseHolds();
	

	private final TabLayoutPanel tabPanel = new TabLayoutPanel(29, Unit.PX);

	public NavTabs() {

		if (Role.isAdmin()) {
			// admin pers tabs
			navUsers = new NavUsers();
			navStocks = new NavStock();
		}

		//if (Role.isDesigner()) {
			navStyle = new NavStyles();
			navWgt = new NavApp();
		//}

		navQuery = new NavApps();
		navTags = new NavTags();
		navWritings = new NavWriting();
		navLists = new NavLists();
		navLinks = new NavLinks();
		navFiles = new NavFiles();
		navHouseHolds = new NavHouseHolds();

		getTabPanel().setWidth("100%");

		forSelectTab();
		this.initWidget(getTabPanel());

	}

	private void forSelectTab() {
		getTabPanel().addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Integer ind = (Integer) event.getSelectedItem();
				HasContainer seen = (HasContainer) getTabPanel().getWidget(ind);
				seen.addContainers();
			}
		});

	}

	public NavFiles getFileTab() {
		return navFiles;
	}

	public void selectFileTab() {
		getTabPanel().selectTab(navFiles);
		navFiles.addContainers();
	}

	public void selectListTab() {
		getTabPanel().selectTab(navLists);
	}

	public void selectLinkTab() {
		getTabPanel().selectTab(navLinks);
	}

	public void selectPageTab() {
		getTabPanel().selectTab(navWritings);
	}

	public void gotoContent() {
		getTabPanel().clear();
		addWritings(Data.CONTENT_COLOR);
		addFileTab(Data.CONTENT_COLOR);
		addLists(Data.CONTENT_COLOR);
		addLinks(Data.CONTENT_COLOR);
		addTags(Data.CONTENT_COLOR);
	}

	public void gotoApplication() {
		getTabPanel().clear();
		addStyle(Data.DESIGN_COLOR);
		addWgt(Data.DESIGN_COLOR);
	}

	public void gotoMail() {
		getTabPanel().clear();
		addMailClient(Data.MAIL_COLOR);
		addContacts(Data.MAIL_COLOR);
		addIssues(Data.MAIL_COLOR);
		addTasks(Data.MAIL_COLOR);
		addNotifs(Data.MAIL_COLOR);
	}


	public void gotoAdmin() {
		getTabPanel().clear();
		addUsers(Data.HOME_COLOR);
		addStock();
		addHouseHold();
		addQuery(Data.HOME_COLOR);
	}
	
	public void gotoHouse() {
		getTabPanel().clear();
		
	}

	private void addStock() {
		getTabPanel().add(this.navStocks, ClientUtil.getTabTitle("Stocks", Data.HOME_COLOR, null));
	}
	
	private void addHouseHold() {
		getTabPanel().add(this.navHouseHolds, ClientUtil.getTabTitle("HouseHolds", Data.HOME_COLOR, null));
	}

	private void addTags(String color) {
		getTabPanel().add(this.navTags, ClientUtil.getTabTitle(Ctrl.trans.tags(), color, null));
	}

	private void addUsers(String color) {
		getTabPanel().add(this.navUsers, ClientUtil.getTabTitle(Ctrl.trans.users(), color, null));
	}

	private void addWritings(String color) {
		if (navWritings == null)
			return;
		getTabPanel().add(this.navWritings, ClientUtil.getTabTitle(Ctrl.trans.writing(), color, "sayfa-toolbar.mp4"));
	}

	private void addMailClient(String mailColor) {
		if (navMail == null)
			return;
		Widget title = ClientUtil.getTabTitle(Ctrl.trans.eMail(), mailColor, null);
		getTabPanel().add(this.navMail, title);
	}

	private void addLists(String color) {
		if (navLists == null)
			return;
		getTabPanel().add(navLists, ClientUtil.getTabTitle(Ctrl.trans.lists(), color, "liste_genel.mp4"));
	}

	private void addLinks(String color) {
		if (navLinks == null)
			return;
		getTabPanel().add(this.navLinks, ClientUtil.getTabTitle(Ctrl.trans.link(), color, null));
	}

	private void addFileTab(String color) {
		if (navFiles == null)
			return;
		getTabPanel().add(this.navFiles, ClientUtil.getTabTitle(Ctrl.trans.files(), color, "dosya-genel.mp4"));
	}

	private void addWgt(String color) {
		if (navWgt == null)
			return;
		getTabPanel().add(this.navWgt, ClientUtil.getTabTitle(Ctrl.trans.layout(), color, null));
	}

	private void addStyle(String color) {
		if (navStyle == null)
			return;
		getTabPanel().add(this.navStyle, ClientUtil.getTabTitle(Ctrl.trans.style(), color, null));
	}
	
	private void addQuery(String color) {
		getTabPanel().add(this.navQuery, ClientUtil.getTabTitle(Ctrl.trans.query(), color, null));
	}

	private void addContacts(String color) {
		if (navContacts == null)
			return;
		getTabPanel().add(this.navContacts, ClientUtil.getTabTitle(Ctrl.trans.contacts(), color, null));
	}

	private void addIssues(String color) {
		if (navIssues == null)
			return;
		getTabPanel().add(this.navIssues, ClientUtil.getTabTitle(Ctrl.trans.issues(), color, null));
	}

	public NavLists getNavLists() {
		return navLists;
	}

	public NavWriting getNavWriting() {
		return navWritings;
	}

	public void reloadUsers() {
		navUsers.addContainers();
	}

	public void reloadMailFolders() {
		navMail.reload();
	}

	@Override
	public Helpy[] helpies() {
		return null;
	}

	public TabLayoutPanel getTabPanel() {
		return tabPanel;
	}

	public void selectIssueTab() {
		getTabPanel().selectTab(navIssues);
	}

	public void selectNotificationTab() {
		getTabPanel().selectTab(navNotifs);
	}
	private void addNotifs(String color) {
		getTabPanel().add(this.navNotifs, ClientUtil.getTabTitle(Ctrl.trans.notifications(), color, null));
	}

	public void selectTasksTab() {
		getTabPanel().selectTab(navTasks);
	}
	private void addTasks(String color) {
		getTabPanel().add(this.navTasks, ClientUtil.getTabTitle(Ctrl.trans.tasks(), color, null));
	}

	


}
