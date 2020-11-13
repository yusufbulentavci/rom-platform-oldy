package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoCanPlayThroughHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * The user agent estimates that if playback were to be started now, the media
 * resource could be rendered at the current playback rate all the way to its
 * end without having to stop for further buffering.
 * 
 * @author michael.guiral
 * 
 */
public class VideoCanPlayThroughEvent extends GwtEvent<VideoCanPlayThroughHandler> {
    private static final Type<VideoCanPlayThroughHandler> TYPE = new Type<VideoCanPlayThroughHandler>();

    public static Type<VideoCanPlayThroughHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoCanPlayThroughHandler handler) {
        handler.onCanPlayThrough(this);
    }

    @Override
    public Type<VideoCanPlayThroughHandler> getAssociatedType() {
        return TYPE;
    }
}
