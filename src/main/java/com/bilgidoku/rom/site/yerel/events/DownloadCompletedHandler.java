package com.bilgidoku.rom.site.yerel.events;

import com.google.gwt.event.shared.EventHandler;

public interface DownloadCompletedHandler extends EventHandler{
	void done(DownloadCompleted event);
}
