package com.bilgidoku.rom.site.yerel.pagedlgs;

import com.bilgidoku.rom.gwt.client.util.Layer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

public class Spinner extends Composite {
	Image img = new Image("/_local/images/spinner.gif");
	
	public Spinner() {	
		img.getElement().getStyle().setZIndex(Layer.layer4);
		img.setVisible(false);
		initWidget(img);

		// Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
		// @Override
		// public boolean execute() {
		// DOM.getElementById("spinner").getStyle().setDisplay(Display.NONE);
		// return false;
		// }
		// }, 1500);

	}

	public void start() {
		img.setVisible(true);
	}

	public void stop() {
		img.setVisible(false);
	}
}
