package com.bilgidoku.rom.gwt.client.util.common;

import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class SiteTree extends Tree {

	SelectedItem rendered = null;

	class SelectedItem {
		String style;

		public SelectedItem(TreeItem founditem, Integer where2) {
			this.item = founditem;
			this.where = where2;
			if (where == 0) {
				style = "site-treeitem-over";
			} else if (where == 2) {
				style = "site-treeitem-left";				
			} else if (where == 1) {
				style = "site-treeitem-top";
			} else {
				style = "site-treeitem-bottom";
			}
		}

		Integer where;
		TreeItem item;

		public void setStyle() {
			item.addStyleName(style);
		}

		public void removeStyle() {
			item.removeStyleName(style);
		}
	}

	public SiteTree() {
		super();
	}

	public SiteTree(boolean enableNodeDrop) {
		super();
		if (enableNodeDrop)
			makeDroppable();
	}

	public String getSelectedItemText() {
		TreeItem ti = this.getSelectedItem();
		if (ti == null)
			return null;
		SiteTreeItemData data = (SiteTreeItemData) ti.getUserObject();
		return data.getTitle();
	}

	public String getItemText(int i) {
		TreeItem ti = this.getItem(i);
		SiteTreeItemData data = (SiteTreeItemData) ti.getUserObject();
		return data.getTitle();
	}

	public TreeItem getItemByText(String text) {
		TreeItem t = null;
		for (int i = 0; i < this.getItemCount(); i++) {
			String label = this.getItem(i).getText().trim();
			if (label.equals(text)) {
				t = this.getItem(i);
				break;
			}
		}
		return t;
	}
	
	public void makeDroppable() {

		this.addDomHandler(new DragOverHandler() {
			public void onDragOver(DragOverEvent event) {
				event.preventDefault();
				SelectedItem target = getTargetTreeItem(SiteTree.this, (Event) event.getNativeEvent());
				if (target == null) {
					return;
				}
				if (target.where == 0) {
					target.item.addStyleName("site-treeitem-over");
				} else if (target.where == 2) {
					target.item.addStyleName("site-treeitem-left");
				} else if (target.where == 1) {
					target.item.addStyleName("site-treeitem-top");
				} else if (target.where == -1) {
					target.item.addStyleName("site-treeitem-bottom");
				}
				if (rendered != null)
					rendered.removeStyle();
				target.setStyle();
				rendered = target;
			}
		}, DragOverEvent.getType());

		this.addDomHandler(new DragLeaveHandler() {
			public void onDragLeave(DragLeaveEvent event) {
				if (rendered != null)
					rendered.removeStyle();
			}
		}, DragLeaveEvent.getType());

		this.addDomHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				event.preventDefault();
				if (rendered != null)
					rendered.removeStyle();

				SelectedItem target = getTargetTreeItem(SiteTree.this, (Event) event.getNativeEvent());
				if (target == null)
					return;

				DropOnTree dt = new DropOnTree();
				dt.targetNode = target.item;
				dt.where = target.where;
				dt.baseEvent = event;
				fireEvent(dt);

			}

		}, DropEvent.getType());

	}

	private SelectedItem getTargetTreeItem(Tree w, Event event) {
		TreeItem founditem = null;
		Integer foundWhere = null;
		for (int i = 0; i < w.getItemCount(); i++) {
			TreeItem item = w.getItem(i);
			if (item.isVisible()) {
				Integer where = contains(event, item.getElement());
				if (where != null) {
					founditem = item;
					foundWhere = where;
					TreeItem ritem = item;
					while (true) {
						boolean found = false;
						for (int j = 0; j < ritem.getChildCount(); j++) {
							TreeItem kitem = ritem.getChild(j);
							if (ritem.isVisible()) {
								where = contains(event, kitem.getElement());
								if (where != null) {
									founditem = kitem;
									foundWhere = where;
									ritem = kitem;
									found = true;
									break;
								}
							}
						}
						if (!found)
							break;
					}
					break;
				}
			}
		}

		if (founditem == null)
			return null;

		return new SelectedItem(founditem, foundWhere);

	}

	private Integer contains(Event event, Element element) {
		int x = element.getAbsoluteLeft();
		int y = element.getAbsoluteTop();
		int w = element.getOffsetWidth();
		int h = element.getOffsetHeight();

		if (h == 0)
			return null;

		boolean isIn = event.getClientX() > x && event.getClientX() < x + w && event.getClientY() > y && event.getClientY() < y + h;

		if (!isIn)
			return null;

		float dif = (float) (y + h - event.getClientY()) / h;
		int difx = event.getClientX() -x;

		if (dif < 0.2f)
			return -1;
		if (dif < 0.8f) {
			if (difx < 20)
				return 2;
			return 0;
		}

		return 1;
	}


	public Integer getItemIndex(TreeItem child) {
		TreeItem parent = child.getParentItem();
		for (int j = 0; j < parent.getChildCount(); j++) {
			TreeItem t = parent.getChild(j);
			if (t == child)
				return j;
		}

		return null;
	}

}
