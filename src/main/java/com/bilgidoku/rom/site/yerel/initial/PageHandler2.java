package com.bilgidoku.rom.site.yerel.initial;

import java.util.ArrayList;
import java.util.Collection;

import com.bilgidoku.rom.gwt.client.util.common.DropOnTree;
import com.bilgidoku.rom.gwt.client.util.common.DropOnTreeHandler;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.common.SiteTree;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.constants.InitialConstants;
import com.bilgidoku.rom.site.yerel.initial.nameuri.NameUriEditCallback;
import com.bilgidoku.rom.site.yerel.initial.nameuri.NameUriEditDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.TreeItem;

public class PageHandler2 implements NameUriEditCallback {
	private final InitialPage initial;
	private final SiteTree pageTree = new SiteTree(true);
	private final InitialConstants con = GWT.create(InitialConstants.class);
//	private final SiteButton btnAddPageGrp = new SiteButton("/_local/images/common/writings_plus(), con.addPageGroup(), con.addPageGroupDesc(), "");
//	private final SiteButton btnAddPage = new SiteButton("/_local/images/common/writing_add(), con.addPage(), con.addPageDesc(), "");
//	private final SiteButton btnDelete = new SiteButton("/_local/images/common/delete(), con.delete(), con.deleteDesc(), "");
//	private final SiteButton btnUpdPage = new SiteButton("/_local/images/common/pencil(), con.update(), con.updateDesc(), "");

	private final SiteButton btnAddPageGrp = null;
	private final SiteButton btnAddPage = null;
	private final SiteButton btnDelete = null;
	private final SiteButton btnUpdPage = null;

	private String contentLang;

	public PageHandler2(final InitialPage initializationPage) {
		this.initial = initializationPage;
		forAddPage();
		forAddPageGrp();
		forUpdate();
		forDelete();
		forSelectionChange();
		forDrop();
	}

	private void forDrop() {
		pageTree.addHandler(new DropOnTreeHandler() {
			public void dropOnTree(DropOnTree event) {
				TreeItem target = event.targetNode;
				if (target == null)
					return;
//				String t = event.baseEvent.getData("text");
//				String[] data = t.split("!");
//				String source = data[0];
//				if (!source.equals(pageTree.getKind())) {
//					return;
//				}
				if (target.getParentItem() == null && event.where == 0) {

					TreeItem oldItem = pageTree.getSelectedItem();
					if (pageExists(target, pageTree.getSelectedItemText()))
						return;

					Details parentDetail = (Details) target.getUserObject();
					Details det = (Details) oldItem.getUserObject();
					oldItem.remove();
					initial.uriRemoved(det.getRealUri());

					det.group = parentDetail.getUri();
					SiteTreeItem node = new SiteTreeItem(pageTree.getSelectedItemText(), "/_local/images/common/writing.png");
					node.setUserObject(det);
					target.addItem(node);
					target.setState(true);
					initial.pageChanged(det.getRealUri(), det.getUri(), det);
				} else {
					Integer ind = pageTree.getItemIndex(target);
					if (ind == null)
						return;
					TreeItem oldItem = pageTree.getSelectedItem();
					if (pageExists(target.getParentItem(), pageTree.getSelectedItemText()))
						return;

					Details parentDetail = (Details) target.getParentItem().getUserObject();
					Details det = (Details) oldItem.getUserObject();
					oldItem.remove();
					initial.uriRemoved(det.getRealUri());

					det.group = parentDetail.getUri();
					SiteTreeItem node = new SiteTreeItem(pageTree.getSelectedItemText(), "/_local/images/common/writing.png");
					node.setUserObject(det);

					if (event.where == -1) {
						target.getParentItem().insertItem(ind + 1, node);
					} else {
						target.getParentItem().insertItem(ind, node);
					}
					initial.pageChanged(det.getRealUri(), det.getUri(), det);
				}

			}
		}, DropOnTree.TYPE);
	}

