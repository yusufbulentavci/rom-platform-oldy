package com.bilgidoku.rom.gwt.client.util.video;

import java.util.List;

import com.bilgidoku.rom.gwt.client.util.video.VideoSource.VideoType;
import com.bilgidoku.rom.gwt.client.util.video.dom.VideoElement;
import com.bilgidoku.rom.gwt.client.util.video.dom.VideoSourceElement;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoAbortEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoCanPlayEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoCanPlayThroughEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoDurationChangeEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoEmptyEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoEndedEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoErrorEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoLoadDataEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoLoadMetadataEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoLoadStartEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoPauseEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoPlayEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoPlayingEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoProgressEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoRateChangeEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoSeekedEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoSeekingEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoStalledEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoSuspendEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoTimeUpdateEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoVolumeChangeEvent;
import com.bilgidoku.rom.gwt.client.util.video.events.VideoWaitingEvent;
import com.bilgidoku.rom.gwt.client.util.video.handlers.HasVideoHandlers;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoAbortHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoCanPlayHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoCanPlayThroughHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoDurationChangeHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoEmptyHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoEndedHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoErrorHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoLoadDataHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoLoadMetadataHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoLoadStartHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoPauseHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoPlayHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoPlayingHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoProgressHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoRateChangeHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoSeekedHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoSeekingHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoStalledHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoSuspendHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoTimeUpdateHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoVolumeChangeHandler;
import com.bilgidoku.rom.gwt.client.util.video.handlers.VideoWaitingHandler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

/**
 * A standard HTML5 video widget
 * 
 * @author michael.guiral
 * 
 */
public class VideoWidget extends Widget implements HasVideoHandlers {
    private static final String UNSUPPORTED_VIDEO_TAG = "Sorry, your browser does not support the &lt;video&gt; element.";
    private VideoElement videoElement;
    private Element unsupportedElement;
    private HandlerManager videoHandlerManager;

    /**
     * Create a default video HTML tag <br/>
     * <br/>
     * Default values<br/>
     * <li>autoPlay : false</li> <li>controls : false</li> <li>poster : null</li>
     */
    public VideoWidget() {
        this(false, false, null);
    }

    /**
     * @param autoPlay
     *            <b>true</b> if you want the user agent automatically begin
     *            playback of the media resource as soon as it can do so without
     *            stopping. <b>false</b> otherwise
     * @param controls
     *            - <b>false</b> if you want to have custom scripted controller,
     *            <b>true</b> if you would like the user agent to provide its
     *            own set of controls.
     * @param poster
     *            - <b>The image file address</b> that the user agent can show
     *            while no video data is available
     */
    public VideoWidget(boolean autoPlay, boolean controls, String poster) {
        super();
        videoElement = VideoElement.as(DOM.createElement(VideoElement.TAG));
        this.videoHandlerManager = new HandlerManager(this);
        setDefaultPlaybackRate(1);
        setElement(videoElement);
        addUnsupportedMessage();
        setAutoPlay(autoPlay);
        setControls(controls);
        setPoster(poster);
    }

    /**
     * @param poster
     *            represent the address of an image file that the user agent can
     *            show while no video data is available
     */
    public void setPoster(String poster) {
        if (poster != null) {
            videoElement.setPoster(poster);
        }
    }

    /**
     * @return <li><b>The image file address</b> that the user agent can show
     *         while no video data is available</li> <br/>
     *         <li><b>null</b> if no image has been set</li>
     * 
     */
    public String getPoster() {
        return videoElement.getPoster();
    }

    /**
     * @param autoPlay
     *            <b>true</b> if you want the user agent automatically begin
     *            playback of the media resource as soon as it can do so without
     *            stopping. <br/>
     *            <b>false</b> otherwise
     * @throws IllegalArgumentException
     *             if autoPlay is <b>null</b>
     */
    public void setAutoPlay(Boolean autoPlay) {
        if (autoPlay == null) {
            throw new IllegalArgumentException("autoplay must not be null");
        }
        videoElement.setAutoPlay(autoPlay);
    }

