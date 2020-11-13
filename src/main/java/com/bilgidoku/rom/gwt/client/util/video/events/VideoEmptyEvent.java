package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoEmptyHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * The media element has not yet been initialized. All attributes are in their
 * initial states.
 * 
 * @author michael.guiral
 * 
 */
public class VideoEmptyEvent extends GwtEvent<VideoEmptyHandler> {
    private static final Type<VideoEmptyHandler> TYPE = new Type<VideoEmptyHandler>();

    public static Type<VideoEmptyHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoEmptyHandler handler) {
        handler.onEmptyState(this);
    }

    @Override
    public Type<VideoEmptyHandler> getAssociatedType() {
        return TYPE;
    }
}
