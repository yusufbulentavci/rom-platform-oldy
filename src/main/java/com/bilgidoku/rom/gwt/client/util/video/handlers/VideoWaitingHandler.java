package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoWaitingEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoWaitingHandler extends EventHandler {
    void onWaiting(VideoWaitingEvent event);
}
