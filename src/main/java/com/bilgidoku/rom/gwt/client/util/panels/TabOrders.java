package com.bilgidoku.rom.gwt.client.util.panels;

import java.util.Date;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.araci.client.site.CartDao;
import com.bilgidoku.rom.gwt.araci.client.site.CartResponse;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.NoSelectionModel;

public class TabOrders extends Composite {
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	private final CellTable<Cart> table = new CellTable<Cart>();
	private final NoSelectionModel<Cart> selectionModel = new NoSelectionModel<Cart>();

	private VerticalPanel holder = new VerticalPanel();
	private String type = "all";

	public TabOrders(String type1) {
		this.type = type1;
		table.setSelectionModel(selectionModel);
		addColumns();

		holder.setSpacing(5);
		holder.add(table);
		initWidget(holder);
		getData();
	}

	private void getData() {
		if (type.equals("one"))
			CartDao.list("/_/_cart", new CartResponse() {
				@Override
				public void array(List<Cart> myarr) {
					list(myarr);
				}
			});
		else
			CartDao.listmycarts("/_/_cart", new CartResponse() {
				@Override
				public void array(List<Cart> myarr) {
					list(myarr);
				}
			});

	}

	protected void list(List<Cart> myarr) {
		table.setRowCount(myarr.size(), true);
		table.setRowData(0, myarr);
		
	}

	private void addColumns() {
		TextColumn<Cart> txtId = new TextColumn<Cart>() {
			@Override
			public String getValue(Cart object) {
				return object.uri.substring(object.uri.lastIndexOf("/") + 1);
			}
		};

		TextColumn<Cart> txtAmount = new TextColumn<Cart>() {
			@Override
			public String getValue(Cart object) {
				return ClientUtil.getPrice(object.totalprice.getValue());
			}
		};

		TextColumn<Cart> txtStatus = new TextColumn<Cart>() {
			@Override
			public String getValue(Cart object) {
				return (object.confirmed) ? trans.opened() : trans.closed();
			}
		};

		DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
		DateCell dateCell = new DateCell(dateFormat);
		Column<Cart, Date> clmnOrderDate = new Column<Cart, Date>(dateCell) {
			@Override
			public Date getValue(Cart object) {
				DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
				Date date1 = dtf.parseStrict(object.creation_date);
				return date1;
			}
		};

		table.addColumn(txtId, trans.orderNumber());
		table.addColumn(clmnOrderDate, trans.orderDate());
		table.addColumn(txtAmount, trans.totalAmount());
		table.addColumn(txtStatus, "");
		

		if (type.equals("one")) {			
			ButtonCell detButton = new ButtonCell();
			Column<Cart, String> detailClmn = new Column<Cart, String>(detButton) {
				public String getValue(Cart object) {
						return "Sipariş Detayları";
				}
			};
			
			detailClmn.setFieldUpdater(new FieldUpdater<Cart, String>() {
				@Override
				public void update(int index, Cart object, String value) {
					new DlgOrderDetail(object);
				}
			});

			table.addColumn(detailClmn, "");
			table.setColumnWidth(detailClmn, 50, Unit.PX);
			
			ButtonCell confirmButton = new ButtonCell();
			Column<Cart, String> confColumn = new Column<Cart, String>(confirmButton) {
				public String getValue(Cart object) {
					if (!object.confirmed)
						return "Confirm";
					else
						return "Rmv Confirm";
				}
			};
			
			confColumn.setFieldUpdater(new FieldUpdater<Cart, String>() {
				@Override
				public void update(int index, Cart object, String value) {
					if (object.confirmed) {
						CartDao.setconfirmed(false, object.uri, new BooleanResponse());
					} else {
						CartDao.setconfirmed(true, object.uri, new BooleanResponse());
					}
				}
			});

			table.addColumn(confColumn, "");			
			table.setColumnWidth(confColumn, 50, Unit.PX);
			

		} else {
			table.addCellPreviewHandler(new Handler<Cart>() {
				@Override
				public void onCellPreview(CellPreviewEvent<Cart> event) {

					if (event.getNativeEvent().getType().contains("click")) {
						Cart selected = event.getValue();
						new DlgOrderDetail(selected);
					}
				}
			});
			
		}

	}

	private class DlgOrderDetail extends ActionBarDlg {
		PnlOrderDetail ord;

		public DlgOrderDetail(Cart selected) {
			super(selected.uri.substring(selected.uri.lastIndexOf("/") + 1) + " nolu sipariş", null, null);

			ord = new PnlOrderDetail();
			run();

			ord.loadCartData(selected.uri, new Runnable() {
				@Override
				public void run() {
					show();
					center();
				}
			});

		}

		@Override
		public Widget ui() {
			return ord;
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

}
