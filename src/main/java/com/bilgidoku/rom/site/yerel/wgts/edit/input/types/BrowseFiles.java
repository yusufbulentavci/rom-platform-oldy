package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.medias.NavFiles;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.BrowseBase;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TreeItem;

public class BrowseFiles extends BrowseBase {
	
	public BrowseFiles() {
		super();
	}

	@Override
	public void addToTabs() {
		final NavFiles navFiles = new NavFiles(false, true);
		tabPanel.add(navFiles, Ctrl.trans.files());
		tabPanel.selectTab(0);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {			
			@Override
			public void execute() {
				navFiles.addContainers();
			}
		});

	}


	@Override
	protected boolean itemSelected(TreeItem item) {
		SiteTreeItemData data = (SiteTreeItemData) item.getUserObject();
		selected = data.getUri();
		
		if (data.isContainer()) {
			selected = null;
			Window.alert(Ctrl.trans.selectAnItem());
			return false;
		}
		return true;
		
	}
}
