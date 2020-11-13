package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoPlayEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoPlayHandler extends EventHandler {
    void onPlay(VideoPlayEvent event);
}
