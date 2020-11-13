package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoLoadStartEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoLoadStartHandler extends EventHandler {
    void onLoadStart(VideoLoadStartEvent event);
}
