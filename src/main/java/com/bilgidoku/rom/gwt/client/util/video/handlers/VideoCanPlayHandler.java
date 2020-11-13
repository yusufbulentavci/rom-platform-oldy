package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoCanPlayEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoCanPlayHandler extends EventHandler {
    void onCanPlay(VideoCanPlayEvent event);
}
