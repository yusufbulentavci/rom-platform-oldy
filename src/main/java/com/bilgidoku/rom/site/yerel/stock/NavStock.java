package com.bilgidoku.rom.site.yerel.stock;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksDao;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksResponse;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.NavBase;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.google.gwt.user.client.ui.Widget;

public class NavStock extends NavBase implements HasContainer {

	public List<Stocks> lastStock;
	
	public NavStock() {
		//construct from one
		super("/_local/images/common/list_old.png", "/_local/images/common/folder.png", "/_local/images/common/folder_key.png", true, 1);
	}

	public NavStock(boolean noToolbar, boolean showItems) {
		//construct from dlg help
		super("/_local/images/common/list_old.png", "/_local/images/common/folder.png", "/_local/images/common/folder_key.png", false, 1);
		this.listItems = showItems;
	}

	@Override
	public void addContainers() {		
		getTree().removeItems();		
		
		getToolbar().buttonsStates(false);

		StocksDao.list("/_/_stocks", new StocksResponse() {
			@Override
			public void array(List<Stocks> value) {
				lastStock = value;
				buildItems();
			}
		});
	}

	public void buildItems() {
		if (lastStock == null || lastStock.size() <= 0)
			return;
		List<Stocks> parents = new ArrayList<Stocks>();
		for (int i = 0; i < lastStock.size(); i++) {
			Stocks stocks = lastStock.get(i);
			if (stocks.firststock == null || stocks.firststock.isEmpty()) {
				parents.add(stocks);
			}
		}
		
		for (Iterator<Stocks> iterator = parents.iterator(); iterator.hasNext();) {
			Stocks stocks = (Stocks) iterator.next();
			addContainer(null, 0L, stocks.uri, stocks.uri, true, true, stocks.title);			
		}
	}

	@Override
	public NavToolbarBase createToolbar() {
		NavToolbar tb = new NavToolbar(this);
		return tb;
	}

	@Override
	public void add(Widget w) {
		getToolbar().add(w);
		
	}

	@Override
	public void clear() {
		getToolbar().clear();		
	}

	@Override
	public Iterator<Widget> iterator() {
		return getToolbar().iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return getToolbar().remove(w);
	}


}
