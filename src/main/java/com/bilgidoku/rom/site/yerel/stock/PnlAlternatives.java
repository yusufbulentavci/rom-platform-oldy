package com.bilgidoku.rom.site.yerel.stock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;

public 	class PnlAlternatives extends Composite {
	private final ListBox lbStocks = new ListBox();
	private final SiteButton btnAdd = new SiteButton("/_local/images/common/add.png", "",
			Ctrl.trans.add(Ctrl.trans.stock()), "");
//	private final SiteButton btnGo = new SiteButton("/_local/images/common/push_talk.png", "",
//			Ctrl.trans.add(Ctrl.trans.tag()), "");

	private final SiteButton btnRmv = new SiteButton("/_local/images/common/bin.png", "",
			Ctrl.trans.removeSelected(), "");
	private final ListBox lbAdded = new ListBox();
	private final Button btnSave = new Button("Save All");
	private final List<Stocks> allStocks;
	private String uri;

	public PnlAlternatives(List<Stocks> stocks, String[] alts, String uri) {
		this.allStocks = stocks;
		this.uri = uri;
		buildLb(stocks);
		loadAlts(alts);
		forSelectFromList();
		forAdd();
		forRemove();
		forSave();
//		forOpen();

		lbAdded.setVisibleItemCount(3);
		lbStocks.setWidth("180px");
		lbAdded.setWidth("140px");
		btnRmv.setEnabled(false);

		FlexTable hp = new FlexTable();
		hp.setStyleName("site-panel");
		hp.setWidget(0, 0, lbStocks);
		hp.setWidget(0, 1, btnAdd);
//		hp.setWidget(0, 2, btnGo);
		hp.setWidget(0, 3, btnSave);
		
		hp.setWidget(1, 0, lbAdded);			
		hp.setWidget(1, 1, btnRmv);

		hp.getFlexCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		initWidget(hp);

	}
	
	public void loadAllStocks(List<Stocks> stocks) {
		
	}

	public void loadAlts(List<Stocks> stocks) {
		for (Iterator<Stocks> itr = stocks.iterator(); itr.hasNext();) {
			Stocks st = (Stocks) itr.next();
			lbAdded.addItem(st.title, st.uri);
		}

	}

	protected void buildLb(List<Stocks> stocks2) {
		List<Stocks> parents = new ArrayList<Stocks>();
		for (int i = 0; i < stocks2.size(); i++) {
			Stocks stocks = stocks2.get(i);
			if (stocks.firststock == null || stocks.firststock.isEmpty()) {
				parents.add(stocks);
			}
		}

		for (Iterator<Stocks> iterator = parents.iterator(); iterator.hasNext();) {
			final Stocks parent = (Stocks) iterator.next();
			lbStocks.addItem(parent.title, parent.uri);
			// find child items
			for (int i = 0; i < stocks2.size(); i++) {
				Stocks item = stocks2.get(i);
				if (item.firststock.equals(parent.uri)) {
					lbStocks.addItem("----" + item.title, item.uri);
				}
			}

		}

	}

	private void forSelectFromList() {
		lbAdded.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (lbAdded.getSelectedIndex() < 0)
					btnRmv.setEnabled(false);
				else
					btnRmv.setEnabled(true);
			}
		});
	}

	private void forRemove() {
		btnRmv.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (lbAdded.getSelectedIndex() < 0)
					return;
				lbAdded.removeItem(lbAdded.getSelectedIndex());
				btnRmv.setEnabled(false);
			}
		});
	}

	private void forAdd() {
		btnAdd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final String addVal = lbStocks.getSelectedValue();
				if (addVal == null || addVal.isEmpty())
					return;

				if (ClientUtil.findIndex(lbAdded, addVal) < 0) {
					lbAdded.addItem(lbStocks.getSelectedItemText(), lbStocks.getSelectedValue());
				}

			}
		});
	}
	
//	public void forOpen() {
//		btnGo.addClickHandler(new ClickHandler() {				
//			@Override
//			public void onClick(ClickEvent event) {
//				String uri = lbAdded.getSelectedValue();
//				
//				TabStock tw = new TabStock(uri);
//				Ctrl.openTab(uri, ClientUtil.getTitleFromUri(uri), tw, Data.CONTENT_COLOR);					
//			}
//		});
//		
//	}

	public void forSave() {
		btnSave.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (lbAdded.getItemCount() <= 0)
					return;

				ArrayList<String> al = new ArrayList<String>();
				for (int i = 0; i < lbAdded.getItemCount(); i++) {
					al.add(lbAdded.getValue(i));
				}

				String[] alts = al.toArray(new String[0]);
				StocksDao.setalternatives(alts, uri, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						Ctrl.setStatus("alts saved");
					}
				});

			}
		});
	}

	public void loadAlts(String[] alts) {
		if (alts == null)
			return;
		for (int i = 0; i < alts.length; i++) {
			lbAdded.addItem(getTitle(alts[i]), alts[i]);
		}
	}

	private String getTitle(String uri) {
		String found = "";
		for (int i = 0; i < allStocks.size(); i++) {
			Stocks sts = allStocks.get(i);
			if (sts.uri.equals(uri)) {
				if (sts.firststock == null || sts.firststock.isEmpty())
					found = sts.title;
				else
					found = "-----" + sts.title;
				break;
			}
		}
		return found;

	}
}
