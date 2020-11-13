package com.bilgidoku.rom.site.yerel.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.JsonResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.OrgDao;
import com.bilgidoku.rom.gwt.araci.client.rom.Tariffmodel;
import com.bilgidoku.rom.gwt.araci.client.rom.TariffmodelDao;
import com.bilgidoku.rom.gwt.araci.client.rom.TariffmodelResponse;
import com.bilgidoku.rom.gwt.araci.client.site.InfoDao;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.InitialsDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteIntegerBox;
import com.bilgidoku.rom.site.yerel.common.SiteLangList;
import com.bilgidoku.rom.site.yerel.common.SitePriceBox;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabOrg extends Composite {

	private ContractsPanel contPnl = new ContractsPanel();
	private PayPnl payPnl = new PayPnl();

	private final ShipForm shipPnl = new ShipForm();
	private final SiteLangList listLangs = new SiteLangList();
	private final TarifePnl tarifPnl = new TarifePnl();
	private final PnlCertificate cerPnl = new PnlCertificate();

	private final HTML eCommLbl = new HTML();
	private final HTML authLbl = new HTML();
	private final Button btnEComm = new Button();
	private final Button btnAuth = new Button();
	private boolean eCommStatus = false;
	private boolean authStatus = false;

	private final StackPanel stack = new StackPanel();
	private final TextBox fbId = new TextBox();
	private final Button btnFbId = new Button(Ctrl.trans.change());

	private final TextBox verifCode = new TextBox();

	public TabOrg() {
		tarifPnl.checkContainer();
		listLangs.loadContentLangs(Ctrl.info().langcodes, Ctrl.info().langcodes[0]);

		this.eCommStatus = Ctrl.info().ecommerce;
		this.authStatus = Ctrl.info().login;

		stack.setVisible(Ctrl.info().ecommerce);
		setECommLabels(eCommStatus);
		setAuthLabels(authStatus);

		forLangChange();
		forEComm();
		forAuth();

		stack.add(contPnl, "Contracts");
		stack.add(payPnl, Ctrl.trans.payment());
		stack.add(shipPnl, Ctrl.trans.shipment());
		stack.add(tarifPnl, "Tarife Modelleri");
//		stack.add(cerPnl, Ctrl.trans.certificates());

		eCommLbl.setStyleName("site-label");
		authLbl.setStyleName("site-label");

		FlowPanel fp = new FlowPanel();
		fp.add(fbId);
		fp.add(btnFbId);
		btnFbId.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				OrgDao.setfbappid(fbId.getValue(), "/_/_org", new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
					}
				});
			}
		});

		FlexTable holder = new FlexTable();

		holder.setHTML(0, 0, Ctrl.trans.websiteLangs());
		holder.setWidget(0, 1, listLangs);

		holder.setHTML(1, 0, Ctrl.trans.facebookId());
		holder.setWidget(1, 1, fp);

		holder.setWidget(2, 0, authLbl);
		holder.setWidget(2, 1, btnAuth);
		
		holder.setWidget(3, 0, cerPnl.forceLbl);
		holder.setWidget(3, 1, cerPnl.btnForceHttp);
		
		holder.setHTML(4, 0, Ctrl.trans.certificates());
		holder.setWidget(4, 1, cerPnl);

		holder.setWidget(5, 0, eCommLbl);
		holder.setWidget(5, 1, btnEComm);

		holder.setWidget(6, 0, stack);

		holder.getFlexCellFormatter().setColSpan(6, 0, 2);

		initWidget(new ScrollPanel(holder));

		OrgDao.getpaystyle("/_/_org", new JsonResponse() {
			@Override
			public void ready(Json value) {
				JSONValue val = value.getValue();
				if (val == null || val.isObject() == null)
					return;

				JSONObject jo = val.isObject();
				payPnl.loadPayData(jo);
			}
		});

		OrgDao.getshipstyle("/_/_org", new JsonResponse() {
			@Override
			public void ready(Json value) {
				JSONValue val = value.getValue();
				if (val == null || val.isObject() == null)
					return;

				JSONObject jo = val.isObject();
				shipPnl.loadData(jo);
			}
		});

		InitialsDao.getfbappid("/_/_initials", new StringResponse() {
			@Override
			public void ready(String value) {
				fbId.setValue(value);
			}
		});

		OrgDao.getstartssl("/_/_org", new StringResponse() {
			@Override
			public void ready(String value) {
				verifCode.setValue(value);
			}
		});
	}

	private void forAuth() {
		btnAuth.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				InfoDao.setlogin(!authStatus, "/_/siteinfo", new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.setStatus(Ctrl.trans.saved());
						authStatus = !authStatus;
						setAuthLabels(authStatus);
						Ctrl.info().login = authStatus;
						// viewDlg.authChanged();
					}
				});
			}
		});
	}

	private void forEComm() {
		btnEComm.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!eCommStatus && !authStatus) {
					Window.alert(Ctrl.trans.warningActiveLoginFirst());
					return;
				}
				InfoDao.setecommerce(!eCommStatus, "/_/siteinfo", new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.setStatus(Ctrl.trans.saved());
						eCommStatus = !eCommStatus;
						setECommLabels(eCommStatus);
						Ctrl.info().ecommerce = eCommStatus;
						stack.setVisible(eCommStatus);
						Ctrl.setStatus("Sözleşmeler kısmında bir kereye mahsus Kaydet (Save) demeyi unutmayın!");
					}
				});
			}
		});
	}

	private void forLangChange() {
		listLangs.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				final String lang = listLangs.getSelectedLang();
				String[] siteLangs = Ctrl.info().langcodes;
				if (!ClientUtil.existInArray(siteLangs, lang)) {
					InfoDao.newlang(lang, "/_/siteinfo", new StringResponse() {
						@Override
						public void ready(String value) {
							List<String> temp = new ArrayList<String>(Arrays.asList(Ctrl.info().langcodes));
							temp.add(lang);

							String[] arr = new String[temp.size()];
							temp.toArray(arr);

							Ctrl.info().langcodes = arr;

							listLangs.loadContentLangs(Ctrl.info().langcodes, Ctrl.info().langcodes[0]);

							Ctrl.setStatus(Ctrl.trans.websiteLangAdded());
							Window.alert(Ctrl.trans.websiteLangAdded());
						}
					});
				}
			}
		});
	}

	private void setECommLabels(boolean eComm) {
		if (eComm) {
			eCommLbl.setHTML(Ctrl.trans.active(Ctrl.trans.eCommerce()));
			btnEComm.setText(Ctrl.trans.deActivate(Ctrl.trans.eCommerce()));
			btnEComm.setTitle(Ctrl.trans.deActivate(Ctrl.trans.eCommerce()));
		} else {
			eCommLbl.setHTML(Ctrl.trans.passive(Ctrl.trans.eCommerce()));
			btnEComm.setText(Ctrl.trans.activate(Ctrl.trans.eCommerce()));
			btnEComm.setTitle(Ctrl.trans.activate(Ctrl.trans.eCommerce()));
		}

	}

	private void setAuthLabels(boolean auth) {
		if (auth) {
			authLbl.setHTML(Ctrl.trans.active(Ctrl.trans.auth()));
			btnAuth.setTitle(Ctrl.trans.deActivate(Ctrl.trans.auth()));
			btnAuth.setText(Ctrl.trans.deActivate(Ctrl.trans.auth()));
		} else {
			authLbl.setHTML(Ctrl.trans.passive(Ctrl.trans.auth()));
			btnAuth.setTitle(Ctrl.trans.activate(Ctrl.trans.auth()));
			btnAuth.setText(Ctrl.trans.activate(Ctrl.trans.auth()));
		}

	}

	private class TarifePnl extends Composite {
		Button btnNew = new Button("Yeni Tarife Modeli");
		Button btnDel = new Button("Seçileni Sil");

		Button btnBase = new Button(Ctrl.trans.change());
		Button btnVat = new Button(Ctrl.trans.change());
		Button btnCoeff = new Button(Ctrl.trans.change());

		private ListBox lbTarifs = new ListBox();
		private TextBox txtTitle = new TextBox();
		private SitePriceBox txtBase = new SitePriceBox();
		private TextBox txtVat = new TextBox();
		private TextBox txtCoeff = new TextBox();
		protected String selTfUri = null;
		final FlexTable form = new FlexTable();

		public TarifePnl() {

			lbTarifs.setVisibleItemCount(10);
			lbTarifs.setSize("150px", "90px");
			loadList();

			actions();

			form.setStyleName("site-holder site-innerform site-padding");
			form.setHTML(0, 0, "Title");
			form.setWidget(0, 1, txtTitle);

			form.setHTML(1, 0, "Base Price");
			form.setWidget(1, 1, txtBase);
			form.setWidget(1, 2, btnBase);

			form.setHTML(2, 0, "Vat %");
			form.setWidget(2, 1, txtVat);
			form.setWidget(2, 2, btnVat);

			form.setHTML(3, 0, "Coefficient");
			form.setWidget(3, 1, txtCoeff);
			form.setWidget(3, 2, btnCoeff);

			form.setVisible(false);

			VerticalPanel pnlList = new VerticalPanel();
			pnlList.addStyleName("site-padding");
			pnlList.addStyleName("site-innerform");

			Widget[] listButtons1 = { btnNew, btnDel };

			pnlList.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
			pnlList.add(ClientUtil.getToolbar(listButtons1, 3));
			pnlList.add(lbTarifs);

			HorizontalPanel hp = new HorizontalPanel();
			hp.setWidth("600px");
			hp.add(pnlList);
			hp.add(form);
			initWidget(hp);

		}

		public void checkContainer() {
		}

		private void loadList() {
			TariffmodelDao.list(Data.TARIFFMODEL_ROOT, new TariffmodelResponse() {
				@Override
				public void array(List<Tariffmodel> myarr) {
					if (myarr == null || myarr.size() <= 0)
						return;

					for (Iterator<Tariffmodel> iterator = myarr.iterator(); iterator.hasNext();) {
						Tariffmodel tf = (Tariffmodel) iterator.next();
						lbTarifs.addItem(tf.title, tf.uri);
					}
				}
			});

		}

		protected void loadData(final String selUri) {
			txtTitle.setValue("");
			txtBase.setSPrice(null);
			txtVat.setValue("");
			txtCoeff.setValue("");

			TariffmodelDao.list(Data.TARIFFMODEL_ROOT, new TariffmodelResponse() {
				@Override
				public void array(List<Tariffmodel> myarr) {
					if (myarr == null || myarr.size() <= 0)
						return;

					for (Iterator<Tariffmodel> iterator = myarr.iterator(); iterator.hasNext();) {
						Tariffmodel tf = (Tariffmodel) iterator.next();
						if (tf.uri.equals(selUri)) {
							txtTitle.setValue(tf.title);
							txtBase.setSPrice(tf.baseprice);
							txtCoeff.setValue(tf.coefficient);
							txtVat.setValue(tf.vatpercentage.intValue() + "");
							break;
						}
					}
				}
			});

		}

		private void actions() {
			lbTarifs.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					form.setVisible(true);
					selTfUri = lbTarifs.getSelectedValue();
					loadData(selTfUri);
				}
			});

			btnNew.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final String ttl = Window.prompt("Tarife Model Adı Giriniz", "");
					if (ttl == null || ttl.isEmpty())
						return;
					TariffmodelDao.neww(ttl, Data.TARIFFMODEL_ROOT, new StringResponse() {
						@Override
						public void ready(String value) {
							form.setVisible(true);
							txtTitle.setValue(ttl);
							selTfUri = value;
							lbTarifs.addItem(ttl, value);
							lbTarifs.setItemSelected(lbTarifs.getItemCount() - 1, true);
							TariffmodelDao.setcode("coef", value, new StringResponse() {
								@Override
								public void ready(String value) {

								}
							});

						}
					});
				}
			});

			btnDel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					TariffmodelDao.destroy(selTfUri, new StringResponse() {
						@Override
						public void ready(String value) {
							form.setVisible(false);
							lbTarifs.removeItem(lbTarifs.getSelectedIndex());
						}
					});
				}
			});

			btnBase.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					// TODO after bulo
					// TariffmodelDao.setbaseprice(, selTfUri, new
					// StringResponse() {
					// @Override
					// public void ready(String value) {
					// Ctrl.setStatus("ok");
					// }
					// });
				}
			});

			btnCoeff.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					TariffmodelDao.setcoefficient(txtCoeff.getValue(), selTfUri, new StringResponse() {
						@Override
						public void ready(String value) {
							Ctrl.setStatus("ok");
						}
					});
				}
			});

			btnVat.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					String val = txtVat.getValue();
					int i = Integer.parseInt(val);
					TariffmodelDao.setvatpercentage(i, selTfUri, new StringResponse() {
						@Override
						public void ready(String value) {
							Ctrl.setStatus("ok");
						}
					});

				}
			});
		}
	}

	private class PayPnl extends Composite {
		final PayPalForm payPalForm = new PayPalForm();
		final TransferForm transferForm = new TransferForm();
		final OnDeliveryForm onDelivForm = new OnDeliveryForm();

		final CheckBox cbPayPal = new CheckBox("pay pal");
		final CheckBox cbTransfer = new CheckBox("transfer");
		final CheckBox cbOnDelivery = new CheckBox("on delivery");

		public PayPnl() {

			payPalForm.setVisible(false);
			transferForm.setVisible(false);
			onDelivForm.setVisible(false);

			cbPayPal.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (cbPayPal.getValue()) {
						payPalForm.setVisible(true);
					} else {
						payPalForm.setVisible(false);
					}
				}
			});

			cbTransfer.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (cbTransfer.getValue()) {
						transferForm.setVisible(true);
					} else {
						transferForm.setVisible(false);
					}
				}
			});

			cbOnDelivery.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (cbOnDelivery.getValue()) {
						onDelivForm.setVisible(true);
					} else {
						onDelivForm.setVisible(false);
					}
				}
			});

			SiteButton save = new SiteButton("/_local/images/common/disk.png", Ctrl.trans.saveAll(), "", "");
			save.setSize("200px", "40px");
			save.removeStyleName("gwt-Button");
			// save.addStyleName("site-btnyellow");

			VerticalPanel vp = new VerticalPanel();
			vp.setSpacing(5);

			vp.add(cbPayPal);
			vp.add(payPalForm);

			vp.add(cbTransfer);
			vp.add(transferForm);

			vp.add(cbOnDelivery);
			vp.add(onDelivForm);

			vp.add(save);

			initWidget(vp);

			save.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					OrgDao.setpaystyle(new Json(getObject()), "/_/_org", new BooleanResponse() {
						@Override
						public void ready(Boolean value) {
							Ctrl.setStatus(Ctrl.trans.saved());
						}
					});

				}
			});

		}

		public void loadPayData(JSONObject jo) {
			if (jo.get("paypal") != null && jo.get("paypal").isObject() != null) {
				JSONObject opp = jo.get("paypal").isObject();
				payPalForm.loadData(opp);
				if (ClientUtil.getBoolean(opp.get("active"))) {
					payPalForm.setVisible(true);
					cbPayPal.setValue(true);
				}
			}

			if (jo.get("transfer") != null && jo.get("transfer").isObject() != null) {
				JSONObject tpp = jo.get("transfer").isObject();
				transferForm.loadData(tpp);
				if (ClientUtil.getBoolean(tpp.get("active"))) {
					transferForm.setVisible(true);
					cbTransfer.setValue(true);
				}
			}

			if (jo.get("ondelivery") != null && jo.get("ondelivery").isObject() != null) {
				onDelivForm.loadData(jo.get("ondelivery").isObject());
				JSONObject dpp = jo.get("ondelivery").isObject();
				onDelivForm.loadData(dpp);
				if (ClientUtil.getBoolean(dpp.get("active"))) {
					onDelivForm.setVisible(true);
					cbOnDelivery.setValue(true);
				}

			}

		}

		public JSONObject getObject() {
			JSONObject jo = new JSONObject();
			if (cbPayPal.getValue()) {
				jo.put("paypal", payPalForm.getObject(cbPayPal.getValue()));
			}
			if (cbTransfer.getValue()) {
				jo.put("transfer", transferForm.getObject(cbTransfer.getValue()));
			}
			if (cbOnDelivery.getValue()) {
				jo.put("ondelivery", onDelivForm.getObject(cbOnDelivery.getValue()));
			}
			return jo;

		}
	}

	private class PayPalForm extends Composite {
		TextBox certId = new TextBox();
		TextBox payPalId = new TextBox();
		SiteIntegerBox chargePerc = new SiteIntegerBox();
		SitePriceBox chargePrice = new SitePriceBox();

		public PayPalForm() {
			FlexTable ft = new FlexTable();
			ft.setHTML(0, 0, "cert Id");
			ft.setWidget(0, 1, certId);

			ft.setHTML(1, 0, "paypal Id");
			ft.setWidget(1, 1, payPalId);

			ft.setHTML(2, 0, "charge perc %");
			ft.setWidget(2, 1, chargePerc);

			ft.setHTML(3, 0, "charge Price");
			ft.setWidget(3, 1, chargePrice);
			ft.setStyleName("site-holder site-innerform site-padding");

			initWidget(ft);
		}

		public void loadData(JSONObject jo) {
			certId.setValue(ClientUtil.getString(jo.get("certid")));
			payPalId.setValue(ClientUtil.getString(jo.get("paypalid")));
			chargePerc.setJSONValue(jo.get("chargepercentage"));
			chargePrice.setPrice(jo.get("chargeprice"));

		}

		public JSONObject getObject(Boolean boolean1) {

			JSONObject jo = new JSONObject();
			jo.put("active", JSONBoolean.getInstance(boolean1));
			jo.put("certid", new JSONString(certId.getValue()));
			jo.put("paypalid", new JSONString(payPalId.getValue()));
			jo.put("chargepercentage", new JSONNumber(chargePerc.getIntVal()));
			jo.put("chargeprice", chargePrice.getPrice());
			return jo;
		}
	}

	private class OnDeliveryForm extends Composite {
		SiteIntegerBox chargePerc = new SiteIntegerBox();
		SitePriceBox chargePrice = new SitePriceBox();

		public OnDeliveryForm() {
			FlexTable ft = new FlexTable();
			ft.setHTML(0, 0, "charge perc %");
			ft.setWidget(0, 1, chargePerc);

			ft.setHTML(1, 0, "charge Price");
			ft.setWidget(1, 1, chargePrice);

			ft.setStyleName("site-holder site-innerform site-padding");

			initWidget(ft);
		}

		public void loadData(JSONObject jo) {
			chargePerc.setJSONValue(jo.get("chargepercentage"));
			chargePrice.setPrice(jo.get("chargeprice"));
		}

		public JSONObject getObject(Boolean boolean1) {
			JSONObject jo = new JSONObject();
			jo.put("active", JSONBoolean.getInstance(boolean1));
			jo.put("chargepercentage", new JSONNumber(chargePerc.getIntVal()));
			jo.put("chargeprice", chargePrice.getPrice());
			return jo;
		}
	}

	private class TransferForm extends Composite {
		SiteIntegerBox chargePerc = new SiteIntegerBox();
		SitePriceBox chargePrice = new SitePriceBox();
		BanksForm bf = new BanksForm();

		public TransferForm() {
			FlexTable ft = new FlexTable();
			ft.setHTML(0, 0, "charge perc %");
			ft.setWidget(0, 1, chargePerc);

			ft.setHTML(1, 0, "charge Price");
			ft.setWidget(1, 1, chargePrice);

			ft.setHTML(2, 0, "Banks");
			ft.setWidget(3, 0, bf);
			ft.getFlexCellFormatter().setColSpan(3, 0, 2);
			ft.setStyleName("site-holder site-innerform site-padding");

			initWidget(ft);
		}

		public void loadData(JSONObject jo) {
			chargePerc.setJSONValue(jo.get("chargepercentage"));
			chargePrice.setPrice(jo.get("chargeprice"));
			if (jo.get("banks") != null && jo.get("banks").isArray() != null)
				bf.loadData(jo.get("banks").isArray());

		}

		public JSONObject getObject(Boolean boolean1) {
			JSONObject jo = new JSONObject();
			jo.put("active", JSONBoolean.getInstance(boolean1));
			jo.put("chargepercentage", new JSONNumber(chargePerc.getIntVal()));
			jo.put("chargeprice", chargePrice.getPrice());
			jo.put("banks", bf.getObject());
			return jo;
		}
	}

	private class ShipForm extends Composite {

		ListBox lbShips = new ListBox();
		Button add = new Button(Ctrl.trans.addItem());
		Button del = new Button(Ctrl.trans.delete());
		Button reset = new Button(Ctrl.trans.resetForm());
		Button save = new Button(Ctrl.trans.save());

		TextBox title = new TextBox();
		SiteIntegerBox chargePerc = new SiteIntegerBox();
		SitePriceBox chargePrice = new SitePriceBox();
		CheckBox cbAcceptPayment = new CheckBox("Accept Payment");
		CheckBox cbAcceptCreditCard = new CheckBox("Accept Credit Card");
		SiteButton saveAll = new SiteButton("/_local/images/common/disk.png", Ctrl.trans.saveAll(), "", "");
		private JSONObject shipData;
		private final FlexTable form = new FlexTable();

		public ShipForm() {

			forSave();
			// forSaveAll();
			forAdd();
			forDel();
			forSelectList();
			forReset();

			save.setSize("200px", "40px");
			save.removeStyleName("gwt-Button");

			form.setHTML(0, 0, "Title");
			form.setWidget(0, 1, title);

			form.setHTML(1, 0, "charge perc %");
			form.setWidget(1, 1, chargePerc);

			form.setHTML(2, 0, "charge Price");
			form.setWidget(2, 1, chargePrice);

			form.setWidget(3, 1, cbAcceptPayment);

			form.setWidget(4, 1, cbAcceptCreditCard);

			form.setWidget(5, 1, save);

			form.setStyleName("site-innerform site-padding");

			del.setEnabled(false);
			form.setVisible(false);

			FlowPanel toolbar = new FlowPanel();
			toolbar.setStyleName("gwt-RichTextToolbar");
			toolbar.add(add);
			toolbar.add(del);

			lbShips.setWidth("100%");
			lbShips.setVisibleItemCount(10);
			toolbar.setWidth("100%");

			VerticalPanel vp = new VerticalPanel();
			vp.setWidth("140px");
			vp.add(toolbar);
			vp.add(lbShips);

			HorizontalPanel hp = new HorizontalPanel();
			hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			hp.setSpacing(5);
			hp.add(vp);
			hp.add(form);
			hp.setStyleName("site-holder");

			initWidget(hp);

		}

		protected void saveAll() {
			OrgDao.setshipstyle(new Json(getObject()), "/_/_org", new BooleanResponse() {
				@Override
				public void ready(Boolean value) {
					Ctrl.setStatus(Ctrl.trans.saved());
				}
			});

		}

		private void forReset() {
			reset.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					emptyForm();
				}
			});

		}

		public void forAdd() {
			add.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					emptyForm();
					form.setVisible(true);
				}
			});
		}

		private void emptyForm() {
			title.setValue(null);
			chargePerc.setValue(null);
			chargePrice.reset();
			cbAcceptCreditCard.setValue(false);
			cbAcceptPayment.setValue(false);
		}

		public void forDel() {
			del.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (Window.confirm(Ctrl.trans.confirmDelete())) {
						String key = lbShips.getValue(lbShips.getSelectedIndex());
						shipData.put(key, null);
						lbShips.removeItem(lbShips.getSelectedIndex());
						emptyForm();
						saveAll();
					}
				}
			});

		}

		private void forSelectList() {
			lbShips.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					if (lbShips.getSelectedIndex() > -1) {
						del.setEnabled(true);

						String key = lbShips.getValue(lbShips.getSelectedIndex());
						if (shipData == null || shipData.get(key) == null)
							return;

						JSONObject jo = shipData.get(key).isObject();
						title.setValue(ClientUtil.getString(jo.get("title")));
						chargePerc.setJSONValue(jo.get("chargepercentage"));
						chargePrice.setPrice(jo.get("chargeprice"));
						cbAcceptPayment.setValue(ClientUtil.getBoolean(jo.get("acceptpayment")));
						cbAcceptCreditCard.setValue(ClientUtil.getBoolean(jo.get("acceptcreditcard")));

						form.setVisible(true);

					} else
						del.setEnabled(false);

				}
			});

		}

		private void forSave() {
			save.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (title.getValue().isEmpty()) {
						Ctrl.setStatus("title empty");
						return;
					}

					String item = title.getValue();
					shipData.put(item, getFormObject());

					int ind = ClientUtil.findIndex(lbShips, item);
					if (ind < 0) {
						lbShips.addItem(item);
						lbShips.setSelectedIndex(lbShips.getItemCount() - 1);
					} else {
						lbShips.setValue(ind, item);
						lbShips.setSelectedIndex(ind);
					}
					emptyForm();
					// form.setVisible(false);
					saveAll();

				}
			});

		}

		public void loadData(JSONObject jo) {
			this.shipData = jo;
			Set<String> keys = jo.keySet();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				lbShips.addItem(key, key);
			}
		}

		private JSONObject getFormObject() {

			if (title.getValue().isEmpty())
				return null;

			JSONObject jo = new JSONObject();
			jo.put("title", new JSONString(title.getValue()));
			jo.put("chargepercentage", new JSONNumber(chargePerc.getIntVal()));
			jo.put("chargeprice", chargePrice.getPrice());

			if (cbAcceptCreditCard.getValue())
				jo.put("acceptcreditcard", JSONBoolean.getInstance(true));
			else
				jo.put("acceptcreditcard", JSONBoolean.getInstance(false));

			if (cbAcceptPayment.getValue())
				jo.put("acceptpayment", JSONBoolean.getInstance(true));
			else
				jo.put("acceptpayment", JSONBoolean.getInstance(true));

			return jo;
		}

		public JSONObject getObject() {

			if (lbShips.getItemCount() <= 0)
				return null;

			return shipData;
		}
	}

	private class BanksForm extends Composite {

		ListBox lbBanks = new ListBox();
		Button add = new Button(Ctrl.trans.addItem());
		Button edit = new Button(Ctrl.trans.change());
		Button del = new Button(Ctrl.trans.deleteItem());
		TextBox bank = new TextBox();
		TextBox username = new TextBox();
		TextBox branch = new TextBox();
		TextBox iban = new TextBox();

		public BanksForm() {

			forAdd();
			forChange();
			forDel();
			forSelectList();

			lbBanks.setWidth("300px");
			lbBanks.setVisibleItemCount(4);
			del.setEnabled(false);

			FlexTable form = new FlexTable();
			form.setHTML(0, 0, "Bank");
			form.setWidget(0, 1, bank);

			form.setHTML(1, 0, "branch");
			form.setWidget(1, 1, branch);

			form.setHTML(2, 0, "username");
			form.setWidget(2, 1, username);

			form.setHTML(3, 0, "iban");
			form.setWidget(3, 1, iban);

			FlowPanel btns = new FlowPanel();
			btns.add(add);
			btns.add(edit);
			btns.add(del);

			form.setWidget(4, 1, btns);

			form.setStyleName("site-innerform site-padding");

			VerticalPanel hp = new VerticalPanel();
			hp.setSpacing(5);
			hp.add(lbBanks);
			hp.add(form);
			hp.setStyleName("site-holder");

			initWidget(hp);

		}

		private void forChange() {
			edit.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (bank.getValue().isEmpty() || username.getValue().isEmpty() || branch.getValue().isEmpty()
							|| iban.getValue().isEmpty()) {
						Window.alert(Ctrl.trans.someValuesEmpty());
						return;
					}

					int ind = lbBanks.getSelectedIndex();
					if (ind < 0) {
						Window.alert(Ctrl.trans.selectAnItem());
						return;
					}

					String val = bank.getValue().trim() + "!" + branch.getValue().trim() + "!"
							+ username.getValue().trim() + "!" + iban.getValue().trim();

					String item = bank.getValue().trim() + " - " + branch.getValue().trim() + " - "
							+ username.getValue().trim() + " - " + iban.getValue().trim();

					lbBanks.setValue(ind, val);
					lbBanks.setItemText(ind, item);
					lbBanks.setSelectedIndex(lbBanks.getItemCount() - 1);

					emptyForm();

				}
			});

		}

		public void loadData(JSONArray ja) {
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo = ja.get(i).isObject();
				String val = ClientUtil.getString(jo.get("bank")) + "!" + ClientUtil.getString(jo.get("branch")) + "!"
						+ ClientUtil.getString(jo.get("username")) + "!" + ClientUtil.getString(jo.get("iban"));

				lbBanks.addItem(val.replace("!", " - "), val);
			}

		}

		public JSONArray getObject() {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < lbBanks.getItemCount(); i++) {
				String val = lbBanks.getValue(i);
				String[] vals = val.split("!");

				JSONObject jo = new JSONObject();
				jo.put("bank", new JSONString(vals[0]));
				jo.put("branch", new JSONString(vals[1]));
				jo.put("username", new JSONString(vals[2]));
				jo.put("iban", new JSONString(vals[3]));
				ja.set(i, jo);

			}
			return ja;

		}

		public void forAdd() {
			add.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (bank.getValue().isEmpty() || username.getValue().isEmpty() || branch.getValue().isEmpty()
							|| iban.getValue().isEmpty()) {
						Window.alert(Ctrl.trans.someValuesEmpty());
						return;
					}

					String val = bank.getValue().trim() + "!" + branch.getValue().trim() + "!"
							+ username.getValue().trim() + "!" + iban.getValue().trim();

					String item = bank.getValue().trim() + " - " + branch.getValue().trim() + " - "
							+ username.getValue().trim() + " - " + iban.getValue().trim();

					lbBanks.addItem(item, val);
					lbBanks.setSelectedIndex(lbBanks.getItemCount() - 1);

					emptyForm();
				}

			});

		}

		private void emptyForm() {
			bank.setValue(null);
			branch.setValue(null);
			username.setValue(null);
			iban.setValue(null);
		}

		public void forDel() {
			del.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (Window.confirm(Ctrl.trans.confirmDelete())) {
						lbBanks.removeItem(lbBanks.getSelectedIndex());
						emptyForm();
					}
				}
			});

		}

		private void forSelectList() {
			lbBanks.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					if (lbBanks.getSelectedIndex() > -1)
						del.setEnabled(true);
					else
						del.setEnabled(false);

					String value = lbBanks.getValue(lbBanks.getSelectedIndex());
					String[] arr = value.split("!");
					if (arr.length > 0) {
						bank.setValue(arr[0]);
						branch.setValue(arr[1]);
						username.setValue(arr[2]);
						iban.setValue(arr[3]);
					}

				}
			});

		}

	}

	class ContractsPanel extends Composite {

		ListBox lbLangs = new ListBox();
		TextArea taBodyRs = new TextArea();
		TextArea taBodyPic = new TextArea();

		public ContractsPanel() {

			taBodyRs.setSize("400px", "250px");
			taBodyPic.setSize("400px", "250px");

			lbLangs.setVisible(false);
			if (Ctrl.info().langcodes.length > 1) {
				lbLangs.setVisible(true);
				for (int i = 0; i < Ctrl.info().langcodes.length; i++) {
					String lng = Ctrl.info().langcodes[i];
					String lang = ClientUtil.findLangMatch(lng);
					lbLangs.addItem(lang, lng);
					lbLangs.addChangeHandler(new ChangeHandler() {
						@Override
						public void onChange(ChangeEvent event) {
							loadData(lbLangs.getSelectedValue());
						}
					});
				}

			}

			final String lang = lbLangs.isVisible() ? lbLangs.getSelectedValue() : Ctrl.infoLang();
			loadData(lang);

			Button save = new Button("save");
			save.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					OrgDao.changelanged(lbLangs.getSelectedValue(), taBodyPic.getValue(), taBodyRs.getValue(),
							"/_/_org", new StringResponse() {
								@Override
								public void ready(String value) {
									Ctrl.setStatus(Ctrl.trans.saved());
								}
							});

				}
			});
			VerticalPanel vp = new VerticalPanel();
			vp.setSpacing(10);
			vp.add(lbLangs);

			StackPanel sp = new StackPanel();
			sp.add(taBodyPic, "Mesafeli Satış Sözleşmesi");
			sp.add(taBodyRs, "Ön Bilgilendirme Formu");
			vp.add(sp);
			vp.add(save);

			initWidget(vp);

		}

		private void loadData(final String lang) {
			OrgDao.getcpic(lang, "/_/_org", new StringResponse() {
				@Override
				public void ready(String value) {
					if (value != null && !value.isEmpty()) {
						taBodyPic.setValue(value);
					} else {
						new GetContract(ContractsPanel.this, "pic", lang);
					}
				}
			});
			OrgDao.getcrs(lang, "/_/_org", new StringResponse() {
				@Override
				public void ready(String value) {
					if (value != null && !value.isEmpty()) {
						taBodyRs.setValue(value);
					} else {
						new GetContract(ContractsPanel.this, "rs", lang);
					}
				}
			});

		}

		public void fileReady(String resp, String type) {
			if (type.equals("pic"))
				taBodyPic.setValue(resp);
			else if (type.equals("rs"))
				taBodyRs.setValue(resp);
			else
				return;
		}

	}


}
