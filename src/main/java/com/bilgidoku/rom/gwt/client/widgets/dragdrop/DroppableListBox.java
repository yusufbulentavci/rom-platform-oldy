package com.bilgidoku.rom.gwt.client.widgets.dragdrop;

import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ListBox;

public class DroppableListBox extends ListBox {

	public DroppableListBox(String dropType) {
		super();
		sinkEvents(Event.ONPASTE);
		this.addDragOverHandler(new DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {
				DroppableListBox.this.setStyleName("site-overfield", true);
			}
		});

		this.addDropHandler(new DropHandler() {
			public void onDrop(DropEvent event) {
				event.preventDefault();
				String data = event.getData("text");
				String[] ret = data.split("!");
				DroppableListBox.this.addItem(ret[2], ret[2]);
			}
		});

		this.addDragLeaveHandler(new DragLeaveHandler() {
			@Override
			public void onDragLeave(DragLeaveEvent event) {
				DroppableListBox.this.setStyleName("site-overfield", false);
			}
		});

		this.addDropHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				DroppableListBox.this.setStyleName("site-overfield", false);
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
