package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

import com.google.gwt.event.shared.GwtEvent;

public class ColorPickedEvent extends GwtEvent<ColorPickedEventHandler> {
	
	private String hexOrRgbaValue;
	
	public ColorPickedEvent(String val) {
		super();
		this.hexOrRgbaValue = val;
	}

	public static Type<ColorPickedEventHandler> TYPE = new Type<ColorPickedEventHandler>();

	public com.google.gwt.event.shared.GwtEvent.Type<ColorPickedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ColorPickedEventHandler handler) {
		handler.colorPicked(this);		
	}

	public String getHexOrRgbaValue() {
		return hexOrRgbaValue;
	}

	public void setHexOrRgbaValue(String hexValue) {
		this.hexOrRgbaValue = hexValue;
	}

}
