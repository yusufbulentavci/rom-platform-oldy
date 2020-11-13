package com.bilgidoku.rom.site.kamu.pay.client;

import java.util.Iterator;
import java.util.Set;

import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

class PnlNext extends Composite {

	public final SiteButton btnNext = new SiteButton(pay.trans.next(), pay.trans.next());

	private final VerticalPanel vp = new VerticalPanel();

	private PayFlowUI payUI;

	public PnlNext(PayFlowUI payUI) {

		this.payUI = payUI;
		forNext();
		btnNext.removeStyleName("gwt-Button");
		btnNext.addStyleName("site-btn site-btnnext");

		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vp.getElement().getStyle().setMarginTop(55, Unit.PX);
		vp.setStyleName("site-panel");
		vp.setSize("200px", "50px");

		initWidget(vp);

	}

	private void forNext() {
		btnNext.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				payUI.goNext();
			}
		});
	}

	public void enableNext() {
		stateBtn(true);
	}

	public void disableNext() {
		stateBtn(false);
	}

	private void stateBtn(boolean b) {
		btnNext.setEnabled(b);
		if (b)
			btnNext.getElement().getStyle().setOpacity(1);
		else
			btnNext.getElement().getStyle().setOpacity(.5);

	}

	public void loadData(Cart cart) {
		vp.clear();
		vp.add(ClientUtil.getTitle(pay.trans.orderSummary(), null));

		JSONObject jo = cart.items.getValue().isObject();
		Set<String> keys = jo.keySet();
		int total = 0;
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			try {
				Double dbl = jo.get(key).isObject().get("amount").isNumber().doubleValue();
				total = total + dbl.intValue();
			} catch (Exception e) {
			}
		}

		vp.add(new HTML(total + " " + pay.trans.products()));
		vp.add(new HTML(pay.trans.totalAmount()));
		vp.add(new HTML(ClientUtil.getPrice(cart.totalprice.getValue())));
		vp.add(btnNext);
	}
}
