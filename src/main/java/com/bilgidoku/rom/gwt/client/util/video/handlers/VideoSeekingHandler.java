package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoSeekingEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoSeekingHandler extends EventHandler {
    void onSeeking(VideoSeekingEvent event);
}
