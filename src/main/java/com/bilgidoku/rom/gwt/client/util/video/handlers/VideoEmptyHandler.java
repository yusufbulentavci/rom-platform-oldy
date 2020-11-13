package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoEmptyEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoEmptyHandler extends EventHandler {
    void onEmptyState(VideoEmptyEvent event);
}
