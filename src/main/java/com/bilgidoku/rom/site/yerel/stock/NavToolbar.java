package com.bilgidoku.rom.site.yerel.stock;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksDao;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksResponse;
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
	private final NavStock nav;

	public NavToolbar(NavStock wa) {
		this.nav = wa;

		Widget[] btns = { btnNew, btnCopyLink };
		this.add(ClientUtil.getToolbar(btns, 2));
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
		// delete virtuals first
		// TODO filelar da silinmeli.
		StocksDao.virtualslist(data.getUri(), new StocksResponse() {
			@Override
			public void array(List<Stocks> myarr) {
				StocksDao.destroy(data.getUri(), new StringResponse() {
					@Override
					public void ready(String value) {
						nav.getTree().getSelectedItem().remove();
					}
				});

				for (int i = 0; i < myarr.size(); i++) {
					Stocks ele = myarr.get(i);
					StocksDao.destroy(ele.uri, new StringResponse());
				}
			}
		});

	}

	@Override
	public void editSelectedItem() {
		TreeItem selItem = nav.getSelectedItem();
		if (selItem == null)
			return;

		SiteTreeItemData selNode = (SiteTreeItemData) selItem.getUserObject();
		editItem(selNode.getUri(), selNode.getTitle());
	}

	@Override
	public void newItem() {

		final DlgNewStock dlg = new DlgNewStock(nav.getSelectedItem() != null ? nav.getSelectedItem().getText() : null);
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				final String title = dlg.getStockTitle();
				if (title == null || title.isEmpty())
					return;

				StocksDao.neww(null, null, null, 1, null, "/_/_stocks", new StringResponse() {
					@Override
					public void ready(final String stockUri) {

						StocksDao.settitle(title, stockUri, new BooleanResponse());

						if (!dlg.isUnder()) {
							nav.addContainer(null, 0L, stockUri, stockUri, true, true, title);
							editItem(stockUri);

						} else {

							final SiteTreeItemData data = ((SiteTreeItemData) nav.getSelectedItem().getUserObject());
							StocksDao.setfirststock(data.getUri(), stockUri, new BooleanResponse() {
								@Override
								public void ready(Boolean ret) {
									Ctrl.setStatus("set first stock" + data.getUri());
									nav.addLeaf(nav.getSelectedItem(), stockUri, title, title, null, true);
									editItem(stockUri, title);

								}
							});
						}
					}
				});
			}
		});
	}

	@Override
	public void renameItem() {
		// TODO Auto-generated method stub
	}

	@Override
	public void newContainer() {
	}

	@Override
	public void reloadContainer(TreeItem item) {

		if (item == null) {
			item = nav.getHolderContainer();
		}

		final TreeItem parent = item;
		if (parent.getChildCount() == 1 && item.getChild(0).getText().equals("!")) {

			final SiteTreeItemData pd = (SiteTreeItemData) parent.getUserObject();
			if (pd == null)
				return;

			if (nav.lastStock.size() <= 0) {
				item.getChild(0).remove();
				return;
			}
			
			item.setState(false, false);
			
			for (int i = 0; i < nav.lastStock.size(); i++) {
				Stocks st = nav.lastStock.get(i);
				if (st.firststock.equals(pd.getUri())) {
					nav.addLeaf(parent, st.uri, st.title, st.title, null, true);
				}
			}
			item.getChild(0).remove();

			item.setState(true, false);


		}

	}

	@Override
	public void editItem(String uri) {
		TabStock tw = new TabStock(uri);
		Ctrl.openTab(uri, ClientUtil.getTitleFromUri(uri), tw, Data.CONTENT_COLOR);
	}

	public void editItem(String uri, String title) {
		TabStock tw = new TabStock(uri);
		Ctrl.openTab(uri, title, tw, Data.CONTENT_COLOR);
	}

	@Override
	public TreeItem getSelectedItem() {
		return nav.getSelectedItem();
	}

	@Override
	public void itemSelected() {
		super.itemSelected();

	}

	@Override
	public void containerSelected() {
		super.containerSelected();

	}
}
