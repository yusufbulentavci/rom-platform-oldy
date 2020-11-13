package com.bilgidoku.rom.site.kamu.pay.client;

import java.util.Iterator;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.araci.client.site.CartDao;
import com.bilgidoku.rom.gwt.araci.client.site.CartResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientData;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PnlShipment extends Composite {

	public static interface ShipTemplate extends SafeHtmlTemplates {
		@Template("<div style='display:inline-block;padding:20px 10px;'>{0}<div style='font-size:20px;color:orange;font-weight:bold;'>{1}</div></div>")
		SafeHtml header(String str, String price);
	}

	protected JSONObject shipAddrObj = null;
	protected JSONObject invAddrObj = null;

	AddrPnl pnlAdr = new AddrPnl();

	ShipWayPnl pnlShip = new ShipWayPnl();

	private String selectedShipStyle;
	private PayFlowUI payFlowUI;

	public PnlShipment(PayFlowUI payFlowUI) {

		this.payFlowUI = payFlowUI;
		VerticalPanel vp = new VerticalPanel();
		vp.add(pnlAdr);
		vp.add(pnlShip);
		initWidget(new SimplePanel(vp));

	}

	public void loadData() {
		pnlAdr.loadData();
		pnlShip.loadData();

	}

	private class ShipWayPnl extends Composite {

		private final VerticalPanel holder = new VerticalPanel();

		private final ShipTemplate TEMPLATE = GWT.create(ShipTemplate.class);

		public ShipWayPnl() {
			holder.setWidth("698px");
			holder.setStyleName("ship_pnl site-panel");
			holder.add(ClientUtil.getTitle(pay.trans.shipmentCompany(), "3"));
			initWidget(holder);
		}

		public void loadData() {
			Set<String> keys = PayFlow.orgShipPrefs.keySet();
			// int i = 0;
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {

				final String key = (String) iterator.next();
				final RadioButton rbKargo = new RadioButton("shipment",
						TEMPLATE.header(key, PayFlow.getShipPrice(PayFlow.orgShipPrefs.get(key).isObject())));
				rbKargo.setTitle(key);

				SimplePanel sp = new SimplePanel();
				sp.setStyleName("ship_row");
				sp.add(rbKargo);

				holder.add(sp);
				rbKargo.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						// removeAllSelectedStyles();
						// rbKargo.getParent().addStyleName("selected");
						selectedShipStyle = key;
						CartDao.setshipstyle(selectedShipStyle, PayFlow.cart.uri, new BooleanResponse() {
							@Override
							public void ready(Boolean value) {
								CartDao.activeget("/_/_cart", new CartResponse() {
									@Override
									public void ready(Cart value) {
										payFlowUI.cartChanged(value);
									}
								});
							}
						});
					}
				});
			}

			if (holder.getWidgetCount() > 1 && PayFlow.cart.shipstyle != null) {
				for (int i = 1; i < holder.getWidgetCount(); i++) {
					SimplePanel sp = (SimplePanel) holder.getWidget(i);
					RadioButton cb = (RadioButton) sp.getWidget();
					if (cb.getTitle().equals(PayFlow.cart.shipstyle)) {
						selectedShipStyle = cb.getTitle();
						cb.setValue(true);
						sp.addStyleName("selected");
						break;
					}
				}

			}

		}

		// protected void removeAllSelectedStyles() {
		// for (int i = 0; i < holder.getWidgetCount(); i++) {
		// SimplePanel sp = (SimplePanel) holder.getWidget(i);
		// sp.removeStyleName("selected");
		// }
		//
		// }
	}

	private class AddrForm extends Composite {
		private final TextArea txtAddress = new TextArea();
		private final ListBox listCountry = new ListBox();
		private final TextBox txtCity = new TextBox();
		private final TextBox txtFirstName = new TextBox();
		private final TextBox txtLastName = new TextBox();
		// private final TextBox txtEMail = new TextBox();
		// private final TextBox txtOrganization = new TextBox();
		private final TextBox txtState = new TextBox();
		private final TextBox txtPhone = new TextBox();
		private final TextBox txtMobile = new TextBox();
		private final TextBox txtPostalCode = new TextBox();

		private final Label lblAddress = new Label();
		private final Label lblCountry = new Label();
		private final Label lblCity = new Label();
		private final Label lblFirstName = new Label();
		private final Label lblLastName = new Label();
		// private final Label lblEMail = new Label();
		// private final Label lblOrganization = new Label();
		private final Label lblState = new Label();
		private final Label lblPhone = new Label();
		// private final Label lblMobile = new Label();
		// private final Label lblPostalCode = new Label();

		private boolean saveContactsToo = true;

		public AddrForm(final String type, String title, final AdrRow adrRow) {

			loadCountries();
			listCountry.setWidth("100%");

			txtAddress.setVisibleLines(3);
			txtAddress.setSize("240px", "100px");
			txtCity.setWidth("120px");
			txtPhone.setWidth("120px");
			txtMobile.setWidth("120px");
			txtPostalCode.setWidth("120px");
			listCountry.setWidth("120px");
			txtState.setWidth("120px");

			FlexTable ft = new FlexTable();

			ft.setHTML(0, 0, "*" + pay.trans.firstName());
			ft.setWidget(0, 1, txtFirstName);
			ft.setWidget(0, 2, lblFirstName);

			ft.setHTML(1, 0, "*" + pay.trans.lastName());
			ft.setWidget(1, 1, txtLastName);
			ft.setWidget(1, 2, lblLastName);

			ft.setHTML(2, 0, "*" + pay.trans.address());
			ft.setWidget(2, 1, txtAddress);
			ft.setWidget(2, 2, lblAddress);

			ft.setHTML(3, 0, "*" + pay.trans.state());
			ft.setWidget(3, 1, txtState);
			ft.setWidget(3, 2, lblState);

			ft.setHTML(4, 0, "*" + pay.trans.city());
			ft.setWidget(4, 1, txtCity);
			ft.setWidget(4, 2, lblCity);

			ft.setHTML(5, 0, pay.trans.postalCode());
			ft.setWidget(5, 1, txtPostalCode);

			ft.setHTML(6, 0, "*" + pay.trans.country());
			ft.setWidget(6, 1, listCountry);
			ft.setWidget(6, 2, lblCountry);

			ft.setHTML(7, 0, "*" + pay.trans.phone());
			ft.setWidget(7, 1, txtPhone);
			ft.setWidget(7, 2, lblPhone);

			ft.setHTML(8, 0, pay.trans.mobile());
			ft.setWidget(8, 1, txtMobile);

			ft.getFlexCellFormatter().setColSpan(5, 1, 2);
			ft.getFlexCellFormatter().setColSpan(8, 1, 2);

			SiteButton save = new SiteButton(pay.trans.save(), pay.trans.save());
			save.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (!check()) {
						return;
					}

					if (type.equals("shipment")) {
						shipAddrObj = getObject();
						CartDao.setshipaddr(new Json(shipAddrObj), PayFlow.cart.uri, new BooleanResponse() {
							public void ready(Boolean value) {
								adrRow.changed();
							}
						});
					} else if (type.equals("invoice")) {
						invAddrObj = getObject();
						CartDao.setinvoiceaddr(new Json(invAddrObj), PayFlow.cart.uri, new BooleanResponse() {
							public void ready(Boolean value) {
								adrRow.changed();
							}
						});
					}

					if (saveContactsToo && type.equals("shipment")) {
						final String cid = Cookies.getCookie("cid");
						ContactsDao.get(cid, new ContactsResponse() {
							public void ready(Contacts value) {
								ContactsDao.change(value.lang_id, value.cipher, txtFirstName.getValue(),
										txtLastName.getValue(), value.icon, value.email, value.fb_id, value.twitter,
										value.web, value.confirmed, txtAddress.getValue(), txtState.getValue(),
										txtCity.getValue(), listCountry.getValue(listCountry.getSelectedIndex()),
										txtPostalCode.getValue(), value.organization, txtPhone.getValue(),
										txtMobile.getValue(), value.fax, value.tags, value.gids, value.uri,
										new StringResponse());

							}

						});
					}

				}
			});

			VerticalPanel vp = new VerticalPanel();
			vp.add(ClientUtil.getTitle(title, "3"));
			vp.add(ft);
			vp.add(save);
			vp.setHeight("100%");

			initWidget(vp);
		}

		private JSONObject getObject() {
			JSONObject jo = new JSONObject();
			jo.put("address", new JSONString(txtAddress.getValue()));
			jo.put("state", new JSONString(txtState.getValue()));
			jo.put("city", new JSONString(txtCity.getValue()));
			jo.put("postalcode", new JSONString(txtPostalCode.getValue()));
			jo.put("country", new JSONString(listCountry.getValue(listCountry.getSelectedIndex())));
			jo.put("phone", new JSONString(txtPhone.getValue()));
			jo.put("mobile", new JSONString(txtMobile.getValue()));
			jo.put("firstname", new JSONString(txtFirstName.getValue()));
			jo.put("lastname", new JSONString(txtLastName.getValue()));
			return jo;
		}

		public void loadData(JSONObject value) {
			txtFirstName.setValue(ClientUtil.getString(value.get("firstname")));
			txtLastName.setValue(ClientUtil.getString(value.get("lastname")));
			txtAddress.setValue(ClientUtil.getString(value.get("address")));
			txtState.setValue(ClientUtil.getString(value.get("state")));
			txtCity.setValue(ClientUtil.getString(value.get("city")));
			txtPostalCode.setValue(ClientUtil.getString(value.get("postalcode")));

			txtPhone.setValue(ClientUtil.getString(value.get("phone")));
			txtMobile.setValue(ClientUtil.getString(value.get("mobile")));

			String counryCode = ClientUtil.getString(value.get("country"));
			ClientUtil.findAndSelect(listCountry, counryCode);

		}

		private void loadCountries() {
			for (int i = 0; i < ClientData.COUNTRYS.length; i++) {
				String[] arr = ClientData.COUNTRYS[i];
				listCountry.addItem(arr[0], arr[1]);
			}
		}

		private boolean check() {
			txtAddress.removeStyleName("warning");
			txtFirstName.removeStyleName("warning");
			txtLastName.removeStyleName("warning");
			txtCity.removeStyleName("warning");
			txtState.removeStyleName("warning");
			txtPhone.removeStyleName("warning");

			lblAddress.setText("");
			lblFirstName.setText("");
			lblLastName.setText("");
			lblCity.setText("");
			lblState.setText("");
			lblPhone.setText("");

			boolean ret = true;

			if (txtAddress.getValue().isEmpty()) {
				txtAddress.addStyleName("warning");
				lblAddress.setText(pay.trans.emptyAddress(""));
				ret = false;
			}

			if (txtFirstName.getValue().isEmpty()) {
				txtFirstName.addStyleName("warning");
				lblFirstName.setText(pay.trans.emptyFirstName());
				ret = false;
			}

			if (txtLastName.getValue().isEmpty()) {
				txtLastName.addStyleName("warning");
				lblLastName.setText(pay.trans.emptyLastName());
				ret = false;
			}

			if (txtCity.getValue().isEmpty()) {
				txtCity.addStyleName("warning");
				lblCity.setText(pay.trans.emptyCity());
				ret = false;
			}

			if (txtState.getValue().isEmpty()) {
				txtState.addStyleName("warning");
				lblState.setText(pay.trans.emptyState());
				ret = false;
			}

			if (txtPhone.getValue().isEmpty()) {
				txtPhone.addStyleName("warning");
				lblPhone.setText(pay.trans.emptyPhone());
				ret = false;

			}

			return ret;
		}

	}

	public void save() {
		CartDao.setshipstyle(selectedShipStyle, PayFlow.cart.uri, new BooleanResponse());
	}

	private class AddrPnl extends Composite {
		VerticalPanel holder = new VerticalPanel();

		public AddrPnl() {
			initWidget(holder);
		}

		public void loadData() {
			holder.clear();
			CartDao.get(PayFlow.cart.uri, new CartResponse() {
				@Override
				public void ready(Cart value) {
					try {
						shipAddrObj = value.shipaddr.getValue().isObject();
						invAddrObj = value.invoiceaddr.getValue().isObject();
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (value.shipaddr == null || value.shipaddr.getValue() == null) {
						// first time
						String cid = Cookies.getCookie("cid");
						ContactsDao.get(cid, new ContactsResponse() {
							@Override
							public void ready(final Contacts conta) {
								final JSONObject addr = getAddrfromContact(conta);
								CartDao.setshipaddr(new Json(addr), PayFlow.cart.uri, new BooleanResponse() {
									public void ready(Boolean value) {
										shipAddrObj = addr;
										holder.add(new AdrRow("shipment", pay.trans.shipmentAddress(), addr));
										holder.add(new AdrRow("invoice", pay.trans.invoiceAddress(), addr, null));
									};
								});
							}

							private JSONObject getAddrfromContact(Contacts conta) {
								JSONObject jo = new JSONObject();
								jo.put("address", new JSONString(conta.address != null ? conta.address : ""));
								jo.put("state", new JSONString(conta.state != null ? conta.state : ""));
								jo.put("city", new JSONString(conta.city != null ? conta.city : ""));
								jo.put("postalcode",
										new JSONString(conta.postal_code != null ? conta.postal_code : ""));
								jo.put("country", new JSONString(conta.country_code != null ? conta.country_code : ""));
								jo.put("phone", new JSONString(conta.phone != null ? conta.phone : ""));
								jo.put("mobile", new JSONString(conta.mobile != null ? conta.mobile : ""));
								jo.put("firstname", new JSONString(conta.first_name != null ? conta.first_name : ""));
								jo.put("lastname", new JSONString(conta.last_name != null ? conta.last_name : ""));
								return jo;
							}
						});

					} else {

						holder.add(new AdrRow("shipment", pay.trans.shipmentAddress(), shipAddrObj));
						holder.add(new AdrRow("invoice", pay.trans.invoiceAddress(), shipAddrObj, invAddrObj));
					}
				}
			});

		}

	}

	private class AdrRow extends Composite {

		private final FlowPanel holder = new FlowPanel();
		private final SiteButton btnChange = new SiteButton(pay.trans.change(), pay.trans.change());
		private final HorizontalPanel btns = new HorizontalPanel();
		private final SimplePanel addrHolder = new SimplePanel();
		/** Fatura adresi için  
		 */
		public AdrRow(final String type, final String title, final JSONObject shipAddr, final JSONObject invAddr) {			
			this(type, title, invAddr);
			
			final SiteButton btnSame = new SiteButton(pay.trans.sameWithShipment(), pay.trans.sameWithShipment());			
			btns.add(btnSame);
			
			btnSame.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					CartDao.setinvoiceaddr(new Json(shipAddr), PayFlow.cart.uri, new BooleanResponse() {
						public void ready(Boolean value) {
							 
							addrHolder.clear();
							addrHolder.add(new com.bilgidoku.rom.gwt.client.util.panels.ShowAddr(title, shipAddr));
							changed();
						}
					});
				}
			});
			
		}
		
		//gonderi adresi
		public AdrRow(final String type, final String title, final JSONObject jsonAddr) {			
			holder.setWidth("678px");
			holder.addStyleName("site-panel");
			
			btns.setStyleName("site-right");

			btnChange.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					holder.clear();
					AddrForm form = new AddrForm(type, title, AdrRow.this);
					if (jsonAddr != null)
						form.loadData(jsonAddr);
					// else if (co != null)
					// form.loadData(co);

					holder.add(form);
				}
			});

			btns.add(btnChange);

			addrHolder.add(new com.bilgidoku.rom.gwt.client.util.panels.ShowAddr(title, jsonAddr));
			
			holder.add(btns);
			holder.add(addrHolder);
			initWidget(holder);

		}

		public void changed() {
			pnlAdr.loadData();
		}

	}

	public boolean check() {
		if (!checkAddr(shipAddrObj)) {
			Window.alert("Teslimat adresi bilgileri doğru şekilde girilmelidir.");
			return false;
		}

		if (!checkAddr(invAddrObj)) {
			Window.alert("Fatura adresi bilgileri doğru şekilde girilmelidir.");
			return false;
		}

		if (selectedShipStyle == null) {
			Window.alert(pay.trans.selectCargoFirm());
			Window.scrollTo(0, 500);
			return false;
		}
		return true;
	}

	private boolean checkAddr(JSONObject addr) {
		if (addr == null)
			return false;
		if (ClientUtil.getString(addr.get("address")).isEmpty()) {
//			Window.alert("address empty");
			return false;
		}
		if (ClientUtil.getString(addr.get("state")).isEmpty()) {
//			Window.alert("state empty");
			return false;
		}

		if (ClientUtil.getString(addr.get("city")).isEmpty()) {
//			Window.alert("city empty");
			return false;
		}

		if (ClientUtil.getString(addr.get("country")).isEmpty()) {
//			Window.alert("country empty");
			return false;
		}

		if (ClientUtil.getString(addr.get("phone")).isEmpty()) {
//			Window.alert("phone empty");
			return false;
		}

		if (ClientUtil.getString(addr.get("firstname")).isEmpty()) {
//			Window.alert("firstname empty");
			return false;
		}

		if (ClientUtil.getString(addr.get("lastname")).isEmpty()) {
//			Window.alert("lastname empty");
			return false;
		}

		return true;
	}

	// public static String ClientUtil.getString(JSONValue jsonValue) {
	// if (jsonValue == null)
	// return "";
	// if (jsonValue.isString() == null) {
	// return "";
	// } else {
	// return jsonValue.isString().stringValue();
	// }
	// }

}
