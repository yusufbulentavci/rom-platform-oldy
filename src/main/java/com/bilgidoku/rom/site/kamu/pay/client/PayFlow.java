package com.bilgidoku.rom.site.kamu.pay.client;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.JsonResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.OrgDao;
import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.araci.client.site.CartDao;
import com.bilgidoku.rom.gwt.araci.client.site.CartResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Composite;

public class PayFlow extends Composite {
	
	public static Cart cart;
	public static JSONObject orgPayPrefs;
	public static JSONObject orgShipPrefs;
	
	private PayFlowUI ui = new PayFlowUI();

	public PayFlow() {
		getData();		
		initWidget(ui);
	}

	private void getData() {
		OrgDao.getorderpref("/_/_org", new JsonResponse() {
			@Override
			public void ready(Json value) {
				JSONValue val = value.getValue();
				final JSONObject jo = val.isObject();
				orgPayPrefs = jo.get("paystyle").isObject();
				orgShipPrefs = jo.get("shipstyle").isObject();

				CartDao.activeget("/_/_cart", new CartResponse() {
					@Override
					public void ready(Cart value) {
						if(value==null){
							Window.alert("there is no active cart");
							Window.Location.replace(Location.getProtocol() + "//" + Location.getHost() + Location.getPath());
							return;
						}
							
						cart = value;
						
						if (!value.design) {
							Window.alert("there is no design cart");
							Window.Location.replace(Location.getProtocol() + "//" + Location.getHost() + Location.getPath());
							return;
						}
						
//						ui = new PayFlowUI();	
//						PayFlow.this.add(ui);
						
						ui.dataLoaded();
						ui.cartChanged(value);
						
					}

					@Override
					public void err(int statusCode, String statusText, Throwable exception) {
						Window.alert("there is no active cart");
						Window.Location.replace(Location.getProtocol() + "//" + Location.getHost() + Location.getPath());
					}

				});

			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
			}
		});

		
	}

	public static String getShipPrice(JSONObject jo) {
		int perc = ClientUtil.getNumber(jo.get("chargepercentage"));
		int price = ClientUtil.getNumber(jo.get("chargeprice").isArray().get(0));
		if (perc == 0 && price == 0) {
			return pay.trans.cargoFree();
		}

		if (price != 0) {
			return ClientUtil.getPrice(jo.get("chargeprice"));
		}

		return getPercPrice(jo.get("chargepercentage"));
	}

	private static String getPercPrice(JSONValue jsonValue) {
		int perc = ClientUtil.getNumber(jsonValue);

		// get total price from cart
		JSONArray ja = cart.totalprice.getValue().isArray();
		int num = ClientUtil.getNumber(ja.get(0));
		double whole = ((double) perc * num) / 10000;

		return ClientUtil.getLocalePrice(whole, ClientUtil.getString(ja.get(1)));
	}

	public static JSONArray getCargoPrice(JSONObject jo) {

		int price = ClientUtil.getNumber(jo.get("chargeprice").isArray().get(0));
		int perc = ClientUtil.getNumber(jo.get("chargepercentage"));

		if (perc == 0 && price == 0) {
			return jo.get("chargeprice").isArray();
		}

		if (price != 0) {
			return jo.get("chargeprice").isArray();
		}

		JSONArray ja = cart.totalprice.getValue().isArray();
		int num = ClientUtil.getNumber(ja.get(0));
		double calcShipPrice = ((double) num * perc) / 100;

		String cur = ClientUtil.getString(cart.totalprice.getValue().isArray().get(1));

		JSONArray ret = new JSONArray();
		ret.set(0, new JSONNumber(calcShipPrice));
		ret.set(1, new JSONString(cur));

		return ret;
	}
	
	public static String getString(JSONObject jo, String name) {
		if (jo == null)
			return "";
		
		if (jo.get(name) == null || jo.get(name).isString() == null || jo.get(name).isString().stringValue() == null)
			return "";
		
		return jo.get(name).isString().stringValue();
		
		
	}
	
	
//	public static String getCurrency(String cur) {
//		if (cur.equals("TL") || cur.equals("YTL"))
//			return "&#x20BA;";
//		
//		if (cur.equals("Euro")) 
//			return "&#8364;";
//		
//		return cur;
//	}


}
