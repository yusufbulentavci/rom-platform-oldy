package com.bilgidoku.rom.site.yerel.pagedlgs;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Styles;
import com.bilgidoku.rom.gwt.araci.client.rom.StylesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.StylesResponse;
import com.bilgidoku.rom.gwt.araci.client.site.InfoDao;
import com.bilgidoku.rom.gwt.client.util.Layer;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.boxer;
import com.bilgidoku.rom.site.yerel.boxing.BoxerGui;
import com.bilgidoku.rom.site.yerel.boxing.BoxerMenu;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.siteinfo.subpanels.PanelBackground;
import com.bilgidoku.rom.site.yerel.siteinfo.subpanels.PanelBanner;
import com.bilgidoku.rom.site.yerel.siteinfo.subpanels.PanelFonts;
import com.bilgidoku.rom.site.yerel.siteinfo.subpanels.PanelOthers;
import com.bilgidoku.rom.site.yerel.siteinfo.subpanels.PanelPageColors;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class SiteDlg extends BoxerMenu {

	private final SiteToolbarButton btnColor = new SiteToolbarButton("/_local/images/common/style.png", Ctrl.trans.pageColors(), Ctrl.trans.pageColors(),
			new String[] { "/sayfa_site_ayarlari/gorunum-palet.mp4", "/sayfa_site_ayarlari/gorunum-testyayinla.mp4" });

	private final SiteToolbarButton btnFont = new SiteToolbarButton("/_local/images/common/text.png", Ctrl.trans.font(), Ctrl.trans.font(),
			"/sayfa_site_ayarlari/gorunum-font.mp4");

	private final SiteToolbarButton btnBack = new SiteToolbarButton("/_local/images/common/picture.png", Ctrl.trans.background(), Ctrl.trans.background(),
			"/sayfa_site_ayarlari/gorunum-arkaplan.mp4");

	private final SiteToolbarButton btnStyles = new SiteToolbarButton("/_local/images/common/aperture.png", Ctrl.trans.style(), Ctrl.trans.style(), "");

	private final SiteToolbarButton btnBanner = new SiteToolbarButton("/_local/images/common/speech_bubble.png", Ctrl.trans.banner(), Ctrl.trans.banner(),
			"/sayfa_site_ayarlari/gorunum-banner.mp4");

	private final SiteToolbarButton btnInfo = new SiteToolbarButton("/_local/images/common/pencil.png", Ctrl.trans.edit(), Ctrl.trans.siteInfo(), "");

	private final Widget[] btnHeaders = { btnColor, btnFont, btnBack, btnStyles, btnBanner, btnInfo };

	private PanelBanner pnlBanner = new PanelBanner(this);
	private PanelBackground pnlBackground = new PanelBackground(this);
	private PanelPageColors pnlColorPalette;

	private PanelFonts pnlFont;
	private PanelOthers pnlInfoOthers = new PanelOthers(this);

	protected boolean fontChanged = false;
	protected boolean bannerChanged = false;
	protected boolean paletteChanged = false;
	protected boolean styleChanged = false;

	
	private boolean titleChanged = false;
	private boolean browserIconChanged = false;

	private final ListBox listStyles = new ListBox();

	private BoxerGui parent;
	private boolean initted = false;

	public SiteDlg(BoxerGui boxerGui) {
		super(Ctrl.trans.siteView(), Layer.layer1);
		this.parent = boxerGui;

		loadStyles();
		forStyleChange();
	}

	public void init(JSONObject infoObj) {
		if (initted) {
			return;
		} else {
			pnlColorPalette = new PanelPageColors(this);
			pnlFont = new PanelFonts(this, infoObj.get("text_font").isObject());
			this.add(ui());

			loadData(infoObj);

			initted = true;

		}

	}

	private void loadStyles() {
		StylesDao.list("/_/styles", new StylesResponse() {
			@Override
			public void array(List<Styles> styles) {
				for (int i = 0; i < styles.size(); i++) {
					String sUri = styles.get(i).uri;
					if (sUri.indexOf("common") <= 0)
						listStyles.addItem(styles.get(i).title, sUri);
				}
			}
		});
	}

	public void loadData(JSONObject siteInfo) {
		pnlBanner.setSelection(ClientUtil.getString(siteInfo.get("banner_img")));

		pnlBackground.loadData(siteInfo.get("palette").isObject());

		ClientUtil.findAndSelect(listStyles, ClientUtil.getString(siteInfo.get("style")));

		pnlColorPalette.loadData(siteInfo.get("palette").isObject());

		String bT = ClientUtil.getString(siteInfo.get("browser_title").isArray().get(0));
		String bi = ClientUtil.getString(siteInfo.get("browser_icon"));

		pnlInfoOthers.loadData(bT, bi, ClientUtil.getBoolean(siteInfo.get("ecommerce")),
				ClientUtil.getBoolean(siteInfo.get("login")));

	}

	private void forStyleChange() {
		listStyles.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				styleChanged = true;
				boxer.infoObj.put("style", new JSONString(listStyles.getValue(listStyles.getSelectedIndex())));
				parent.infoChanged();
			}
		});
	}

	private Widget ui() {

		listStyles.setVisibleItemCount(6);
		listStyles.setWidth("100%");

		SimplePanel styles = new SimplePanel();
		styles.add(listStyles);
		styles.setStyleName("site-padding");

		pnlFont.setWidth("98%");
		pnlColorPalette.setSize("98%", "320px");
		pnlBanner.setSize("98%", "320px");
		pnlInfoOthers.setWidth("98%");

		StackLayoutPanel pageStack = new StackLayoutPanel(Unit.PX);
		pageStack.setPixelSize(200, 500);

		btnColor.setWidth("100%");
		btnFont.setWidth("100%");
		btnBack.setWidth("100%");
		btnStyles.setWidth("100%");
		btnBanner.setWidth("100%");
		btnInfo.setWidth("100%");

		pageStack.add(pnlColorPalette, btnColor, 30);
		pageStack.add(pnlFont, btnFont, 30);
		pageStack.add(pnlBackground, btnBack, 30);
		pageStack.add(styles, btnStyles, 30);
		pageStack.add(pnlBanner, btnBanner, 30);
		pageStack.add(pnlInfoOthers, btnInfo, 30);

		return pageStack;
	}

	public void saveInfo() {

		if (fontChanged) {
			InfoDao.textfont(new Json(pnlFont.getFont()), "/_/siteinfo", new StringResponse() {
				public void ready(String value) {
					fontChanged = false;
				};
			});
		}

		if (bannerChanged) {
			InfoDao.bannerimg(pnlBanner.getSelected(), "/_/siteinfo", new StringResponse() {
				public void ready(String value) {
					bannerChanged = false;
				};
			});
		}

		if (paletteChanged) {
			JSONObject palette = new JSONObject();
			pnlColorPalette.putColors(palette);
			pnlBackground.putBackground(palette);

			InfoDao.palette(new Json(palette), "/_/siteinfo", new StringResponse() {
				public void ready(String value) {
					paletteChanged = false;
				};
			});
		}

		if (styleChanged) {
			InfoDao.style(listStyles.getValue(listStyles.getSelectedIndex()), "/_/siteinfo", new StringResponse() {
				public void ready(String value) {
					styleChanged = false;
				};
			});
		}

		if (titleChanged) {
			InfoDao.browsertitle(boxer.pageLang, pnlInfoOthers.getBrowserTitle(), "/_/siteinfo", new StringResponse() {
				public void ready(String value) {
					titleChanged = false;
				};
			});
		}

		if (browserIconChanged) {
			InfoDao.browsericon(pnlInfoOthers.getBrowserIcon(), "/_/siteinfo", new StringResponse() {
				public void ready(String value) {
					browserIconChanged = false;
				};
			});
		}

	}

	public boolean isChanged() {
		return styleChanged || paletteChanged || bannerChanged || fontChanged;
	}

	private JSONValue strOrNull(String s) {
		if (s == null)
			return JSONNull.getInstance();
		return new JSONString(s);
	}

	public void preview() {
		parent.infoChanged();
	}

	public void bannerChanged() {
		bannerChanged = true;
		boxer.infoObj.put("banner_img", strOrNull(pnlBanner.getSelected()));
		parent.infoChanged();
	}

	public void paletteChanged() {
		paletteChanged = true;
		JSONObject palette = new JSONObject();
		pnlColorPalette.putColors(palette);
		pnlBackground.putBackground(palette);

		boxer.infoObj.put("palette", palette);

		parent.infoChanged();
	}

	public void fontChanged() {
		fontChanged = true;

		boxer.infoObj.put("text_font", pnlFont.getFont());

		parent.infoChanged();
	}

	public void titleChanged() {
		// TODO Auto-generated method stub

	}

	public void browserIconChanged() {
		browserIconChanged = true;
	}


	public Widget[] getStackHeaders() {
		return btnHeaders;
	}

}
