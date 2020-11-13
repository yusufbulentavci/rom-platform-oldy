package com.bilgidoku.rom.site.yerel.tags;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Tags;
import com.bilgidoku.rom.gwt.araci.client.rom.TagsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.TagsResponse;
import com.bilgidoku.rom.gwt.client.util.common.AskForValueDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
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

	private final NavTags nav;

	public NavToolbar(NavTags wa) {
		this.nav = wa;

		Widget[] btns = { btnNew, btnReload, ClientUtil.getSeperator(), btnEdit, btnDelete, btnCopyLink };
		this.add(ClientUtil.getToolbar(btns, 7));
	}

	@Override
	public void deleteItem() {
		final TreeItem toDel = nav.getSelectedItem();
		if (toDel == null)
			return;
		boolean res = Window.confirm(Ctrl.trans.confirmDelete());
		if (!res)
			return;
		SiteTreeItemData tag = (SiteTreeItemData) toDel.getUserObject();
		TagsDao.destroy(tag.getUri(), new StringResponse() {
			public void ready(String value) {
				toDel.remove();
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
	public void editItem(final String uri) {
		TabTag dlg = new TabTag(uri);
		Ctrl.openTab(uri, ClientUtil.getTitleFromUri(uri), dlg, Data.CONTENT_COLOR);
	}

	@Override
	public void newItem() {
		
		final AskForValueDlg dlg = new AskForValueDlg(Ctrl.trans.newItem(), "");
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				final String sTitle = dlg.getValue();
				if (sTitle == null || sTitle.isEmpty())
					return;

				TagsDao.neww(Ctrl.infoLang(), sTitle, null, "/_/tags", new StringResponse() {
					public void ready(String value) {
						Ctrl.setStatus(sTitle + " " + Ctrl.trans.saved());
						reloadContainer(null);
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
		nav.getTree().removeItems();
		TagsDao.list(Ctrl.infoLang(), Data.TAG_ROOT, new TagsResponse() {
			@Override
			public void array(List<Tags> value) {
				for (int i = 0; i < value.size(); i++) {
					Tags tag = value.get(i);
					nav.addLeaf(null, tag.uri, tag.title[0]);
				}

			}
		});
	}

	@Override
	public TreeItem getSelectedItem() {
		return nav.getSelectedItem();
	}

}
