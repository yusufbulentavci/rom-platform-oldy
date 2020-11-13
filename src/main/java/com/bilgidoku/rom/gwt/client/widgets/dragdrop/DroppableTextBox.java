package com.bilgidoku.rom.gwt.client.widgets.dragdrop;

import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TextBox;

public class DroppableTextBox extends TextBox {

	public DroppableTextBox() {
		super();
		sinkEvents(Event.ONPASTE);
		this.addDragOverHandler(new DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {
				DroppableTextBox.this.setStyleName("site-overfield", true);
			}
		});

		this.addDragLeaveHandler(new DragLeaveHandler() {
			@Override
			public void onDragLeave(DragLeaveEvent event) {
				DroppableTextBox.this.setStyleName("site-overfield", false);
			}
		});

		this.addDropHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				event.preventDefault();
				DroppableTextBox.this.setStyleName("site-overfield", false);
				String data = event.getData("text");
				DroppableTextBox.this.setValue(data);

			}
		});
	}

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		switch (event.getTypeInt()) {
		case Event.ONPASTE:
			fireEvent(new PasteEvent());
			break;
		}
	}

}
