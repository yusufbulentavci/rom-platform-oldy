package com.bilgidoku.rom.site.kamu.activate.client;

import com.bilgidoku.rom.gwt.client.util.common.TopOld;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;

public class ActivateUI  extends Composite{
	
	public ActivateUI() {
		
		Steps st = new Steps();
		DockLayoutPanel holder = new DockLayoutPanel(Unit.PX);
		holder.addNorth(new TopOld("contact"), 34);
		holder.add(st);
		this.initWidget(holder);

	}
	
}
