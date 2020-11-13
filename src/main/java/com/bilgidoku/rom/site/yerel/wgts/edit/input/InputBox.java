package com.bilgidoku.rom.site.yerel.wgts.edit.input;

import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.InputChangedHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

public abstract class InputBox extends Composite{
	
	public HandlerRegistration addInputChangedHandler(InputChangedHandler handler) {
		return this.addHandler(handler, InputChanged.TYPE);
	}

}
