package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.BrowseBase;
import com.bilgidoku.rom.site.yerel.writings.NavWriting;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.TreeItem;

public class BrowsePages extends BrowseBase {

	public BrowsePages() {
		super();
	}

	@Override
	protected void addToTabs() {
		final NavWriting navWriting = new NavWriting(false, true);
		tabPanel.add(navWriting, Ctrl.trans.page());
		tabPanel.selectTab(0);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				navWriting.addContainers();
			}
		});

	}

	@Override
	protected boolean itemSelected(TreeItem item) {
		SiteTreeItemData data = (SiteTreeItemData) item.getUserObject();
		selected = data.getUri();
		
		if (data.isContainer()) {
			selected = null;
			return false;
		}
		
		if (selected.equals(Data.WRITING_ROOT))
			selected = "/";
		else
			selected = selected.replaceFirst(Data.WRITING_ROOT, "");

		return true;

	}
}
