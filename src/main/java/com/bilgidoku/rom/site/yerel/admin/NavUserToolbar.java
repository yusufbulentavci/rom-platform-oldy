package com.bilgidoku.rom.site.yerel.admin;


import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.InitialsDao;
import com.bilgidoku.rom.gwt.client.util.common.AskForValueDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.gwt.client.util.help.Helpy;
import com.bilgidoku.rom.gwt.client.util.help.NeedHelp;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.one;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class NavUserToolbar extends NavToolbarBase implements NeedHelp{
	private final NavUsers nav;

	public NavUserToolbar(NavUsers wa) {
		this.nav = wa;
		Widget[] btns = { btnNew, btnReload, ClientUtil.getSeperator(), btnEdit, btnDelete };
		this.add(ClientUtil.getToolbar(btns, 5));
	}

	@Override
	public void deleteItem() {
		final TreeItem toDel = nav.getSelectedItem();
		if (toDel == null)
			return;

		boolean res = Window.confirm(Ctrl.trans.confirmDelete());
		if (!res)
			return;

		final String userName = toDel.getText().trim();

		InitialsDao.removeuser(userName, "/_/_initials", new StringResponse() {
			@Override
			public void ready(String value) {
				toDel.remove();
				Ctrl.closeTab(userName);
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
		String userName = selItem.getText().trim();
		SiteTreeItemData u = (SiteTreeItemData) selItem.getUserObject();
		edit1Item(userName, u.getUri());
	}

	@Override
	public void editItem(final String uri) {
//		Window.alert("edit ıtem");
//		editSelectedItem();
	}
	
	private void edit1Item(String userName, String contactId) {
		TabUser tw = new TabUser(userName, contactId);
		Ctrl.openTab(userName, userName, tw, Data.HOME_COLOR);
	}

	@Override
	public void newItem() {
		final AskForValueDlg dlg = new AskForValueDlg(Ctrl.trans.userName(), Ctrl.trans.checkUserName());
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				final String usr = dlg.getValue();
				if (usr == null || usr.isEmpty())
					return;
				
				if (!ClientUtil.isValidUserName(usr)) {
					Window.alert(Ctrl.trans.regexpUserName());
					return;
				}

				if (usr.length() < 1) {
					Window.alert(Ctrl.trans.tooShort(Ctrl.trans.userName()));
					return;
				}

				if (usr.length() > 64) {
					Window.alert(Ctrl.trans.tooLong(Ctrl.trans.userName()));
					return;
				}

				if (usr.equals("abuse") || usr.equals("postmaster") || usr.equals("rombot")) {
					Window.alert(Ctrl.trans.forbiddenUserNames());
					return;
				}

				/**
				 * TODO: country değişmeli
				 */
				InitialsDao.adduser(Ctrl.info().langcodes[0], "TR", usr, Data.INITIAL_PASSWORD,
						one.userMail, usr, "", "", "","","/_/_initials", new StringResponse() {
							@Override
							public void ready(String contactId) {
								if (nav.getTree().getItemCount() <= 0) {
									// first user is admin
									int role = 1 << Data.ADMINROLE;
									nav.addLeaf(null, contactId, role + "", usr, null, true);									
								} else {
									Ctrl.reloadUsers();
								}
								edit1Item(usr, contactId);
							}
							
						});

			}
		});

	}

	@Override
	public void renameItem() {
	}

	@Override
	public void newContainer() {
	}

	@Override
	public void reloadContainer(TreeItem item) {
		nav.addContainers();

	}

	@Override
	public Helpy[] helpies() {
		return new Helpy[]{btnNew.getHelpy(), btnEdit.getHelpy()};
	}

	@Override
	public TreeItem getSelectedItem() {
		return nav.getSelectedItem();
	}

}
