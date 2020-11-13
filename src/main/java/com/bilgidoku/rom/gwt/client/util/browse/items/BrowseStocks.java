package com.bilgidoku.rom.gwt.client.util.browse.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksDao;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksResponse;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BrowseStocks extends ActionBarDlg {

	VerticalPanel vp1 = new VerticalPanel();
	VerticalPanel list = new VerticalPanel();
	public String selected = null;
	private List<Stocks> realStocks = null;

	public BrowseStocks() {
		super("Stocks", null, "OK");

		run();

		addRealStocks();

	}

	public void addRealStocks() {
		StocksDao.list("/_/_stocks", new StocksResponse() {
			@Override
			public void array(List<Stocks> value) {
				
				realStocks = value;
				
				List<Stocks> parents = new ArrayList<Stocks>();
				// find parent stocks
				for (int i = 0; i < realStocks.size(); i++) {
					Stocks stocks = realStocks.get(i);
					if (stocks.firststock == null || stocks.firststock.isEmpty()) {
						parents.add(stocks);
					}
				}

				// iterate parents
				for (Iterator<Stocks> iterator = parents.iterator(); iterator.hasNext();) {

					final Stocks stocks = (Stocks) iterator.next();
					HTML line = new HTML(
							"<b style='line-height:30px;text-decoration:underline;font-size:14px;cursor:pointer;'>"
									+ stocks.title + "</b>");
					line.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							populateVirtuals(stocks.uri);

						}
					});
					vp1.add(line);

					// find childs
					for (int i = 0; i < realStocks.size(); i++) {
						final Stocks st = realStocks.get(i);
						if (st.firststock.equals(stocks.uri)) {
							HTML line1 = new HTML(
									"<b style='padding-left:30px;font-size:14px;cursor:pointer;'>" + st.title + "</b>");
							line1.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									populateVirtuals(st.uri);

								}
							});
							vp1.add(line1);

						}
					}

				}

			}
		});
	}

	private void populateVirtuals(final String uri) {
		list.clear();
		
		HTML h = getRowUi(findInRealStocks(uri));
		if (h != null)
			list.add(h);
		
		// öncelikle kendisini ekle
		StocksDao.virtualslist(uri, new StocksResponse() {
			@Override
			public void array(List<Stocks> myarr) {

				for (int i = 0; i < myarr.size(); i++) {
					
					final Stocks stock = myarr.get(i);
					
					list.add(getRowUi(stock));
				}
			}
		});

	}
	
	private Stocks findInRealStocks(String uri) {
		for (int i = 0; i < realStocks.size(); i++) {
			Stocks stocks = realStocks.get(i);
			if (stocks.uri.equals(uri))
				return stocks;
			
		}
		return null;
	}

	private HTML getRowUi(final Stocks stock) {
		
		final String id = stock.uri.substring(stock.uri.lastIndexOf("/") + 1);

		final HTML h = new HTML("<img src='" + stock.icon
				+ "' width='120px' style='vertical-align: middle;'>" + id + " " + stock.title);
		h.setTitle(stock.uri);
		h.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selected = stock.uri;
				clearSelectionStyle(list, "site-rect");
				h.addStyleName("site-rect");

			}

			private void clearSelectionStyle(VerticalPanel list, String string) {
				for (int j = 0; j < list.getWidgetCount(); j++) {
					Widget widget2 = list.getWidget(j);
					widget2.removeStyleName("site-rect");
				}

			}
		});
		
		return h;
		
	}

	@Override
	public Widget ui() {
		ScrollPanel sp = new ScrollPanel();
		sp.setSize("250px", "380px");
		sp.add(list);

		ScrollPanel sp1 = new ScrollPanel();
		sp1.setSize("250px", "380px");
		sp1.add(vp1);

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(vp1);
		hp.add(sp);

		return hp;
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ok() {
		// TODO Auto-generated method stub

	}

}
