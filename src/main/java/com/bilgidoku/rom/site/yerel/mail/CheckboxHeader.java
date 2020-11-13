package com.bilgidoku.rom.site.yerel.mail;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasValue;

public class CheckboxHeader extends Header<Boolean> implements HasValue<Boolean> {

	private boolean checked;
	private HandlerManager handlerManager;

	public CheckboxHeader() {
		// TODO consider custom cell with text
		super(new CheckboxCell());
		checked = false;
	}

	// This method is invoked to pass the value to the CheckboxCell's render
	// method
	@Override
	public Boolean getValue() {
		return checked;
	}

	@Override
	public void onBrowserEvent(Context context, com.google.gwt.dom.client.Element elem, NativeEvent event) {
		int eventType = Event.as(event).getTypeInt();
		if (eventType == Event.ONCHANGE) {
			event.preventDefault();
			setValue(!checked, true);
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return ensureHandlerManager().addHandler(ValueChangeEvent.getType(), handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		ensureHandlerManager().fireEvent(event);
	}

	@Override
	public void setValue(Boolean value) {
		checked = value;
	}

	@Override
	public void setValue(Boolean value, boolean fireEvents) {
		checked = value;
		if (fireEvents) {
			ValueChangeEvent.fire(this, value);
		}
	}

	private HandlerManager ensureHandlerManager() {
		if (handlerManager == null) {
			handlerManager = new HandlerManager(this);
		}
		return handlerManager;
	}
}
