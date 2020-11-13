package com.bilgidoku.rom.gwt.client.util.browse.items;

import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Files;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.araci.client.site.FilesResponse;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class BrowseFiles extends ActionBarDlg {

	private final Tree tree = new Tree();
	public String selected = null;

	public BrowseFiles() {
		super("Dosyalar", null, "Tamam");
		populate();
		run();

	}

	private void populate() {

		ContainersDao.listing("site", "files", "/_/c", new ContainersResponse() {
			public void array(List<Containers> value) {
				for (int i = 0; i < value.size(); i++) {
					Containers con = value.get(i);
					ClientUtil.addContainer(null, 0L, con.uri, con.uri_prefix, ClientUtil.getTitleFromUri(con.uri),
							tree);
				}
			}
		});
	}

	@Override
	public Widget ui() {
		tree.addOpenHandler(new OpenHandler<TreeItem>() {
			public void onOpen(OpenEvent<TreeItem> event) {
				TreeItem item = event.getTarget();
				loadContainer(item);
			}
		});
		return tree;
	}

	@Override
	public void cancel() {
		selected = null;
	}

	@Override
	public void ok() {
		selected = ((SiteTreeItemData) tree.getSelectedItem().getUserObject()).getUri();
	}

	public TreeItem addWritingHome() {
		String text = "Ana Sayfa";
		SiteTreeItem node = new SiteTreeItem(text, "");
		node.setUserObject(new SiteTreeItemData(text, Data.WRITING_ROOT, true, "/"));
		node.addTextItem("");
		node.setState(false);
		tree.addItem(node);
		return node;
	}

	public void loadContainer(TreeItem item) {

		SiteTreeItemData pd = (SiteTreeItemData) item.getUserObject();
		if (!pd.isContainer())
			return;

		if (item.getChildCount() == 1) {
			getData(item);
		}

	}

	private List<Containers> cons = null;
	private List<Files> files = null;

	private void getData(final TreeItem parent) {
		SiteTreeItemData pd = (SiteTreeItemData) parent.getUserObject();

		ContainersDao.listsub(pd.getUri(), "/_/c", new ContainersResponse() {
			@Override
			public void array(List<Containers> value) {
				cons = value;
				dataReady(parent);
			}
		});

		FilesDao.list("en", 0, 1000, pd.getUri(), new FilesResponse() {
			public void array(List<Files> value) {
				files = value;
				dataReady(parent);
			}
		});

	}

	protected void dataReady(TreeItem parent) {
		if (cons == null || files == null)
			return;

		parent.setState(false, false);
		
		for (int i = 0; i < cons.size(); i++) {
			Containers con = cons.get(i);
			ClientUtil.addContainer(parent, 0L, con.uri, con.uri_prefix, ClientUtil.getTitleFromUri(con.uri), null);
		}

		for (int i = 0; i < files.size(); i++) {
			Files file = files.get(i);
			ClientUtil.addLeaf(parent, file.uri, ClientUtil.getTitleFromUri(file.uri), null);
		}

		cons = null;
		files = null;

		parent.getChild(0).remove();
		parent.setState(true, false);

	}

}
