package com.bilgidoku.rom.gwt.client.util.panels;

import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.araci.client.site.CartDao;
import com.bilgidoku.rom.gwt.araci.client.site.CartResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.gwt.client.util.ecommerce.PnlCartItems;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PnlOrderDetail extends Composite {
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	private final VerticalPanel rows = new VerticalPanel();
	private final HorizontalPanel holder = new HorizontalPanel();
	private FlowInterface payFlowUI = null;

	public PnlOrderDetail() {
		holder.add(rows);
		initWidget(new ScrollPanel(holder));

	}

	public void loadCartData(Cart value, final Runnable ok) {
		rows.clear();
		if (value == null)
			return;

		FlexTable hp = new FlexTable();
		hp.setStyleName("site-panel");
		hp.setWidth("700px");
		//shipment
		hp.setWidget(0, 0, ClientUtil.getTitle(trans.shipmentAddress(), "3"));
		if (payFlowUI != null) {
			hp.setWidget(0, 1, goBackToStep(0));
			hp.setWidget(1, 0, new ShowAddr(null, ClientUtil.getObject(value.shipaddr.getValue())));
			hp.getFlexCellFormatter().setColSpan(1, 0, 2);
			hp.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		} else {
			hp.setWidget(1, 0, new ShowAddr(null, ClientUtil.getObject(value.shipaddr.getValue())));
		}

		//invoice
		FlexTable hp1 = new FlexTable();
		hp1.setStyleName("site-panel");
		hp1.setWidth("700px");
		hp1.setWidget(0, 0, ClientUtil.getTitle(trans.invoiceAddress(), "3"));
		if (payFlowUI != null) {
			hp1.setWidget(0, 1, goBackToStep(0));
			hp1.setWidget(1, 0, new ShowAddr(null, ClientUtil.getObject(value.invoiceaddr.getValue())));
			hp1.getFlexCellFormatter().setColSpan(1, 0, 2);
			hp1.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		} else {
			hp1.setWidget(1, 0, new ShowAddr(null, ClientUtil.getObject(value.invoiceaddr.getValue())));
		}
		//payment
		FlexTable hp2 = new FlexTable();
		hp2.setStyleName("site-panel");
		hp2.setWidth("700px");
		hp2.setWidget(0, 0, ClientUtil.getTitle(trans.payment(), "3"));
		if (payFlowUI != null) {
			hp2.setWidget(0, 1, goBackToStep(1));
			hp2.setWidget(1, 0, new ShowPay(value, null));
			hp2.getFlexCellFormatter().setColSpan(1, 0, 2);
			hp2.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		} else {
			hp2.setWidget(1, 0, new ShowPay(value, null));
		}
		
		PnlCartItems items = new PnlCartItems(value, null);
		items.setStyleName("site-panel");
		items.setWidth("700px");

		rows.add(items);
		rows.add(hp);
		rows.add(hp1);
		rows.add(hp2);
		if (ok != null)
			ok.run();
		
	}
	
	public void loadCartData(final String uri, final Runnable ok) {
		CartDao.get(uri, new CartResponse() {
			@Override
			public void ready(Cart value) {
				loadCartData(value, ok);
			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				super.err(statusCode, statusText, exception);
			}

		});

	}
	
	private Widget goBackToStep(final int i) {
		SiteButton change = new SiteButton(trans.change(), trans.change());
		change.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				payFlowUI.gotoStep(i);
			}
		});
		return change;
	}


