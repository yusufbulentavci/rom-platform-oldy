package com.bilgidoku.rom.site.kamu.register.client;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;


public class register extends RomEntryPoint{

	public register() {
		super("Rom Server Register Application", false, null, true, false);
	}

	@Override
	public void main() {
		RomEntryPoint.one.clear();
		RegisterUI lui = new RegisterUI();
		RomEntryPoint.one.addToRootPanel(lui);
	}

}
