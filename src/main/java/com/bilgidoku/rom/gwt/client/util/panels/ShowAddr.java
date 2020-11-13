package com.bilgidoku.rom.gwt.client.util.panels;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ShowAddr extends Composite {
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	protected static interface Address extends SafeHtmlTemplates {
		@Template("<div style='border-spacing:5px 1px;border:none;width:100%;'>" + "<div>{4}</div>"
				+ "<div>{0} {1}</div>" + "<div>{2}&nbsp;&nbsp;{3}</div>" + "<div>{7}: {5}</div>" + "<div>{8}: {6}</div>"
				+ "</div>")
		SafeHtml text(String address, String postalCode, String city, String state, String name, String phone,
				String mobilePhone, String tranPhone, String tranMobPhone);
	}

	private Address TEMPLATE = GWT.create(Address.class);

	public ShowAddr(String title, JSONObject jo) {
		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(10);
		if (title != null)
			vp.add(ClientUtil.getTitle(title, "3"));

		if (jo != null) {
			HTML shipadr = new HTML(
					TEMPLATE.text(ClientUtil.getString(jo.get("address")), ClientUtil.getString(jo.get("postalcode")),
							ClientUtil.getString(jo.get("city")), ClientUtil.getString(jo.get("state")),
							ClientUtil.getString(jo.get("firstname")) + " " + ClientUtil.getString(jo.get("lastname")),
							ClientUtil.getString(jo.get("phone")), ClientUtil.getString(jo.get("mobile")),
							trans.phone(), trans.mobile()));

			vp.add(shipadr);
		}

		initWidget(vp);

	}

}