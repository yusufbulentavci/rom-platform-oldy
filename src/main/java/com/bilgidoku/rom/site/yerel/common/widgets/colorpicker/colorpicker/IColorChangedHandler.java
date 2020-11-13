package com.bilgidoku.rom.site.yerel.common.widgets.colorpicker.colorpicker;

import com.google.gwt.event.shared.EventHandler;

public interface IColorChangedHandler extends EventHandler {
	void colorChanged(ColorChangedEvent event);
}
