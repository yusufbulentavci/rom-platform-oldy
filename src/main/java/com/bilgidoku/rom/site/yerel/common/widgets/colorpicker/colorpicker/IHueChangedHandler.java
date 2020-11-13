package com.bilgidoku.rom.site.yerel.common.widgets.colorpicker.colorpicker;

import com.google.gwt.event.shared.EventHandler;

public interface IHueChangedHandler extends EventHandler {
	void hueChanged(HueChangedEvent event);
}