    /**
     * @param controls
     *            <b>false</b> if you want to have custom scripted controller, <br/>
     *            <b>true</b> if you would like the user agent to provide its
     *            own set of controls.
     * @throws IllegalArgumentException
     *             if controls is <b>null</b>
     */
    public void setControls(Boolean controls) {
        if (controls == null) {
            throw new IllegalArgumentException("controls must not be null");
        }
        videoElement.setControls(controls);
    }

    /**
     * @return <b>true</b> if the user agent automatically begin playback. <br/>
     *         <b>false</b> otherwise
     */
    public boolean isAutoPlay() {
        return videoElement.isAutoPlay();
    }

    /**
     * @return <b>false</b> if you want to have custom scripted controller <br/>
     *         <b>true</b> if you would like the user agent to provide its own
     *         set of controls.
     */
    public boolean isControls() {
        return videoElement.isControls();
    }

    /**
     * @param sources
     *            list of {@link VideoSource} that represent all the available
     *            sources for the video element
     */
    public void setSources(List<VideoSource> sources) {
        for (VideoSource videoSource : sources) {
            VideoSourceElement sourceElement = VideoSourceElement.as(DOM.createElement(VideoSourceElement.TAG));
            if (videoSource.getSrc() == null) {
                throw new IllegalArgumentException("src must not be null");
            }
            sourceElement.setSrc(videoSource.getSrc());
            if (videoSource.getVideoType() != null) {
                sourceElement.setType(videoSource.getVideoType().getType());
            }
            if (VideoType.WEBM.equals(videoSource.getVideoType())) {
                videoElement.insertAfter(sourceElement, unsupportedElement);
            } else {
                videoElement.appendChild(sourceElement);
            }
        }
    }

    /**
     * Add a message that be show if the user agent can display HTML5 video tag
     */
    private void addUnsupportedMessage() {
        unsupportedElement = DOM.createElement("p");
        unsupportedElement.setInnerHTML(UNSUPPORTED_VIDEO_TAG);
        videoElement.appendChild(unsupportedElement);
    }

    /**
     * Switch the playback status between paused and played
     */
    public void playPause() {
        videoElement.playPause();
    }

    /**
     * @return <b>true</b> if playback is paused<br/>
     *         <b>false</b> otherwise
     */
    public boolean isPaused() {
        return videoElement.isPaused();
    }

    /**
     * @return <b>true</b> if playback is played <br/>
     *         <b>false</b> otherwise
     */
    public boolean isPlayed() {
        return videoElement.isPlayed();
    }

    /**
     * @return <b>true</b> if the user agent is currently seeking. <br/>
     *         <b>false</b> otherwise
     */
    public boolean isSeeking() {
        return videoElement.isSeeking();
    }

    /**
     * @param time
     *            the time where user agent want to seek
     * @return <b>true</b> if it is possible for the user agent to seek. <br/>
     *         <b>false</b> otherwise
     */
    public boolean isSeekable(double time) {
        return videoElement.isSeekable(time);
    }

    /**
     * @param currentTime
     *            the current playback position, expressed in seconds
     */
    public void setCurrentTime(double currentTime) {
        videoElement.setCurrentTime(currentTime);
    }

    /**
     * @return the current playback position, expressed in seconds
     */
    public double getCurrentTime() {
        return videoElement.getCurrentTime();
    }

    /**
     * @return <b>the initial playback position</b>, that is, time to which the
     *         media resource was automatically seeked when it was loaded. <br/>
     *         <b>0</b> if the initial playback position is still unknown.
     */
    public double getInitialTime() {
        return videoElement.getInitialTime();
    }

    /**
     * @return <li><b>the length of the media resource, in seconds, </b>assuming
     *         that the start of the media resource is at time zero.</li> <br/>
     *         <li><b>-1</b> for unbounded streams.</li>
     * @throws NumberFormatException
     *             if duration is NaN
     */
    public double getDuration() {
        double duration = videoElement.getDuration();
        if (Double.isNaN(duration)) {
            throw new NumberFormatException("The video the duration isn't available");
        } else if (Double.isInfinite(duration)) {
            duration = -1;
        }
        return duration;
    }

