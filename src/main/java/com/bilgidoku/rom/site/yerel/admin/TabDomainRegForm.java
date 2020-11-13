package com.bilgidoku.rom.site.yerel.admin;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TabDomainRegForm extends Composite {

//	FlexTable pnlAdmin = new FlexTable();
//	FlexTable pnlTech = new FlexTable();
	FlexTable pnlRegis = new FlexTable();
	public TabDomainRegForm() {
		initWidget(ui());
	}

	private Widget ui() {

		pnlRegis.setHTML(0, 0, Ctrl.trans.id());
		pnlRegis.setWidget(0, 1, new MyTextBox("registrant-id"));

		pnlRegis.setHTML(1, 0, Ctrl.trans.name());
		pnlRegis.setWidget(1, 1, new MyTextBox("registrant-name"));
		pnlRegis.setWidget(1, 2, ClientUtil.getWarningRed("*"));

		pnlRegis.setHTML(2, 0, Ctrl.trans.organization());
		pnlRegis.setWidget(2, 1, new MyTextBox("registrant-organization"));
		pnlRegis.setWidget(2, 2, ClientUtil.getWarningRed("*"));

		pnlRegis.setHTML(3, 0, Ctrl.trans.street_line1());
		pnlRegis.setWidget(3, 1, new MyTextBox("registrant-street-1"));

		pnlRegis.setHTML(4, 0, Ctrl.trans.street_line2());
		pnlRegis.setWidget(4, 1, new MyTextBox("registrant-street-2"));
		
		pnlRegis.setHTML(5, 0, Ctrl.trans.city());
		pnlRegis.setWidget(5, 1, new MyTextBox("registrant-city"));

		pnlRegis.setHTML(6, 0, Ctrl.trans.state());
		pnlRegis.setWidget(6, 1, new MyTextBox("registrant-state"));

		pnlRegis.setHTML(7, 0, Ctrl.trans.postalCode());
		pnlRegis.setWidget(7, 1, new MyTextBox("registrant-postalcode"));

		pnlRegis.setHTML(8, 0, Ctrl.trans.country());
		pnlRegis.setWidget(8, 1, new MyTextBox("registrant-country"));

		pnlRegis.setHTML(9, 0, Ctrl.trans.phone());
		pnlRegis.setWidget(9, 1, new MyTextBox("registrant-phone"));

		pnlRegis.setHTML(10, 0, Ctrl.trans.phoneExt());
		pnlRegis.setWidget(10, 1, new MyTextBox("registrant-phoneext"));

		pnlRegis.setHTML(11, 0, Ctrl.trans.fax());
		pnlRegis.setWidget(11, 1, new MyTextBox("registrant-fax"));

		pnlRegis.setHTML(12, 0, Ctrl.trans.faxExt());
		pnlRegis.setWidget(12, 1, new MyTextBox("registrant-faxext"));

		pnlRegis.setHTML(13, 0, Ctrl.trans.eMail());
		pnlRegis.setWidget(13, 1, new MyTextBox("registrant-email"));

		StackPanel vp = new StackPanel();

		vp.add(pnlRegis, Ctrl.trans.registrant());
		

		return vp;
		
	}

	public Json getRegistrant() {
						
		JSONObject obj = new JSONObject();
		int rowCount =  pnlRegis.getRowCount();
		
		for (int i = 0; i < rowCount; i++) {
			MyTextBox tb = (MyTextBox) pnlRegis.getWidget(i, 1);
			String value = tb.getValue();
			String name = tb.getName();
			
			if (name.equals("registrant-name") && (value == null || value.isEmpty())) {
				Window.alert(Ctrl.trans.emptyValue(Ctrl.trans.registrant() + " " + Ctrl.trans.name()));
				return null;
				
			}

			if (name.equals("registrant-organization") && (value == null || value.isEmpty())) {
				Window.alert(Ctrl.trans.emptyValue(Ctrl.trans.registrant() + " " + Ctrl.trans.organization()));
				return null;		
			}

			obj.put(name.replace("registrant-", ""), new JSONString(value));
		}
		
		return new Json(obj);
		
		
	}

	private class MyTextBox extends TextBox {

		public MyTextBox(String name) {
			this.setName(name);
		}
	}

}
