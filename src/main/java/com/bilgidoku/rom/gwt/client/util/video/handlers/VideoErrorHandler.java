package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoErrorEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoErrorHandler extends EventHandler {
    void onError(VideoErrorEvent event);
}
