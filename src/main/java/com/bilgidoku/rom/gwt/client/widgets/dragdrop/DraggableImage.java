package com.bilgidoku.rom.gwt.client.widgets.dragdrop;

import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Image;

/**
 * A SiteTreeItem is a treeitem that has hover effect, drag and drop capability.
 */
public class DraggableImage extends Image {

	public DraggableImage(final String thumbUrl, final String mediaUrl, final String title) {
		super(thumbUrl);
		this.setTitle(title);
		this.getElement().setDraggable(Element.DRAGGABLE_TRUE);
		this.addDragStartHandler(new DragStartHandler() {
			public void onDragStart(DragStartEvent event) {
				event.setData("text", "media!" + title + "!" + thumbUrl + "!" + mediaUrl);
				event.getDataTransfer().setDragImage(DraggableImage.this.getElement(), 10, 10);
			}
		});
	}
}
