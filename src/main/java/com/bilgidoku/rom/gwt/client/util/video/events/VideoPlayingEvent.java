package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoPlayingHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Playback has started.
 * 
 * @author michael.guiral
 * 
 */
public class VideoPlayingEvent extends GwtEvent<VideoPlayingHandler> {
    private static final Type<VideoPlayingHandler> TYPE = new Type<VideoPlayingHandler>();

    public static Type<VideoPlayingHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoPlayingHandler handler) {
        handler.onPlaying(this);
    }

    @Override
    public Type<VideoPlayingHandler> getAssociatedType() {
        return TYPE;
    }
}
