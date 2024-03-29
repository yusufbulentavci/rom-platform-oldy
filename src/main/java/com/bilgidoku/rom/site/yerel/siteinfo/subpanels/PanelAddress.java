package com.bilgidoku.rom.site.yerel.siteinfo.subpanels;

import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.client.util.common.ClientData;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.content.HasAddress;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class PanelAddress extends ScrollPanel {
	private final TextArea txtAddress = new TextArea();
	private final ListBox listCountry = new ListBox();
	private final TextBox txtCity = new TextBox();
	private final TextBox txtFax = new TextBox();
	private final TextBox txtFirstName = new TextBox();
	private final TextBox txtLastName = new TextBox();
	private final TextBox txtEMail = new TextBox();
	private final TextBox txtOrganization = new TextBox();
	private final TextBox txtState = new TextBox();
	private final TextBox txtPhone = new TextBox();
	private final TextBox txtMobile = new TextBox();
	private final TextBox txtPostalCode = new TextBox();
	private final ListBox listLangs = new ListBox();

	public PanelAddress(final HasAddress caller) {
		loadCountries();
		loadLangs();

		KeyUpHandler hey = new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				caller.addressChanged();

			}
		};

		ChangeHandler mey = new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				caller.addressChanged();

			}
		};

		txtFirstName.addKeyUpHandler(hey);
		txtCity.addKeyUpHandler(hey);
		txtFax.addKeyUpHandler(hey);
		txtLastName.addKeyUpHandler(hey);
		txtEMail.addKeyUpHandler(hey);
		txtOrganization.addKeyUpHandler(hey);
		txtState.addKeyUpHandler(hey);
		txtPhone.addKeyUpHandler(hey);
		txtMobile.addKeyUpHandler(hey);
		txtPostalCode.addKeyUpHandler(hey);
		txtAddress.addKeyUpHandler(hey);
		
		listLangs.addChangeHandler(mey);
		listCountry.addChangeHandler(mey);

		txtAddress.setVisibleLines(3);
		txtAddress.setWidth("190px");
		listLangs.setWidth("100px");
		listCountry.setWidth("190px");

		FlexTable ft = new FlexTable();
		ft.addStyleName("site-padding");

		ft.setHTML(0, 0, "First Name");
		ft.setWidget(0, 1, txtFirstName);
		ft.setHTML(1, 0, "Last Name");
		ft.setWidget(1, 1, txtLastName);

		ft.setHTML(2, 0, "Email");
		ft.setWidget(2, 1, txtEMail);
		ft.setHTML(3, 0, "Organization");
		ft.setWidget(3, 1, txtOrganization);

		ft.setHTML(4, 0, Ctrl.trans.address());
		ft.setWidget(4, 1, txtAddress);

		ft.setHTML(5, 0, Ctrl.trans.city());
		ft.setWidget(5, 1, txtCity);
		ft.setHTML(6, 0, "State");
		ft.setWidget(6, 1, txtState);

		ft.setHTML(7, 0, Ctrl.trans.postalCode());
		ft.setWidget(7, 1, txtPostalCode);
		ft.setHTML(8, 0, Ctrl.trans.country());
		ft.setWidget(8, 1, listCountry);

		ft.setHTML(9, 0, Ctrl.trans.phone());
		ft.setWidget(9, 1, txtPhone);
		ft.setHTML(10, 0, "Mobile");
		ft.setWidget(10, 1, txtMobile);

		ft.setHTML(11, 0, Ctrl.trans.fax());
		ft.setWidget(11, 1, txtFax);
		ft.setHTML(12, 0, "Lang");
		ft.setWidget(12, 1, listLangs);

		ft.setHeight("50px");
		this.add(ft);
	}

	private void loadLangs() {
		for (int i = 0; i < Data.LANG_CODES.length; i++) {
			String[] arr = Data.LANG_CODES[i];
			listLangs.addItem(arr[1], arr[0]);
		}
	}

	private void loadCountries() {
		for (int i = 0; i < ClientData.COUNTRYS.length; i++) {
			String[] arr = ClientData.COUNTRYS[i];
			listCountry.addItem(arr[0], arr[1]);
		}
	}

	public void loadData(Contacts value) {
		setAddress(value.address);
		setState(value.state);
		setCity(value.city);
		setPostalCode(value.postal_code);
		setCountry(value.country_code);
		setPhone(value.phone);
		setMobile(value.mobile);
		setEMail(value.email);
		setFax(value.fax);
		setOrganization(value.organization);
		setFirstName(value.first_name);
		setLastName(value.last_name);
		setLang(value.lang_id);
	}

	public void loadData(JSONObject addr) {
		setAddress(ClientUtil.getString(addr.get("address")));
		setState(ClientUtil.getString(addr.get("state")));
		setCity(ClientUtil.getString(addr.get("city")));
		setPostalCode(ClientUtil.getString(addr.get("postalCode")));
		setCountry(ClientUtil.getString(addr.get("country")));
		setPhone(ClientUtil.getString(addr.get("phone")));
		setMobile(ClientUtil.getString(addr.get("mobile")));
		setEMail(ClientUtil.getString(addr.get("email")));
		setFax(ClientUtil.getString(addr.get("fax")));
		setOrganization(ClientUtil.getString(addr.get("organization")));
		setFirstName(ClientUtil.getString(addr.get("firstname")));
		setLastName(ClientUtil.getString(addr.get("lastname")));
		setLang(ClientUtil.getString(addr.get("lang")));
	}

	private void setCity(String value) {
		txtCity.setValue(value);

	}

	public JSONObject getAddressObj() {
		JSONObject jo = new JSONObject();
		jo.put("address", new JSONString(txtAddress.getValue()));
		jo.put("state", new JSONString(txtState.getValue()));
		jo.put("city", new JSONString(txtCity.getValue()));
		jo.put("postalCode", new JSONString(txtPostalCode.getValue()));
		jo.put("country", new JSONString(listCountry.getValue(listCountry.getSelectedIndex())));
		jo.put("phone", new JSONString(txtPhone.getValue()));
		jo.put("mobile", new JSONString(txtMobile.getValue()));
		jo.put("email", new JSONString(txtEMail.getValue()));
		jo.put("fax", new JSONString(txtFax.getValue()));
		jo.put("organization", new JSONString(txtOrganization.getValue()));
		jo.put("firstname", new JSONString(txtFirstName.getValue()));
		jo.put("lastname", new JSONString(txtLastName.getValue()));
		jo.put("lang", new JSONString(listLangs.getValue(listLangs.getSelectedIndex())));
		return jo;
	}

	public void resetForm() {
		txtAddress.setValue("");
		txtState.setValue("");
		txtCity.setValue("");
		txtPostalCode.setValue("");
		listCountry.setSelectedIndex(0);
		txtPhone.setValue("");
		txtMobile.setValue("");
		txtEMail.setValue("");
		txtFax.setValue("");
		txtOrganization.setValue("");
		txtFirstName.setValue("");
		txtLastName.setValue("");
		listLangs.setSelectedIndex(0);
	}

	public String getCity() {
		return txtCity.getValue();
	}

	public String getFax() {
		return txtFax.getValue();
	}

	public String getFirstName() {
		return txtFirstName.getValue();
	}

	public String getLastName() {
		return txtLastName.getValue();
	}

	public String getEMail() {
		return txtEMail.getValue();
	}

	public String getOrganization() {
		return txtOrganization.getValue();
	}

	public String getState() {
		return txtState.getValue();
	}

	public String getPhone() {
		return txtPhone.getValue();
	}

	public String getMobile() {
		return txtMobile.getValue();
	}

	public String getPostalCode() {
		return txtPostalCode.getValue();
	}

	public String getAddress() {
		return txtAddress.getValue();
	}

	public String getCountry() {
		return listCountry.getValue(listCountry.getSelectedIndex());
	}

	public String getLang() {
		return listLangs.getValue(listLangs.getSelectedIndex());
	}

	public void setFirstName(String value) {
		txtFirstName.setValue(value);
	}

	public void setLastName(String value) {
		txtLastName.setValue(value);
	}

	public void setEMail(String value) {
		txtEMail.setValue(value);
	}

	public void setOrganization(String value) {
		txtOrganization.setValue(value);
	}

	public void setState(String value) {
		txtState.setValue(value);
	}

	public void setPhone(String value) {
		txtPhone.setValue(value);
	}

	public void setMobile(String value) {
		txtMobile.setValue(value);
	}

	public void setPostalCode(String value) {
		txtPostalCode.setValue(value);
	}

	public void setAddress(String value) {
		txtAddress.setValue(value);
	}

	public void setCountry(String value) {
		ClientUtil.findAndSelect(listCountry, value);
	}

	public void setLang(String value) {
		ClientUtil.findAndSelect(listLangs, value);
	}

	public void setFax(String fax) {
		txtFax.setValue(fax);
	}

	private void reformatPhone(TextBox phoneField) {
		String text = phoneField.getText();
		text = text.replaceAll("\\D+", "");
		if (text.length() == 10) {
			phoneField.setText("(" + text.substring(0, 3) + ") " + text.substring(3, 6) + " " + text.substring(6, 8)
					+ " " + text.substring(8, 10));
		}
	}

}
