package com.bilgidoku.rom.site.kamu.pay.client;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.araci.client.site.CartDao;
import com.bilgidoku.rom.gwt.araci.client.site.CartResponse;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PnlConfirm extends Composite {

	public PnlConfirm() {		
		final TextArea ta = new TextArea();
		ta.setSize("100%", "50px");
		if (PayFlow.cart != null && PayFlow.cart.notice != null)
			ta.setValue(PayFlow.cart.notice);

		Anchor aRs = new Anchor(pay.trans.preInformingConditions());
		aRs.addClickHandler(new ClickHandler() {				
			@Override
			public void onClick(ClickEvent event) {
				new CartParametric("crs");
			}
		});
		
		Anchor aPic = new Anchor(pay.trans.remoteSaleContract());
		aPic.addClickHandler(new ClickHandler() {				
			@Override
			public void onClick(ClickEvent event) {
				new CartParametric("cpic");
				
			}
		});
		
		final CheckBox cb = new CheckBox();
		SiteButton btnFin = new SiteButton(pay.trans.finish(), "");
		btnFin.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!cb.getValue()) {
					Window.alert(pay.trans.checkReadContractsAlert());
					return;
				}

				if (ta.getValue() != null && !ta.getValue().isEmpty())
					CartDao.setnotice(ta.getValue(), PayFlow.cart.uri, new BooleanResponse());
				
				CartDao.setdesign(false, PayFlow.cart.uri, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						if (PayFlow.cart.paystyle.equals("paypal")) {
							CartDao.getencrypted("paypal", PayFlow.cart.uri, new CartResponse() {
								public void ready(Cart value) {
									if (value.nesting == null || !value.nesting.containsKey("encryptedcart")) {
										Window.alert("System error");
										return;
									}
									String str = value.nesting.get("encryptedcart");
									Window.Location.replace(str);								
								};
							});	
						} else {
							Window.alert(pay.trans.orderSuccess());
							Window.Location.replace(Location.getProtocol() + "//" + Location.getHost());
						} 
						
					}
				});
				}
		});
		
		FlowPanel cbPnl = new FlowPanel();
		cbPnl.add(cb);
		cbPnl.add(aRs);
		cbPnl.add(new HTML(pay.trans.and()));
		cbPnl.add(aPic);
		cbPnl.add(new HTML(pay.trans.iApprove()));

		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(10);
		btnFin.addStyleName("site-btnfinish");
		
		vp.setStyleName("site-panel");
		vp.getElement().getStyle().setMarginTop(55, Unit.PX);
		vp.setWidth("250px");
		vp.add(new HTML("<b>" + pay.trans.note() + ":</b>"));
		vp.add(ta);
		vp.add(cbPnl);
		vp.add(btnFin);
		
		initWidget(vp);
	}
	
	public void loadData(Cart cart) {
		
	}
}
