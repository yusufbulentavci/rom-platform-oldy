package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

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

public class BrowseContainers extends BrowseBase {

	public BrowseContainers() {
		super();
	}

	@Override
	protected void addToTabs() {
		final NavLists navLists = new NavLists(false, false);
		final NavWriting navWritings = new NavWriting(false, false);		
		final NavLinks navLinks = new NavLinks(false, false);
		final NavFiles navFiles = new NavFiles(false, false);

		tabPanel.setWidth("320px");		
		tabPanel.setHeight("500px");
		
		tabPanel.add(navFiles, Ctrl.trans.media());
		tabPanel.add(navLists, Ctrl.trans.lists());
		tabPanel.add(navWritings, Ctrl.trans.writing());
		tabPanel.add(navLinks, Ctrl.trans.link());
		
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
		
		if (!data.isContainer()) {
			Window.alert(Ctrl.trans.selectAContainer());
			return false;

		}

		selected = data.getUri();
		return true;

	}

	public String getSelected() {
		return selected;
	}
}
