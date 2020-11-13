package com.bilgidoku.rom.site.yerel.admin;

import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.InitialsDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.one;
import com.bilgidoku.rom.site.yerel.contacts.TabContact;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabUser extends Composite implements HasWidgets {
	private final StackPanel stackPanel = new StackPanel();

	private final RoleSelect roles;

	public TabUser(String userName, String contactId) {
		getUserRole(userName);
		roles = new RoleSelect(0);
		ui(userName, contactId);
		initWidget(stackPanel);
	}

	private void getUserRole(final String userName) {
		InitialsDao.getusers("/_/_initials", new ArrayResponse<String>() {
			@Override
			public void array(List<String[]> arr) {
				int role = 0;
				for (int i = 0; i < arr.size(); i++) {
					String[] user = arr.get(i);
					// u[0] => userName treeitem.getText()
					// u[1] => contactId data.getUri()
					// role => data.getTitle()

					if (user[0].equals(userName)) {
						try {
							role = Integer.parseInt(user[2]);
						} catch (Exception e) {
							// TODO: handle exception
						}

						break;
					}
				}

				if (role != 0)
					roles.updateRoles(role);

			}
		});

	}

	private void ui(String userName, String contactId) {

		stackPanel.setPixelSize(540, 200);
		stackPanel.setSize("540px", "200px");

		stackPanel.add(getRoleForm(contactId, userName), userName, true);

		stackPanel.add(getPassForm(userName), Ctrl.trans.changePassword(), false);

	}

	private Widget getPassForm(final String userName) {
		// PasswordTextBox rctPass = new PasswordTextBox();
		final PasswordTextBox newPass = new PasswordTextBox();
		final PasswordTextBox newPass2 = new PasswordTextBox();
		Button btnSavePassword = new Button(Ctrl.trans.save());
		Button btnCancel = new Button(Ctrl.trans.cancel());

		btnSavePassword.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (newPass.getValue().isEmpty() || newPass2.getValue().isEmpty()) {
					Window.alert(Ctrl.trans.someValuesEmpty());
					newPass.setFocus(true);
					return;
				}
				if (!newPass.getValue().equals(newPass2.getValue())) {
					Window.alert(Ctrl.trans.differentPasswords());
					newPass.setFocus(true);
					return;
				}

				if (newPass.getValue().length() < 6) {
					Window.alert(Ctrl.trans.tooShort(Ctrl.trans.newPassword()));
					newPass.setFocus(true);
					return;
				}

				if (newPass.getValue().length() > 40) {
					Window.alert(Ctrl.trans.tooLong(Ctrl.trans.newPassword()));
					newPass.setFocus(true);
					return;
				}

				InitialsDao.changepass(userName, newPass.getValue(), "/_/_initials", new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.setStatus(userName + " " + Ctrl.trans.saved());
						newPass.setValue("");
						newPass2.setValue("");
					}
				});
			}
		});
		btnCancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				newPass.setValue("");
				newPass2.setValue("");
			}
		});

		HorizontalPanel btns = new HorizontalPanel();
		btns.setSpacing(3);
		btns.add(btnSavePassword);
		btns.add(btnCancel);
		FlexTable form = new FlexTable();
		form.setHeight("50px");
		// form.setSize("100%", "50px");
		form.setHTML(1, 0, Ctrl.trans.newPassword());
		form.setWidget(1, 1, newPass);
		form.setHTML(2, 0, Ctrl.trans.confirmPassword());
		form.setWidget(2, 1, newPass2);
		form.setWidget(3, 0, btns);
		form.getFlexCellFormatter().setColSpan(3, 0, 2);
		form.addStyleName("site-padding");
		form.getFlexCellFormatter().setWidth(0, 0, "150px");
		return form;
	}

	private Widget getRoleForm(final String contactId, final String userName) {
		Anchor ancContact = new Anchor(Ctrl.trans.seeContactInfo());
		ancContact.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabContact tw = new TabContact(contactId, false);
				Ctrl.openTab(contactId, userName, tw, Data.MAIL_COLOR);
			}
		});

		Button btnSave = new Button(Ctrl.trans.save());
		btnSave.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				InitialsDao.changeroles(userName, roles.getRoles(), "/_/_initials", new StringResponse() {
					@Override
					public void ready(String value) {
						String user=RomEntryPoint.com().get("user");
						if (user.equals(userName)) {
							one.userRole = roles.getRoles();
						}

						Ctrl.setStatus(userName + " " + Ctrl.trans.saved());
						Ctrl.reloadUsers();
					}
				});
			}
		});
		FlexTable form = new FlexTable();
		form.setHeight("50px");
		// form.setSize("100%", "100%");
		form.setHTML(0, 0, Ctrl.trans.role());
		form.setWidget(0, 1, roles);
		form.setWidget(1, 1, btnSave);
		form.setWidget(2, 1, ancContact);
		form.addStyleName("site-padding");
		return form;
	}

	@Override
	public void add(Widget w) {
		stackPanel.add(w);

	}

	@Override
	public void clear() {
		stackPanel.clear();

	}

	@Override
	public Iterator<Widget> iterator() {
		return stackPanel.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return stackPanel.remove(w);
	}

}