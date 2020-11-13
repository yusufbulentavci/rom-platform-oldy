package com.bilgidoku.rom.site.yerel.common;

import com.bilgidoku.rom.gwt.client.util.help.Helpy;
import com.bilgidoku.rom.gwt.client.util.help.NeedHelp;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class TabPanelBase extends TabLayoutPanel implements NeedHelp{

	public TabPanelBase(double barHeight, Unit barUnit) {
		super(barHeight, barUnit);
	}

	@Override
	public Helpy[] helpies() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
