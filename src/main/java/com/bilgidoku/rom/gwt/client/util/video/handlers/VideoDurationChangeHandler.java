package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoDurationChangeEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoDurationChangeHandler extends EventHandler {
    void onDurationChange(VideoDurationChangeEvent event);
}
