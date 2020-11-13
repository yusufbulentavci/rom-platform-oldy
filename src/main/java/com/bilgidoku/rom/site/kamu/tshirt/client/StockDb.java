package com.bilgidoku.rom.site.kamu.tshirt.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksDao;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksResponse;

interface ProductReady {
	public void ready(Stock p);
}

public class StockDb {

	final Map<String, Stock> products = new HashMap<String, Stock>();

	private String stock;

	public void changeFirstStock(final String uri, final ProductReady p) {
		products.clear();
		stock = uri;
		getProduct(stock, null, p);
	}

	public Stock getStock(String uri) {
		return products.get(uri);
	}

	public void getProduct(final String uri, final Stocks sto, final ProductReady p) {
		if (products.containsKey(uri)) {
			p.ready(products.get(uri));
			return;
		}

		StocksResponse sr = new StocksResponse() {
			int wait = 0;
			Stock pr;
			boolean called = false;

			@Override
			public void ready(Stocks s) {
				pr = products.get(s.uri);
				if (pr == null) {
					pr = new Stock(s);
					products.put(s.uri, pr);
				}

				if (s.firststock != null) {
					wait++;
					getProduct(s.firststock, null, new ProductReady() {

						@Override
						public void ready(Stock p) {
							wait--;
							pr.setFirstStock(p);
							done();
						}
					});
				}

				if (s.virtualsparent != null) {
					wait++;
					getProduct(s.virtualsparent, null, new ProductReady() {

						@Override
						public void ready(Stock p) {
							wait--;
							pr.setVirtualsParent(p);
							done();
						}
					});
				}

				done();

			}

			public void done() {
				if (wait > 0 || called)
					return;

				p.ready(pr);
				called = true;
			}

		};

		if (sto == null) {
			StocksDao.get(uri, sr);
		} else {
			sr.ready(sto);
		}
	}

	public void listProductsWithSameFirstStock(final Stock sel, final Runnable done) {
		final String selstr = sel.getStock().firststock == null ? sel.getStock().uri : sel.getStock().firststock;
		StocksDao.listalternatives(selstr, "/_/_stocks", new StocksResponse() {
			int wait = 0;
			boolean called = false;

			@Override
			public void array(List<Stocks> myarr) {
				wait = myarr.size();
				if (wait == 0) {
					done();
					return;
				}
				for (int i = 0; i < myarr.size(); i++) {
					Stocks s = myarr.get(i);
					getProduct(s.uri, s, new ProductReady() {
						@Override
						public void ready(Stock p) {
							wait--;
							sel.addSibling(p);
							done();
						}
					});
				}
			}

			public void done() {
				if (wait == 0) {

					// first stock is also an alternative
					if (sel.getStock().firststock != null) {
						StocksDao.get(selstr, new StocksResponse() {
							@Override
							public void ready(Stocks value) {
								sel.addSibling(new Stock(value));
								call(done);
							}

						});
					} else {
						done.run();
					}
				}
			}

			private void call(final Runnable done) {
				if (called)
					return;
				called = true;
				done.run();
			}
		});
	}

	int waitAlts = 0;
	boolean calledAlts = false;

	public void listAlternatives(final Stock sel, final Runnable done) {
		calledAlts = false;
		String[] alts = sel.getStock().alternatives;
		if (alts != null && alts.length > 0) {
			waitAlts = alts.length;
			for (int i = 0; i < alts.length; i++) {
				String uri = alts[i];
				getProduct(uri, null, new ProductReady() {
					@Override
					public void ready(Stock p) {
						waitAlts--;
						sel.addAlternative(p);
						doneAlts(done);
					}

				});
			}
		}

		doneAlts(done);

	}

	private void doneAlts(Runnable done) {
		if (calledAlts)
			return;
		if (waitAlts == 0) {
			calledAlts = true;
			done.run();
		}
	}

}
