package com.bilgidoku.rom.gwt.client.util.video.dom;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TagName;

@TagName(VideoSourceElement.TAG)
public class VideoSourceElement extends Element {
    public static final String TAG = "source";

    public static VideoSourceElement as(Element elem) {
        assert elem.getTagName().equalsIgnoreCase(TAG);
        return (VideoSourceElement) elem;
    }

    protected VideoSourceElement() {
    }

    public final native void setType(String type) /*-{
    	this.type = type;
    }-*/;

    public final native String getType() /*-{
    	return this.type;
    }-*/;

    public final native void setSrc(String src) /*-{
    	this.src = src;
    }-*/;

    public final native String getSrc() /*-{
    	return this.src;
    }-*/;
}