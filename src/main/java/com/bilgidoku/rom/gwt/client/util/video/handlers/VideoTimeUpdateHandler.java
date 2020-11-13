package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoTimeUpdateEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoTimeUpdateHandler extends EventHandler {
    void onTimeUpdated(VideoTimeUpdateEvent event);
}
