package com.bilgidoku.rom.site.yerel.common;

import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteTree;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemDelete;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemDeleteHandler;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemEdit;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemEditHandler;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TreeItem;

public abstract class NavBase extends Composite implements HasWidgets {// extends ResizeComposite {

	private final SiteTree tree = new SiteTree();
	private NavToolbarBase toolbar = createToolbar();
	public final String itemImage;
	public final String containerImage;
	public final String containerPrivateImage;
	public final boolean isEditable;
	public boolean listItems = true; 

	public NavBase(String itemImg, String containerImg,
			String containerPrivateImg, boolean hasToolbar1, int toolbarLines) {
//		containerType = con;
//		itemType = item;
		itemImage = itemImg;
		containerImage = containerImg;
		containerPrivateImage = containerPrivateImg;
		isEditable = hasToolbar1;

		ScrollPanel scr = new ScrollPanel(getTree());
		// scr.setWidth("350px");
		forSelect();
		if (hasToolbar1) {
			DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
			if (toolbarLines == 1)
				dock.addNorth(toolbar, 32);
			else
				dock.addNorth(toolbar, 64);

			dock.add(scr);
			dock.setStyleName("site-panels");
			initWidget(dock);
			forExpand();
			forEdit();

		} else {
			ScrollPanel sp = new ScrollPanel(getTree());
			sp.setHeight("300px");
			initWidget(sp);
			forExpand();
		}

	}


	private void forExpand() {
		getTree().addOpenHandler(new OpenHandler<TreeItem>() {
			public void onOpen(OpenEvent<TreeItem> event) {
				TreeItem item = event.getTarget();
				getToolbar().reloadContainer(item);
			}
		});
	}

	public abstract NavToolbarBase createToolbar();

	private void forSelect() {
		getTree().addSelectionHandler(new SelectionHandler<TreeItem>() {
			public void onSelection(SelectionEvent<TreeItem> event) {

				TreeItem ti = event.getSelectedItem();
				SiteTreeItemData dt = (SiteTreeItemData) ti.getUserObject();
				if (ti.getParentItem() == null && dt.isContainer()) {
					getToolbar().rootLevelSelected();
					return;
				}
				
				if (dt.isContainer()) {
					getToolbar().containerSelected();
					return;
				}
				
				
				getToolbar().itemSelected();
				

			}
		});
	}

	private void forEdit() {
	}

	public SiteTree getTree() {
		return tree;
	}

	public void addContainer(TreeItem parent, Containers value, boolean isDeletable, boolean isEditable) {
		addContainer(parent, value.mask, value.uri, value.uri_prefix, isDeletable, isEditable);
	}

	
	public void addContainer(TreeItem parent, Long mask, String uri, String uri_prefix, boolean isDeletable, boolean isEditable) {
		String text = uri.substring(uri.lastIndexOf("/") + 1);
		addContainer(parent, mask, uri, uri_prefix, isDeletable, isEditable, text);
	}
	
	public void addContainer(TreeItem parent, Long mask, String uri, String uri_prefix, boolean isDeletable, boolean isEditable, String text) {
		String conImg = containerImage;
		
//		if (!CRoleMask.maskIsPublic(mask))
//			conImg = containerPrivateImage;
		
		if (mask.equals(Data.WRITING_PRIVATE_MASK))
			conImg = containerPrivateImage;


		SiteTreeItemData data = new SiteTreeItemData(text, uri, true, uri_prefix);
		data.setMask(mask);
		
		NavTreeItem node = new NavTreeItem(text, conImg, null, uri, !isEditable, isEditable, isDeletable);		
		node.setUserObject(data);
		
		node.addTextItem("!");
		node.setState(false);

		if (parent == null)
			tree.addItem(node);
		else
			parent.addItem(node);

		if (isDeletable)
			deletable(node);
		if (isEditable)
			editable(node);
	}

	
	public void deletable(SiteTreeItem node) {
		node.addTreeItemDeleteHandler(new TreeItemDeleteHandler() {
			@Override
			public void deleteTreeItem(TreeItemDelete event) {
				getToolbar().deleteItem();
			}
		});
		
	}


