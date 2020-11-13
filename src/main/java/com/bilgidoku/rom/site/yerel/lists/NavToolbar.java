package com.bilgidoku.rom.site.yerel.lists;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Contents;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsResponse;
import com.bilgidoku.rom.gwt.araci.client.site.ListsDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.NewItemDlg;

import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class NavToolbar extends NavToolbarBase {
	private final NavLists nav;

	public NavToolbar(NavLists wa) {
		this.nav = wa;
		btnNew.setHelpy("liste_yeni.mp4");

		Widget[] btns = { btnNewContainer, btnReload, btnNew, ClientUtil.getSeperator(), btnEdit, btnDelete,
				btnCopyLink };
		this.add(ClientUtil.getToolbar(btns, 8));
	}

	@Override
	public void deleteItem() {
		final TreeItem toDel = nav.getSelectedItem();
		if (toDel == null)
			return;

		final SiteTreeItemData data = (SiteTreeItemData) toDel.getUserObject();
		boolean res = Window.confirm(Ctrl.trans.confirmDelete());
		if (!res)
			return;
		if (data.isContainer())
			ListsDao.extinct(data.getUri(), new StringResponse() {
				public void ready(String value) {
					nav.getTree().getSelectedItem().remove();
					// Ctrl.closeTab(data.getUri());
				}
			});
		else
			ListsDao.destroy(data.getUri(), new StringResponse() {
				public void ready(String value) {
					nav.getTree().getSelectedItem().remove();
					Ctrl.closeTab(data.getUri());
				}
			});

	}

	@Override
	public void editSelectedItem() {
		TreeItem selItem = nav.getSelectedItem();
		if (selItem == null)
			return;

		SiteTreeItemData selNode = (SiteTreeItemData) selItem.getUserObject();
		editItem(selNode.getUri());
	}

	@Override
	public void newItem() {

		final TreeItem selItem = nav.getHolderContainer();
		if (selItem == null)
			return;

		SiteTreeItemData cont = (SiteTreeItemData) selItem.getUserObject();
		final NewItemDlg dlg = new NewItemDlg(cont.getUriPrefix(), Ctrl.trans.newList(), false, null, cont.getUri());
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if (dlg.getUri() != null && !dlg.getUri().isEmpty()) {
					ListsDao.neww(Ctrl.infoLang(), dlg.getNamed(), dlg.getUri(), dlg.getDelegated(),
							new StringResponse() {
								public void ready(String value) {
									nav.addLeaf(selItem, value, dlg.getNamed(), true);
									editItem(value);
								}
							});
				}
			}
		});

	}

	@Override
	public void renameItem() {
		// TODO Auto-generated method stub
	}

	@Override
	public void newContainer() {
		final NewItemDlg dlg = new NewItemDlg(nav.getHolderUriPrefix(), Ctrl.trans.newContainer(), true,
				Data.LIST_ROOT + "/", nav.getHolderUri());
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {

				if (dlg.getUri() != null && !dlg.getUri().isEmpty()) {
					ListsDao.breed(dlg.getUri(), Data.WRITING_PRIVATE_MASK, dlg.getDelegated(),
							new ContainersResponse() {
								@Override
								public void ready(Containers value) {
									Ctrl.setStatus(Ctrl.trans.added(dlg.getNamed()));
									TreeItem parent = nav.getHolderContainer();
									if (dlg.getIsOnTop())
										parent = null;
									nav.addContainer(parent, value, true, false);
								}
							});

				}
			}

		});
	}

	@Override
	public void reloadContainer(TreeItem item) {
		boolean reload = false;
		if (item == null) {
			item = nav.getHolderContainer();
			reload = true;
		}

		final TreeItem parent = item;
		final SiteTreeItemData pd = (SiteTreeItemData) parent.getUserObject();

		if (pd == null) {
			parent.removeItems();
			// dummy container
			nav.addContainers();
		} else {

			if ((item.getChildCount() == 1 && item.getChild(0).getText().equals("!")) || reload) {

				parent.removeItems();

				ContainersDao.listsub(pd.getUri(), "/_/c", new ContainersResponse() {
					@Override
					public void array(List<Containers> value) {
						for (int i = 0; i < value.size(); i++) {
							Containers con = value.get(i);
							nav.addContainer(parent, con, true, false);
						}
					}
				});

				if (nav.listItems)
					ListsDao.list(Ctrl.infoLang(), "", pd.getUri(), new ContentsResponse() {
						public void array(List<Contents> value) {
							for (int i = 0; i < value.size(); i++) {
								Contents s = value.get(i);
								nav.addLeaf(parent, s.uri, s.title[0], true);
							}
						}
					});

				// TODO bu aslında asenkron çağrıların ardında olmalı
				parent.setState(true, false);
			}

		}

	}

	@Override
	public void editItem(String uri) {
		TabList tw = new TabList(uri);
		Ctrl.openTab(uri, ClientUtil.getTitleFromUri(uri), tw, Data.CONTENT_COLOR);
	}

	@Override
	public TreeItem getSelectedItem() {
		return nav.getSelectedItem();
	}

}
