package com.bilgidoku.rom.gwt.client.util.browse.image.search;

import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.shared.CRoleMask;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class DlgDownloadFolders extends ActionBarDlg {

	private final Tree bTree = new Tree();
	public String selected = null;

	public DlgDownloadFolders() {
		super("Folders", null, "OK");

		ContainersDao.listing("site", "files", "/_/c", new ContainersResponse() {
			public void array(List<Containers> value) {
				for (int i = 0; i < value.size(); i++) {
					Containers con = value.get(i);
					// if (con.uri.indexOf("image") > 0)
					addContainer(null, con.mask, con.uri, con.uri_prefix);
				}
			}
		});

		forExpand();

		run();
		show();
		center();
	}

	public DlgDownloadFolders(String resourceUri) {
		this();
		if (resourceUri != null && !resourceUri.isEmpty())
			addContainer(null, 0L, resourceUri, ":r:" + resourceUri);
	}

	
	private void forExpand() {
		bTree.addOpenHandler(new OpenHandler<TreeItem>() {
			public void onOpen(OpenEvent<TreeItem> event) {
				TreeItem item = event.getTarget();
				reloadContainer(item);
			}
		});
	}

	protected void reloadContainer(final TreeItem item) {
	
		SiteTreeItemData pd = (SiteTreeItemData) item.getUserObject();
		if (!pd.isContainer())
			return;

		if ((item.getChildCount() == 1 && item.getChild(0).getText().equals("!"))) {
			// do not reload every time it expands, reload every time when
			item.removeItems();

			ContainersDao.listsub(pd.getUri(), "/_/c", new ContainersResponse() {
				@Override
				public void array(List<Containers> cons) {
					for (int i = 0; i < cons.size(); i++) {
						Containers con = cons.get(i);
						addContainer(item, con.mask, con.uri, con.uri_prefix);
					}
					item.setState(true, false);

				}
			});
		}

	}

	@Override
	public Widget ui() {
		bTree.setSize("250px", "460px");
		ScrollPanel wTree = new ScrollPanel(bTree);

		return wTree;
	}

	@Override
	public void cancel() {
		selected = null;
	}

	@Override
	public void ok() {
		selected = null;
		TreeItem selectedItem = bTree.getSelectedItem();
		if (selectedItem == null)
			return;

		SiteTreeItemData data = (SiteTreeItemData) selectedItem.getUserObject();

		selected = data.getUri();

	}

	public void addContainer(TreeItem parent, Long mask, final String uri, String uri_prefix) {		
		String conImg = "/_public/images/bar/folder.png";
		
		if (mask.equals(0L)) {
			conImg = "/_public/images/bar/blue_folder.png"; 
		} else if (!CRoleMask.maskIsPublic(mask))
			conImg = "/_public/images/bar/folder_key.png";

		String text = uri.substring(uri.lastIndexOf("/") + 1);

		final TreeItem node = new TreeItem(new HTML("<img src='" + conImg + "'/>" + text));
		node.setUserObject(new SiteTreeItemData(text, uri, true, uri_prefix));
		node.addTextItem("!");
		node.setState(false);

		if (parent == null)
			bTree.addItem(node);
		else
			parent.addItem(node);

	}

}
