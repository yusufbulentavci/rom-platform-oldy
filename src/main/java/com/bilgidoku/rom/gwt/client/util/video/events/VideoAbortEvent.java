package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoAbortHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * The user agent stops fetching the media data before it is completely
 * downloaded, but not due to an error.
 * 
 * @author michael.guiral
 * 
 */
public class VideoAbortEvent extends GwtEvent<VideoAbortHandler> {
    private static final Type<VideoAbortHandler> TYPE = new Type<VideoAbortHandler>();

    public static Type<VideoAbortHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoAbortHandler handler) {
        handler.onAbort(this);
    }

    @Override
    public Type<VideoAbortHandler> getAssociatedType() {
        return TYPE;
    }
}