//	private class ShowCart extends Composite {
//		private final VerticalPanel holder = new VerticalPanel();
//		FlexTable ft = new FlexTable();
//
//		public ShowCart(Cart value) {
//			ft.setStyleName("site-panel");
//			ft.setWidth("700px");
//			holder.add(ft);
//			holder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
//
//			initWidget(holder);
//
//			loadData(value);
//
//		}
//
//		private void loadData(Cart value) {
//
//			JSONValue value2 = value.calcdetails.getValue();
//			if (value2 == null)
//				return;
//
//			JSONObject jo = value2.isObject();
//
//			Set<String> keys = jo.keySet();
//
//			ft.setCellSpacing(0);
//			ft.setCellPadding(0);
//
//			ft.setWidget(0, 0, new HTML("<div class='cell-header grey'>" + trans.product() + "</div>"));
//			ft.setWidget(0, 1, new HTML("<div class='cell-header grey'>" + trans.price() + "</div>"));
//			ft.setWidget(0, 2,
//					new HTML("<div class='cell-header grey' style='text-align:center;'>" + trans.number() + "</div>"));
//			ft.setWidget(0, 3, new HTML("<div class='cell-header grey'>" + trans.total() + "</div>"));
//
//			int ind = 1;
//
//			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
//
//				final String stockUri = (String) iterator.next();
//
//				JSONObject item = jo.get(stockUri).isObject();
//
//				String wtitle = ClientUtil.getString(item.get("wtitle"));
//				if (wtitle.length() > 50)
//					wtitle = wtitle.subSequence(0, 47) + "...";
//
//				// String wuri = ClientUtil.getString(item.get("wuri"));
//				String totalprice = ClientUtil.getPrice(item.get("price"));
//
//				final String amount = ClientUtil.getString(item.get("amount"));
//				String wprice = "";
//				try {
//					wprice = ClientUtil.getPrice(item.get("tariff").isObject().get("price"));
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//
//				ft.setWidget(ind, 0, getProduct(stockUri));
//				ft.setWidget(ind, 1, new HTML("<div class='cell cell-number'>" + wprice + "</div>"));
//				ft.setWidget(ind, 2, new HTML(amount));
//				ft.setWidget(ind, 3, new HTML("<div class='cell cell-number'>" + totalprice + "</div>"));
//
//				ft.getFlexCellFormatter().setVerticalAlignment(ind, 2, HasVerticalAlignment.ALIGN_MIDDLE);
//				ft.getFlexCellFormatter().setHorizontalAlignment(ind, 2, HasHorizontalAlignment.ALIGN_CENTER);
//				ft.getFlexCellFormatter().setWidth(ind, 2, "100%");
//				ft.getFlexCellFormatter().setVerticalAlignment(ind, 3, HasVerticalAlignment.ALIGN_MIDDLE);
//				ft.getFlexCellFormatter().setWidth(ind, 3, "100%");
//
//				ind++;
//
//			}
//
//
//			ft.setWidget(ind, 2, new HTML("<div class='cell-text'> " + trans.totalOfProducts() + "</div>"));
//			ft.setWidget(ind++, 3, new HTML("<div class='cell-bold cell-number'> "
//					+ ClientUtil.getPrice(value.itemsprice.getValue()) + "</div>"));
//
//			ft.setWidget(ind, 2, new HTML("<div class='cell-text'> " + trans.shipmentPrice() + "</span>"));
//			ft.setWidget(ind++, 3, new HTML("<div class='cell-bold cell-number'> "
//					+ ClientUtil.getPrice(value.shipprice.getValue()) + "</div>"));
//
//			ft.setWidget(ind, 2, new HTML("<div class='cell-text'> " + trans.total() + "</span>"));
//			ft.setWidget(ind++, 3, new HTML("<div class='cell-bold cell-number'> "
//					+ ClientUtil.getPrice(value.totalprice.getValue()) + "</div>"));
//		}
//
//		public Widget getProduct(String stockUri) {
//			final HorizontalPanel row = new HorizontalPanel();
//			row.setSpacing(8);
//			StocksDao.get(stockUri, new StocksResponse() {
//				@Override
//				public void ready(Stocks st) {
//					if (st.virtualsparent != null) {
//
//						VerticalPanel vpDesc = new VerticalPanel();
//						
//						HTML title = new HTML(st.title);
//						title.setWidth("144px");
//						title.setStyleName("site-header3");
//
//						
//						JSONObject jo = st.options.getValue().isObject();
//						JSONObject joFront = ClientUtil.getObject(jo.get("front"));
//						JSONObject joBack = ClientUtil.getObject(jo.get("back"));
//						
//						if (joFront != null && joBack != null) {
//							//there are two images
//							String furi = ClientUtil.getString(joFront.get("rendered"));
//							String buri = ClientUtil.getString(joBack.get("rendered"));
//
//							SiteImageThumb first = new SiteImageThumb(furi, st.title + " - Ön");
//							first.setSize("90px", "100px");
//							SiteImageThumb second = new SiteImageThumb(buri, st.title + " - Arka");
//							
//							row.add(first);
//							vpDesc.add(title);
//							vpDesc.add(second);
//							row.add(vpDesc);							
//						} else if (joFront != null) {
//							String furi = ClientUtil.getString(joFront.get("rendered"));
//							SiteImageThumb first = new SiteImageThumb(furi, st.title + " - Ön");
//							first.setSize("90px", "100px");
//							row.add(first);
//							vpDesc.add(title);
//							row.add(vpDesc);
//						} else if (joBack != null) {
//							
//							String buri = ClientUtil.getString(joFront.get("rendered"));
//							SiteImageThumb second = new SiteImageThumb(buri, st.title + " - Arka");
//							row.add(second);
//							vpDesc.add(title);
//							row.add(vpDesc);							
//						}
//
//
//					} else {
//						row.add(new HTML("<h2>" + st.title + "</h2>"));
//					}
//				}
//			});
//			return row;
//		}
//
//	}

	private class ShowPay extends Composite {

		public ShowPay(Cart cartData, Widget title) {
			String ps = cartData.paystyle;

			FlexTable holder = new FlexTable();
			holder.setWidth("100%");
			if (title != null)
				holder.setWidget(0, 0, title);
			holder.setHTML(1, 0, trans.paymentStyle());
			holder.setHTML(1, 1, ps != null ? ps.replace("__", " ") : "");
			holder.setHTML(2, 0, trans.total());
			holder.setHTML(2, 1, ClientUtil.getPrice(cartData.totalprice.getValue()));

			initWidget(holder);

		}

	}

	protected static interface CheckContractsTemplate extends SafeHtmlTemplates {
		@Template("<span><a href='{0}'>{1}</a> {5} <a href='{2}'>{3}</a> {4}</span>")
		SafeHtml text(String contractLink1, String contract, String contractLink2, String contract2, String body,
				String and);
	}

	public void savedMessage() {
		holder.clear();
		holder.add(new HTML(trans.orderSuccess() + " " + trans.orderNumber()));

	}

	public void isOnFlow(FlowInterface payFlowUI) {
		this.payFlowUI = payFlowUI;
	}

}
