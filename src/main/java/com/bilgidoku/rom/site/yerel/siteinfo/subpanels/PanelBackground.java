package com.bilgidoku.rom.site.yerel.siteinfo.subpanels;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.widgets.ImageBox;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.InputChangedHandler;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.pagedlgs.SiteDlg;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PanelBackground extends ScrollPanel {
	private final Button btnNoBackground = new Button(Ctrl.trans.noBackground());
	// private final SiteButton btnUploadBackground = new
	// SiteButton(Ctrl.trans.uploadBackground(), Ctrl.trans.uploadBackground());

	private final ImageBox imgBackground = new ImageBox();

	private final SiteDlg view;
	private PanelBackImage imagePanel;
	private PanelBackPattern patternPanel;
	public boolean initted = false;
	private final CheckBox cb = new CheckBox("Tekrarla");

	public PanelBackground(SiteDlg viewDlg) {
		this.view = viewDlg;
		init();
	}

	public void init() {
		initted = true;
		imagePanel = new PanelBackImage(this);
		patternPanel = new PanelBackPattern(this);
		btnNoBackground.setTitle(Ctrl.trans.noBackgroundDesc());
		forNoBack();
		
		imgBackground.addHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				imagePanel.removeSelection();
				patternPanel.removeSelection();
				cb.setValue(false);
				view.paletteChanged();
			}
		}, InputChanged.TYPE);
		
		imgBackground.getElement().getStyle().setMargin(4, Unit.PX);
		cb.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				view.paletteChanged();				
			}
		});
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(imgBackground);
		hp.add(cb);
		
		VerticalPanel fp = new VerticalPanel();
		fp.add(btnNoBackground);
		fp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
		fp.add(new HTML(ClientUtil.getHeader(Ctrl.trans.selectOne())));
		fp.add(hp);

		fp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
		fp.add(new HTML(ClientUtil.getHeader(Ctrl.trans.patternSuggections())));
		fp.add(patternPanel);

		fp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
		fp.add(new HTML(ClientUtil.getHeader(Ctrl.trans.backgroundSuggestions())));
		fp.add(imagePanel);
		this.add(fp);

	}

	private void forNoBack() {
		btnNoBackground.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				imagePanel.removeSelection();
				patternPanel.removeSelection();
				imgBackground.setImage(null);				
				view.paletteChanged();
				view.preview();
			}
		});
	}

	public void backPatternChanged(String img) {
		imagePanel.removeSelection();
		imgBackground.setImage(null);
		view.paletteChanged();
	}

	public void backImageChanged(String img) {
		patternPanel.removeSelection();
		imgBackground.setImage(null);
		view.paletteChanged();
	}

	public void loadData(JSONObject jo) {
		String backImg = ClientUtil.getString(jo.get("back_img"));
		String backPtn = ClientUtil.getString(jo.get("back_ptn"));
		
		if (backImg.startsWith("/_rm"))
			imagePanel.setSelection(backImg);
		else {
			imgBackground.setImage(backImg);
			cb.setValue(false);
		}
		
		if (backPtn.startsWith("/_rm"))
			patternPanel.setSelection(backPtn);
		else {
			imgBackground.setImage(backPtn);
			cb.setValue(true);
		}
		
	}

	public void putBackground(JSONObject jo) {
		if (imgBackground.getImgPath() != null && !imgBackground.getImgPath().isEmpty()) {			
			if (cb.getValue()) {
				jo.put("back_ptn", strOrNull(imgBackground.getImgPath()));
			} else {
				jo.put("back_img", strOrNull(imgBackground.getImgPath()));	
			}
			return;
		} 
		
		jo.put("back_img", strOrNull(imagePanel.getSelected()));
		jo.put("back_ptn", strOrNull(patternPanel.getSelected()));

	}

	private JSONValue strOrNull(String s) {
		if (s == null)
			return JSONNull.getInstance();
		return new JSONString(s);
	}

}
