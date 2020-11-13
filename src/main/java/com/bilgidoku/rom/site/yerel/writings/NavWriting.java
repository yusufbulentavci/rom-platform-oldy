package com.bilgidoku.rom.site.yerel.writings;

import java.util.Iterator;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.bilgidoku.rom.site.yerel.common.NavTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.DropOnTree;
import com.bilgidoku.rom.gwt.client.util.common.DropOnTreeHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class NavWriting extends NavBase implements HasContainer {

	private boolean containersAdded = false;

	public NavWriting() {
		super("/_local/images/common/writing.png", "/_local/images/common/folder_page.png",
				"/_local/images/common/folder_page_locked.png", true, 1);
		getTree().makeDroppable();
		forDrop();
	}

	public NavWriting(boolean hasToolbar, boolean showItems) {
		super("/_local/images/common/writing.png", "/_local/images/common/folder_page.png",
				"/_local/images/common/folder_page_locked.png", hasToolbar, 1);
		this.listItems = showItems;
	}

	private void forDrop() {
		getTree().addHandler(new DropOnTreeHandler() {
			public void dropOnTree(final DropOnTree event) {
				final TreeItem dragItem = getTree().getSelectedItem();
				SiteTreeItemData dragItemData = (SiteTreeItemData) dragItem.getUserObject();

				if (dragItemData.isContainer()) {
					Window.alert("Container move is not allowed");
					return;
				}

//				final TreeItem sourceItem = dragItem.getParentItem();

				final TreeItem targetItem = event.targetNode;
				if (targetItem == null)
					return;

				final SiteTreeItemData targetData = (SiteTreeItemData) targetItem.getUserObject();

				final String dragData = event.baseEvent.getData("text");
				
				if (!targetData.isContainer()) {
					//make target a container
					TreeItem parent = targetItem.getParentItem();
					final String parentContainer = ((SiteTreeItemData) parent.getUserObject()).getUri();
					
					final String uri = Data.WRITING_ROOT + targetData.getUri();
					final String uriPrefix = targetData.getUri() + "/";

					WritingsDao.breed(uri, Ctrl.infoLang(), Data.WRITING_PUBLIC_MASK, uriPrefix, "w:standart", targetData.getTitle(), parentContainer,
							new ContainersResponse() {
								@Override
								public void ready(Containers value) {
									Ctrl.setStatus("container created" + uri);

									NavTreeItem prnt = (NavTreeItem) targetItem;
									prnt.changeIcon("/_local/images/common/folder_page.png");
									prnt.setUserObject(new SiteTreeItemData(targetData.getTitle(), uri, true, uriPrefix));
									prnt.addTextItem("!");

									moveSourceHere(dragData, uri, prnt, dragItem.getParentItem());

								}
							});
				} else {
					moveSourceHere(dragData, targetData.getUri(), targetItem, dragItem.getParentItem());
				}
				
				
				
			}
		}, DropOnTree.TYPE);
	}

	private void moveSourceHere(String sourceUri, String targetUri, final TreeItem target, final TreeItem source) {
		ResourcesDao.changecontainer(targetUri, sourceUri, new StringResponse() {
			@Override
			public void ready(String value) {
				Ctrl.setStatus("page moved, reload containers");
				//reload source
				getTree().setSelectedItem(null, false);
				getTree().setSelectedItem(source);
				getToolbar().reloadContainer(null);

				Scheduler.get().scheduleDeferred(new ScheduledCommand() {					
					@Override
					public void execute() {
						getTree().setSelectedItem(null, false);
						getTree().setSelectedItem(target);
						getToolbar().reloadContainer(null);
						
					}
				});
			}
		});		
		
	} 
	
	@Override
	public void addContainers() {

		if (containersAdded)
			return;

		containersAdded = true;
		
		getTree().removeItems();

		final TreeItem home = addWritingHome();	

		getToolbar().buttonsStates(false);

		getToolbar().reloadContainer(home);		
		
		

	}

	@Override
	public NavToolbarBase createToolbar() {
		NavToolbar tb = new NavToolbar(this);
		return tb;
	}

	@Override
	public void add(Widget w) {
		getToolbar().add(w);

	}

	@Override
	public void clear() {
		getToolbar().clear();

	}

	@Override
	public Iterator<Widget> iterator() {
		return getToolbar().iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return getToolbar().remove(w);
	}

	public SiteTreeItemData getSelectedData() {
		SiteTreeItemData data = ((SiteTreeItemData) this.getSelectedItem().getUserObject());
		return data;
	}

}
