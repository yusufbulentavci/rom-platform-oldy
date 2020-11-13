package com.bilgidoku.rom.site.yerel.wgts.edit;

import com.bilgidoku.rom.shared.RunException;


public interface Seen {
	public void showData() throws RunException;
	public void dataChanged(String name, String value);
	public void dataChanged(String type, String name, String value);
	
}
