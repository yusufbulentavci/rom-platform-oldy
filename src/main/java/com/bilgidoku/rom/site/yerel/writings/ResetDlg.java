package com.bilgidoku.rom.site.yerel.writings;

import com.bilgidoku.rom.gwt.araci.client.site.Writings;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.writings.wizard.ApplyReadyImages;
import com.bilgidoku.rom.site.yerel.writings.wizard.ApplyTemplate;
import com.bilgidoku.rom.site.yerel.writings.wizard.PageReady;
import com.bilgidoku.rom.site.yerel.writings.wizard.PnlTemplates;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ResetDlg extends DialogBox implements PageReady {
	final PnlTemplates pnlTempl = new PnlTemplates();
	final Button btnOK = new Button("Ok");
	final Button btnClose = new Button("Close");
	final LangList langs = new LangList();

	public ResetDlg() {
		btnClose.setStyleName("site-closebutton");
		forClose();

		VerticalPanel vp = new VerticalPanel();

		if (Ctrl.info().langcodes.length > 1) {
			vp.add(langs);
		}

		vp.add(pnlTempl);
		vp.add(btnOK);
		vp.add(btnClose);

		this.add(vp);
		this.setText(Ctrl.trans.reset());
		this.hide();
	}

	private void forClose() {
		btnClose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide(true);
			}
		});

	}

	public void loadData(final String parentUri, final String parentTitle, final String pageTitle, final String pageUri) {

		new ApplyReadyImages();

		btnOK.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm(Ctrl.trans.askReset())) {
					final String template = pnlTempl.getSelected();
					if (template == null || template.isEmpty()) {
						Window.alert(Ctrl.trans.selectAnItem());
						return;
					}
					
					if (Ctrl.info().langcodes.length == 1) {
						new ApplyTemplate(parentUri, parentTitle, pageTitle, pageUri, template, ResetDlg.this, Ctrl
								.infoLang());
						return;
					}
					// if page exists in that new language
					WritingsDao.get(Ctrl.infoLang(), pageUri, new WritingsResponse() {
						public void ready(Writings value) {
							String selLang = langs.getSelectedLang();
							if (!ClientUtil.existInArray(value.langcodes, selLang)) {
								// create or give an alert
								Window.alert(Ctrl.trans.pageNotExistsInThatLanguage() + " ("+ selLang +") ");
								
							} else {
								new ApplyTemplate(parentUri, parentTitle, pageTitle, pageUri, template, ResetDlg.this,
										selLang);
							}
						};
					});

				} else {
					ResetDlg.this.hide();
				}

			}

		});

	}

	@Override
	public void pageReady(String title, String uri, String lang) {
		Window.alert(Ctrl.trans.pageReset());
		ResetDlg.this.hide();

	}

}
