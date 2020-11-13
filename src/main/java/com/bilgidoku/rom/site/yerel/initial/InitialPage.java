package com.bilgidoku.rom.site.yerel.initial;

import java.util.Collection;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.SwitchLang;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.constants.InitialConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class InitialPage {
	public boolean isfirstRun = true; 
	private final InitialConstants con = GWT.create(InitialConstants.class);
	private final FlexTable bottom = new FlexTable();
	private final ContentLangHandler lang = new ContentLangHandler(this);
	private SectorHandler sector = new SectorHandler(this);
	private DomainNameHandler domain = new DomainNameHandler(this);
	private FlexTable form = new FlexTable();
	private PageHandler2 page = new PageHandler2(this);
	private MenuHandler main = new MenuHandler(this, MenuHandler.MENU_MAIN, con.mainMenu(), con.mainMenuDesc());
	private MenuHandler footer = new MenuHandler(this, MenuHandler.MENU_FOOTER, con.footerMenu(), con.footerMenuDesc());
	private MenuHandler vitrine = new MenuHandler(this, MenuHandler.MENU_VITRINE, con.vitrine(), con.vitrineDesc());
	private SaveHandler save = new SaveHandler(this);
	private DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
	private final String userName; 

	public InitialPage(String userName) {
		this.userName = userName;
		setSupportedLangs();
		ui();		
		RomEntryPoint.one.addToRootPanel(dock);
	}
	
	public void step1() {
		form.getWidget(1, 0).setVisible(false);
		form.getWidget(1, 1).setVisible(false);
		form.getWidget(2, 0).setVisible(false);
		form.getWidget(2, 1).setVisible(false);
		bottom.setVisible(false);
	}

	public void step2() {
		form.getWidget(1, 0).setVisible(true);
		form.getWidget(1, 1).setVisible(true);
		lang.getLangList().setSelectedIndex(0);
	}

	public void step3() {
		form.getWidget(2, 0).setVisible(true);
		form.getWidget(2, 1).setVisible(true);		
	}
	
	public void step4() {
		bottom.setVisible(true);
	}

	
	public void selectFirstPage() {
		page.initialState();
	}
	
	private void setSupportedLangs() {
		//getAvailableLocaleNames: gwt.xml dosyasından alınır
		lang.getLangList().addItem("", "");
		String[] localeNames = LocaleInfo.getAvailableLocaleNames();
		for (String localeName : localeNames) {
			if (!localeName.equals("default")) {
				String nativeName = LocaleInfo.getLocaleNativeDisplayName(localeName);
				lang.getLangList().addItem(nativeName, localeName);
			}
		}		
	}

	public void loadDatas() {
		save.load(this);		
	}

	public void langChanged() {
		this.domain.closeAddressPanel();
		this.sector.setContentLang(lang.getLang());
		this.page.setContentLang(lang.getLang());
	}

	public void setLang(String site_lang) {
		lang.setLang(site_lang);
		langChanged();
	}

	public void setSector(String sector2) {
		sector.setSector(sector2);
	}
	
	public void emptyPageTree() {
		this.page.emptyPageTree();
	}
	
	public void domainNameChanged() {
		//step2();

		//boolean has = domain.getDomainBox().getValue() != null;
		//this.main.setState(has);
		//this.footer.setState(has);
		//this.vitrine.setState(has);
		//this.save.setState(has);
	}

	public void setDomainName(String domain_name) {
		domain.setDomainName(domain_name);
		domainNameChanged();
	}

	public Panel getPanel() {
		return dock;
	}

	public void addPage(String name, String uri, boolean menu, boolean footer, boolean vitrine, String app, String group) {
		this.page.addNewPage(name, uri, menu, footer, vitrine, app, group);
	}

	public void addGroup(String name, String uri, boolean menu, boolean footer, boolean vitrine, String app) {
		this.page.addGetPageGroup(name, uri, menu, footer, vitrine, app);
	}

	public void groupChanged(String oldRealUri, Details det) {
		uriChanged(det.getRealUri());
		menuApplyChangeToAll(oldRealUri, det);
	}

	public void pageChanged(String oldRealUri, String oldUri, Details det) {
		uriChanged(det.getRealUri());
		menuApplyChangeToAll(oldRealUri, det);
	}

	public void uriChanged(String uri) {
		domain.uriChanged(uri);
	}

	private void menuApplyChangeToAll(String oldRealUri, Details det) {
		menuApplyChange(main, det.menu, oldRealUri, det.getRealUri());
		menuApplyChange(footer, det.footer, oldRealUri, det.getRealUri());
		menuApplyChange(vitrine, det.vitrine, oldRealUri, det.getRealUri());
	}

	private void menuApplyChange(MenuHandler main2, boolean menu, String oldRealUri, String realUri) {
		if (menu) {
			if (!main2.changeUri(oldRealUri, realUri)) {
				main2.addUri(realUri);
			}
		} else {
			main2.removeUri(realUri);
		}
	}

	public void uriRemoved(String realUri) {
		domain.noSelection();
		main.removeUri(realUri);
		footer.removeUri(realUri);
		vitrine.removeUri(realUri);
	}

	public Details getSelectedDetail() {
		Details sel = page.getSelected();
		if (sel != null)
			return sel;
		return null;
	}

	public Details getDetail(String name) {
		/*
		 * Details sel = page.getDetail(name); if (sel != null) return sel;
		 * return null;
		 */
		return null;
	}

	public Collection<Details> getPages() {
		return page.getPages();
	}

	public String getLang() {
		return lang.getLang();
	}

	public String getDomainName() {
		return domain.getDomainName();
	}

	public String getSector() {
		return sector.getSector();
	}

	private void ui() {
		
		domain.getDomainBox().setValue(con.firstDomain());
		domain.getUri().setText("http://www." + con.firstDomain() + ".com");		
		sector.getSectorList().setWidth("170px");		
		sector.getExampleGroups().setSize("200px", "100px");
		sector.getExamplePages().setSize("200px", "100px");
		main.getPageList().setSize("200px", "112px");
		footer.getPageList().setSize("200px", "112px");
		vitrine.getPageList().setSize("200px", "112px");

		domain.getUri().getElement().getStyle().setFontWeight(FontWeight.BOLDER);

		// Sol ust		
		form.setSize("100%", "100%");
		form.setHTML(0, 0, "1. " + con.domainName());
		form.setWidget(0, 1, domain.getUi());
		form.setWidget(1, 0, new Label("2. " + con.contentLang()));
		form.setWidget(1, 1, lang.getLangList());
		form.setWidget(2, 0, new Label("3. " + con.sector()));
		form.setWidget(2, 1, sector.getSectorList());
		form.setWidget(2, 2, domain.getUri());
		form.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		form.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		form.getFlexCellFormatter().setColSpan(0, 1, 2);
		form.getFlexCellFormatter().setColSpan(1, 1, 2);
		form.getFlexCellFormatter().setWidth(1, 0, "150px");
		form.getElement().getStyle().setPadding(7, Unit.PX);
		
		
		// page groups
		ScrollPanel pages = new ScrollPanel(page.getPageTree());
		pages.setHeight("350px");

		Widget[] btns =  {page.getAddPageGrpBtn(), page.getAddPageBtn(), page.getUpdateBtn(), page.getDelBtn()};
		VerticalPanel pagePanel = new VerticalPanel();
		pagePanel.add(ClientUtil.getToolbar(btns, 4));
		pagePanel.add(pages);
		pagePanel.setStyleName("site-panel");

		CaptionPanel pageCaption = new CaptionPanel(con.pages());
		pageCaption.add(pagePanel);

		// main menu
		VerticalPanel v = new VerticalPanel();
		v.add(save.getSave());
		v.add(save.getFinish());
		
		bottom.setCellPadding(3);
		bottom.setWidget(0, 0, getExamples());
		bottom.setWidget(0, 1, pageCaption);
		bottom.setWidget(0, 2, getMenus());
		bottom.setWidget(0, 3, v);
		
		SiteToolbarButton btnLogout = new SiteToolbarButton("logout","Log out " + userName, "Log out");
		btnLogout.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				RomEntryPoint.com().post("*userneed", "mode", "logout");
			}
		});
		
		FlexTable f = new FlexTable();
		//f.setWidget(0, 0, getLangSwitch().getListLang());
		f.setWidget(0, 0, new SwitchLang());
		f.setWidget(0, 1, btnLogout);
		//f.setWidget(0, 2, new MenuBarTest());
		
