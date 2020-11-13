package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoEndedEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoEndedHandler extends EventHandler {
    void onVideoEnded(VideoEndedEvent event);
}
