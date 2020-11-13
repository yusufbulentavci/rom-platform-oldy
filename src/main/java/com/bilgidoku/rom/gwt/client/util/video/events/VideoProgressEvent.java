package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoProgressHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * The user agent is fetching media data.
 * 
 * @author michael.guiral
 * 
 */
public class VideoProgressEvent extends GwtEvent<VideoProgressHandler> {
    private static final Type<VideoProgressHandler> TYPE = new Type<VideoProgressHandler>();

    public static Type<VideoProgressHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoProgressHandler handler) {
        handler.onProgress(this);
    }

    @Override
    public Type<VideoProgressHandler> getAssociatedType() {
        return TYPE;
    }
}
