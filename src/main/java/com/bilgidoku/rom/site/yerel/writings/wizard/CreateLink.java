package com.bilgidoku.rom.site.yerel.writings.wizard;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsDao;
import com.bilgidoku.rom.gwt.araci.client.site.LinksDao;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.writings.PnlTitleUri;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class CreateLink extends Composite {

	final Button btnAdd = new Button(Ctrl.trans.addItem());

	final PnlTitleUri askName = new PnlTitleUri();
	final PageLinks parent;
	
	public CreateLink(PageLinks caller, String parentUri) {
		this.parent = caller;

		forSave(parentUri);

		HorizontalPanel holder = new HorizontalPanel();
		holder.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		holder.add(askName);
		holder.add(btnAdd);
		initWidget(holder);

	}

	private void forSave(final String parentUri) {
		btnAdd.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final String title = askName.getTitle();
				final String uri = askName.getUri();
				LinksDao.neww(Ctrl.infoLang(), title, parentUri + "/" + uri, parentUri,
						new StringResponse() {
							@Override
							public void ready(String value) {
								updateLink(title, value);
								parent.linkAdded(title, value);
							}


							@Override
							public void err(int statusCode, String statusText, Throwable exception) {
								if (statusCode == 500)
									parent.linkAdded(title, parentUri + "/" + uri);
								else
									super.err(statusCode, statusText, exception);
							}
						});
			}
		});

	}
	
	protected void updateLink(String title, String uri) {
		String medIcon = null;
		String largeIcon = null;
		String thumb = parent.getThumbImage();
		if (thumb != null && !thumb.isEmpty()) {
			medIcon = thumb.replace("_t.", "_m.");
			largeIcon = thumb.replace("_t.", "_l.");
		}
		ContentsDao.largeicon(largeIcon, uri, new StringResponse());
		ContentsDao.mediumicon(medIcon, uri, new StringResponse());
	}

}
