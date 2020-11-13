package com.bilgidoku.rom.site.yerel.boxing;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.InfoDao;
import com.bilgidoku.rom.gwt.client.util.Layer;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.boxer;
import com.bilgidoku.rom.site.yerel.pagedlgs.PageDlg;
import com.bilgidoku.rom.site.yerel.pagedlgs.SiteDlg;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class BoxerGui {

	private SiteDlg viewDlg = new SiteDlg(this);
	private DlgPickWidget addWgtDlg = new DlgPickWidget(this);
	private AddImageDlg addImageDlg = new AddImageDlg(this);
	public PageDlg pageDlg = new PageDlg(this);
	private CopyContentDlg copyDlg = new CopyContentDlg();

	public final BoxerMenuButton pageImg = new BoxerMenuButton.Builder("/_local/images/common/gear.png", "gear")
			.setTitle(Ctrl.trans.pageSettings()).setHelpUri("/sayfaeditoru/sayfa-edit-toolbar.mp4").setMenu(pageDlg)
			.setActiveImage("/_local/images/common/gear_white.png").build();

	public final BoxerMenuButton openPageImg = new BoxerMenuButton.Builder("/_local/images/common/play.png", "show")
			.setTitle(Ctrl.trans.play()).build();

	public final BoxerMenuButton viewImg = new BoxerMenuButton.Builder("/_local/images/common/brush.png", "paint")
			.setTitle(Ctrl.trans.siteSettings()).setHelpUri("/sayfa_site_ayarlari/gorunum.mp4").setMenu(viewDlg)
			.setActiveImage("/_local/images/common/brush_white.png").build();

	public final BoxerMenuButton addWgtImg = new BoxerMenuButton.Builder("/_local/images/common/plus.png", "add")
			.setTitle(Ctrl.trans.add(Ctrl.trans.widget()))
			.setHelpUris(new String[] { "/sayfaeditoru/metin-ekle.mp4",
					"/sayfa_bilesenler/ayarlar-parametresizyerlestir.mp4",
					"/help/sayfa_bilesenler/ayarlar-parametreliyerlestir.mp4",
					"/sayfa_bilesenler/ayarlar-sayfabolumler.mp4" })
			.setActiveImage("/_local/images/common/plus_white.png").setMenu(addWgtDlg).build();

	final BoxerMenuButton saveImg = new BoxerMenuButton.Builder("/_local/images/common/save.png", "save")
			.setTitle(Ctrl.trans.save()).build();

	final BoxerMenuButton publishImg = new BoxerMenuButton.Builder("/_local/images/common/podcast_big.png", "publish")
			.setTitle(Ctrl.trans.publish()).setHelpUri("/sayfa_site_ayarlari/gorunum-testyayinla.mp4").build();

	final BoxerMenuButton helpImg = new BoxerMenuButton.Builder("/_local/images/common/help_black.png", "help")
			.setTitle(Ctrl.trans.help()).build();

	final BoxerMenuButton copyImg = new BoxerMenuButton.Builder("/_local/images/common/copy.png", "copy")
			.setTitle(Ctrl.trans.copy(Ctrl.trans.content())).setMenu(copyDlg)
			.setActiveImage("/_local/images/common/copy_white.png").build();

	final BoxerMenuButton clipImg = new BoxerMenuButton.Builder("/_local/images/common/clip.png", "attach")
			.setTitle("Attach images").setMenu(addImageDlg).setActiveImage("/_local/images/common/clip_white.png")
			.build();
	final BoxerMenuButton selectImg = new BoxerMenuButton.Builder("/_local/images/common/clip.png", "attach")
			.setTitle("Select components").setMenu(addImageDlg).setActiveImage("/_local/images/common/clip_white.png")
			.build();

	final BoxerMenuButton[] menuBtns = { pageImg, viewImg, addWgtImg, copyImg, clipImg, selectImg };

	private String uri;
	private HTML showLang;
	private BoxerGuiCb myBoxer;
	private HelpScenePage help;

	public BoxerGui(BoxerGuiCb box, String uri) {

		this.myBoxer = box;
		this.uri = uri;

		showLang = new HTML(getLangFlag(boxer.pageLang));
		help = new HelpScenePage(this);
		copyDlg.loadLangs(boxer.writing.langcodes);

		addPointer();
		addPageButtons();

		forGear();
		forShow();
		forView();
		forAdd();
		forSave();
		forPublish();
		forAddImage();
		forCopy();
		forHelp();
		forSelect();

		viewDlg.setMyButton(viewImg);
		addWgtDlg.setMyButton(addWgtImg);
		addImageDlg.setMyButton(clipImg);
		pageDlg.setMyButton(pageImg);
		copyDlg.setMyButton(copyImg);

	}

	private void forAddImage() {
		clipImg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addImageDlg.loadData();
				deactivateAllButMe(clipImg);
			}
		});
	}
	
	private void forSelect() {
		selectImg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				myBoxer.selectAllBody();
			}
		});
	}

	private void forCopy() {
		copyImg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deactivateAllButMe(copyImg);
			}
		});

	}

	private SafeHtml getLangFlag(String flg) {
		String flag = "";
		if (flg.equals("tr"))
			flag = "/_local/images/common/flag_tr.png";
		else if (flg.equals("en")) {
			flag = "/_local/images/common/flag_en.png";
		} else if (flg.equals("ru")) {
			flag = "/_local/images/common/flag_ru().png";
		}
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		if (!flag.isEmpty())
			sb.appendHtmlConstant("<img src='" + flag + "' style='vertical-align: middle;z-index:" + Layer.layer2
					+ ";position:fixed;'/>");
		else
			sb.appendHtmlConstant(flg);

		return sb.toSafeHtml();
	}

	private void addPointer() {
		final Image pointer = new Image("/_local/images/common/pointer2.gif");
		pointer.getElement().setPropertyString("id", "pagepointer");
		pointer.getElement().getStyle().setZIndex(10000000);
		pointer.getElement().getStyle().setDisplay(Display.NONE);
		pointer.getElement().getStyle().setPosition(Position.ABSOLUTE);
		RomEntryPoint.one.addToRootPanel(pointer, 0, 0);
	}

	private void forHelp() {
		helpImg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				help.go();
			}
		});
	}

	private void forView() {
		viewImg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				viewDlg.init(com.bilgidoku.rom.site.yerel.boxer.infoObj);
				deactivateAllButMe(viewImg);
			}
		});
	}

	private void forPublish() {
		publishImg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Permanent.startSpinner();
				InfoDao.publish(Ctrl.info().uri, new StringResponse() {
					@Override
					public void ready(String value) {
						Permanent.stopSpinner();
						RomEntryPoint.one.setStatus(Ctrl.trans.published());
					}
				});
			}
		});

	}

	private void forSave() {
		saveImg.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				save();
			}
		});
	}

	private void forAdd() {
		addWgtImg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deactivateAllButMe(addWgtImg);
			}
		});

	}

	private void forShow() {
		openPageImg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				openPage();
			}
		});

	}

	private void forGear() {
		pageImg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				pageDlg.loadData(com.bilgidoku.rom.site.yerel.boxer.writing);
				deactivateAllButMe(pageImg);
			}
		});

	}

	protected void deactivateAllButMe(BoxerMenuButton me) {
		for (int i = 0; i < menuBtns.length; i++) {
			menuBtns[i].deactivate();
		}
		me.activate();
	}

	private void addPageButtons() {

		RomEntryPoint.one.addToRootPanel(boxer.status, 42, 13);
		RomEntryPoint.one.addToRootPanel(showLang, 8, 16);
		RomEntryPoint.one.addToRootPanel(saveImg, Layer.start1, 45);
		RomEntryPoint.one.addToRootPanel(addWgtImg, Layer.start1, 85);
		RomEntryPoint.one.addToRootPanel(pageImg, Layer.start1, 125);
		RomEntryPoint.one.addToRootPanel(viewImg, Layer.start1, 165);
		RomEntryPoint.one.addToRootPanel(openPageImg, Layer.start1, 205);
		RomEntryPoint.one.addToRootPanel(publishImg, Layer.start1, 245);
		RomEntryPoint.one.addToRootPanel(helpImg, Layer.start1, 285);
		RomEntryPoint.one.addToRootPanel(clipImg, Layer.start1, 325);
		RomEntryPoint.one.addToRootPanel(selectImg, Layer.start1, 400);

		if (boxer.writing.langcodes.length > 1) {
			RomEntryPoint.one.addToRootPanel(copyImg, Layer.start1, 365);
			RomEntryPoint.one.addToRootPanel(copyDlg, Layer.start2, 300);
		} else {
			copyImg.setVisible(false);
		}

		boxer.status.getElement().getStyle().setZIndex(Layer.layer4);

		RomEntryPoint.one.addToRootPanel(viewDlg, Layer.start2, 5);
		RomEntryPoint.one.addToRootPanel(addWgtDlg, Layer.start2, 5);
		RomEntryPoint.one.addToRootPanel(pageDlg, Layer.start2, 5);
		RomEntryPoint.one.addToRootPanel(addImageDlg, Layer.start2, 5);

	}

	protected void openPage() {
		Window.open(uri, "_blank", "");
	}

	private void save() {
		saveWriting();
		saveInfo();
		myBoxer.save();
		RomEntryPoint.one.setStatus(Ctrl.trans.saved());
	}

	public void saveWriting() {
		pageDlg.saveWriting();
	}

	public void saveInfo() {
		viewDlg.saveInfo();
	}

	public void widgetSelected() {
		addWgtImg.deactivate();
		myBoxer.pickWidgetFromWidgetList(addWgtDlg.selected.getKey());
	}

	public void imageWidgetSelected(String selected) {
		clipImg.deactivate();
		myBoxer.addImageWidget(selected);
	}

	public boolean writingChanged() {
		return pageDlg.pageChanged();
	}

	public boolean isViewChanged() {
		return viewDlg.isChanged();
	}

	public void infoChanged() {
		myBoxer.infoChanged();
	}

	public void preview() {
		myBoxer.preview();
	}

	public Widget[] sitePrefHeaders() {
		return viewDlg.getStackHeaders();
	}

	public Widget[] pagePrefHeaders() {
		return pageDlg.getStackHeaders();
	}

	public boolean isPageOpen() {
		return pageImg.isActive();
	}

	public boolean isSiteOpen() {
		return viewImg.isActive();
	}

	// public void ecommChanged() {
	// addWgtDlg.infoChanged();
	// pageDlg.infoChanged();
	// }

}