//		FlexTable tepewebTop = new FlexTable();
//		tepewebTop.setCellPadding(0);
//		tepewebTop.setCellSpacing(0);
//		tepewebTop.getElement().getStyle().setPadding(0, Unit.EM);
//		tepewebTop.getElement().getStyle().setMargin(0, Unit.EM);
//		tepewebTop.setSize("100%", "100%");
//		tepewebTop.setWidget(0, 0, Ctrl.getLogo());
//		tepewebTop.setWidget(0, 1, f);
//		tepewebTop.setStyleName("gwt-RichTextToolbar");
//		tepewebTop.getFlexCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
//		tepewebTop.getFlexCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
		
		FlexTable h = new FlexTable();
		h.setCellPadding(0);
		h.setCellSpacing(0);
		h.setHTML(0, 0, con.initialHeader());
		h.setWidget(1, 0, form);
		h.setWidget(2, 0, bottom);
		h.setStyleName("site-loginform");
		h.getFlexCellFormatter().setStyleName(0, 0, "site-tableheader");
		
//		dock.addNorth(tepewebTop, 34);
		dock.addWest(new SimplePanel(), 150);
		dock.addEast(new SimplePanel(), 150);
		dock.addNorth(new SimplePanel(), 7);
		//dock.addSouth(new SimplePanel(), 50);
		dock.add(h);
		dock.setStyleName("site-innerform");
	}
	
	private FlexTable getExamples() {
		CaptionPanel grp = new CaptionPanel(con.exampleGroups());
		grp.add(sector.getExampleGroups());

		CaptionPanel pg = new CaptionPanel(con.examplePages());
		pg.add(sector.getExamplePages());

		FlexTable exm = new FlexTable();
		// exm.setCellPadding(10);
		exm.setWidget(0, 1, grp);
		exm.setWidget(0, 2, sector.getAddGrpBtn());
		exm.setWidget(1, 1, pg);
		exm.setWidget(1, 2, sector.getAddPageBtn());
		return exm;
	}

	private FlexTable getMenus() {
		VerticalPanel prCmds = new VerticalPanel();
		prCmds.add(main.getAdd());
		prCmds.add(main.getUp());
		prCmds.add(main.getDown());
		prCmds.add(main.getDelete());

		HorizontalPanel mainMenuPanel = new HorizontalPanel();
		mainMenuPanel.add(prCmds);
		mainMenuPanel.add(main.getPageList());
		CaptionPanel mainMenuWrapper = new CaptionPanel(con.mainMenu());
		mainMenuWrapper.setSize("92%", "100%");
		mainMenuWrapper.add(mainMenuPanel);

		//
		VerticalPanel hiliCmds = new VerticalPanel();
		hiliCmds.add(vitrine.getAdd());
		hiliCmds.add(vitrine.getUp());
		hiliCmds.add(vitrine.getDown());
		hiliCmds.add(vitrine.getDelete());
		HorizontalPanel hiliMenuPanel = new HorizontalPanel();
		hiliMenuPanel.add(hiliCmds);
		hiliMenuPanel.add(vitrine.getPageList());
		CaptionPanel hiliMenuWrapper = new CaptionPanel(con.vitrine());
		hiliMenuWrapper.setSize("92%", "100%");
		hiliMenuWrapper.add(hiliMenuPanel);

		//
		VerticalPanel secCmds = new VerticalPanel();
		secCmds.add(footer.getAdd());
		secCmds.add(footer.getUp());
		secCmds.add(footer.getDown());
		secCmds.add(footer.getDelete());
		HorizontalPanel sec = new HorizontalPanel();
		sec.add(secCmds);
		sec.add(footer.getPageList());
		CaptionPanel secMenuWrapper = new CaptionPanel(con.footerMenu());
		secMenuWrapper.setSize("92%", "100%");
		secMenuWrapper.add(sec);

		FlexTable menus = new FlexTable();
		menus.setWidget(0, 0, mainMenuWrapper);
		menus.setWidget(1, 0, hiliMenuWrapper);
		menus.setWidget(2, 0, secMenuWrapper);
		menus.getFlexCellFormatter().setColSpan(0, 0, 2);
		menus.getFlexCellFormatter().setColSpan(1, 0, 2);
		menus.getFlexCellFormatter().setColSpan(2, 0, 2);
		return menus;
	}

	public String getSelectedPageGroup() {
		return page.getSelectedPageGroup();
	}

	public void setMenuButtonStates(boolean isActive) {
		main.setState(isActive);
		footer.setState(isActive);
		vitrine.setState(isActive);		
	}

	public Boolean getIsTransfer() {
		return domain.getIsTransfer();
	}

	public String getAuthInfo() {
		return domain.getAuthInfo();
	}

	public void setContact(Json contact) {
		domain.setContact(contact);
	}

	public JSONObject getContact() {
		return domain.getContact();
	}


}