	private void forDelete() {
		btnDelete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Details det = getSelected();
				if (det == null)
					return;
				pageTree.getSelectedItem().remove();
				initial.uriRemoved(det.getRealUri());
			}
		});

	}

	private void forUpdate() {
		btnUpdPage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Details det = getSelected();
				NameUriEditDialog nue = new NameUriEditDialog(PageHandler2.this, true, con.updateItem(), false, det, null);
				nue.show();
			}
		});
	}

	private void forAddPage() {
		btnAddPage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				NameUriEditDialog nue = new NameUriEditDialog(PageHandler2.this, false, con.addPage(), false, getSelectedPageGroup());				
				nue.show();
				nue.getName().setFocus(true);
			}
		});
	}

	private void forAddPageGrp() {
		btnAddPageGrp.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				NameUriEditDialog nue = new NameUriEditDialog(PageHandler2.this, false, con.addPageGroup(), false, null);
				nue.show();
				nue.getName().setFocus(true);
			}
		});
	}


	public SiteTree getPageTree() {
		return pageTree;
	}

	public SiteButton getAddPageBtn() {
		return btnAddPage;
	}

	public SiteButton getAddPageGrpBtn() {
		return btnAddPageGrp;
	}

	public SiteButton getDelBtn() {
		return btnDelete;
	}

	public SiteButton getUpdateBtn() {
		return btnUpdPage;
	}

	public TreeItem addGetPageGroup(String name, String uri, boolean menu, boolean footer, boolean vitrine, String app) {
		TreeItem found = pageGroupExists(uri);
		if (found != null)
			return found;
		Details det = new Details(name, uri, menu, footer, vitrine, app, "", true);
		SiteTreeItem grp = new SiteTreeItem(getTextForUri(uri), "/_local/images/common/writings.png");
		grp.setUserObject(det);
		pageTree.addItem(grp);
		this.initial.groupChanged(det.getUri(), det);
		return grp;
	}

	private String getTextForUri(String uri) {
		//String text = (uri.equals("") ? aliasForHome : uri);
		//return text;
		return "/" + uri;
	}

	private TreeItem pageGroupExists(String uri) {
		String nuri = getTextForUri(uri);
		TreeItem parent = pageTree.getItemByText(nuri);
		if (parent != null)
			return parent;
		else
			return null;
	}

	public boolean addNewPage(String name, String uri, boolean menu, boolean footer, boolean vitrine, String app, String group) {
		TreeItem parent = addGetPageGroup(group, group, true, true, false, "group");

		if (pageExists(parent, name))
			return false;

		Details det = new Details(name, uri, menu, footer, vitrine, app, group, false);
		SiteTreeItem item = new SiteTreeItem(getTextForUri(uri), "/_local/images/common/writing.png");
		item.setUserObject(det);
		parent.addItem(item);
		parent.setState(true);
		this.initial.pageChanged(det.getRealUri(), det.getUri(), det);
		return true;
	}

	private boolean pageExists(TreeItem parent, String name) {
		boolean found = false;
		for (int i = 0; i < parent.getChildCount(); i++) {
			TreeItem child = parent.getChild(i);
			SiteTreeItemData data = (SiteTreeItemData) child.getUserObject();
			if (data.getTitle().equals(name)) {
				found = true;
				break;
			}
		}
		return found;
	}

	@Override
	public String getContentLang() {
		return contentLang;
	}

	public void setContentLang(String editLang) {
		this.contentLang = editLang;
	}

	public Details getSelected() {
		TreeItem item = pageTree.getSelectedItem();
		if (item == null)
			return null;
		Details uo = (Details) item.getUserObject();
		return uo;
	}

	private void forSelectionChange() {
		pageTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem det = event.getSelectedItem();
				if (det != null) {
					Details uo = (Details) det.getUserObject();
					initial.uriChanged(uo.getRealUri());
					buttonsState(true);
					initial.setMenuButtonStates(true);
				}
			}
		});
	}

	private void buttonsState(boolean active) {
		btnAddPage.setEnabled(active);
		btnUpdPage.setEnabled(active);
		btnDelete.setEnabled(active);
		btnAddPageGrp.setEnabled(active);
	}

	public Collection<Details> getPages() {
		Collection<Details> pages = new ArrayList<Details>();
		for (int i = 0; i < pageTree.getItemCount(); i++) {
			TreeItem item = pageTree.getItem(i);
			pages.add((Details) item.getUserObject());
			if (item.getChildCount() > 0) {
				for (int j = 0; j < item.getChildCount(); j++) {
					TreeItem mitem = item.getChild(j);
					pages.add((Details) mitem.getUserObject());
				}
			}
		}
		return pages;
	}

	@Override
	public void nameChanged(String name, String uri, boolean menu, boolean footer, boolean vitrine, String app) {
		Details det = getSelected();
		
		String oldUri = det.getUri();
		String oldRealUri = det.getRealUri();
		det.name = name;
		det.setUri(uri);
		det.menu = menu;
		det.footer = footer;
		det.vitrine = vitrine;
		det.app = app;
		//TODO
		SiteTreeItem item = (SiteTreeItem) pageTree.getSelectedItem();
		if (item == null)
			return;
		item.setUserObject(det);
		item.setText(getTextForUri(det.getUri()));

		if (isGroup()) {
			initial.groupChanged(oldRealUri, det);
		} else {
			initial.pageChanged(oldRealUri, oldUri, det);
		}

	}

	private boolean isGroup() {
		if (pageTree.getSelectedItem() == null)
			return false;

		Details d = (Details) pageTree.getSelectedItem().getUserObject();
		return d.isContainer();
	}

	public String getSelectedPageGroup() {
		String parent = "";
		TreeItem item = pageTree.getSelectedItem();
		if (item != null && item.getParentItem() == null) {
			parent = ((Details) item.getUserObject()).getUri();
		} else if (item != null && item.getParentItem() != null) {
			parent = ((Details) item.getParentItem().getUserObject()).getUri();
		}
		return parent;
	}

	public void emptyPageTree() {
		pageTree.removeItems();
		
	}

	public void initialState() {
		if (pageTree.getItemCount() > 0) {
			pageTree.setSelectedItem(pageTree.getItem(0));
		}
		
	}

}
