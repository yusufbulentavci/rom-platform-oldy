package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoDurationChangeHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * The duration attribute has just been updated.
 * 
 * @author michael.guiral
 * 
 */
public class VideoDurationChangeEvent extends GwtEvent<VideoDurationChangeHandler> {
    private static final Type<VideoDurationChangeHandler> TYPE = new Type<VideoDurationChangeHandler>();

    public static Type<VideoDurationChangeHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoDurationChangeHandler handler) {
        handler.onDurationChange(this);
    }

    @Override
    public Type<VideoDurationChangeHandler> getAssociatedType() {
        return TYPE;
    }
}
