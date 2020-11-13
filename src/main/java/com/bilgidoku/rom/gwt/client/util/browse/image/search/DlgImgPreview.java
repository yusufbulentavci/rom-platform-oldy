package com.bilgidoku.rom.gwt.client.util.browse.image.search;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class DlgImgPreview extends DialogBox {

	public DlgImgPreview(String path) {
		final Image img = new Image(path);		
		this.setModal(false);
		this.setAutoHideEnabled(true);
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		this.setWidget(new SimplePanel(img));

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {			
			@Override
			public void execute() {
				DlgImgPreview.this.setSize(img.getWidth() + "px", img.getHeight() + "px");
				DlgImgPreview.this.center();				
			}
		});
		
	}
}

