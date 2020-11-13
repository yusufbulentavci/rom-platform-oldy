package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoVolumeChangeEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoVolumeChangeHandler extends EventHandler {
    void onVolumeChange(VideoVolumeChangeEvent event);
}
