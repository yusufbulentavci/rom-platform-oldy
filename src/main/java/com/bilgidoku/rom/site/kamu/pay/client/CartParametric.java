package com.bilgidoku.rom.site.kamu.pay.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.OrgDao;
import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.araci.client.site.CartDao;
import com.bilgidoku.rom.gwt.araci.client.site.CartResponse;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.DialogBase;
import com.bilgidoku.rom.min.MinUtil;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class CartParametric {

	class ShowContract extends DialogBase {		
		private TextArea ta = new TextArea();
		
		public ShowContract(String title, String value) {
			super(title, "OK");
			this.setHTML(ActionBarDlg.getDlgCaption(null, title));
			this.setTitle(title);
			ta.setEnabled(false);
			ta.setSize("600px", "300px");
			ta.setStyleName("site-chatdlgin");
			ta.setValue(value);
			this.addStyleName("site-chatdlg");
			run();
			this.show();
			this.center();
		}

		@Override
		public Widget ui() {			
			return ta;
		}

		@Override
		public void cancel() {
	
		}

		@Override
		public void ok() {
	
		}
	}

	public CartParametric(final String sozlesme) {
		CartDao.activeget("/_/_cart", new CartResponse() {

			@Override
			public void ready(final Cart cart) {
				if (cart == null) {
					Window.alert("there is no active cart");
					Window.Location.replace(Location.getProtocol() + "//" + Location.getHost() + Location.getPath());
					return;
				}

				if (!cart.design) {
					Window.alert("there is no design cart");
					Window.Location.replace(Location.getProtocol() + "//" + Location.getHost() + Location.getPath());
					return;
				}

				try {
					cartReady(cart, sozlesme);
				} catch (RunException e) {
					Sistem.printStackTrace(e, "Cart reading error");
				}

			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				Window.alert("there is no active cart");
				Window.Location.replace(Location.getProtocol() + "//" + Location.getHost() + Location.getPath());
			}

		});
	}

	/**
	 * 
	 * [ORDER-DETAILS] 
	 * SHIP_TO-ADDRESS 
	 * SHIP_TO-NAME
	 * SHIP_TO-PHONE
	 * -SHIP_TO-FAX
	 * -SHIP_TO-EMAIL
	 * 
	 * INVOICE-ADDRESS
	 * INVOICE-NAME
	 * INVOICE-PHONE
	 * INVOICE-EMAIL
	 * INVOICE-FAX
	 * 
	 * ORDER-DATE
	 * 
	 * 
	 * 
	 * DELIVERY-TYPE
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * DELIVERY-DATE
	 * 
	 * 
	
	
	{
	"host_id": 1,
	"uri": "/_/_cart/5",
	"container": "/_/_cart",
	"html_file": null,
	"modified_date": "2016-05-06T16:56:00.29138",
	"creation_date": "2016-05-06T13:58:00.509822",
	"delegated": null,
	"ownercid": "/_/co/1",
	"gid": null,
	"relatedcids": null,
	"mask": 1835446,
	"nesting": null,
	"dbfs": null,
	"weight": 100,
	"lexemes": null,
	"rtags": null,
	"aa": null,
	"sid": null,
	"active": true,
	"design": false,
	"confirmed": false,
	"paysatisfied": false,
	"payconfirmed": false,
	"cancelled": false,
	"shipdate": null,
	"invoicesent": false,
	"items": {
		"/_/_stocks/3": {
			"amount": 2
		}
	},
	"itemsprice": [6000, "TRY"],
	"paystyle": null,
	"payments": null,
	"calcdetails": {
		"/_/_stocks/3": {
			"amount": "2",
			"tariff": {
				"price": [3000, "TRY"]
			},
			"stock": "/_/_stocks/3",
			"wuri": "/deneme.test/test1",
			"wtitle": "test1",
			"wsummary": "test 1 summary",
			"price": [6000, "TRY"]
		}
	},
	"shipstyle": "home",
	"shipaddr": {
		"country": "",
		"firstname": "mlos.net",
		"address": "",
		"city": "",
		"phone": "",showTemplate
		"postalcode": "",
		"mobile": "",
		"state": "",
		"lastname": "mlos.net"
	},
	"shipref": null,
	"shipdays": null,
	"shipprice": [0, "TRY"],
	"vatprice": null,
	"discountprice": null,
	"totalprice": [6000, "TRY"],
	"invoiceaddr": null,
	"notice": null,
	"issue": null,
	"lang_id": "tr",
	"validity": 0
}
	 * @param cart 
	 * @param sozlesme 
	
	
	 * 
	 * @throws RunException
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	protected void cartReady(final Cart cart, final String sozlesme) throws RunException {
		
		final Map<String, String> parameters = new HashMap<String, String>();
		orderDetails(cart, parameters);

		JSONObject jo = cart.shipaddr.getValue().isObject();
		address(cart, parameters, "SHIP_TO-", jo);
		
		if(cart.invoiceaddr!=null && cart.invoiceaddr.getValue().isObject()!=null){
			jo=cart.invoiceaddr.getValue().isObject();
		}
		
		address(cart, parameters, "INVOICE-", jo);
		
		parameters.put("ORDER-DATE", cart.modified_date);
		
		showTemplate(sozlesme, parameters);
	}

	private void showTemplate(String sozlesme, final Map<String, String> parameters) {		
		if(sozlesme.equals("cpic")){
			OrgDao.getcrs(null, "/_/_org", new StringResponse(){
				@Override
				public void ready(String value) {
					templateReady(value, parameters);
				}
			});
		}else{
			OrgDao.getcpic(null, "/_/_org", new StringResponse(){
				@Override
				public void ready(String value) {
					templateReady(value, parameters);
				}
			});
		}
		
	}

	protected void templateReady(String contract, final Map<String, String> parameters) {
		for (String key : parameters.keySet()) {
			String value=parameters.get(key);
			contract=contract.replaceAll(key, value);
		}
		ShowContract sc = new ShowContract("Contract", contract);
		sc.show();
	}

	/**
	 * 
	 * 
	"shipaddr": {
		"country": "",
		"firstname": "mlos.net",
		"address": "",
		"city": "",
		"phone": "",
		"postalcode": "",
		"mobile": "",
		"state": "",
		"lastname": "mlos.net"
	}
	 * @param cart 
	 * @param parameters 
	 * 
	 * @param string
	 * @param jo
	 * @throws RunException
	 */
	
	private void address(final Cart cart, Map<String, String> parameters, String prefix, JSONObject jo) throws RunException {
		parameters.put(prefix + "ADDRESS", ClientUtil.getString(jo, "address"));
		ClientUtil.getString(jo, "address");
		parameters.put(prefix + "NAME", MinUtil.merge(" ", "No name", ClientUtil.getString(jo, "firstname"),
				ClientUtil.getString(jo, "lastname")));
		parameters.put(prefix+"PHONE", MinUtil.coalesce(ClientUtil.getString(jo, "phone"), ClientUtil.getString(jo, "phone"), "no-phone"));

	}
	
	private List<Stocks> sts = new ArrayList<Stocks>();

	private void orderDetails(Cart cart, Map<String, String> parameters) {
		sts.clear();
		// "ORDER-DETAILS"s
		StringBuilder sb = new StringBuilder();
		sb.append("Ürün adı\tAdet\tBirim fiyat\tFiyat\n");
		JSONObject jo = cart.calcdetails.getValue().isObject();
		for (String key : jo.keySet()) {
			JSONObject joo = jo.get(key).isObject();			
			sb.append(joo.get("wtitle").isString().stringValue());
			sb.append("\t");
			sb.append(joo.get("amount").isString().stringValue());
			sb.append("\t");

			sb.append((ClientUtil.getPrice(joo.get("tariff").isObject().get("price"))));			
			sb.append("\t");
			
			sb.append(ClientUtil.getPrice(joo.get("price")));
			sb.append("\n");
		}
		

		sb.append("Ürünler toplamı:");
		sb.append(ClientUtil.getPrice(cart.itemsprice.getValue()));
		sb.append("\n");
		
		sb.append("Kargo Bedeli:");
		sb.append(ClientUtil.getPrice(cart.shipprice.getValue()));
		sb.append("\n");

		sb.append("KDV:");
		sb.append(ClientUtil.getPrice(cart.vatprice.getValue()));
		sb.append("\n");

		sb.append("Toplam:");
		sb.append(ClientUtil.getPrice(cart.totalprice.getValue()));
		sb.append("\n");

		parameters.put("ORDER-DETAILS", sb.toString());
	}


	
}
