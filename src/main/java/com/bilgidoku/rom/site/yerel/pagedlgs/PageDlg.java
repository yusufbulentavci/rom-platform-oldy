package com.bilgidoku.rom.site.yerel.pagedlgs;


import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsDao;
import com.bilgidoku.rom.gwt.araci.client.site.Writings;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.client.util.Layer;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.boxer;
import com.bilgidoku.rom.site.yerel.boxing.BoxerGui;
import com.bilgidoku.rom.site.yerel.boxing.BoxerMenu;
import com.bilgidoku.rom.site.yerel.boxing.BoxerMenuButton;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.common.content.AppPnl;
import com.bilgidoku.rom.site.yerel.common.content.HasContent;
import com.bilgidoku.rom.site.yerel.common.content.HasResource;
import com.bilgidoku.rom.site.yerel.common.content.HasTags;
import com.bilgidoku.rom.site.yerel.events.DlgClosed;
import com.bilgidoku.rom.site.yerel.events.DlgClosedHandler;
import com.bilgidoku.rom.site.yerel.subpanels.PanelManageDialog;
import com.bilgidoku.rom.site.yerel.subpanels.PnlAddToList;
import com.bilgidoku.rom.site.yerel.subpanels.PnlContent;
import com.bilgidoku.rom.site.yerel.subpanels.PnlStockMatch;
import com.bilgidoku.rom.site.yerel.subpanels.PnlTags;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class PageDlg extends BoxerMenu implements HasContent, HasResource, HasTags {

	private AppPnl resource;
	private PnlTags pnlTags;

	private PanelManageDialog pnlDialog;

	private final PnlStockMatch pnlStock = new PnlStockMatch(this);

	private final SiteToolbarButton btnContent = new SiteToolbarButton( "/_local/images/common/pencil.png", Ctrl.trans.edit(), Ctrl.trans.edit(),
			"/sayfa_ayarlari/ayarlar-duzenle.mp4");

	private final SiteToolbarButton btnSearch = new SiteToolbarButton("/_local/images/common/search_.png", Ctrl.trans.searchKeywords(), 
			Ctrl.trans.searchKeywords(), "/sayfa_ayarlari/ayarlar-anahtar.mp4");

	private final SiteToolbarButton btnAddToList = new SiteToolbarButton("/_local/images/common/list.png", Ctrl.trans.addToList(), Ctrl.trans.addToList(),
			"/sayfa_ayarlari/ayarlar-listeyeekle.mp4");

	private final SiteToolbarButton btnDialog = new SiteToolbarButton("/_local/images/common/speech_bubble.png", Ctrl.trans.comments(), Ctrl.trans.comments(),
			new String[] { "/sayfa_yorumlar/yorumlar_aktif.mp4",
					"/sayfa_yorumlar/yorumlar_onaylanmadangostermeayari.mp4", "/sayfa_yorumlar/yorumlar_oturumac.mp4",
					"/sayfa_yorumlar/yorumlar_Onaybekleyenekrani.mp4" });

	private final SiteToolbarButton btnTags = new SiteToolbarButton("/_local/images/common/tag.png", Ctrl.trans.tags(), Ctrl.trans.tags(), "");

	private final SiteToolbarButton btnStock = new SiteToolbarButton("/_local/images/common/sepet.png", Ctrl.trans.stock(), Ctrl.trans.stock(),
			"/sayfa_ayarlari/ayarlar-stok.mp4");

	private final SiteToolbarButton btnHtmlFile = new SiteToolbarButton("/_local/images/common/structure.png", Ctrl.trans.structure(), Ctrl.trans.structure(), "");

	private final SiteToolbarButton btnRelatedItems = new SiteToolbarButton("/_local/images/common/page.png", Ctrl.trans.relatedItems(), Ctrl.trans.relatedItems(),
			"");

	private final ListBox listHtmlfiles = new ListBox();

	private Widget[] pageHeaders = { btnContent, btnSearch, btnAddToList, btnDialog, btnTags, btnStock, btnHtmlFile };

	public StackLayoutPanel pageStack = new StackLayoutPanel(Unit.PX);

	private PnlAddToList pnlAddToList;
	private PnlContent pnlContent = new PnlContent(this, false, true, false);
	private TextArea txtSearchKeys = new TextArea();

	private boolean tagsChanged = false;
	private boolean tipChanged = false;
	private boolean htmlFileChanged = false;
	
	private BoxerGui parent;

	public PageDlg(final BoxerGui boxerGui) {
		super(Ctrl.trans.pageSettings(), Layer.layer1);

		this.parent = boxerGui;

		loadHtmlFiles();
		forSearchKeywords();
		forChangeListHtmlFiles();

		pnlTags = new PnlTags(this);
		pnlDialog = new PanelManageDialog(this);
		pnlAddToList = new PnlAddToList();

		this.add(getPageStack());


	}

	private void forChangeListHtmlFiles() {
		listHtmlfiles.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				listHtmlfiles.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						RomEntryPoint.one.setStatus(Ctrl.trans.saveAndReload());
						htmlFileChanged = true;
					}
				});
			}
		});

	}

	public void loadData(Writings writing) {

		ClientUtil.findAndSelect(listHtmlfiles, writing.html_file);

		resource = new AppPnl(this);

		pnlContent.load(writing);

		// these panels are language independent
		pnlTags.loadData(writing.tags);
		pnlDialog.load(writing.uri, writing.title[0], writing.dialog_uri);
		pnlAddToList.load(writing.uri);
		resource.selectApp(writing.html_file);

//		pnlRelatedItems.loadData(writing.carray, writing.uri);
		pnlStock.selectStock(writing.stock_uri, writing.uri);

	}

	private void loadHtmlFiles() {
		listHtmlfiles.addItem(Ctrl.trans.standard(), "w:standart");
		listHtmlfiles.addItem(Ctrl.trans.noHeaderFooter(), "w:noheaderfooter");
		listHtmlfiles.addItem(Ctrl.trans.noHeader(), "w:noheader");
		listHtmlfiles.addItem(Ctrl.trans.noFooter(), "w:nofooter");
		// listHtmlfiles.addItem(Ctrl.trans.noSpot(), "w:nospot");
		// listHtmlfiles.addItem(Ctrl.trans.onlyBody(), "w:onlybody");
		// listHtmlfiles.addItem(Ctrl.trans.onlySpot(), "w:onlyspot");
		listHtmlfiles.addItem(Ctrl.trans.standard() + "-old", "w:oldstandart");

	}

	private void forSearchKeywords() {
		txtSearchKeys.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				tipChanged = true;
			}
		});

	}

	private StackLayoutPanel getPageStack() {


		listHtmlfiles.setVisibleItemCount(6);
		listHtmlfiles.setSize("100%", "150px");

		SimplePanel pnlHtmlFiles = new SimplePanel();
		pnlHtmlFiles.setWidth("98%");
		pnlHtmlFiles.add(listHtmlfiles);
		pnlHtmlFiles.setStyleName("site-padding");

		pnlAddToList.setWidth("100%");
		pnlDialog.setWidth("100%");
		pnlTags.setWidth("100%");
		pnlContent.setWidth("100%");

		txtSearchKeys.setSize("93%", "95%");

		btnHtmlFile.setWidth("100%");
		btnContent.setWidth("100%");
		btnSearch.setWidth("100%");
		btnAddToList.setWidth("100%");
		btnDialog.setWidth("100%");
		btnTags.setWidth("100%");
		btnStock.setWidth("100%");

		pageStack.setPixelSize(200, 500);

		pageStack.add(pnlContent, btnContent, 30);
		pageStack.add(new SimplePanel(txtSearchKeys), btnSearch, 30);
		pageStack.add(pnlAddToList, btnAddToList, 30);
		pageStack.add(pnlDialog, btnDialog, 30);
		pageStack.add(pnlTags, btnTags, 30);
		pageStack.add(pnlHtmlFiles, btnHtmlFile, 30);
//		pageStack.add(pnlRelatedItems, btnRelatedItems, 30);

		pageStack.add(pnlStock, btnStock, 30);

		showStockPanel();

		return pageStack;
	}

	private void showStockPanel() {
		if (boxer.getECommerce()) {
			UIObject.setVisible(pnlStock.getElement(), true);
			pageStack.getHeaderWidget(pnlStock).setVisible(true);
		} else {
			UIObject.setVisible(pnlStock.getElement(), false);
			pageStack.getHeaderWidget(pnlStock).setVisible(false);
			pageStack.showWidget(pnlContent);
		}

	}

	public void saveWriting() {
		pnlContent.save(boxer.writing.uri, boxer.pageLang);
		if (tagsChanged) {
			WritingsDao.tags(pnlTags.getTags(), boxer.writing.uri, new StringResponse() {
				@Override
				public void ready(String value) {
					tagsChanged = false;
					super.ready(value);
				}
			});
		}

		if (tipChanged) {
			ContentsDao.tip(boxer.pageLang, txtSearchKeys.getValue(), boxer.writing.uri, new StringResponse() {
				@Override
				public void ready(String value) {
					PageDlg.this.tipChanged = false;
				}
			});
		}

		if (htmlFileChanged) {
			ResourcesDao.sethtmlfile(listHtmlfiles.getValue(listHtmlfiles.getSelectedIndex()), boxer.writing.uri,
					new StringResponse() {
						@Override
						public void ready(String value) {
							PageDlg.this.htmlFileChanged = false;
						}
					});
		}

		RomEntryPoint.one.setStatus(boxer.writing.title[0] + " " + Ctrl.trans.saved());
	}

	@Override
	public void appChanged(String value) {
	}

	@Override
	public void contentChanged() {
		ClientUtil.jsonObjPutStrAsArr(com.bilgidoku.rom.site.yerel.boxer.item, "title", pnlContent.getPageTitle());
		ClientUtil.jsonObjPutStr(com.bilgidoku.rom.site.yerel.boxer.item, "icon", pnlContent.getIcon());
		ClientUtil.jsonObjPutStr(com.bilgidoku.rom.site.yerel.boxer.item, "medium_icon", pnlContent.getMedIcon());
		ClientUtil.jsonObjPutStr(com.bilgidoku.rom.site.yerel.boxer.item, "large_icon", pnlContent.getLargeIcon());
		ClientUtil.jsonObjPutStrAsArr(com.bilgidoku.rom.site.yerel.boxer.item, "summary", pnlContent.getSummary());

		parent.preview();

	}

	@Override
	public void tagsChanged() {
		tagsChanged = true;

	}

	public boolean pageChanged() {
		return tagsChanged || tipChanged || pnlContent.changed();
	}

	public void commentsChanged(String dlgUri) {
		ClientUtil.jsonObjPutStr(com.bilgidoku.rom.site.yerel.boxer.item, "dialog_uri", dlgUri);
		parent.preview();
	}

	public Widget[] getStackHeaders() {
		return pageHeaders;
	}

	public void infoChanged() {
		if (boxer.getECommerce()) {
			// show pnlStock
			UIObject.setVisible(pnlStock.getElement(), true);
			pageStack.getHeaderWidget(pnlStock).setVisible(true);

		} else {
			UIObject.setVisible(pnlStock.getElement(), false);
			pageStack.getHeaderWidget(pnlStock).setVisible(false);
		}

	}

	@Override
	public void langChanged(String lang) {
		// TODO Auto-generated method stub

	}

}
