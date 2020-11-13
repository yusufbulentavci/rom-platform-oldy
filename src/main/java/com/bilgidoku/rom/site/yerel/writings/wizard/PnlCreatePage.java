package com.bilgidoku.rom.site.yerel.writings.wizard;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.writings.PnlTitleUri;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class PnlCreatePage extends Composite implements PageReady {

	final PageReady parent;
	final String parentUri;
	final Button btnAdd = new Button(Ctrl.trans.addItem());
	final PnlTitleUri askName = new PnlTitleUri();
	final String template;
	private String parentName;

	public PnlCreatePage(String pageName, String template, String[] alternativeNames, PageReady caller, String parentUri, String parentName) {

		this.parent = caller;
		this.parentUri = parentUri;
		this.parentName = parentName;
		
		this.template = template;

		if (alternativeNames != null)
			askName.setAlternativeNames(alternativeNames);

		if (template.equals("home")) {
			askName.home();
		}

		askName.setTitleValue(pageName);

		forSave();

		HorizontalPanel holder = new HorizontalPanel();
		holder.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		holder.add(askName);
		holder.add(btnAdd);
		initWidget(holder);

	}

	private void forSave() {
		btnAdd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final String title = askName.getTitle();
				final String uri = askName.getUri();
				final String writingUri = parentUri + "/" + uri;
				final String containerUri = "/_/writings" + parentUri;
				
				if (title.isEmpty()) {
					Window.alert(Ctrl.trans.emptyValue(Ctrl.trans.title()));
					return;
				}
				
				WritingsDao.neww(Ctrl.infoLang(), title, writingUri, containerUri, new StringResponse() {
					public void ready(final String writingUri) {
						new ApplyTemplate(containerUri, parentName, title, writingUri, template, PnlCreatePage.this);						
					}

					@Override
					public void err(int statusCode, String statusText, Throwable exception) {
						if (statusCode == 500)
							exists(title, writingUri, Ctrl.infoLang());
						else
							super.err(statusCode, statusText, exception);
					}
				});

			}
		});

	}

	@Override
	public void pageReady(String title, String uri, String lang) {
//		Window.alert("page created");
		parent.pageReady(title, uri, lang);
	}

	public void exists(String title, String uri, String lang) {
//		Window.alert("page exists");
		parent.pageReady(title, uri, lang);
	}

	public void reset() {
		askName.reset();		
	}

}
