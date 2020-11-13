package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.links.NavLinks;
import com.bilgidoku.rom.site.yerel.lists.NavLists;
import com.bilgidoku.rom.site.yerel.medias.NavFiles;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.BrowseBase;
import com.bilgidoku.rom.site.yerel.writings.NavWriting;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TreeItem;

public class BrowseTag extends BrowseBase {


	public BrowseTag() {
		super();
	}

	@Override
	protected void addToTabs() {
		//list olarak kullanılabilecek: containers and list
		final NavLists navList = new NavLists(false, true);
		final NavWriting navWritings = new NavWriting(false, false);
		final NavLinks navLinks = new NavLinks(false, false);
		final NavFiles navFiles = new NavFiles(false, false);

		tabPanel.add(navList, ClientUtil.getTabTitle(Ctrl.trans.list(), "/_local/images/common/list.png"));
		tabPanel.add(navWritings, ClientUtil.getTabTitle(Ctrl.trans.page(), "/_local/images/common/folder_page.png"));		
		tabPanel.add(navFiles, ClientUtil.getTabTitle(Ctrl.trans.folder(), "/_local/images/common/folder.png"));
		tabPanel.add(navLinks, ClientUtil.getTabTitle(Ctrl.trans.link(), "/_local/images/common/link.png"));

		tabPanel.selectTab(0);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				navList.addContainers();
			}
		});
		
	}

	@Override
	protected boolean itemSelected(TreeItem item) {
		SiteTreeItemData data = (SiteTreeItemData) item.getUserObject();
		selected = data.getUri();
		
		if (selected.startsWith(Data.LIST_ROOT) && data.isContainer()) {
			selected = null;
			Window.alert(Ctrl.trans.selectAnItem());
			return false;
		}
		return true;
		
	}
}
