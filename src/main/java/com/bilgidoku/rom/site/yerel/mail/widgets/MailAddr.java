package com.bilgidoku.rom.site.yerel.mail.widgets;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsDao;
import com.bilgidoku.rom.gwt.client.widgets.images.Images;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.ImageAnchor;
import com.bilgidoku.rom.site.yerel.contacts.DlgAskName;
import com.bilgidoku.rom.site.yerel.mail.core.InternetAddress;
import com.bilgidoku.rom.site.yerel.mail.core.MailUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class MailAddr extends Composite {

	public MailAddr(InternetAddress addr, boolean isContact) {
		Images img = GWT.create(Images.class);
		ImageResource imgContactPlus = img.contact();
		
		final ImageAnchor addToContacts = new ImageAnchor();
		addToContacts.addStyleName("site-leftborder");
		addToContacts.changeResource(imgContactPlus);
		addToContacts.setTitle("Add to contacts");

		forAddContact(addToContacts, addr);

		HorizontalPanel fp = new HorizontalPanel();
		fp.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		fp.add(new Label(MailUtil.mailFormat(addr.getPersonal(), addr.getAddress())));
		if (!isContact)
			fp.add(addToContacts);
		fp.setStyleName("token-input-token-facebook");
		this.initWidget(fp);
	}

	private void forAddContact(ImageAnchor addToContacts, final InternetAddress addr) {
		addToContacts.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final DlgAskName dlg = new DlgAskName(addr.getAddress());
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						final DlgAskName pp = (DlgAskName) event.getTarget();
						if (pp.firstName != null && !pp.firstName.isEmpty()) {
							/**
							 * TODO: Country değişmeli
							 */
							ContactsDao.neww(Ctrl.infoLang(), "TR", pp.email, null, pp.firstName, pp.lastName, "","", null, "/_/co",
									new StringResponse() {
										public void ready(String value) {
											Ctrl.setStatus(Ctrl.trans.added(Ctrl.trans.contact()));
										};
									});
						} else {
							Window.alert(Ctrl.trans.emptyValue(Ctrl.trans.firstName()));
						}
					}
				});
				dlg.show();
			}
		});

	}

}
