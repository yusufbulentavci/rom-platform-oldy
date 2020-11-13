package com.bilgidoku.rom.site.yerel.writings.wizard;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.writings.PnlTitleUri;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PnlCreateContainer extends Composite implements PageReady {

	final PageWizard parent;
	final String parentUri;
	final Button btnAdd = new Button(Ctrl.trans.addItem());
	final PnlTitleUri askName = new PnlTitleUri();
	final String template;

	public PnlCreateContainer(String pageName, String template, String[] alternativeNames, PageWizard caller,
			String parentUri) {

		this.parent = caller;
		this.parentUri = parentUri;
		this.template = template;

		if (alternativeNames != null)
			askName.setAlternativeNames(alternativeNames);

		if (template.indexOf("home") >= 0) {
			askName.home();
		}

		askName.setTitleValue(pageName);

		forSave();

		VerticalPanel holder = new VerticalPanel();
		btnAdd.setText(Ctrl.trans.next());
		btnAdd.setStyleName("site-nextbutton");
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
				createContainer(uri, title);
			}
		});

	}

	protected void createContainer(final String uri, final String title) {

		String conUri = "/_/writings/" + uri;
		String parent = "/_/writings";
		String uriPrefix = "/" + uri + "/";
		String writingUri = "/" + uri;

		if (uri.isEmpty()) {
			//since home already created
			conUri = "/_/writings";
			parent = null;
			uriPrefix = "/";
			writingUri = "/";
			new ApplyTemplate(conUri, title, title, writingUri, template, PnlCreateContainer.this);
			return;
		}

		final String cUri = conUri;
		final String wUri = writingUri;
		

		WritingsDao.breed(conUri, Ctrl.infoLang(), Data.WRITING_PUBLIC_MASK, uriPrefix, "w:standart", title, parent,
				new ContainersResponse() {
					@Override
					public void ready(Containers value) {
						new ApplyTemplate(cUri, title, title, wUri, template, PnlCreateContainer.this);
					}

					@Override
					public void err(int statusCode, String statusText, Throwable exception) {
						if (statusCode == 500)
							exists(title, wUri, Ctrl.infoLang());
						else
							super.err(statusCode, statusText, exception);
					}

				});

	}

	public void exists(String title, String uri, String lang) {
		pageReady(title, uri, lang);

	}

	@Override
	public void pageReady(String title, String uri, String lang) {
		parent.pageAdded(title, uri);
	}

}
