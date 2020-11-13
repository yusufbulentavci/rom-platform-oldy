package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoRateChangeEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoRateChangeHandler extends EventHandler {
    void onRateChange(VideoRateChangeEvent event);
}