    /**
     * @return <b>true</b> if playback has reached the end of the media
     *         resource. <br/>
     *         <b>false</b> otherwise
     */
    public boolean isEnded() {
        return videoElement.isEnded();
    }

    /**
     * The default rate has no direct effect on playback, but if the user
     * switches to a fast-forward mode, when they return to the normal playback
     * mode, it is expected that the rate of playback will be returned to the
     * default rate of playback.
     * 
     * @param defaultPlaybackRate
     *            the desired speed at which the media resource is to play. <br/>
     *            if value < 1.0 the playback is slower <br/>
     *            if value > 1.0 the playback is faster
     */
    public void setDefaultPlaybackRate(double defaultPlaybackRate) {
        videoElement.setDefaultPlaybackRate(defaultPlaybackRate);
    }

    /**
     * The default rate has no direct effect on playback, but if the user
     * switches to a fast-forward mode, when they return to the normal playback
     * mode, it is expected that the rate of playback will be returned to the
     * default rate of playback.
     * 
     * @return the default rate of playback, for when the user is not
     *         fast-forwarding or reversing through the media resource.
     */
    public double getDefaultPlaybackRate() {
        return videoElement.getDefaultPlaybackRate();
    }

    /**
     * @param playbackRate
     *            the current rate playback, where 1.0 is normal speed.
     */
    public void setPlaybackRate(double playbackRate) {
        videoElement.setPlaybackRate(playbackRate);
    }

    /**
     * @return the current rate playback, where 1.0 is normal speed.
     */
    public double getPlaybackRate() {
        return videoElement.getPlaybackRate();
    }

    /**
     * @return the current buffer position end time, in second
     */
    public double getBufferedEndTime() {
        return videoElement.getBufferedEndTime();
    }

    /**
     * @return <b>the address</b> of the current media resource. <br/>
     *         <b>""</b> when there is no media resource.
     */
    public String getCurrentSrc() {
        return videoElement.getCurrentSrc();
    }

    /**
     * This function is call in JNI code to dispatch {@link GwtEvent}
     * 
     * @param event
     */
    public void fireEvent(Object event) {
        if (event instanceof GwtEvent<?>) {
            GwtEvent<?> gwtEvent = (GwtEvent<?>) event;
            if (videoHandlerManager != null) {
                videoHandlerManager.fireEvent(gwtEvent);
            }
        }
    }

