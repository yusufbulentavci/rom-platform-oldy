package com.bilgidoku.rom.gwt.client.util.video.events;

import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoVolumeChangeHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Either the volume attribute or the muted attribute has changed. Fired after
 * the relevant attribute's setter has returned.
 * 
 * @author michael.guiral
 * 
 */
public class VideoVolumeChangeEvent extends GwtEvent<VideoVolumeChangeHandler> {
    private static final Type<VideoVolumeChangeHandler> TYPE = new Type<VideoVolumeChangeHandler>();

    public static Type<VideoVolumeChangeHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VideoVolumeChangeHandler handler) {
        handler.onVolumeChange(this);
    }

    @Override
    public Type<VideoVolumeChangeHandler> getAssociatedType() {
        return TYPE;
    }
}
