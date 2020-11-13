package com.bilgidoku.rom.gwt.client.util.ecommerce;

import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DlgCart extends ActionBarDlg {
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	private Cart cart;
	Ecommerce ecommerce;
	public DlgCart(Ecommerce ecommerce) {
		super(trans.cart(), null, null);
		this.ecommerce=ecommerce;
	}

	public void update(Cart cart) {
		this.cart = cart;
		run();
	}

	@Override
	public Widget ui() {
		if (this.cart == null || this.cart.calcdetails == null || totalItemCount(this.cart.items.getValue().isObject()) == 0) {
			HTML h = new HTML(trans.emptyCart());
			h.getElement().getStyle().setPadding(20, Unit.PX);
			return h;
		} else {
			SiteButton btnFinishOrder = new SiteButton(trans.finishOrder(), trans.finishOrder());
			btnFinishOrder.addStyleName("btn-fancy");
			btnFinishOrder.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Location.replace("/_public/contact.html?tab=flow");
				}
			});
			btnFinishOrder.setWidth("200px");
			PnlCartItems items = new PnlCartItems(cart, new CartCallBack() {

				@Override
				public void changeStockAmount(String stock, int i) {
					ecommerce.changeCartStockAmount(stock, i);
				}
			});

			items.setHeight("360px");

			VerticalPanel vp = new VerticalPanel();
			vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			vp.setSpacing(4);
			vp.add(items);
			vp.add(btnFinishOrder);
			return vp;
		}
	}
	
	private int totalItemCount(JSONObject object) {
		int ret = 0;
		for (String k : object.keySet()) {
			JSONObject o = object.get(k).isObject();
			ret += o.get("amount").isNumber().doubleValue();
		}
		return ret;
	}


	@Override
	public void cancel() {
	}

	@Override
	public void ok() {
	}

}
