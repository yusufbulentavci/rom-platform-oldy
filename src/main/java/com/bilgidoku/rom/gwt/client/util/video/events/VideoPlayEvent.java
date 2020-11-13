package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoPlayHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Playback has begun. Fired after the play() method has returned, or when the
 * autoplay attribute has caused playback to begin
 * 
 * @author michael.guiral
 * 
 */
public class VideoPlayEvent extends GwtEvent<VideoPlayHandler> {
    private static final Type<VideoPlayHandler> TYPE = new Type<VideoPlayHandler>();

    public static Type<VideoPlayHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoPlayHandler handler) {
        handler.onPlay(this);
    }

    @Override
    public Type<VideoPlayHandler> getAssociatedType() {
        return TYPE;
    }
}
