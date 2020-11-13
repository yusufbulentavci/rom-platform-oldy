package com.bilgidoku.rom.site.yerel.admin;

import com.bilgidoku.rom.gwt.client.util.common.SiteTree;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemEdit;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemEditHandler;
import com.bilgidoku.rom.site.yerel.data.Domain;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TreeItem;

public class NavDomains extends ResizeComposite {

	private final DomainActions wa = new DomainActions(this);
	private final NavDomainToolbar toolbar = new NavDomainToolbar(wa);
	private final SiteTree tree = new SiteTree();

	public NavDomains() {
		DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
		dock.addNorth(toolbar, 30);
		ScrollPanel scr = new ScrollPanel(tree);
		scr.setSize("100%", "100%");
		dock.add(scr);
		dock.setStyleName("site-panels");
		initWidget(dock);
	}

	private void makeOpennable(SiteTreeItem node) {
		node.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				wa.openLeaf();
			}
		});
		node.addTreeItemEditHandler(new TreeItemEditHandler() {
			@Override
			public void editTreeItem(TreeItemEdit event) {
				wa.openLeaf();
			}
		});
	}

	public void addDomains() {
		tree.removeItems();
		for (Domain u : wa.domains) {
			addDomain(u);			
		}
	}

	public void addDomain(Domain u) {
//		SiteTreeItem node = new SiteTreeItem(u.getDomainName(), img.domain());
//		tree.addItem(node);
//		makeOpennable(node);		
	}

	public TreeItem getSelectedItem() {
		return tree.getSelectedItem();
	}

}
