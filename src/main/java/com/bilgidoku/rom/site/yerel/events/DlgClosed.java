package com.bilgidoku.rom.site.yerel.events;

import com.google.gwt.event.shared.GwtEvent;

public class DlgClosed extends GwtEvent<DlgClosedHandler> {
	public DlgClosed() {
		super();
	}

	public static Type<DlgClosedHandler> TYPE = new Type<DlgClosedHandler>();

	public com.google.gwt.event.shared.GwtEvent.Type<DlgClosedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DlgClosedHandler handler) {
		handler.dlgClosed(this);

	}

}