	public TreeItem addWritingHome() {
		String text = Ctrl.trans.homePage();
		SiteTreeItem node = new NavTreeItem(text, containerImage, null, "/", !isEditable, true, true);
		node.setUserObject(new SiteTreeItemData(text, Data.WRITING_ROOT, true, "/"));
		node.addTextItem("!");
		node.setState(false);
		tree.addItem(node);
		editable(node);
		return node;
	}

	public void editable(SiteTreeItem node) {
		node.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				getToolbar().editSelectedItem();
			}
		});

		node.addTreeItemEditHandler(new TreeItemEditHandler() {
			@Override
			public void editTreeItem(TreeItemEdit event) {
				getToolbar().editSelectedItem();
			}
		});

	}


	public void addLeaf(TreeItem parent, String uri, String title) {
		addLeaf(parent, uri, title, title, null, true);
	}

	public void addLeaf(TreeItem parent, String uri, String title, boolean uriSeen) {
		if (uriSeen) {
			String seenuri = uri.substring(uri.lastIndexOf("/") + 1);
			addLeaf(parent, uri, title, seenuri, null, true);
		} else {
			addLeaf(parent, uri, title, title, null, true);
		}
	}

	public void addLeaf(TreeItem parent, String uri, String title, Image img) {
		addLeaf(parent, uri, title, title, img, true);
	}

	public void addLeaf(TreeItem parent, String uri, String title, String seenText, Image img, boolean isDeletable) {
		SiteTreeItem node = new NavTreeItem(seenText, itemImage, img, uri, !isEditable, isEditable, isDeletable);
		node.setUserObject(new SiteTreeItemData(title, uri, false, uri));
		if (parent == null)
			tree.addItem(node);
		else {
			parent.addItem(node);
		}
		
		if (isDeletable)
			deletable(node);
		if (isEditable)
			editable(node);
	}


	public void selectFirstContainer() {
		if (getTree().getItemCount() > 0) {
			getTree().getItem(0).setState(true);
			getTree().setSelectedItem(getTree().getItem(0));
		}
	}

	protected void selectFirstItem() {
		if (getTree().getItemCount() <= 0)
			return;
		TreeItem parent = getTree().getItem(0);
		if (parent.getChildCount() <= 0)
			return;

		getTree().setSelectedItem(getTree().getItem(0));

	}

	public NavToolbarBase getToolbar() {
		return toolbar;
	}

	public TreeItem getSelectedItem() {
		return tree.getSelectedItem();
	}

	public TreeItem getHolderContainer() {
		TreeItem item = tree.getSelectedItem();
		if (item == null)
			return null; 
		
		SiteTreeItemData pd = (SiteTreeItemData) item.getUserObject();
		if (pd.isContainer()) {
			return item;
		} else {
			return item.getParentItem();
		} 
	}

	public String getHolderUriPrefix() {
		TreeItem parent = getHolderContainer();
		if (parent == null || parent.getUserObject() == null)
			return "";
		else {
			SiteTreeItemData data = (SiteTreeItemData) parent.getUserObject();
			return data.getUriPrefix();
		}
	}

	public String getHolderUri() {
		TreeItem parent = getHolderContainer();
		if (parent == null || parent.getUserObject() == null)
			return null;
		else {
			SiteTreeItemData data = (SiteTreeItemData) parent.getUserObject();
			return data.getUri();
		}
	}
	
	public void makeContainer(TreeItem parent) {
		
		
	}
	

//	public void addWritingContainer(TreeItem parent, Containers value) {
//		ImageResource conImg = containerImage;
//		if (!CRoleMask.maskIsPublic(value.mask))
//			conImg = containerPrivateImage;
//
//		String text = value.uri.substring(value.uri.lastIndexOf("/") + 1);
//		
//		SiteTreeItem node = new NavTreeItem(text, conImg, null, value.uri, !hasToolbar, true, true);
//		node.setUserObject(new SiteTreeItemData(text, value.uri, true, value.uri_prefix));
//		node.addItem(new Label(""));
//		node.setState(false);
//
//		if (parent == null)
//			tree.addItem(node);
//		else
//			parent.addItem(node);
//
//		deletable(node);
//		editable(node);
//	}


}
