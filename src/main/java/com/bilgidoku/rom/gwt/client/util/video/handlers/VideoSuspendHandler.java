package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoSuspendEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoSuspendHandler extends EventHandler {
    void onSuspend(VideoSuspendEvent event);
}
