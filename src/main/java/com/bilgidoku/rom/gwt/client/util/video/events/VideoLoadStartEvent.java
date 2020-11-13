package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoLoadStartHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * The user agent begins looking for media data, as part of the resource
 * selection algorithm.
 * 
 * @author michael.guiral
 * 
 */
public class VideoLoadStartEvent extends GwtEvent<VideoLoadStartHandler> {
    private static final Type<VideoLoadStartHandler> TYPE = new Type<VideoLoadStartHandler>();

    public static Type<VideoLoadStartHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoLoadStartHandler handler) {
        handler.onLoadStart(this);
    }

    @Override
    public Type<VideoLoadStartHandler> getAssociatedType() {
        return TYPE;
    }
}
