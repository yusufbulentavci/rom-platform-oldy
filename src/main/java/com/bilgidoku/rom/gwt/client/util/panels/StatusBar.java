package com.bilgidoku.rom.gwt.client.util.panels;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.ui.HTML;

public class StatusBar extends HTML{
	
	static StatusBar one;
	
	public static StatusBar getOne(){
		if(one==null)
			one=new StatusBar();
		return one;
	}
	
	private StatusBar(){
		super();
	}

	public void setStatus(String stat) {
		this.setVisible(true);
		this.setHTML(stat);
		this.setStyleName("site-statusbar");

		Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
			@Override
			public boolean execute() {
				setHTML("");
				setVisible(false);
				return false;
			}
		}, 5000);
		
	}

	public void setError(String stat) {
		this.setVisible(true);
		this.setHTML(stat);
		this.setStyleName("site-statuserror");

		Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
			@Override
			public boolean execute() {
				setHTML("");
				setVisible(false);
				// context.clearRect(0, 0, 400, 400);
				return false;
			}
		}, 5000);
	}

}
