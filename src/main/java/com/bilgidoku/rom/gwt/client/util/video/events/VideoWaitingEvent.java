package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoWaitingHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Playback has stopped because the next frame is not available, but the user
 * agent expects that frame to become available in due course.
 * 
 * @author michael.guiral
 * 
 */
public class VideoWaitingEvent extends GwtEvent<VideoWaitingHandler> {
    private static final Type<VideoWaitingHandler> TYPE = new Type<VideoWaitingHandler>();

    public static Type<VideoWaitingHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoWaitingHandler handler) {
        handler.onWaiting(this);
    }

    @Override
    public Type<VideoWaitingHandler> getAssociatedType() {
        return TYPE;
    }
}
