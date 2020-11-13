package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoLoadDataHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * The user agent can render the media data at the current playback position for
 * the first time.
 * 
 * @author michael.guiral
 * 
 */
public class VideoLoadDataEvent extends GwtEvent<VideoLoadDataHandler> {
    private static final Type<VideoLoadDataHandler> TYPE = new Type<VideoLoadDataHandler>();

    public static Type<VideoLoadDataHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoLoadDataHandler handler) {
        handler.onDataLoaded(this);
    }

    @Override
    public Type<VideoLoadDataHandler> getAssociatedType() {
        return TYPE;
    }
}
