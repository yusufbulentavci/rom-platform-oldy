package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.links.NavLinks;
import com.bilgidoku.rom.site.yerel.medias.NavFiles;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.BrowseBase;
import com.bilgidoku.rom.site.yerel.writings.NavWriting;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.TreeItem;

public class BrowseAll extends BrowseBase {

	public BrowseAll() {
		super();
	}

	@Override
	protected void addToTabs() {
		// all items
//		final NavLists navList = new NavLists(false, true);
		final NavWriting navWritings = new NavWriting(false, true);
		final NavLinks navLinks = new NavLinks(false, true);
		final NavFiles navFiles = new NavFiles(false, true);

		tabPanel.add(navWritings, ClientUtil.getTabTitle(Ctrl.trans.page(), "/_local/images/common/folder_page.png"));
		tabPanel.add(navFiles, ClientUtil.getTabTitle(Ctrl.trans.folder(), "/_local/images/common/folder.png"));
		// tabPanel.add(navList, ClientUtil.getTabTitle(Ctrl.trans.list(),
		// "/_local/images/common/list.png"));
		tabPanel.add(navLinks, ClientUtil.getTabTitle(Ctrl.trans.link(), "/_local/images/common/link.png"));

		tabPanel.selectTab(0);

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				navWritings.addContainers();
			}
		});

	}

	@Override
	protected boolean itemSelected(TreeItem item) {
		SiteTreeItemData data = (SiteTreeItemData) item.getUserObject();
		int tab = tabPanel.getTabBar().getSelectedTab();
		if (tab == 0) {
			if (data.isContainer()) {
				selected = data.getUriPrefix();
				if (data.getUriPrefix().length() > 1)
					selected = data.getUriPrefix().substring(0, data.getUriPrefix().length()-1);
			} else
				selected = data.getUri();
			return true;
		} else if (tab == 1 || tab == 2) {
			if (data.isContainer()) {
				return false;
			}
			selected = data.getUri();
			return true;
		} else {
			selected = data.getUri();
			return true;
		}

	}
}
