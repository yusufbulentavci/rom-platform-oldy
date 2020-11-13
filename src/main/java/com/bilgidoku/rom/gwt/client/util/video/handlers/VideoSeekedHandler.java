package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoSeekedEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoSeekedHandler extends EventHandler {
    void onSeeked(VideoSeekedEvent event);
}
