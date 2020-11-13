package com.bilgidoku.rom.site.yerel.wgts.edit.input;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public abstract class BrowseBase extends ActionBarDlg {

	public final TabPanel tabPanel = new TabPanel();
	public String selected = null;

	public BrowseBase() {
		super(Ctrl.trans.writings(), null, Ctrl.trans.ok());
		tabPanel.setSize("250px", "300px");
		run();
	}

	@Override
	public Widget ui() {
		forSelectTab();
		addToTabs();
		return tabPanel;
	}

	@Override
	public void ok() {
		
		if (selected != null)
			return;
		
		NavBase w = (NavBase) tabPanel.getWidget(tabPanel.getTabBar().getSelectedTab());

		TreeItem item = w.getSelectedItem();
		if (item == null) {
			BrowseBase.this.hide();
			return;
		}

		if (!itemSelected(item))
			return;
		
	}
	
	@Override
	public void cancel() {
	}
	
	protected abstract void addToTabs();

	private void forSelectTab() {
		tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Integer ind = (Integer) event.getSelectedItem();
				if (tabPanel.getWidget(ind) instanceof HasContainer) {
					HasContainer seen = (HasContainer) tabPanel.getWidget(ind);
					seen.addContainers();
				}
			}
		});

	}

	protected abstract boolean itemSelected(TreeItem item);
}
