package com.bilgidoku.rom.site.kamu.pay.client;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.araci.client.site.CartDao;
import com.bilgidoku.rom.gwt.araci.client.site.CartResponse;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.ecommerce.CartCallBack;
import com.bilgidoku.rom.gwt.client.util.ecommerce.PnlCartItems;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PnlCartList extends Composite {

	private final VerticalPanel vp = new VerticalPanel();
	private PayFlowUI payFlowUI;

	public PnlCartList(PayFlowUI payFlowUI) {
		this.payFlowUI = payFlowUI;
		vp.setStyleName("site-panel");
		vp.setWidth("700px");
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		vp.setSpacing(4);
		this.initWidget(new SimplePanel(vp));

		CartDao.activeget("/_/_cart", new CartResponse() {
			@Override
			public void ready(Cart value) {
				loadData(value);
			}
		});
	}

	private void loadData(Cart cart) {
		vp.clear();
		if (cart == null || cart.calcdetails == null) {
			HTML h = new HTML(pay.trans.emptyCart());
			h.getElement().getStyle().setPadding(20, Unit.PX);
			vp.add(h);
		} else {
			SiteButton btnFinishOrder = new SiteButton("Alışverişe Devam Et", "Alışverişe Devam Et");
			btnFinishOrder.addStyleName("site-btnprev");
			btnFinishOrder.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Location.replace("/");
				}
			});
			btnFinishOrder.setWidth("200px");
			// vp.add(getCartUi(cart));
			vp.add(new PnlCartItems(cart, new CartCallBack() {
				@Override
				public void changeStockAmount(String stock, int i) {
					changeCartStockAmount(stock, i);
				}
			}));
			vp.add(btnFinishOrder);
		}
	}

	protected void changeCartStockAmount(String stockUri, int diff) {
		CartDao.add(stockUri, diff, null, "/_/_cart", new BooleanResponse() {
			@Override
			public void ready(Boolean value) {
				refreshCart(true);
			}
		});
	}

	protected void refreshCart(boolean b) {
		CartDao.activeget("/_/_cart", new CartResponse() {
			@Override
			public void ready(Cart value) {
				loadData(value);
				payFlowUI.cartChanged(value);
			}
		});
	}

}
