package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoSeekingHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * The seeking attribute changed to true and the seek operation is taking long
 * enough that the user agent has time to fire the event.
 * 
 * @author michael.guiral
 * 
 */
public class VideoSeekingEvent extends GwtEvent<VideoSeekingHandler> {
    private static final Type<VideoSeekingHandler> TYPE = new Type<VideoSeekingHandler>();

    public static Type<VideoSeekingHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoSeekingHandler handler) {
        handler.onSeeking(this);
    }

    @Override
    public Type<VideoSeekingHandler> getAssociatedType() {
        return TYPE;
    }
}
