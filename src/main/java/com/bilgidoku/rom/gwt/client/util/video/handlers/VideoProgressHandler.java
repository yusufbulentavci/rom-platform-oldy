package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoProgressEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoProgressHandler extends EventHandler {
    void onProgress(VideoProgressEvent event);
}
