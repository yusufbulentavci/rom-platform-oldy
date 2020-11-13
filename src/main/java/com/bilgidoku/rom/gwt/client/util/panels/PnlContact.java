package com.bilgidoku.rom.gwt.client.util.panels;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsResponse;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.common.SiteMessage;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;

public class PnlContact extends Composite implements HasAddress, HasPassword {

	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	public static String lang = "en";
	private final StackLayoutPanel stackPanel = new StackLayoutPanel(Unit.EM);
	private final SiteButton btnSaveInfo = new SiteButton(trans.save(), "");
	private final PnlAddress pnlAddr = new PnlAddress(this);
	private final PnlChangepass pnlPassword = new PnlChangepass(this);
	private String userName;

	public PnlContact(String contactUri, String userName) {
		this.userName = userName;
		ContactsDao.get(contactUri, new ContactsResponse() {
			@Override
			public void ready(Contacts value) {
				forSaveContact(value.uri, value.tags, value.fb_id, value.twitter, value.confirmed, value.icon,
						value.gids);
				pnlAddr.loadData(value);
			}
		});

		btnSaveInfo.setWidth("120px");

		FlowPanel pnlContact = new FlowPanel();
		pnlContact.add(pnlAddr);
		pnlContact.add(btnSaveInfo);

		stackPanel.setPixelSize(580, 470);
		stackPanel.getElement().getStyle().setMargin(10, Unit.PX);
		stackPanel.add(pnlContact, trans.about(), 3);

		stackPanel.add(pnlPassword, trans.changePassword(), 3);

		initWidget(stackPanel);
	}

	private void forSaveContact(final String uri, final String[] tags, final String fb_id, final String twitter,
			final Boolean confirmed, final String icon, final String[] gids) {
		btnSaveInfo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ContactsDao.change(PnlContact.lang, "cipher", pnlAddr.getFirstName(), pnlAddr.getLastName(), icon,
						pnlAddr.getEMail(), fb_id, twitter, "web", confirmed, pnlAddr.getAddress(), pnlAddr.getState(),
						pnlAddr.getCity(), pnlAddr.getCountry(), pnlAddr.getPostalCode(), pnlAddr.getOrganization(),
						pnlAddr.getPhone(), pnlAddr.getMobile(), pnlAddr.getFax(), tags, gids, uri,
						new StringResponse() {
							@Override
							public void ready(String value) {
								new SiteMessage(trans.saved(trans.contactInfo()), trans.contactInfo());
							}
						});

			}
		});
	}

	@Override
	public void addressChanged() {
	}

	@Override
	public void changePassword(String password) {
		// InitialsDao.changepass(SessionManage.getMyCid(), password,
		// "/_/_initials", new StringResponse() {
		// @Override
		// public void ready(String value) {
		// new DlgMessage(trans.saved(trans.password()));
		// pnlPassword.clear();
		// }
		//
		// });
		String cid=RomEntryPoint.com().get("user.cid");
		ContactsDao.changepwd(password, cid, new StringResponse() {
			@Override
			public void ready(String value) {
				new SiteMessage(trans.saved(trans.newPassword()), trans.newPassword());
				pnlPassword.clear();
			}

		});

	}

}
