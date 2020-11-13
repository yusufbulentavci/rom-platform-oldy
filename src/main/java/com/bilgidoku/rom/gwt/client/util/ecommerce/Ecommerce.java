package com.bilgidoku.rom.gwt.client.util.ecommerce;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Cart;
import com.bilgidoku.rom.gwt.araci.client.site.CartDao;
import com.bilgidoku.rom.gwt.araci.client.site.CartResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Info;
import com.bilgidoku.rom.gwt.araci.client.site.InfoDao;
import com.bilgidoku.rom.gwt.araci.client.site.InfoResponse;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.gwt.client.util.common.ActionBar;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;

public class Ecommerce extends CompBase{
	public static final CompInfo info = new CompInfo("+ecommerce", 100, new String[] {}, new String[] { "_wndtop", "+topwindow" },
			null);
	public static final CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return new Ecommerce();
		}
	};

	boolean cartReady = false;
	Cart cart;

	DlgCart dlgCart = new DlgCart(this);
	private boolean enabled=false;

	Ecommerce() {
		InfoDao.get("tr", "/_/siteinfo", new InfoResponse() {
			@Override
			public void ready(Info value) {
				if(value.ecommerce!=null && value.ecommerce){
					enabled=true;
					refreshCart(false);
				}
			}
		});
	}

	private void refreshCart(final boolean showDlg) {
		this.cartReady = false;
		CartDao.activeget("/_/_cart", new CartResponse() {
			@Override
			public void ready(Cart value) {
				cart = value;
				cartReady = true;
				dlgCart.update(cart);

				ActionBar bar=(ActionBar) RomEntryPoint.cm().comp("+actionbar", null);
				bar.cartChanged(value == null ? 0 : countAmount(cart.items.getValue().isObject()), showDlg);
			}

			private int countAmount(JSONObject object) {
				int ret = 0;
				for (String k : object.keySet()) {
					JSONObject o = object.get(k).isObject();
					ret += o.get("amount").isNumber().doubleValue();
				}
				return ret;
			}
		});
	}

	public void cartDeleteStock(String stockUri) {
		int am = (int) cart.items.getValue().isObject().get(stockUri).isObject().get("amount").isNumber().doubleValue();

		changeCartStockAmount(stockUri, -am);

	}

	public void changeCartStockAmount(String stockUri, int diff) {
		if (!enabled)
			return;

		CartDao.add(stockUri, diff, null, "/_/_cart", new BooleanResponse() {
			@Override
			public void ready(Boolean value) {
				refreshCart(true);
			}
		});
	}

	public void showCart() {
		if (dlgCart == null)
			return;
		dlgCart.show();
		dlgCart.setPopupPosition(Window.getClientWidth() - 620, 25);

	}
	
	public boolean isShowing() {
		if (dlgCart == null)
			return false;		
		return dlgCart.isShowing();
	}

	
	public void hideCart() {
		if (dlgCart == null)
			return;
		dlgCart.hide();
	}

	public void checkOut() {
		if (cart == null)
			return;
		Location.replace("/_public/contact.html?tab=flow");
	}

	@Override
	public CompInfo compInfo() {
		return info;
	}

}
