package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoStalledEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoStalledHandler extends EventHandler {
    void onStalled(VideoStalledEvent event);
}
