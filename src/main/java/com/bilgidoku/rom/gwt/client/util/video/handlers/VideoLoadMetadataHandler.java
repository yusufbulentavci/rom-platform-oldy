package com.bilgidoku.rom.gwt.client.util.video.handlers;

import com.bilgidoku.rom.gwt.client.util.video.events.VideoLoadMetadataEvent;
import com.google.gwt.event.shared.EventHandler;

public interface VideoLoadMetadataHandler extends EventHandler {
    void onMetadataLoaded(VideoLoadMetadataEvent event);
}
