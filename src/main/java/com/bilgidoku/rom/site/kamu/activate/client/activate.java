package com.bilgidoku.rom.site.kamu.activate.client;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;


public class activate extends RomEntryPoint {

	public activate() {
		super("Rom Server Active App", false, null, true, false);
	}

	@Override
	protected void main() {
		
		RomEntryPoint.one.clear();
		ActivateUI lui = new ActivateUI();
		RomEntryPoint.one.addToRootPanel(lui);

	}

}
