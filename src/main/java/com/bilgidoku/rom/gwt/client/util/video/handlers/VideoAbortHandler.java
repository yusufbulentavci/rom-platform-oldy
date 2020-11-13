package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoAbortEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoAbortHandler extends EventHandler {
    void onAbort(VideoAbortEvent event);
}
