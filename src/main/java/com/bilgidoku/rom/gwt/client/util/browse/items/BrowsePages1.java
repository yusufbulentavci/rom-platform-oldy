package com.bilgidoku.rom.gwt.client.util.browse.items;

import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Contents;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsResponse;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
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

public class BrowsePages1 extends ActionBarDlg {

	private final Tree tree = new Tree();
	public String selected = null;

	public BrowsePages1() {
		super("Sayfalar", null, "Tamam");
		populate();
		run();

	}

	private void populate() {

		SiteTreeItem node = new SiteTreeItem("Ana Sayfa", "");
		node.setUserObject(new SiteTreeItemData("Ana Sayfa", Data.WRITING_ROOT, true, "/"));
		node.addTextItem("");
		node.setState(false);
		tree.addItem(node);
		
		loadContainer(node);
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
		selected = ((SiteTreeItemData)tree.getSelectedItem().getUserObject()).getUri();
		
		if (selected.equals(Data.WRITING_ROOT))
			selected = "/";
		else
			selected = selected.replaceFirst(Data.WRITING_ROOT, "");

		
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

	private List<Containers> cons = null;
	private List<Contents> pages = null;

	public void loadContainer(TreeItem item) {
		
//		tree.setSelectedItem(null, false);
//		tree.setSelectedItem(item);
//		if (item.getChildCount() == 1) {
//			getData(item);
//		} else {
//			item.setState(true, false);
//		}
//		
		
		SiteTreeItemData pd = (SiteTreeItemData) item.getUserObject();
		if (!pd.isContainer())
			return;

		if (item.getChildCount() == 1) {
			getData(item);
		}

		

	}

	private void getData(TreeItem item) {
		cons = null;
		pages = null;
		final TreeItem parent = item;
		final SiteTreeItemData pd = (SiteTreeItemData) parent.getUserObject();
		
		ContainersDao.listsub(pd.getUri(), "/_/c", new ContainersResponse() {
			@Override
			public void array(List<Containers> value) {
				cons = value;
				dataReady(parent);
			}
		});
		WritingsDao.list("tr", "", pd.getUri(), new ContentsResponse() {
			public void array(List<Contents> value) {
				pages = value;
				dataReady(parent);
			}
		});
	}

	protected void dataReady(TreeItem parent) {
		if (cons == null || pages == null)
			return;

		parent.setState(false, false);
		
		for (int i = 0; i < pages.size(); i++) {
			Contents s = pages.get(i);

			if (s.uri.equals("/"))
				continue;
			
			Containers con = isContainer(s.uri);
			if (con == null) {
				ClientUtil.addLeaf(parent, s.uri, s.title[0], tree);
			} else {
				ClientUtil.addContainer(parent, con.mask, con.uri, con.uri_prefix, s.title[0], tree);
			}
			
			
		}

		pages = null;
		cons = null;
		
		parent.getChild(0).remove();
		parent.setState(true, false);


	}

	private Containers isContainer(String uri) {
		if (cons.size() == 0)
			return null;

		for (int i = 0; i < cons.size(); i++) {
			Containers con = cons.get(i);
			if (con.uri.equals(Data.WRITING_ROOT + uri)) {
				return con;
			}

		}
		return null;
	}

}