    /**
     * Handlers
     */
    /**
     * The user agent stops fetching the media data before it is completely
     * downloaded, but not due to an error.
     * 
     * @param abortHandler
     */
    @Override
    public HandlerRegistration addAbortHandler(VideoAbortHandler abortHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoAbortEvent.getType(), abortHandler);
        addAbortEventHandler();
        return handlerRegistration;
    }

    /**
     * The user agent can resume playback of the media data, but estimates that
     * if playback were to be started now, the media resource could not be
     * rendered at the current playback rate up to its end without having to
     * stop for further buffering of content.
     * 
     * @param canPlayHandler
     */
    @Override
    public HandlerRegistration addCanPlayHandler(VideoCanPlayHandler canPlayHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoCanPlayEvent.getType(), canPlayHandler);
        addCanPlayEventHandler();
        return handlerRegistration;
    }

    /**
     * The user agent estimates that if playback were to be started now, the
     * media resource could be rendered at the current playback rate all the way
     * to its end without having to stop for further buffering.
     * 
     * @param canPlayThroughHandler
     */
    @Override
    public HandlerRegistration addCanPlayThroughHandler(VideoCanPlayThroughHandler canPlayThroughHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoCanPlayThroughEvent.getType(), canPlayThroughHandler);
        addCanPlayThroughEventHandler();
        return handlerRegistration;
    }

    /**
     * The duration attribute has just been updated.
     * 
     * @param durationChangeHandler
     */
    @Override
    public HandlerRegistration addDurationChangeHandler(VideoDurationChangeHandler durationChangeHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoDurationChangeEvent.getType(), durationChangeHandler);
        addDurationChangeEventHandler();
        return handlerRegistration;
    }

    /**
     * The media element has not yet been initialized. All attributes are in
     * their initial states.
     * 
     * @param emptyHandler
     */
    @Override
    public HandlerRegistration addEmptyHandler(VideoEmptyHandler emptyHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoEmptyEvent.getType(), emptyHandler);
        addEmptyEventHandler();
        return handlerRegistration;
    }

    /**
     * Playback has stopped because the end of the media resource was reached.
     * 
     * @param endedHandler
     */
    @Override
    public HandlerRegistration addEndedHandler(VideoEndedHandler endedHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoEndedEvent.getType(), endedHandler);
        addEndedEventHandler();
        return handlerRegistration;
    }

    /**
     * An error occurs while fetching the media data.
     * 
     * @param errorHandler
     */
    @Override
    public HandlerRegistration addErrorHandler(VideoErrorHandler errorHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoErrorEvent.getType(), errorHandler);
        addErrorEventHandler();
        return handlerRegistration;
    }

    /**
     * The user agent can render the media data at the current playback position
     * for the first time.
     * 
     * @param loadDataHandler
     */
    @Override
    public HandlerRegistration addLoadDataHandler(VideoLoadDataHandler loadDataHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoLoadDataEvent.getType(), loadDataHandler);
        addLoadDataEventHandler();
        return handlerRegistration;
    }

    /**
     * The user agent has just determined the duration and dimensions of the
     * media resource
     * 
     * @param loadMetadataHandler
     */
    @Override
    public HandlerRegistration addLoadMetadataHandler(VideoLoadMetadataHandler loadMetadataHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoLoadMetadataEvent.getType(), loadMetadataHandler);
        addLoadMetadataEventHandler();
        return handlerRegistration;
    }

    /**
     * The user agent begins looking for media data, as part of the resource
     * selection algorithm.
     * 
     * @param loadStartHandler
     */
    @Override
    public HandlerRegistration addLoadStartHandler(VideoLoadStartHandler loadStartHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoLoadStartEvent.getType(), loadStartHandler);
        addLoadStartEventHandler();
        return handlerRegistration;
    }

    /**
     * Playback has been paused. Fired after the pause() method has returned.
     * 
     * @param pauseHandler
     */
    @Override
    public HandlerRegistration addPauseHanlder(VideoPauseHandler pauseHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoPauseEvent.getType(), pauseHandler);
        addPauseEventHandler();
        return handlerRegistration;
    }

    /**
     * Playback has begun. Fired after the play() method has returned, or when
     * the autoplay attribute has caused playback to begin
     * 
     * @param playHandler
     */
    @Override
    public HandlerRegistration addPlayHandler(VideoPlayHandler playHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoPlayEvent.getType(), playHandler);
        addPlayEventHandler();
        return handlerRegistration;
    }

    /**
     * Playback has started.
     * 
     * @param playingHandler
     */
    @Override
    public HandlerRegistration addPlayingHandler(VideoPlayingHandler playingHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoPlayingEvent.getType(), playingHandler);
        addPlayingEventHandler();
        return handlerRegistration;
    }

    /**
     * The user agent is fetching media data
     * 
     * @param progressHandler
     */
    @Override
    public HandlerRegistration addProgressHandler(VideoProgressHandler progressHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoProgressEvent.getType(), progressHandler);
        addProgressEventHandler();
        return handlerRegistration;
    }

    /**
     * Either the defaultPlaybackRate or the playbackRate attribute has just
     * been updated
     * 
     * @param rateChangeHandler
     */
    @Override
    public HandlerRegistration addRateChangeHandler(VideoRateChangeHandler rateChangeHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoRateChangeEvent.getType(), rateChangeHandler);
        addRateChangeEventHandler();
        return handlerRegistration;
    }

    /**
     * The seeking attribute changed to false
     * 
     * @param seekedHandler
     */
    @Override
    public HandlerRegistration addSeekedHandler(VideoSeekedHandler seekedHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoSeekedEvent.getType(), seekedHandler);
        addSeekedEventHandler();
        return handlerRegistration;
    }

    /**
     * The seeking attribute changed to true and the seek operation is taking
     * long enough that the user agent has time to fire the event
     * 
     * @param seekingHandler
     */
    @Override
    public HandlerRegistration addSeekingHandler(VideoSeekingHandler seekingHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoSeekingEvent.getType(), seekingHandler);
        addSeekingEventHandler();
        return handlerRegistration;
    }

    /**
     * The user agent is trying to fetch media data, but data is unexpectedly
     * not forthcoming
     * 
     * @param stalledHandler
     */
    @Override
    public HandlerRegistration addStalledHandler(VideoStalledHandler stalledHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoStalledEvent.getType(), stalledHandler);
        addStalledEventHandler();
        return handlerRegistration;
    }

    /**
     * The user agent is intentionally not currently fetching media data, but
     * does not have the entire media resource downloaded
     * 
     * @param suspendHandler
     */
    @Override
    public HandlerRegistration addSuspendHandler(VideoSuspendHandler suspendHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoSuspendEvent.getType(), suspendHandler);
        addSuspendEventHandler();
        return handlerRegistration;
    }

    /**
     * Add a listener when the current playback position changed as part of
     * normal playback or in an especially interesting way, for example
     * discontinuously.
     * 
     * @param timeUpdateHandler
     */
    @Override
    public HandlerRegistration addTimeUpdateHandler(VideoTimeUpdateHandler timeUpdateHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoTimeUpdateEvent.getType(), timeUpdateHandler);
        addTimeUpdateEventHandler();
        return handlerRegistration;
    }

    /**
     * Either the volume attribute or the muted attribute has changed. Fired
     * after the relevant attribute's setter has returned
     * 
     * @param volumeChangeHandler
     */
    @Override
    public HandlerRegistration addVolumeChangeHandler(VideoVolumeChangeHandler volumeChangeHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoVolumeChangeEvent.getType(), volumeChangeHandler);
        addVolumeChangeEventHandler();
        return handlerRegistration;
    }

    /**
     * Playback has stopped because the next frame is not available, but the
     * user agent expects that frame to become available in due course
     * 
     * @param waitingHandler
     */
    @Override
    public HandlerRegistration addWaitingHandler(VideoWaitingHandler waitingHandler) {
        HandlerRegistration handlerRegistration = videoHandlerManager.addHandler(VideoWaitingEvent.getType(), waitingHandler);
        addWaitingEventHandler();
        return handlerRegistration;
    }

    /**
     * JNI for event handlers
     */
    private final native void addAbortEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('abort', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoAbortEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addCanPlayEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('canplay', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoCanPlayEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addCanPlayThroughEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('canplaythrough', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoCanPlayThroughEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addDurationChangeEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('durationchange', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoDurationChangeEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addEmptyEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('emptied', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoEmptyEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addEndedEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('ended', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoEndedEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addErrorEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('error', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoErrorEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addLoadDataEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('loadeddata', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoLoadDataEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addLoadMetadataEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('loadedmetadata', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoLoadMetadataEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addLoadStartEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('loadstart', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoLoadStartEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addPauseEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('pause', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoPauseEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addPlayEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('play', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoPlayEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addPlayingEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('playing', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoPlayingEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addProgressEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('progress', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoProgressEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addRateChangeEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('ratechange', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoRateChangeEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addSeekedEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('seeked', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoSeekedEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addSeekingEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('seeking', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoSeekingEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addStalledEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('stalled', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoStalledEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addSuspendEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('suspend', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoSuspendEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addTimeUpdateEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('timeupdate', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoTimeUpdateEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addVolumeChangeEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('volumechange', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoVolumeChangeEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;

    private final native void addWaitingEventHandler() /*-{
           var videoElement = this.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::videoElement;
           var videoWidget = this;
           videoElement.addEventListener('waiting', function(){
               var event = @com.bilgidoku.rom.gwt.client.util.video.events.VideoWaitingEvent::new()();
               videoWidget.@com.bilgidoku.rom.gwt.client.util.video.VideoWidget::fireEvent(Ljava/lang/Object;)(event);
           }, true);
       }-*/;
}