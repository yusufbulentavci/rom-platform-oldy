package com.bilgidoku.rom.site.yerel.serverbrowse;

import com.bilgidoku.rom.gwt.client.util.browse.image.BrowseCallback;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.medias.NavFiles;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BrowseFileFolders extends Composite {

	private final Button btnCancel = new Button(Ctrl.trans.cancel());
	private final Button btnOk = new Button(Ctrl.trans.ok());
	
	final Button btnClose = new Button("Close");

	final NavFiles navFiles = new NavFiles(false, false);

	private BrowseCallback cb;

	public BrowseFileFolders() {
		this(null);
	}
	
	public BrowseFileFolders(BrowseCallback cb) {
		this.cb=cb;
		btnClose.setStyleName("site-closebutton");
		
		VerticalPanel gp = new VerticalPanel();
		gp.setStyleName("site-innerform");
		gp.setSpacing(10);
		gp.add(navFiles);
		
		if (Location.getParameter("CKEditor") != null && !Location.getParameter("CKEditor").isEmpty()) {			
			FlowPanel fp = new FlowPanel();
			fp.add(btnOk);
			fp.add(btnCancel);
			forOk();
			forCancel();
			gp.add(fp);
		}

//		navFiles.getTree().addSelectionHandler(new SelectionHandler<TreeItem>() {
//			public void onSelection(SelectionEvent<TreeItem> event) {
//
//				TreeItem ti = event.getSelectedItem();
//				SiteTreeItemData dt = (SiteTreeItemData) ti.getUserObject();
//				if (ti.getParentItem() == null && dt.isContainer()) {
//					getToolbar().rootLevelSelected();
//					return;
//				}
//				
//				if (dt.isContainer()) {
//					getToolbar().containerSelected();
//					return;
//				}
//				
//				
//				getToolbar().itemSelected();
//				
//
//			}
//		});


		initWidget(gp);

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				navFiles.addContainers();
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

	public String getSelected() {		
		TreeItem item = navFiles.getSelectedItem();
		if (item == null) {
			return null;
		}

		SiteTreeItemData data = (SiteTreeItemData) item.getUserObject();
		return data.getUri();

	}
}
