package com.bilgidoku.rom.gwt.client.widgets.dragdrop;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DragPanel extends VerticalPanel {
	private boolean drag_drop = false;
	private boolean move = false;
	private Element movingPanelElement;

	public void setMovingPanelElement(Element movingPanelElement) {
		this.movingPanelElement = movingPanelElement;
	}

	public DragPanel() {
		super();
		DOM.sinkEvents(this.getElement(), Event.ONMOUSEDOWN | Event.ONMOUSEMOVE | Event.ONMOUSEUP | Event.ONMOUSEOVER);

	}

	@Override
	public void onBrowserEvent(Event event) {

		final int eventType = DOM.eventGetType(event);
		if (Event.ONMOUSEOVER == eventType) {

			if (isCursorResize(event)) {
				DOM.setStyleAttribute(this.getElement(), "cursor", "s-resize");
			} else if (isCursorMove(event)) {
				DOM.setStyleAttribute(this.getElement(), "cursor", "move");
			} else {
				DOM.setStyleAttribute(this.getElement(), "cursor", "default");
			}

		}
		if (Event.ONMOUSEDOWN == eventType) {
			if (isCursorResize(event)) {
				if (drag_drop == false) {
					drag_drop = true;

					DOM.setCapture(this.getElement());

				}
			} else if (isCursorMove(event)) {
				DOM.setCapture(this.getElement());
				move = true;
			}

		} else if (Event.ONMOUSEMOVE == eventType) {
			if (!isCursorResize(event) && !isCursorMove(event)) {
				DOM.setStyleAttribute(this.getElement(), "cursor", "default");
			}
			if (drag_drop == true) {
				int absY = DOM.eventGetClientY(event);
				int originalY = DOM.getAbsoluteTop(this.getElement());
				if (absY > originalY) {
					Integer height = absY - originalY;
					this.setHeight(height + "px");
				}
			} else if (move == true) {
				RootPanel.get().setWidgetPosition(this, DOM.eventGetClientX(event), DOM.eventGetClientY(event));
			}
		} else if (Event.ONMOUSEUP == eventType) {
			if (move == true) {
				move = false;
				DOM.releaseCapture(this.getElement());
			}
			if (drag_drop == true) {
				drag_drop = false;
				DOM.releaseCapture(this.getElement());

			}

		}
	}

	protected boolean isCursorResize(Event event) {
		int cursor = DOM.eventGetClientY(event);
		int initial = this.getAbsoluteTop();
		int height = this.getOffsetHeight();

		if (initial + height - 20 < cursor && cursor <= initial + height)
			return true;
		else
			return false;

	}

	protected boolean isCursorMove(Event event) {
		int cursor = DOM.eventGetClientY(event);
		int initial = movingPanelElement.getAbsoluteTop();
		int height = movingPanelElement.getOffsetHeight();
		if (initial <= cursor && cursor <= initial + height)
			return true;
		else
			return false;

	}
}