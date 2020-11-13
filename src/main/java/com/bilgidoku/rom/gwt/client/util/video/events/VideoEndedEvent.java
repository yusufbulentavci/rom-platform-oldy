package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoEndedHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Playback has stopped because the end of the media resource was reached.
 * 
 * @author michael.guiral
 * 
 */
public class VideoEndedEvent extends GwtEvent<VideoEndedHandler> {
    private static final Type<VideoEndedHandler> TYPE = new Type<VideoEndedHandler>();

    public static Type<VideoEndedHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoEndedHandler handler) {
        handler.onVideoEnded(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<VideoEndedHandler> getAssociatedType() {
        return TYPE;
    }
}
