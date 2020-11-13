package com.bilgidoku.rom.site.yerel.wgts.edit;

import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;

public class DraggableLabel extends Label {
	private String type;

	public DraggableLabel() {
		super();
		makeTagLabelDraggable();
	}

	public DraggableLabel(String labelText, String type) {
		super(labelText);
		this.type = type;
		makeTagLabelDraggable();
	}

	@SuppressWarnings("deprecation")
	private void makeTagLabelDraggable() {
		this.getElement().setDraggable(Element.DRAGGABLE_TRUE);

		this.addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				event.setData("text", type + "!" + DraggableLabel.this.getText());
				event.getDataTransfer().setDragImage(DraggableLabel.this.getElement(), 20, 8);
			}
		});

		this.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				Label l = (Label) event.getSource();
				l.addStyleName("site-overfield");
			}
		});

		this.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				Label l = (Label) event.getSource();
				l.removeStyleName("site-overfield");
			}
		});

	}

}
