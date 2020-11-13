package com.bilgidoku.rom.site.yerel.common;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

public class SiteCurrencyBox extends Composite{

	public SiteCurrencyBox() {
		ListBox lb = new ListBox();
		lb.addItem("$", "0");
		lb.addItem("TL", "1");
		lb.addItem("Euro", "2");		
		initWidget(lb);
	}
	
	

}
