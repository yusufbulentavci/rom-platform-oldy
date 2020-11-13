package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoStalledHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * The user agent is trying to fetch media data, but data is unexpectedly not
 * forthcoming
 * 
 * @author michael.guiral
 * 
 */
public class VideoStalledEvent extends GwtEvent<VideoStalledHandler> {
    private static final Type<VideoStalledHandler> TYPE = new Type<VideoStalledHandler>();

    public static Type<VideoStalledHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoStalledHandler handler) {
        handler.onStalled(this);
    }

    @Override
    public Type<VideoStalledHandler> getAssociatedType() {
        return TYPE;
    }
}
