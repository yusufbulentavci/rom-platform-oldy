package com.bilgidoku.rom.site.yerel.siteinfo.subpanels;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.InputChangedHandler;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.pagedlgs.SiteDlg;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.FontBox;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PanelFonts extends ScrollPanel {
	private FontBox listHeaderFonts;
	private FontBox listTextFonts;

	private SiteDlg view;
	protected boolean initialTextLoad = false;
	protected boolean initialHeaderLoad = false;

	JSONObject textFont = null;
	JSONObject headerFont = null;

	public PanelFonts(final SiteDlg viewDlg, JSONObject font) {
		this.view = viewDlg;
		if (font.get("textfont") != null)
			textFont = font.get("textfont").isObject();

		if (font.get("headerfont") != null)
			headerFont = font.get("headerfont").isObject();

		listTextFonts = new FontBox("textfont", textFont);
		listHeaderFonts = new FontBox("headerfont", headerFont);

		this.add(ui());
		forTextFontChange();
		forHeaderFontChange();
	}

	private void forHeaderFontChange() {
		listTextFonts.addInputChangedHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				textFont = listTextFonts.objFont;
				view.fontChanged();
			}
		});

	}

	private void forTextFontChange() {
		listHeaderFonts.addInputChangedHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				headerFont = listHeaderFonts.objFont;
				view.fontChanged();
			}
		});
	}

	public Widget ui() {
		VerticalPanel ft = new VerticalPanel();
		ft.add(new HTML(ClientUtil.getHeader(Ctrl.trans.headerFonts() + ":")));
		ft.add(listHeaderFonts);
		ft.add(new HTML(ClientUtil.getHeader(Ctrl.trans.textFonts() + ":")));
		ft.add(listTextFonts);
		return ft;
	}

	public JSONObject getFont() {
		JSONObject jo = new JSONObject();
		jo.put("textfont", textFont);
		jo.put("headerfont", headerFont);
		return jo;
	}
}
