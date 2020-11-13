package com.bilgidoku.rom.site.yerel.contacts;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.mail.TabNewMail;
import com.bilgidoku.rom.site.yerel.mail.core.InternetAddress;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class NavConToolbar extends NavToolbarBase {
	private final NavCon nav;

	private final SiteToolbarButton btnSMS = new SiteToolbarButton("/_local/images/common/mobile_phone.png", "", Ctrl.trans.send(),"");
	private final SiteToolbarButton btnEMail = new SiteToolbarButton("/_local/images/common/send_mail.png", "", Ctrl.trans.send(),"");
	private final SiteToolbarButton btnChat = new SiteToolbarButton("/_local/images/common/speech_bubble.png", "", Ctrl.trans.chat(),"");

	private final TextBox txtSearch = new TextBox();
	private final SiteToolbarButton btnSearch = new SiteToolbarButton("/_local/images/common/search_.png", "", Ctrl.trans.search(),"");

	public NavConToolbar(NavCon wa) {
		this.nav = wa;

		Widget[] btns = { btnNew, btnEdit, btnDelete, btnSMS, btnEMail, btnChat, btnReload,
				new HTML(Ctrl.trans.search()), txtSearch, btnSearch };
		this.add(ClientUtil.getToolbar(btns, 7));

		forSendSMStoContact();
		forSendMailtoContact();
		forChat();
		forSearch();
		forSearchEnter();

	}

	@Override
	public void deleteItem() {
		final TreeItem toDel = nav.getSelectedItem();
		if (toDel == null)
			return;

		boolean res = Window.confirm(Ctrl.trans.confirmDelete());
		if (!res)
			return;

		final SiteTreeItemData contact = (SiteTreeItemData) toDel.getUserObject();
		ContactsDao.destroy(contact.getUri(), new StringResponse() {
			public void ready(String value) {
				toDel.remove();
				Ctrl.closeTab(contact.getUri());
			}
		});

	}

	@Override
	public void editSelectedItem() {
		TreeItem selItem = nav.getSelectedItem();
		if (selItem == null) {
			Window.alert(Ctrl.trans.selectAnItem());
			return;
		}
		final SiteTreeItemData contact = (SiteTreeItemData) selItem.getUserObject();
		TabContact tw = new TabContact(contact.getUri(), false);
		Ctrl.openTab(contact.getUri(), selItem.getText(), tw,  Data.MAIL_COLOR);
	}

	@Override
	public void editItem(final String uri) {
	}

	@Override
	public void newItem() {
		DlgAskName dlg = new DlgAskName(null);
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				final DlgAskName pp = (DlgAskName) event.getTarget();
				if (pp.firstName != null && !pp.firstName.isEmpty()) {
					/**
					 * TODO: Country değişmeli
					 */
					ContactsDao.neww(Ctrl.infoLang(), "TR", pp.email, null, pp.firstName, pp.lastName,"","", null, "/_/co", 
							new StringResponse() {
								public void ready(String value) {
									Contacts con = new Contacts();
									con.first_name = pp.firstName;
									con.last_name = pp.lastName;
									con.email = pp.email;
									con.uri = value;
									nav.addContact(con);
								};
							});
				}
			}
		});
		dlg.show();
	}

	@Override
	public void renameItem() {
	}

	@Override
	public void newContainer() {
	}

	@Override
	public void reloadContainer(TreeItem item) {
		final TreeItem con = nav.getHolderContainer();
		if (con == null)
			return;

		con.removeItems();

		ContactsDao.list("", Data.CONTACT_CONTAINER, new ContactsResponse() {
			@Override
			public void array(List<Contacts> value) {

				for (int i = 0; i < value.size(); i++) {
					Contacts con = value.get(i);
					nav.addContact(con);
				}
			}
		});

	}

	private final void forSearch() {
		btnSearch.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				search();
			}
		});
	}

	protected void search() {
		String str = txtSearch.getValue();
		nav.getTree().removeItems();
		ContactsDao.list(str, Data.CONTACT_CONTAINER, new ContactsResponse() {
			@Override
			public void array(List<Contacts> value) {
				for (int i = 0; i < value.size(); i++) {
					Contacts con = value.get(i);
					nav.addContact(con);
				}
			}
		});

	}

	private final void forSendMailtoContact() {
		btnEMail.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendMail();
			}
		});
	}

	private final void forSendSMStoContact() {
		btnSMS.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert(Ctrl.trans.notImplemented());

			}
		});
	}

	private void forSearchEnter() {
		txtSearch.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode()) {
					search();
				}
			}
		});
	}

	private void forChat() {
		btnChat.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				startChat();
			}
		});

	}


	public void sendMail() {
		SiteTreeItemData con = (SiteTreeItemData) nav.getSelectedItem().getUserObject();
		InternetAddress ia = new InternetAddress(con.getTitle(), con.getTextFromUri());

		TabNewMail tw = new TabNewMail(null, new InternetAddress[] { ia }, null, null);
		Ctrl.openTab("New Mail", Ctrl.trans.newMail(), tw, Data.MAIL_COLOR);
	}

	public void startChat() {
		final SiteTreeItemData con = (SiteTreeItemData) nav.getSelectedItem().getUserObject();
//		OturumIciCagriDao.rtdlgcreate("im", "", false, false, null, new String[] { con.getUri() }, new StringResponse() {
//			@Override
//			public void ready(String value) {
//
//				TabChat tc = new TabChat(value);
//				Ctrl.openTab(value, "Chat with " + con.getTitle(), tc, "/_local/images/common/picture(), Data.MAIL_COLOR);
//			}
//
//		});
	}

	@Override
	public void itemSelected() {
		super.itemSelected();
		btnChat.setEnabled(true);
		btnEMail.setEnabled(true);
		btnSMS.setEnabled(true);
	}

	@Override
	public void containerSelected() {
		super.containerSelected();
		btnChat.setEnabled(false);
		btnEMail.setEnabled(false);
		btnSMS.setEnabled(false);

	}

	@Override
	public TreeItem getSelectedItem() {
		return nav.getSelectedItem();
	}

}
