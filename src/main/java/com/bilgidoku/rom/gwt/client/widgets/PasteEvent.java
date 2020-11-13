package com.bilgidoku.rom.gwt.client.widgets;

import com.google.gwt.event.shared.GwtEvent;

public class PasteEvent extends GwtEvent<PasteHandler> {
	public PasteEvent() {
		super();
	}

	public static Type<PasteHandler> TYPE = new Type<PasteHandler>();

	public com.google.gwt.event.shared.GwtEvent.Type<PasteHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PasteHandler handler) {
		handler.paste(this);		
	}

}
