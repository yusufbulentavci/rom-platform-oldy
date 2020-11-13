package com.bilgidoku.rom.site.yerel.events;

import com.google.gwt.event.shared.GwtEvent;

public class ResizeEvent extends GwtEvent<ResizeEventHandler> {
	
	public ResizeEvent() {
		super();
	}

	public static Type<ResizeEventHandler> TYPE = new Type<ResizeEventHandler>();

	public com.google.gwt.event.shared.GwtEvent.Type<ResizeEventHandler> getAssociatedType() {
		return TYPE;
	}
	@Override
	protected void dispatch(ResizeEventHandler handler) {
		handler.resized(this);		
	}

}
