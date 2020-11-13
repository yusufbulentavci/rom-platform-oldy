package com.bilgidoku.rom.site.yerel.medias;

import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Files;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.DropOnTreeHandler;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.bilgidoku.rom.gwt.client.util.common.DropOnTree;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class NavFiles extends NavBase implements HasContainer {
	private boolean containersAdded = false;

	public NavFiles() {
		super("/_local/images/common/image.png", "/_local/images/common/folder.png",
				"/_local/images/common/folder_key.png", true, 1);
		forDrop();
		getTree().makeDroppable();
	}

	public NavFiles(boolean noToolbar, boolean showItems) {
		super("/_local/images/common/image.png", "/_local/images/common/folder.png",
				"/_local/images/common/folder_key.png", false, 1);
		this.listItems = showItems;
	}

	public void addContainers() {
		if (containersAdded)
			return;
		this.addContainerNodes("files");
		containersAdded = true;
	}

	public void addContainerNodes(String type) {
		getTree().removeItems();

		ContainersDao.listing("site", type, "/_/c", new ContainersResponse() {
			public void array(List<Containers> value) {
				for (int i = 0; i < value.size(); i++) {
					Containers con = value.get(i);
					addContainer(null, con, false, false);
				}
			}
		});
	}

	@Override
	public NavToolbarBase createToolbar() {
		NavToolbar ctb = new NavToolbar(this);
		return ctb;
	}

	private void forDrop() {
		getTree().addHandler(new DropOnTreeHandler() {
			public void dropOnTree(final DropOnTree event) {
				// TODO dosyaya rename etmeye kalkıyor.
				TreeItem dragItem = getTree().getSelectedItem();
				SiteTreeItemData dragItemData = (SiteTreeItemData) dragItem.getUserObject();

				if (dragItemData.isContainer())
					return;

				final TreeItem fromFolder = dragItem.getParentItem();

				TreeItem trg = event.targetNode;
				if (trg == null)
					return;

				SiteTreeItemData targetData = (SiteTreeItemData) trg.getUserObject();
				if (!targetData.isContainer()) {
					trg = trg.getParentItem();
					targetData = (SiteTreeItemData) trg.getUserObject();
				}

				final TreeItem toFolder = trg;

				String dragData = event.baseEvent.getData("text");
				if (dragData.indexOf("!") > 0) {
					// drag from search
					String[] dataArr = dragData.split("!");
					String mUrl = dataArr[3];
					newFile(trg, mUrl);

				} else {
					// move-rename
					if (dragData.startsWith("/f/")) {
						// drag from itself
						String newUri = targetData.getUri() + dragData.substring(dragData.lastIndexOf("/"));
						FilesDao.rename(newUri, dragData, new StringResponse() {
							public void ready(String value) {

								getTree().setSelectedItem(null, false);
								getTree().setSelectedItem(fromFolder);
								getToolbar().reloadContainer(null);

								Scheduler.get().scheduleDeferred(new ScheduledCommand() {
									@Override
									public void execute() {
										getTree().setSelectedItem(null, false);
										getTree().setSelectedItem(toFolder);
										getToolbar().reloadContainer(null);

									}
								});

								// getToolbar().reloadContainer(fromFolder);
								// getToolbar().reloadContainer(toFolder);

							};
						});

					}
				}
			}
		}, DropOnTree.TYPE);
	}

	protected void newFile(final TreeItem parent, String mUrl) {
		SiteTreeItemData container = (SiteTreeItemData) parent.getUserObject();
		FilesDao.neww(Ctrl.infoLang(), container.getUri(), null, mUrl, null, null, null, container.getUri(), new StringResponse() {
			public void ready(String value) {

				Image img = new Image(value);

				String[] sp = value.split("/");
				if (sp.length < 2)
					return;
				String itemName = sp[sp.length - 1];

				addLeaf(parent, value, itemName, img);
			}
		});
	}

	private String getItemName(String uri) {
		String[] sp = uri.split("/");
		if (sp.length < 2)
			return null;
		return sp[sp.length - 1];
	}

	protected void addLeaf(TreeItem parent, String uri) {
		if (uri == null)
			return;
		Image img = new Image(uri);
		addLeaf(parent, uri, getItemName(uri), img);
	}

	protected void addLeaf(TreeItem parent, Files file) {
		if (file.uri == null)
			return;

		if (ClientUtil.isImage(file.uri)) {
			Image img = new Image(file.uri);
			addLeaf(parent, file.uri, getItemName(file.uri), img);

		} else {
			addLeaf(parent, file.uri, getItemName(file.uri), null);
		}

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

	public void reloadContainer(TreeItem parent) {
		getToolbar().reloadContainer(parent);
	}

}
