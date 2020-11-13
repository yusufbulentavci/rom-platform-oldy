package com.bilgidoku.rom.gwt.client.util.browse.image;

import com.google.gwt.event.shared.GwtEvent;

public class DownloadCompleted extends GwtEvent<DownloadCompletedHandler> {
	public String selectedUri = null;
	public DownloadCompleted(String item) {
		super();
		selectedUri = item;
	}

	public static Type<DownloadCompletedHandler> TYPE = new Type<DownloadCompletedHandler>();

	public com.google.gwt.event.shared.GwtEvent.Type<DownloadCompletedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DownloadCompletedHandler handler) {
		handler.done(this);

	}

}
