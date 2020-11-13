package com.bilgidoku.rom.gwt.client.util.common;

import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * A SiteTreeItem is a treeitem that has hover effect, drag and drop capability.
 */
@SuppressWarnings("deprecation")
public class SiteTreeItem extends TreeItem {

	protected Label lblItem = new Label();
	protected HTML btnEdit = new HTML(ClientUtil.imageItemHTML("/_local/images/common/pencil.png", ""));
	protected HTML btnDelete = new HTML(ClientUtil.imageItemHTML("/_local/images/common/bin.png", ""));
	protected HTML icon = new HTML();
	protected PopupPanel simplePopup;
	protected Image overImage;
	protected HorizontalPanel holder = new HorizontalPanel();
	private String dragData;

	public SiteTreeItem(String text, String imgIcon) {
		super();		
		ui(text, imgIcon);
		this.setWidget(holder);
	}

	public SiteTreeItem(String text, String imgIcon, String dragData) {
		this(text, imgIcon);
		this.dragData = dragData;
		forDrag();
	}

	private void forDrag() {
		lblItem.getElement().setDraggable(Element.DRAGGABLE_TRUE);
		lblItem.addDragStartHandler(new DragStartHandler() {
			public void onDragStart(DragStartEvent event) {
				event.setData("text", dragData);
				event.getDataTransfer().setDragImage(lblItem.getElement(), 10, 10);
			}
		});
	}

	protected void showPopUp(Image img, Widget source) {
		int left = source.getAbsoluteLeft() + 20;
		int top = source.getAbsoluteTop() + 20;
		if (left < 0)
			left = 0;
		if (top < 0)
			top = 0;

		simplePopup = new ImagePopup(img, left, top);

	}

	private void ui(String text, String imgIcon) {
		if (imgIcon != null)
			icon.setHTML(ClientUtil.imageItemHTML(imgIcon, ""));
		btnEdit.setVisible(false);
		btnDelete.setVisible(false);
		
		if (text == null || text.isEmpty())
			text = "";
		
		if (text.length() > 25) {
			text = text.substring(0, 25) + "...";
		}
		
		String decoded = URL.decode(text);
		this.lblItem.setText(decoded);
		
		holder.setSpacing(0);
		holder.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		holder.add(icon);
		holder.add(lblItem);
		holder.add(btnEdit);
		holder.add(btnDelete);
		
	}

	public void setText(String text) {
		this.lblItem.setText(text);
	}

	public void setTitle(String text) {
		super.setText(text);
		this.lblItem.setText(text);
	}

	public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
		return this.lblItem.addDoubleClickHandler(handler);
	}

	public HandlerRegistration addTreeItemEditHandler(TreeItemEditHandler handler) {
		return this.lblItem.addHandler(handler, TreeItemEdit.TYPE);
	}

	public HandlerRegistration addTreeItemDeleteHandler(TreeItemDeleteHandler handler) {
		return this.lblItem.addHandler(handler, TreeItemDelete.TYPE);
	}

	public String getDragData() {
		return dragData;
	}

	public void setDragData(String dragData) {
		this.dragData = dragData;
	}
	
	public void changeIcon(String imgIcon) {
		if (imgIcon != null)
			icon.setHTML(ClientUtil.imageItemHTML(imgIcon, ""));
	}
	
	private class ImagePopup extends PopupPanel {		
		public ImagePopup(Image img, int left,  int top) {
			super(true);
			img.setWidth("100px");
			this.setWidget(img);
			this.setPopupPosition(left, top);
			this.show();
		}
	}
	

}
