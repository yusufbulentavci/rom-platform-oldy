package com.bilgidoku.rom.gwt.client.widgets;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.json.client.JSONObject;

public class InputChanged extends GwtEvent<InputChangedHandler> {
	
	public String named;
	public String newValue;
	public JSONObject newObject;
	

	public InputChanged(String name, String value) {
		super();
		this.named = name;
		this.newValue = value;		
	}

	public static Type<InputChangedHandler> TYPE = new Type<InputChangedHandler>();

	public com.google.gwt.event.shared.GwtEvent.Type<InputChangedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(InputChangedHandler handler) {
		handler.changed(this);

	}

}
