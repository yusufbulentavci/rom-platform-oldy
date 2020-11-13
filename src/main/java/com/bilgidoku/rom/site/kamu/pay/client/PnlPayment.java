package com.bilgidoku.rom.site.kamu.pay.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.site.CartDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.kamu.pay.client.constants.paytrans;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

public class PnlPayment extends Composite {

	public static paytrans trans = (paytrans) GWT.create(paytrans.class);

	private final HorizontalPanel rows = new HorizontalPanel();
	private SimplePanel optPnl = new SimplePanel();
	TransferOptionsPnl pnlOptTransfer = new TransferOptionsPnl();

	private String[] selectedBank = null;

	private JSONObject jo;

	public PnlPayment() {
		VerticalPanel vp = new VerticalPanel();
		vp.setStyleName("site-panel");
		vp.setWidth("700px");
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		vp.setSpacing(4);
		vp.add(rows);
		vp.add(optPnl);
		initWidget(new SimplePanel(vp));
	}

	public void loadData() {

		this.jo = PayFlow.orgPayPrefs;

		Set<String> keys = jo.keySet();

		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			final String key = (String) iterator.next();
			String title = key;
			if (key.equals("paypal"))
				title = trans.paypal();
			else if (key.equals("ondelivery")) {
				title = trans.onDelivery();
			} else if (key.equals("transfer")) {
				title = trans.transfer();
			}
			
			final RadioButton rb = new RadioButton("payment", title);
			rb.setTitle(key);

			SimplePanel sp = new SimplePanel();
			sp.setStyleName("pay_row");
			sp.add(rb);

			rows.add(sp);

			rb.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					String key = rb.getTitle();
					removeAllSelectedStyles();
					SimplePanel vp = (SimplePanel) rb.getParent();
					vp.addStyleName("selected");
					switchDetailPnl(key);
					// selectedPayStyle = key;
				}
			});

		}

		if (rows.getWidgetCount() > 0 && PayFlow.cart.paystyle != null) {
			for (int i = 0; i < rows.getWidgetCount(); i++) {
				SimplePanel sp = (SimplePanel) rows.getWidget(i);
				RadioButton cb = (RadioButton) sp.getWidget();
				if (cb.getTitle().equals(PayFlow.cart.paystyle)) {
					switchDetailPnl(PayFlow.cart.paystyle);
					cb.setValue(true);
					sp.addStyleName("selected");
					break;
				}
			}

		}

	}

	protected void switchDetailPnl(String key) {
		optPnl.clear();
		HTML desc = null;
		if (key.equals("transfer")) {
			pnlOptTransfer.loadData(jo.get(key).isObject());
			pnlOptTransfer.selectFirst();
			optPnl.add(pnlOptTransfer);
			return;
		} else if (key.equals("ondelivery")) {
			desc = new HTML(pay.trans.onDeliveryDesc());
		} else {
			desc = new HTML(pay.trans.paypalDesc());
		}

		SimplePanel sp = new SimplePanel();
		sp.setStyleName("site-panel");
		sp.add(desc);
		optPnl.add(sp);

	}

	protected void removeAllSelectedStyles() {
		for (int i = 0; i < rows.getWidgetCount(); i++) {
			SimplePanel sp = (SimplePanel) rows.getWidget(i);
			sp.removeStyleName("selected");
		}

	}

	private class TransferOptionsPnl extends Composite {

		private final CellList<String[]> listBanks = new CellList<String[]>(new CellTemplate());
		private final SingleSelectionModel<String[]> listSelModel = new SingleSelectionModel<String[]>();
		private List<String[]> data;

		public TransferOptionsPnl() {
			listBanks.setSelectionModel(listSelModel);
			initWidget(listBanks);
			listBanks.addStyleName("site-panel");
			setStyleName("site-panel");

			forBankSelectionChange();
		}

		private void forBankSelectionChange() {
			listSelModel.addSelectionChangeHandler(new Handler() {
				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					selectedBank = listSelModel.getSelectedObject();
				}
			});

		}

		public void selectFirst() {
			if (data.size() > 0) {
				listSelModel.setSelected(data.get(0), true);
			}

		}

		public void loadData(JSONObject to) {
			JSONArray ja = to.get("banks").isArray();

			data = new ArrayList<String[]>();

			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo = ja.get(i).isObject();
				String bank = ClientUtil.getString(jo.get("bank"));
				String branch = ClientUtil.getString(jo.get("branch"));
				String username = ClientUtil.getString(jo.get("username"));
				String iban = ClientUtil.getString(jo.get("iban"));
				String[] arr = { bank, branch, username, iban };
				data.add(arr);
			}

			listBanks.setRowCount(data.size(), true);
			listBanks.setRowData(0, data);

		}

	}

	// private class OnDelivOptionsPnl extends Composite {
	// public OnDelivOptionsPnl() {
	// HTML desc = new HTML(pay.trans.onDeliveryDesc());
	// initWidget(new SimplePanel(desc));
	// }
	// }

	private class CellTemplate extends AbstractCell<String[]> {
		@Override
		public void render(Context context, String[] row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			sb.appendHtmlConstant("<div style='display:inline-block;padding:10px;' class='bank-row'>"
					+ "<span style='width:110px;'>" + row[0] + "</span><span style='width:130px;'>" + row[1]
					+ "</span><span style='width:130px;'>" + row[2] + "</span><span style='width:155px;'>" + row[3]
					+ "</span></div>");
		}
	}

	public String getSelectedPayment() {
		String selected = null;
		for (int i = 0; i < rows.getWidgetCount(); i++) {
			SimplePanel sp = (SimplePanel) rows.getWidget(i);
			RadioButton rb = (RadioButton) sp.getWidget();
			if (rb.getValue()) {
				selected = rb.getTitle();
				break;
			}
		}
		return selected;

	}

	public boolean check() {
		if (getSelectedPayment() == null) {
			Window.alert(pay.trans.selectPayment());
			return false;
		}
		return true;
	}

	public void save() {
		String selectedPayStyle = getSelectedPayment();
		if (getSelectedPayment().equals("transfer")) {
			StringBuilder builder = new StringBuilder();
			for (String s : selectedBank) {
				builder.append(s + "-");
			}
			String bankAccount = builder.toString();
			selectedPayStyle = selectedPayStyle + "__" + bankAccount;

		}
		CartDao.setpaystyle(selectedPayStyle, PayFlow.cart.uri, new BooleanResponse());
	}
}
