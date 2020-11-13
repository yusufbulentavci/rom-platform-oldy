package com.bilgidoku.rom.gwt.client.util.video.dom;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TagName;

@TagName(VideoElement.TAG)
public class VideoElement extends Element {
    public static final String TAG = "video";

    public static VideoElement as(Element elem) {
        assert elem.getTagName().equalsIgnoreCase(TAG);
        return (VideoElement) elem;
    }

    protected VideoElement() {
    }

    // getters setters
    public final native void setPoster(String poster) /*-{
           this.poster = poster;
       }-*/;

    public final native String getPoster() /*-{
           return this.poster;
       }-*/;

    public final native void setAutoPlay(boolean autoPlay) /*-{
           this.autoplay = autoPlay;
       }-*/;

    public final native boolean isAutoPlay() /*-{
           return this.autoplay;
       }-*/;

    public final native void setControls(boolean controls) /*-{
           this.controls = controls;
       }-*/;

    public final native boolean isControls() /*-{
           return this.controls;
       }-*/;

    /**
     * Seek to the given time.
     * 
     * @param time
     *            the playback position, in seconds.
     */
    public final native void setCurrentTime(double time) /*-{
           this.currentTime = time;
       }-*/;

    /**
     * @return the current playback position, in seconds.
     */
    public final native double getCurrentTime() /*-{
           return this.currentTime;
       }-*/;

    public final native double getDefaultPlaybackRate() /*-{
           return this.defaultPlaybackRate;
       }-*/;

    public final native void setDefaultPlaybackRate(double defaultPlaybackRate) /*-{
           this.defaultPlaybackRate = defaultPlaybackRate;
       }-*/;

    public final native double getPlaybackRate() /*-{
           return this.playbackRate;
       }-*/;

    public final native void setPlaybackRate(double playbackRate) /*-{
           this.defaultPlaybackRate = playbackRate;
       }-*/;

    /**
     * @return <li><b>the initial playback position</b>, that is, time to which
     *         the media resource was automatically seeked when it was loaded.</li>
     * <br/>
     *         <li><b>0</b> if the initial playback position is still unknown.</li>
     */
    public final native double getInitialTime() /*-{
           return this.initialTime;
       }-*/;

    /**
     * @return <li><b>the length of the media resource, in seconds, </b>assuming
     *         that the start of the media resource is at time zero.</li> <br/>
     *         <li><b>NaN</b> if the duration isn't available.</li> <br/>
     *         <li><b>Infinity</b> for unbounded streams.</li>
     */
    public final native double getDuration() /*-{
           return this.duration;
       }-*/;

    public final native boolean isPaused() /*-{
           return this.paused;
       }-*/;

    public final native boolean isPlayed() /*-{
           return !this.paused;
       }-*/;

    public final native boolean isEnded() /*-{
           return this.ended;
       }-*/;

    // actions functions
    public final native void playPause() /*-{
           if (!this.paused){
               this.pause();
           } else {
               this.play();
           }
       }-*/;

    public final native boolean isSeeking() /*-{
           return this.seeking;
       }-*/;

    public final native boolean isSeekable(double time) /*-{
           var seekableEndTimeRange = this.seekable.end(this.seekable.length - 1);
           return time <= seekableEndTimeRange;
       }-*/;

    public final native double getBufferedEndTime() /*-{
           return this.buffered.end(this.buffered.length -1);
       }-*/;

    public final native String getCurrentSrc() /*-{
           return this.currentSrc;
       }-*/;
}