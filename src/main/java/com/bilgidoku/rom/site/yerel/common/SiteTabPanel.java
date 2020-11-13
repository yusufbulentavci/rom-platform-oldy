package com.bilgidoku.rom.site.yerel.common;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class SiteTabPanel extends TabLayoutPanel {

	public SiteTabPanel(double barHeight, Unit barUnit) {
		super(barHeight, barUnit);
	}


//	public boolean exists(String title) {
//		int index = getIndex(title);
//		if (index < 0)
//			return false;
//		else
//			return true;
//	}


	public void flashTab(int index) {
		Widget hdr = this.getTabWidget(index);
		hdr.addStyleDependentName("flash");
	}

//	public void add(String uri, Widget tab, Widget header) {
//		int index = getIndex(uri);
//		if (index < 0) {
//			this.add(tab, header);
//			this.selectTab(tab, false);
//		} else {
//			this.selectTab(index, false);
//		}
//
//	}

//	public void closeAndAdd(String title, Widget header, Widget tab) {
//		int index = getIndex(title);
//		if (index < 0) {
//			this.add(header, tab);
//			this.selectTab(tab);
//		} else {
//			this.selectTab(index);
//		}
//	}

	public int getIndex(String id) {
		int index = -1;
		boolean found = false;
		for (int j = 0; j < this.getWidgetCount(); j++) {
			Widget tabHeader = this.getTabWidget(j);
			index = this.getWidgetIndex(this.getWidget(j));
			if (tabHeader.getTitle().equals(id)) {
				found = true;
				break;
			}
		}
		if (found)
			return index;
		else
			return -1;

	}

}
