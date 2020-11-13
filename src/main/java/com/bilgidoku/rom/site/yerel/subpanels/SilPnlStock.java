package com.bilgidoku.rom.site.yerel.subpanels;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksDao;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksResponse;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteIntegerBox;
import com.bilgidoku.rom.site.yerel.common.SitePriceBox;
import com.bilgidoku.rom.site.yerel.pagedlgs.PageDlg;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SilPnlStock extends Composite {

	private final VerticalPanel pnlHolder = new VerticalPanel();

	private final Button btnStartStopStock = new Button(Ctrl.trans.newStock());
	private final CheckBox cbStartSale = new CheckBox(Ctrl.trans.onSale());
	private final Button btnSave = new Button(Ctrl.trans.save());
	// private PageDlg parent;
	private String writingUri;
	private final FlexTable form = new FlexTable();
	private final SitePriceBox txtPr = new SitePriceBox();
	private final SiteIntegerBox txtAmount = new SiteIntegerBox();
	private String stockUri;

	public SilPnlStock(PageDlg parent1) {
		// parent = parent1;
		ui();

		forNewDeleteStock();
		forStartSale();
		forSave();
		
		pnlHolder.setHeight("50px");
		pnlHolder.setStyleName("site-holder");
		initWidget(pnlHolder);
	}

	private void forSave() {
		btnSave.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				save();
			}
		});
	}

	private void forStartSale() {
		cbStartSale.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				StocksDao.setonsale(cbStartSale.getValue(), stockUri, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						if (cbStartSale.getValue())
							Window.alert(Ctrl.trans.startSelling());
						else
							Window.alert(Ctrl.trans.stopSelling());
					}
				});
			}
		});

	}

	public void load(String uri, String suri) {
		this.writingUri = uri;
		this.stockUri = suri;

		if (this.stockUri == null)
			return;

		btnStartStopStock.setText(Ctrl.trans.deleteStock());
		form.setVisible(true);
		cbStartSale.setVisible(true);
		
		StocksDao.get(stockUri, new StocksResponse() {
			@Override
			public void ready(Stocks value) {

				
				Json tarife = value.tariff;
				JSONObject jo = tarife.getValue().isObject();
				
				txtPr.setPrice(jo.get("price"));
				txtAmount.setValue(value.amount.toString());
				cbStartSale.setValue(value.onsale);
				
				
			}
		});

	}

	private void forNewDeleteStock() {
		btnStartStopStock.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (stockUri == null) {
					WritingsDao.setstock("", writingUri, new StringResponse() {
						@Override
						public void ready(String value) {
							stockUri = value;
							btnStartStopStock.setText(Ctrl.trans.deleteStock());
							form.setVisible(true);
							cbStartSale.setVisible(true);
							cbStartSale.setValue(true);
						}
					});
				} else {

					if (Window.confirm(Ctrl.trans.confirmDelete())) {
						WritingsDao.nostock(true, writingUri, new StringResponse() {
							@Override
							public void ready(String value) {
								stockUri = null;
								form.setVisible(false);
								cbStartSale.setVisible(false);
								cbStartSale.setValue(false);
								btnStartStopStock.setText(Ctrl.trans.newStock());
							}

						});
					}
				}
			}
		});

	}

	private void ui() {
		
		form.setStyleName("site-innerform");
		
		cbStartSale.setVisible(false);
		form.setVisible(false);
		
		form.setHTML(0, 0, Ctrl.trans.price());
		form.setWidget(0, 1, txtPr);

		form.setHTML(1, 0, Ctrl.trans.amount());
		form.setWidget(1, 1, txtAmount);

		form.setWidget(2, 0, btnSave);

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(cbStartSale);
		hp.add(btnStartStopStock);		
		
		
		pnlHolder.add(form);
		pnlHolder.add(hp);

	}

	protected void save() {
		if (this.stockUri == null)
			return;
		
		JSONObject jp = new JSONObject();
		jp.put("price", txtPr.getPrice());

		
		Json jo = new Json(jp);
		StocksDao.settariff(jo, this.stockUri, new BooleanResponse());
		StocksDao.setamount(txtAmount.getIntVal(), this.stockUri, new BooleanResponse());
		
		Window.alert(Ctrl.trans.ok());
	}

}
