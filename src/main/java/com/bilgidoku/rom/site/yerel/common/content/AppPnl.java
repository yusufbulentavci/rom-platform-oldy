package com.bilgidoku.rom.site.yerel.common.content;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;

public class AppPnl extends SimplePanel {
	private ListBox listApps = new ListBox(false);

	private final HasResource caller;
	private boolean appHasChanged = false;

	public AppPnl(HasResource tab) {
		for (int i = 0; i < Data.LAYOUTS.length; i++) {
			listApps.addItem(Data.LAYOUTS[i]);
		}

		caller = tab;
		forAppChange();
		this.add(listApps);
	}

	private void forAppChange() {
		listApps.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				setAppHasChanged(true);
				caller.appChanged(listApps.getValue(listApps.getSelectedIndex()));

			}
		});

	}

	// private void loadApps(List<Apps> apps) {
	// for (int i = 0; i < apps.size(); i++) {
	// listApps.addItem(apps.get(i).title, apps.get(i).uri);
	// }
	// }

	public void selectApp(String app) {
		ClientUtil.findAndSelect(listApps, app);
	}

	public String getApp() {
		return listApps.getValue(listApps.getSelectedIndex());
	}

	public boolean appHasChanged() {
		return appHasChanged;
	}

	public void setAppHasChanged(boolean appHasChanged) {
		this.appHasChanged = appHasChanged;
	}

	public void save(String uri) {
		if (appHasChanged()) {
			ResourcesDao.sethtmlfile(getApp(), uri, new StringResponse() {
				@Override
				public void ready(String value) {
				}
			});
		}
	}

}
