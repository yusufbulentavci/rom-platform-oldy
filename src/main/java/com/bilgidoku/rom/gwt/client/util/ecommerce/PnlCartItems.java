package com.bilgidoku.rom.gwt.client.util.ecommerce;

import java.util.Iterator;
import java.util.Set;

import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.common.SiteImageThumb;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PnlCartItems extends ScrollPanel {

	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	private CartCallBack callback;

	public PnlCartItems(Cart cart, CartCallBack callback) {
		this.callback = callback;
		this.add(getCartUi(cart));
	}

	private Widget getCartUi(Cart cart) {
		JSONValue val = cart.calcdetails.getValue();
		if (val == null || val.isObject() == null)
			return new SimplePanel();
			
		JSONObject jo = val.isObject();
		Set<String> keyStocks = jo.keySet();

		FlexTable ft = new FlexTable();
		ft.setCellSpacing(0);
		ft.setCellPadding(0);

		ft.setWidget(0, 0, new HTML("<div class='cell-header grey'>" + trans.product() + "</div>"));
		ft.setWidget(0, 1, new HTML("<div class='cell-header grey'>" + trans.price() + "</div>"));
		ft.setWidget(0, 2,
				new HTML("<div class='cell-header grey' style='text-align:center;'>" + trans.number() + "</div>"));
		ft.setWidget(0, 3, new HTML("<div class='cell-header grey'>" + trans.total() + "</div>"));
		ft.setWidget(0, 4, new HTML("<div class='cell-header grey'>&nbsp;</div>"));

		int ind = 1;

		for (Iterator<String> iterator = keyStocks.iterator(); iterator.hasNext();) {

			final String stockUri = (String) iterator.next();

			JSONObject item = jo.get(stockUri).isObject();
			
			String wtitle = ClientUtil.getString(item.get("wtitle"));
			if (wtitle.length() > 50)
				wtitle = wtitle.subSequence(0, 47) + "...";

			// String wuri = ClientUtil.getString(item.get("wuri"));
			String totalprice = ClientUtil.getPrice(item.get("price"));

			final String amount = ClientUtil.getString(item.get("amount"));
			String wprice = "";
			try {
				wprice = ClientUtil.getPrice(item.get("tariff").isObject().get("price"));
			} catch (Exception e) {
				// TODO: handle exception
			}

			ft.setWidget(ind, 0, getProduct(item, stockUri));
			ft.setWidget(ind, 1, new HTML("<div class='cell cell-number'>" + wprice + "</div>"));
			
			if (callback != null) {

				SiteButton delete = new SiteButton("/_public/images/bar/bin.png", "", trans.delete());
				delete.addStyleName("btn-sml");
				delete.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						int a = Integer.parseInt(amount);
						// changeCartStockAmount(stockUri, -a);
						callback.changeStockAmount(stockUri, -a);
					}
				});
				
				ft.setWidget(ind, 2, getPlusMinus(stockUri, amount));
				ft.setWidget(ind, 3, new HTML("<div class='cell cell-number'>" + totalprice + "</div>"));
				ft.setWidget(ind, 4, delete);

				
			} else {

				ft.setHTML(ind, 2, amount);
				ft.setWidget(ind, 3, new HTML("<div class='cell cell-number'>" + totalprice + "</div>"));
				
			}


			ft.getFlexCellFormatter().setVerticalAlignment(ind, 2, HasVerticalAlignment.ALIGN_MIDDLE);
			ft.getFlexCellFormatter().setHorizontalAlignment(ind, 2, HasHorizontalAlignment.ALIGN_CENTER);
			ft.getFlexCellFormatter().setWidth(ind, 2, "100%");
			ft.getFlexCellFormatter().setVerticalAlignment(ind, 3, HasVerticalAlignment.ALIGN_MIDDLE);
			ft.getFlexCellFormatter().setWidth(ind, 3, "100%");

			ind++;

		}

		ft.setWidget(ind, 2, new HTML("<div class='cell-text'> " + trans.totalOfProducts() + "</div>"));
		ft.setWidget(ind++, 3, new HTML(
				"<div class='cell-bold cell-number'> " + ClientUtil.getPrice(cart.itemsprice.getValue()) + "</div>"));

		ft.setWidget(ind, 2, new HTML("<div class='cell-text'> " + trans.shipmentPrice() + "</span>"));
		ft.setWidget(ind++, 3, new HTML(
				"<div class='cell-bold cell-number'> " + ClientUtil.getPrice(cart.shipprice.getValue()) + "</div>"));

		ft.setWidget(ind, 2, new HTML("<div class='cell-text'> " + trans.total() + "</span>"));
		ft.setWidget(ind++, 3, new HTML(
				"<div class='cell-bold cell-number'> " + ClientUtil.getPrice(cart.totalprice.getValue()) + "</div>"));

		return ft;
	}

	public Widget getProduct(JSONObject item, String stockUri) {
		final HorizontalPanel sp = new HorizontalPanel();
		sp.setSpacing(8);

		JSONObject jo = ClientUtil.getObject(item.get("options"));
		JSONObject joCanvases = ClientUtil.getObject(jo.get("canvas"));

		String stitle = ClientUtil.getString(jo.get("title"));

		VerticalPanel vp = new VerticalPanel();
		int count = 0;
		Set<String> canvases = joCanvases.keySet();
		for (String canvas : canvases) {
			
			JSONObject jo1 = ClientUtil.getObject(joCanvases.get(canvas));
			String rendered = ClientUtil.getString(jo1.get("rendered"));			
			JSONObject rgf = ClientUtil.getObject(jo1.get("rgf"));
			if (rgf == null || rgf.get("elms") == null || rgf.get("elms").isArray() == null || rgf.get("elms").isArray().size() <= 0)
				continue;

			if (count == 0) {
				SiteImageThumb first = new SiteImageThumb(rendered, canvas == "front" ? "Ön" : "Arka");
				first.setSize("90px", "100px");
				sp.add(first);
				count++;
			} else {
				SiteImageThumb second = new SiteImageThumb(rendered, canvas == "front" ? "Ön" : "Arka");
				vp.add(second);
			}
		}

		final String id = stockUri.substring(stockUri.lastIndexOf("/") + 1);

		HTML title = new HTML(stitle);
		title.setWidth("185px");
		title.setStyleName("site-header3");
		title.setTitle(id);

		vp.add(title);

		sp.add(vp);

		return sp;
	}

	public Widget getPlusMinus(final String stockUri, String amount) {
		Button plus = new Button("+");
		Button minus = new Button("-");

		plus.setStyleName("site-btn btn-sml");
		minus.setStyleName("site-btn btn-sml");

		plus.setSize("20px", "20px");
		minus.setSize("20px", "20px");

		plus.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				callback.changeStockAmount(stockUri, 1);
				// changeCartStockAmount(stockUri, 1);
			}
		});
		minus.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				callback.changeStockAmount(stockUri, -1);
				// changeCartStockAmount(stockUri, -1);
			}
		});

		HTML lbl = new HTML("<span style='padding: 0 4px;+'>" + amount + "</span>");
		lbl.getElement().getStyle().setDisplay(Display.INLINE);

		FlowPanel btns = new FlowPanel();
		btns.add(minus);
		btns.add(lbl);
		btns.add(plus);

		return btns;
	}

}
