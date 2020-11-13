package com.bilgidoku.rom.site.yerel.siteinfo.subpanels;

import com.bilgidoku.rom.gwt.client.widgets.ImageBox;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.InputChangedHandler;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.pagedlgs.SiteDlg;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PanelOthers extends VerticalPanel {
	
	private final TextBox txtBrTitle = new TextBox();
	private final ImageBox txtBrIcon = new ImageBox();
	private final HTML browTitleLbl = new HTML(Ctrl.trans.browserTitle());
	private final HTML browIconLbl = new HTML(Ctrl.trans.browserIcon());

	public PanelOthers(final SiteDlg viewDlg) {
		txtBrTitle.setWidth("95%");

		txtBrTitle.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				viewDlg.titleChanged();
			}
		});

		txtBrIcon.addChangedHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				viewDlg.browserIconChanged();
			}
		});		

		browTitleLbl.setStyleName("site-label");
		browIconLbl.setStyleName("site-label");
		txtBrTitle.addStyleName("site-input");
		txtBrIcon.addStyleName("site-input");

		this.add(browTitleLbl);
		this.add(txtBrTitle);

		this.add(browIconLbl);
		this.add(txtBrIcon);
		
	}


	public void loadData(String browserTitle, String browserIcon, boolean eComm, boolean auth) {
		txtBrTitle.setValue(browserTitle);
		txtBrIcon.setImage(browserIcon);
	}


	public String getBrowserTitle() {
		return txtBrTitle.getValue();
	}

	public String getBrowserIcon() {
		return txtBrIcon.getImgPath();
	}


}
