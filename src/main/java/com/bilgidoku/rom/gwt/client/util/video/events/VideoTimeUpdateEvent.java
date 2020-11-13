package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoTimeUpdateHandler;
import com.google.gwt.event.dom.client.DomEvent;

/**
 * The current playback position changed as part of normal playback or in an
 * especially interesting way, for example discontinuously.
 * 
 * @author michael.guiral
 * 
 */
public class VideoTimeUpdateEvent extends DomEvent<VideoTimeUpdateHandler> {
    private static final Type<VideoTimeUpdateHandler> TYPE = new Type<VideoTimeUpdateHandler>("timeupdate", new VideoTimeUpdateEvent());

    public static Type<VideoTimeUpdateHandler> getType() {
        return TYPE;
    }

    protected VideoTimeUpdateEvent() {
    }

    @Override
    public com.google.gwt.event.dom.client.DomEvent.Type<VideoTimeUpdateHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoTimeUpdateHandler handler) {
        handler.onTimeUpdated(this);
    }
}
