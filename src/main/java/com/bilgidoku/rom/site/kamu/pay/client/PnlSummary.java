package com.bilgidoku.rom.site.kamu.pay.client;

import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.client.util.panels.PnlOrderDetail;
import com.google.gwt.user.client.ui.Composite;

public class PnlSummary extends Composite {

	private final PnlOrderDetail order = new PnlOrderDetail();

	public PnlSummary(PayFlowUI payFlowUI) {
		order.isOnFlow(payFlowUI);
		initWidget(order);
	}

	public void loadData(Cart cart) {
		order.loadCartData(cart, null);		
	}

}
