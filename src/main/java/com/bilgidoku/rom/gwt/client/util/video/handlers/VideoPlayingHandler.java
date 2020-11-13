package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoPlayingEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoPlayingHandler extends EventHandler {
    void onPlaying(VideoPlayingEvent event);
}
