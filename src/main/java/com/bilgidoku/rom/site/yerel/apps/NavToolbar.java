package com.bilgidoku.rom.site.yerel.apps;

import com.bilgidoku.rom.gwt.araci.client.rom.Apps;
import com.bilgidoku.rom.gwt.araci.client.rom.AppsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.AppsResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.client.util.common.AskForValueDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class NavToolbar extends NavToolbarBase {
	private final NavApps nav;

	public NavToolbar(NavApps wa) {
		this.nav = wa;
		Widget[] btns = { btnNew, btnEdit, btnDelete };
		this.add(ClientUtil.getToolbar(btns, 3));
	}

	@Override
	public void deleteItem() {
		final TreeItem toDel = nav.getSelectedItem();
		if (toDel == null) 
			return;
		
		boolean res = Window.confirm(Ctrl.trans.confirmDelete());
		if (!res)
			return;
		final SiteTreeItemData style = (SiteTreeItemData) toDel.getUserObject();
		AppsDao.destroy(style.getUri(), new StringResponse() {
			public void ready(String value) {
				toDel.remove();
				Ctrl.closeTab(style.getUri());
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
	private JSONArray arrStyle = null;
	
	@Override
	public void editItem(final String uri) {		
		AppsDao.get(uri, new AppsResponse() {
			@Override
			public void ready(Apps value) {
//				arrStyle = value.codes.getValue().isArray();
//				TabApps tw = new TabApps(value.title, uri, arrStyle);
//				Ctrl.openTab(uri, value.title, tw, Data.DESIGN_COLOR);
			}			
		});		

	}

	@Override
	public void newItem() {
		final AskForValueDlg dlg = new AskForValueDlg(Ctrl.trans.enterTitle(), Ctrl.trans.ok());
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				final String sTitle = dlg.getValue();
				if (sTitle != null && !sTitle.isEmpty()) {
					AppsDao.neww(sTitle, Data.APP_CONTAINER, new StringResponse() {
						public void ready(String value) {					
							nav.addLeaf(null, value, sTitle);
							editItem(value);
						}				
					});
				}
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
	}

	@Override
	public TreeItem getSelectedItem() {
		return nav.getSelectedItem();
	}

}
