package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoPauseEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoPauseHandler extends EventHandler {
    void onPause(VideoPauseEvent event);
}
