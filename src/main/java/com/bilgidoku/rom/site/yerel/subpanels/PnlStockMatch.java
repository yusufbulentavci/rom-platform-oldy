package com.bilgidoku.rom.site.yerel.subpanels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksDao;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksResponse;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.pagedlgs.PageDlg;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PnlStockMatch extends Composite {

	private final ListBox lbFirstStocks = new ListBox();
	private final ListBox lbVirtualStocks = new ListBox();
	private final HTML divInfo = new HTML();
	private final VerticalPanel pnlHolder = new VerticalPanel();
	private final SiteButton btnAdd = new SiteButton(Ctrl.trans.match(), Ctrl.trans.stockStart()); 
	private final SiteButton btnDel = new SiteButton(Ctrl.trans.stopSelling(), Ctrl.trans.stopSelling());
	private String writingUri;

	public PnlStockMatch(PageDlg parent1) {
		StocksDao.list("/_/_stocks", new StocksResponse() {
			@Override
			public void array(final List<Stocks> stocks) {
				List<Stocks> parents = new ArrayList<Stocks>();
				for (int i = 0; i < stocks.size(); i++) {
					Stocks st = stocks.get(i);
					if (st.firststock == null || st.firststock.isEmpty()) {
						parents.add(st);
					}
				}

				for (Iterator<Stocks> iterator = parents.iterator(); iterator.hasNext();) {
					final Stocks parent = (Stocks) iterator.next();
					lbFirstStocks.addItem(parent.title, parent.uri);
					for (int i = 0; i < stocks.size(); i++) {
						Stocks item = stocks.get(i);
						if (item.firststock.equals(parent.uri)) {
							lbFirstStocks.addItem("-----" + item.title, item.uri);
						}
					}
				}
			}
		});
		
		btnAdd.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				if (lbFirstStocks.getSelectedIndex() < 0)
					return;
				
				//first look at virtual stocks 
				String stUri = lbVirtualStocks.getSelectedValue();				
				if (lbVirtualStocks.getSelectedIndex() < 0) {
					stUri = lbFirstStocks.getSelectedValue();
				}
				final String suri = stUri;
				WritingsDao.setstock(stUri, writingUri, new StringResponse() {
					@Override
					public void ready(String value) {
						divInfo.setHTML("<b>Eşlenen Stok:</b>" + suri);						
					}
				});				
			}
		});
		
		btnDel.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				//do not delete stock, empty field
				WritingsDao.nostock(false, writingUri, new StringResponse() {
					@Override
					public void ready(String value) {
						lbFirstStocks.setSelectedIndex(-1);
						divInfo.setHTML("<b>Eşlenen Stok Yok</b>");
					}
				});				
			}
		});
		
		lbFirstStocks.addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				lbVirtualStocks.clear();
				StocksDao.virtualslist(lbFirstStocks.getSelectedValue(), new StocksResponse() {
					@Override
					public void array(List<Stocks> myarr) {
						for (int i = 0; i < myarr.size(); i++) {							
							Stocks st = myarr.get(i);
							lbVirtualStocks.addItem(st.title, st.uri);						
							
						}
					}
				});
			}
		});
		
		HorizontalPanel fp = new HorizontalPanel();
		fp.add(btnAdd);
		fp.add(btnDel);
		
		lbFirstStocks.setVisibleItemCount(6);
		lbVirtualStocks.setVisibleItemCount(6);
		lbVirtualStocks.setWidth("190px");
		
		pnlHolder.setSpacing(3);
		pnlHolder.setStyleName("site-holder");
		
		pnlHolder.add(divInfo);
		pnlHolder.add(lbFirstStocks);
		pnlHolder.add(lbVirtualStocks);
		pnlHolder.add(fp);
		initWidget(pnlHolder);
		
	}

	public void selectStock(String stockUri, String writingUri) {
		this.writingUri = writingUri;
		divInfo.setHTML("<b>Eşlenen Stok:</b>" + stockUri);
//		ClientUtil.findAndSelect(lbVirtualStocks, stockUri);
//		ClientUtil.findAndSelect(lbFirstStocks, stockUri);
		
	}
}
