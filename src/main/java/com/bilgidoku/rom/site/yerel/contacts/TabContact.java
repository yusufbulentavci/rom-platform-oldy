package com.bilgidoku.rom.site.yerel.contacts;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsResponse;
import com.bilgidoku.rom.gwt.client.util.common.Role;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.admin.PasswordChangeForm;
import com.bilgidoku.rom.site.yerel.common.content.HasAddress;
import com.bilgidoku.rom.site.yerel.common.content.HasTags;
import com.bilgidoku.rom.site.yerel.siteinfo.subpanels.PanelAddress;
import com.bilgidoku.rom.site.yerel.subpanels.PnlTags;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TabContact extends Composite implements HasAddress, HasTags {

	// private final StackLayoutPanel stackPanel = new
	// StackLayoutPanel(Unit.EM);
	private final Button btnSaveInfo = new Button(Ctrl.trans.save());
	private final Button btnSaveTag = new Button(Ctrl.trans.save());
	private final Button btnClrAdrForm = new Button(Ctrl.trans.cancel());
	private final PanelAddress pnlAddr = new PanelAddress(this);
	private final PasswordChangeForm pnlPassword;
	private PnlTags pnlTags = new PnlTags(this);
	private final TabPanel tp = new TabPanel();

	public TabContact(String uri, boolean isOwnProfile) {
		pnlPassword = new PasswordChangeForm(uri);
		ContactsDao.get(uri, new ContactsResponse() {
			@Override
			public void ready(Contacts value) {
				forSaveInfo(value.uri, value.first_name + " " + value.last_name);
				forSaveTag(value.uri, value.first_name + " " + value.last_name);
				forClrAdrForm();
				pnlAddr.loadData(value);
				pnlTags.loadData(value.tags);
				pnlTags.setLang(value.lang_id);
			}
		});

		ui(isOwnProfile);
		initWidget(tp);
	}

	private void forSaveTag(final String uri, final String title) {
		btnSaveTag.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				save(uri, title);
			}
		});
	}

	private void forClrAdrForm() {
		btnClrAdrForm.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				pnlAddr.resetForm();
			}
		});
	}

	private void forSaveInfo(final String uri, final String title) {
		btnSaveInfo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				save(uri, title);
			}
		});
	}

	protected void save(String uri, final String title) {
		// TODO gid[] nedir?? su an null
		ContactsDao.change(pnlAddr.getLang(), "cipher", pnlAddr.getFirstName(), pnlAddr.getLastName(), "icon",
				pnlAddr.getEMail(), "", "", "", false, pnlAddr.getAddress(), pnlAddr.getState(), pnlAddr.getCity(),
				pnlAddr.getCountry(), pnlAddr.getPostalCode(), pnlAddr.getOrganization(), pnlAddr.getPhone(),
				pnlAddr.getMobile(), pnlAddr.getFax(), pnlTags.getTags(), null, uri, new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.setStatus(Ctrl.trans.saved());
					}
				});

	}

	private void ui(boolean isOwnProfile) {

		HorizontalPanel contactBtns = new HorizontalPanel();
		contactBtns.setSpacing(3);
		contactBtns.add(btnSaveInfo);
		contactBtns.add(btnClrAdrForm);

		VerticalPanel pnlAddress = new VerticalPanel();
		pnlAddress.add(pnlAddr);
		pnlAddress.add(contactBtns);

		VerticalPanel pnlTag = new VerticalPanel();
		pnlTag.add(pnlTags);
		pnlTag.add(btnSaveTag);

		tp.add(pnlAddress, Ctrl.trans.contact());

		if (isOwnProfile || Role.isAdmin()) {
			tp.add(pnlPassword, Ctrl.trans.changePassword());
		}
		tp.add(pnlTag, Ctrl.trans.tags());
		tp.selectTab(0);

	}

	@Override
	public void addressChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public void tagsChanged() {
		// TODO Auto-generated method stub

	}
}
