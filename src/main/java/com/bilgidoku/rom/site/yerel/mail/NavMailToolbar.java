package com.bilgidoku.rom.site.yerel.mail;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.MailsDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.help.Helpy;
import com.bilgidoku.rom.gwt.client.util.help.NeedHelp;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class NavMailToolbar extends NavToolbarBase implements NeedHelp {
	private final NavMailBox nav;

	public NavMailToolbar(NavMailBox wa) {
		// forEditContainer();
		this.nav = wa;
		btnNew.changeTitle(Ctrl.trans.compose());
		btnNew.setHTML(ClientUtil.imageItemHTML("/_local/images/common/send_mail.png", ""));
		// Widget[] btns = { btnNew, btnNewContainer, btnReload };
		Widget[] btns = { btnNew, btnReload };
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
		SiteTreeItemData cont = (SiteTreeItemData) toDel.getUserObject();
		if (!cont.isContainer())
			return;

		MailsDao.destroy(cont.getUri(), new StringResponse() {
			public void ready(String value) {
//				nav.getTree().getSelectedItem().remove();
			}
		});
	}

	@Override
	public void editSelectedItem() {
		TreeItem item = nav.getSelectedItem();
		MailTreeItemData data = (MailTreeItemData) item.getUserObject();
		TabMailList tw = new TabMailList(data.getUri(), nav);
		Ctrl.closeAndOpenTab(data.getUri(), ClientUtil.getTitleFromUri(data.getUri()), tw, Data.MAIL_COLOR);
	}

	@Override
	public void newItem() {
		TabNewMail tw = new TabNewMail(null, null, null, null);
		Ctrl.openTab("New Mail", Ctrl.trans.newMail(), tw, Data.MAIL_COLOR);
	}

	@Override
	public void renameItem() {
	}

	@Override
	public void newContainer() {
		// final NewPageDlg dlg = new NewPageDlg("", "",
		// nav.getHolderUriPrefix(), Data.LIST_ROOT, false,
		// true, Ctrl.trans.newMail());
		// dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
		// @Override
		// public void onClose(CloseEvent<PopupPanel> event) {
		// String title = dlg.getNamed();
		// if (title != null && !title.isEmpty()) {
		// createContainer(title, dlg.getFixedTitle(), dlg.getTemplate(),
		// dlg.isPrivate(), dlg.toTop());
		// }
		// }
		//
		// });

	}

	protected void createContainer(String titl, String fixedTitle, String template, boolean isPrivate,
			final boolean toTop) {
		//
		// String uri = "";
		// String delegated = "";
		// if (toTop) {
		// uri = Data.LIST_ROOT + "/" +fixedTitle;
		// delegated = "";
		// } else if (nav.getHolderUriPrefix() != null) {
		// uri = nav.getHolderUriPrefix() + fixedTitle;
		// delegated = nav.getHolderUri();
		// } else {
		// Window.alert(Ctrl.trans.selectAContainer());
		// return;
		// }
		//
		// MailsDao.breed(uri, isPrivate ? Data.WRITING_PRIVATE_MASK :
		// Data.WRITING_PUBLIC_MASK, delegated,
		// new ContainersResponse() {
		// @Override
		// public void ready(Containers value) {
		// TreeItem parent = nav.getHolderContainer();
		// if (toTop)
		// parent = null;
		// nav.addContainer(parent, value, false, false);
		// }
		// });
	}

	@Override
	public void reloadContainer(TreeItem item) {
//		nav.addContainers();
	}

	@Override
	public void editItem(String uri) {
		// TODO Auto-generated method stub

	}

	@Override
	public Helpy[] helpies() {
		return new Helpy[] {};
	}

	@Override
	public TreeItem getSelectedItem() {
		return nav.getSelectedItem();
	}

}
