package com.bilgidoku.rom.site.kamu.pay.client;

import com.bilgidoku.rom.gwt.client.util.panels.TabOrders;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContactUI extends Composite {

	private final NavMain nav = new NavMain(this);
	private final HorizontalPanel holder = new HorizontalPanel();
	
	public ContactUI() {
				
		holder.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		holder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		holder.add(nav);
		holder.setCellWidth(nav, "190px");
		holder.add(new TabOrders("pay"));
		holder.addStyleName("site-holder");
		holder.setSize("800px", "400px");

//		DockLayoutPanel lp = new DockLayoutPanel(Unit.PX);
//		lp.addNorth(new TopOld(pay.trans.contactTitle(), Version.pomversion), 34);
//		lp.add(new ScrollPanel(holder));

		this.initWidget(holder);
	}
	

	public void changeMainPnl(Widget pnl) {
		if (holder.getWidgetCount() > 1)
			holder.remove(1);
		holder.add(pnl);
	}


}
