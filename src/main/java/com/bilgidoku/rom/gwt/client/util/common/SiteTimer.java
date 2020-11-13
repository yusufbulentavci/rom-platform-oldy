package com.bilgidoku.rom.gwt.client.util.common;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class SiteTimer extends Composite {
	private final Label countdown = new Label();
	int count;
	Timer t;
	
	public SiteTimer() {
		countdown.setStyleName("site-btn disabled");
		initWidget(countdown);
	}

	public void startCountDown(int seconds, Runnable runnable) {
		if (seconds <= 1)
			return;
		count = seconds -1;
		setLabel(count);
		t = new Timer() {
			@Override
			public void run() {
				count--;
				setLabel(count);						        
		        if (count == 0) {		        	
		        	cancel();
		        }
		        
			}
		};
		t.scheduleRepeating(1000); // 1 saniye
	}

	protected void setLabel(int count2) {
	    int hours = (int) count2 / 3600;
	    int remainder = (int) count2 - hours * 3600;
	    int mins = remainder / 60;
	    remainder = remainder - mins * 60;
	    int secs = remainder;
	    countdown.setText((hours != 0 ? hours + ":" : "") + (mins <= 9 ? "0" + mins : mins) + ":" + (secs <= 9 ? "0" + secs : secs));		
	}

	public void stop() {
		if (t == null)
			return;
		t.cancel();
	}
}
