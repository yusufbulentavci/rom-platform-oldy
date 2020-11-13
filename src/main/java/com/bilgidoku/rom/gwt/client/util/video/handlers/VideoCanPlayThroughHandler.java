package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoCanPlayThroughEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoCanPlayThroughHandler extends EventHandler {
    void onCanPlayThrough(VideoCanPlayThroughEvent event);
}
