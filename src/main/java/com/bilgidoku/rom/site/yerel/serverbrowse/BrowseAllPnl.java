package com.bilgidoku.rom.site.yerel.serverbrowse;

import com.bilgidoku.rom.gwt.client.util.browse.image.BrowseCallback;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.bilgidoku.rom.site.yerel.links.NavLinks;
import com.bilgidoku.rom.site.yerel.lists.NavLists;
import com.bilgidoku.rom.site.yerel.medias.NavFiles;
import com.bilgidoku.rom.site.yerel.writings.NavWriting;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BrowseAllPnl extends Composite {

	private final Button btnCancel = new Button(Ctrl.trans.cancel());
	private final Button btnOk = new Button(Ctrl.trans.ok());
	
	final Button btnClose = new Button("Close");

	final NavLists navList = new NavLists(false, true);
	final NavWriting navWritings = new NavWriting(false, true);
	final NavLinks navLinks = new NavLinks(false, true);
	final NavFiles navFiles = new NavFiles(false, true);

	// public String selectedFile = null;
	TabPanel tabPanel = new TabPanel();
	private BrowseCallback cb;

	public BrowseAllPnl() {
		this(null);
	}
	
	public BrowseAllPnl(BrowseCallback cb) {
		this.cb=cb;
		btnClose.setStyleName("site-closebutton");

		// FlowPanel fp = new FlowPanel();
		// fp.add(btnClose);
		// fp.add(btnOK);

		tabPanel.setWidth("320px");
		tabPanel.setHeight("420px");

		tabPanel.add(navWritings, ClientUtil.getTabTitle(Ctrl.trans.page(), "/_local/images/common/folder_page.png"));
		tabPanel.add(navFiles, ClientUtil.getTabTitle(Ctrl.trans.files(), "/_local/images/common/folder.png"));
		tabPanel.add(navList, ClientUtil.getTabTitle(Ctrl.trans.list(), "/_local/images/common/list.png"));
		tabPanel.add(navLinks, ClientUtil.getTabTitle(Ctrl.trans.link(), "/_local/images/common/link.png"));

		forSelectTab();

		tabPanel.selectTab(0);

		VerticalPanel gp = new VerticalPanel();
		gp.setStyleName("site-innerform");
		gp.setSpacing(10);
		gp.add(tabPanel);
		
		if (Location.getParameter("CKEditor") != null && !Location.getParameter("CKEditor").isEmpty()) {
			
			FlowPanel fp = new FlowPanel();
			fp.add(btnOk);
			fp.add(btnCancel);
			forOk();
			forCancel();
			gp.add(fp);
		}


		initWidget(gp);

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				navWritings.addContainers();
			}
		});
	}
	
	private void forCancel() {
		btnOk.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				cb.selected(null);

			}
		});
	}

	private void forOk() {
		btnOk.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				cb.selected(getSelected());

			}
		});
	}

	private void forSelectTab() {
		tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Integer ind = (Integer) event.getSelectedItem();
//				activeIndex = ind;
				HasContainer seen = (HasContainer) tabPanel.getWidget(ind);
				seen.addContainers();
			}
		});

	}
	//
	// private void forClose() {
	// btnClose.addClickHandler(new ClickHandler() {
	// @Override
	// public void onClick(ClickEvent event) {
	// callback.cancel();
	// }
	// });
	//
	// }

	public String getSelected() {

		NavBase w = (NavBase) tabPanel.getWidget(tabPanel.getTabBar().getSelectedTab());
		TreeItem item = w.getSelectedItem();
		if (item == null) {
			return null;
		}

		SiteTreeItemData data = (SiteTreeItemData) item.getUserObject();

		String selectedFile = data.getUri().replaceFirst(Data.WRITING_ROOT, "");
		if (selectedFile.isEmpty()) {
			selectedFile = "/"; // home page
		}
		return selectedFile;

	}
}
