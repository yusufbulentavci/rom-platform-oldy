package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoLoadDataEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoLoadDataHandler extends EventHandler {
    void onDataLoaded(VideoLoadDataEvent event);
}
