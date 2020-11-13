package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoPauseHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Playback has been paused. Fired after the pause() method has returned.
 * 
 * @author michael.guiral
 * 
 */
public class VideoPauseEvent extends GwtEvent<VideoPauseHandler> {
    private static final Type<VideoPauseHandler> TYPE = new Type<VideoPauseHandler>();

    public static Type<VideoPauseHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoPauseHandler handler) {
        handler.onPause(this);
    }

    @Override
    public Type<VideoPauseHandler> getAssociatedType() {
        return TYPE;
    }
}
