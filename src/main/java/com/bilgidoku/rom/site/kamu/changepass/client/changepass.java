package com.bilgidoku.rom.site.kamu.changepass.client;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.TokensDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.TopOld;
import com.bilgidoku.rom.gwt.client.util.panels.HasPassword;
import com.bilgidoku.rom.gwt.client.util.panels.PnlChangepass;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.http.client.URL;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;



public class changepass extends RomEntryPoint {
	private String token;

	public changepass() {
		super("Rom Server Change Password App", false, null, true, false);
	}

	@Override
	protected void main() {
		
		RomEntryPoint.one.clear();
		
		this.token = Location.getParameter("token");
		if (token == null || token.isEmpty()) {
			Window.alert("no token");
			return;
		}
		PnlChangepass form = new PnlChangepass(new HasPassword() {
			
			@Override
			public void changePassword(String password) {
				JSONObject jo = new JSONObject();
				jo.put("pass", new JSONString(password));
				Json js = new Json(jo);

				TokensDao.change(js, token, new StringResponse() {

					@Override
					public void ready(String value) {
						TokensDao.ready(token, new BooleanResponse() {
							@Override
							public void ready(Boolean value) {
								Window.alert("Okey, we are now creating your platform. "
										+ "Please check your email about login information."
										+ " If we are transferring your domain, it may take up to six days.");
								
								UrlBuilder builder = Location.createUrlBuilder().removeParameter("gwt.codesvr");
								Window.Location.replace("/_public/login.html?redirect=" + URL.encode(builder.buildString()));

							}
						});
					}

				});
			}
		});
		form.setStyleName("site-loginform");
		
		FlexTable pnlCenter = new FlexTable();
		pnlCenter.setSize("100%", "100%");
		pnlCenter.setWidget(0, 0, form);
		pnlCenter.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		pnlCenter.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		DockLayoutPanel holder = new DockLayoutPanel(Unit.PX);
		holder.addNorth(new TopOld("one"), 34);
		holder.add(pnlCenter);

		
		//ChangepassUI lui = new ChangepassUI();
		RomEntryPoint.one.addToRootPanel(holder);

	}

}
