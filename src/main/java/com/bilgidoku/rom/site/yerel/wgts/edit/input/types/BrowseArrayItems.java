package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksDao;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Writings;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsResponse;
import com.bilgidoku.rom.gwt.client.util.browse.items.BrowseStocks;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;

public class BrowseArrayItems extends ActionBarDlg {

	private class MinimumItem {
		private String uri;
		private String title;
		private String icon;

		public MinimumItem(String uri, String title, String icon) {
			this.uri = uri;
			this.title = title;
			this.icon = icon;
		}
	}

	private final ItemCell itemCell = new ItemCell();
	private final CellList<MinimumItem> itemList = new CellList<MinimumItem>(itemCell);
	private final SingleSelectionModel<MinimumItem> itemListSelModel = new SingleSelectionModel<MinimumItem>();

	private final SiteButton btnAdd = new SiteButton("/_local/images/common/add.png", Ctrl.trans.page(),
			Ctrl.trans.add(Ctrl.trans.page()), "");

	private final SiteButton btnAddStock = new SiteButton("/_local/images/common/add.png", Ctrl.trans.stock(),
			Ctrl.trans.add(Ctrl.trans.page()), "");

	private final SiteButton btnRmv = new SiteButton("/_local/images/common/bin.png", "", Ctrl.trans.removeSelected(),
			"");
	private SiteButton btnUp = new SiteButton("/_public/images/bar/arrow_top.png", "", Ctrl.trans.moveUp(), "");
	private SiteButton btnDown = new SiteButton("/_public/images/bar/arrow_bottom.png", "", Ctrl.trans.moveDown(), "");

	private List<MinimumItem> data = new ArrayList<MinimumItem>();

	final Button btnClose = new Button("Close");
	final Button btnOK = new Button("OK");
	public String returningArray = null;

	public BrowseArrayItems() {
		super(Ctrl.trans.addItem(), null, Ctrl.trans.ok());

		itemList.setSelectionModel(itemListSelModel);

		forAddItem();
		forAddStock();
		forRemoveItem();
		forUp();
		forDown();

		btnRmv.setVisible(false);
		run();
	}

	private void forAddStock() {
		btnAddStock.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final BrowseStocks bi = new BrowseStocks();
				bi.show();
				bi.center();
				bi.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {

						if (bi.selected == null || bi.selected.isEmpty())
							return;

						StocksDao.get(bi.selected, new StocksResponse() {
							@Override
							public void ready(Stocks value) {
								data.add(new MinimumItem(value.uri, value.title, value.icon));
								populate();
							}
						});

					}
				});

			}
		});

	}

	private void populate() {
		btnRmv.setVisible(data.size() > 0 ? true : false);

		itemList.setRowCount(data.size(), true);
		itemList.setRowData(0, data);
	}

	private void forRemoveItem() {
		btnRmv.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (itemListSelModel.getSelectedObject() == null) {
					return;
				}
				if (Window.confirm(Ctrl.trans.confirmDelete())) {
					data.remove(itemListSelModel.getSelectedObject());
					populate();
				}
			}
		});
	}

	private void forDown() {
		btnDown.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (itemListSelModel.getSelectedObject() == null) {
					return;
				}
				MinimumItem selObj = itemListSelModel.getSelectedObject();
				int ind = data.indexOf(selObj);
				if ((ind + 1) > 0 && (data.size() -1 >= ind )) {
					MinimumItem toMove = data.get(ind + 1);
					data.set(ind + 1, data.get(ind));
					data.set(ind, toMove);
				}
				populate();
			}
		});
	}

	private void forUp() {
		btnUp.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (itemListSelModel.getSelectedObject() == null) {
					Window.alert(Ctrl.trans.selectAnItem());
					return;
				}

				MinimumItem selObj = itemListSelModel.getSelectedObject();
				int i = data.indexOf(selObj);
				if (i > 0) {
					MinimumItem toMove = data.get(i);
					data.set(i, data.get(i - 1));
					data.set(i - 1, toMove);
				}
				populate();

			}
		});
	}

	private void forAddItem() {
		btnAdd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final BrowsePages bi = new BrowsePages();
				bi.show();
				bi.center();
				bi.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {

						if (bi.selected == null || bi.selected.isEmpty())
							return;

						WritingsDao.get(Ctrl.infoLang(), bi.selected, new WritingsResponse() {
							@Override
							public void ready(Writings value) {
								data.add(new MinimumItem(value.uri, value.title[0], value.medium_icon));
								populate();
							}
						});
					}
				});

			}
		});

	}

	private class ItemCell extends AbstractCell<MinimumItem> {
		@Override
		public void render(Context context, MinimumItem row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			sb.appendHtmlConstant("<div style='width: 151px;position:relative;' class='site-item'>"
					+ "<img style='width:40px; height:40px;float:left;margin-right:5px;' src='" + row.icon + "'></img>"
					+ "<div style='height:40px;line-height:40px;text-align:left;'>" + row.title.substring(0, 20)
					+ "</div></div>");

		}
	}

	private int counter = 0;

	public void loadData(final JSONArray myarr) {
		if (myarr == null)
			return;

		for (int i = 0; i < myarr.size(); i++) {
			String val;
			try {
				val = myarr.get(i).isString().stringValue();
				if (val.startsWith(Data.STOCK_ROOT)) {
					StocksDao.get(val, new StocksResponse() {
						@Override
						public void ready(Stocks value) {
							data.add(new MinimumItem(value.uri, value.title, value.icon));
							counter++;
							if (counter == myarr.size()) {
								populate();
							}
						}

						@Override
						public void err(int statusCode, String statusText, Throwable exception) {
							// if deleted
							counter++;
							if (counter == myarr.size()) {
								populate();
							}
						}
					});

				} else
					WritingsDao.get(Ctrl.infoLang(), val, new WritingsResponse() {
						@Override
						public void ready(Writings value) {
							data.add(new MinimumItem(value.uri, value.title[0], value.medium_icon));
							counter++;
							if (counter == myarr.size()) {
								populate();
							}
						}

						@Override
						public void err(int statusCode, String statusText, Throwable exception) {
							// if deleted
							counter++;
							if (counter == myarr.size()) {
								populate();
							}
						}
					});

			} catch (RunException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public Widget ui() {
		Widget[] btns = { btnAdd, btnAddStock, btnRmv, btnUp, btnDown };
		VerticalPanel vp = new VerticalPanel();
		vp.setSize("160px", "320px");
		vp.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		vp.add(ClientUtil.getToolbar(btns, 2));
		vp.add(new ScrollPanel(itemList));

		return vp;
	}

	@Override
	public void cancel() {
	}

	@Override
	public void ok() {
		StringBuffer uris = new StringBuffer();

		for (int i = 0; i < data.size(); i++) {
			MinimumItem wr = data.get(i);
			uris.append('"' + wr.uri + '"');
			uris.append(",");
		}

		String str = uris.toString();
		returningArray = str.substring(0, str.length() - 1);

	}

}
