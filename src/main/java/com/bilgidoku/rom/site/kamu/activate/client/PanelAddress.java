package com.bilgidoku.rom.site.kamu.activate.client;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.kamu.activate.client.constants.activatetext;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PanelAddress extends ScrollPanel {
	private final activatetext trans = GWT.create(activatetext.class);

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
	private final SiteButton next = new SiteButton(trans.next(), trans.nextDesc(), "next");
	
	public PanelAddress(Steps steps) {
		next.setEnabled(false);
		loadCountries();		
		
		forNext(steps);
		forKeyPressFirstName();
		
		txtAddress.setVisibleLines(3);
		txtAddress.setWidth("300px");
		

		FlexTable ft = new FlexTable();
		ft.addStyleName("site-padding");
		ft.setHTML(0, 0, trans.firstName());
		ft.setWidget(0, 1, txtFirstName);
		ft.setHTML(0, 2, trans.lastName());
		ft.setWidget(0, 3, txtLastName);

		ft.setHTML(1, 0, trans.email());
		ft.setWidget(1, 1, txtEMail);
		ft.setHTML(1, 2, trans.organization());
		ft.setWidget(1, 3, txtOrganization);

		ft.setHTML(2, 0, trans.address());
		ft.setWidget(2, 1, txtAddress);

		ft.setHTML(3, 0, trans.city());
		ft.setWidget(3, 1, txtCity);
//		ft.setHTML(3, 2, trans.state());
//		ft.setWidget(3, 3, txtState);

		ft.setHTML(4, 0, trans.postalCode());
		ft.setWidget(4, 1, txtPostalCode);
		ft.setHTML(4, 2, trans.country());
		ft.setWidget(4, 3, listCountry);

		ft.setHTML(5, 0, trans.phone());
		ft.setWidget(5, 1, txtPhone);
		ft.setHTML(5, 2, trans.mobile());
		ft.setWidget(5, 3, txtMobile);

		ft.setHTML(6, 0, trans.fax());
		ft.setWidget(6, 1, txtFax);

		ft.getFlexCellFormatter().setColSpan(2, 1, 3);
		ft.setHeight("50px");
		
		HTML title = new HTML("<h2>" + trans.address() + "</h2>");
		title.setStyleName("site-title");
		
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(title);
		vp.add(ft);
		vp.add(next);
		vp.setStyleName("site-panel");
		
		this.add(vp);
	}

	private void forKeyPressFirstName() {
		txtFirstName.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				
				String input = txtFirstName.getText();
				if (input.length() > 2) 
					next.setEnabled(true);
		        
			}
		});
		
	}

	private void forNext(final Steps steps) {
		next.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				steps.state3OK(getAddressObj());
				
			}
		});
		
	}

	private void loadCountries() {
		for (int i = 0; i < COUNTRYS.length; i++) {
			String[] arr = COUNTRYS[i];
			listCountry.addItem(arr[0], arr[1]);
		}
	}


	
	public void loadData(JSONObject addr) {
		setAddress(Steps.getStringValue(addr, "address"));
		setState(Steps.getStringValue(addr, "state"));
		setCity(Steps.getStringValue(addr, "city"));
		setPostalCode(Steps.getStringValue(addr, "postalCode"));
		setCountry(Steps.getStringValue(addr, "country"));
		setPhone(Steps.getStringValue(addr, "phone"));
		setMobile(Steps.getStringValue(addr, "mobile"));
		setEMail(Steps.getStringValue(addr, "email"));
		setFax(Steps.getStringValue(addr, "fax"));
		setOrganization(Steps.getStringValue(addr, "organization"));
		setFirstName(Steps.getStringValue(addr, "firstname"));
		setLastName(Steps.getStringValue(addr, "lastname"));		
	}
	
	public void setJson(JSONObject jb) throws NotReadyException {
		JSONObject jo=new JSONObject();
		jo.put("address", new JSONString(txtAddress.getValue()));
		jo.put("state", new JSONString(txtState.getValue()));
		jo.put("postalCode", new JSONString(txtPostalCode.getValue()));
		jo.put("country", new JSONString(Steps.notNull("country",getCountry())));
		jo.put("phone", new JSONString(txtPhone.getValue()));
		jo.put("mobile", new JSONString(txtMobile.getValue()));
		jo.put("email", new JSONString(Steps.notNull("email", txtEMail.getValue())));
		jo.put("fax", new JSONString(txtFax.getValue()));
		jo.put("organization", new JSONString(txtOrganization.getValue()));
		jo.put("firstname", new JSONString(txtFirstName.getValue()));
		jo.put("lastname", new JSONString(txtLastName.getValue()));
		jb.put("contact", jo);
		
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
//		ClientUtil.findAndSelect(listCountry, value);
	}

	public void setLang(String value) {
//		ClientUtil.findAndSelect(listLangs, value);
	}

	public void setFax(String fax) {
		txtFax.setValue(fax);
	}


	private void reformatPhone(TextBox phoneField) {
		String text = phoneField.getText();
		text = text.replaceAll("\\D+", "");
		if (text.length() == 10) {
			phoneField.setText("(" + text.substring(0, 3) + ") " + text.substring(3, 6) + " " + text.substring(6, 8) + " " + text.substring(8, 10));
		}
	}

	private static final String[][] COUNTRYS = { { "Turkey", "TR" }, { "Afghanistan", "AF" }, { "Albania", "AL" },
		{ "Algeria", "DZ" }, { "American Samoa", "AS" }, { "Andorra", "AD" }, { "Angola", "AO" },
		{ "Anguilla", "AI" }, { "Antarctica", "AQ" }, { "Antigua and Barbuda", "AG" }, { "Argentina", "AR" },
		{ "Armenia", "AM" }, { "Aruba", "AW" }, { "Australia", "AU" }, { "Austria", "AT" }, { "Azerbaijan", "AZ" },
		{ "Bahamas", "BS" }, { "Bahrain", "BH" }, { "Bangladesh", "BD" }, { "Barbados", "BB" },
		{ "Belarus", "BY" }, { "Belgium", "BE" }, { "Belize", "BZ" }, { "Benin", "BJ" }, { "Bermuda", "BM" },
		{ "Bhutan", "BT" }, { "Bolivia", "BO" }, { "Bosnia and Herzegovina", "BA" }, { "Botswana", "BW" },
		{ "Brazil", "BR" }, { "British Indian Ocean Territory", "IO" }, { "British Virgin Islands", "VG" },
		{ "Brunei", "BN" }, { "Bulgaria", "BG" }, { "Burkina Faso", "BF" }, { "Burma (Myanmar)", "MM" },
		{ "Burundi", "BI" }, { "Cambodia", "KH" }, { "Cameroon", "CM" }, { "Canada", "CA" },
		{ "Cape Verde", "CV" }, { "Cayman Islands", "KY" }, { "Central African Republic", "CF" }, { "Chad", "TD" },
		{ "Chile", "CL" }, { "China", "CN" }, { "Christmas Island", "CX" }, { "Cocos (Keeling) Islands", "CC" },
		{ "Colombia", "CO" }, { "Comoros", "KM" }, { "Republic of the Congo", "CG" },
		{ "Democratic Republic of the Congo", "CD" }, { "Cook Islands", "CK" }, { "Costa Rica", "CR" },
		{ "Croatia", "HR" }, { "Cuba", "CU" }, { "Cyprus", "CY" }, { "Czech Republic", "CZ" }, { "Denmark", "DK" },
		{ "Djibouti", "DJ" }, { "Dominica", "DM" }, { "Dominican Republic", "DO" }, { "Timor-Leste", "TL" },
		{ "Ecuador", "EC" }, { "Egypt", "EG" }, { "El Salvador", "SV" }, { "Equatorial Guinea", "GQ" },
		{ "Eritrea", "ER" }, { "Estonia", "EE" }, { "Ethiopia", "ET" }, { "Falkland Islands", "FK" },
		{ "Faroe Islands", "FO" }, { "Fiji", "FJ" }, { "Finland", "FI" }, { "France", "FR" },
		{ "French Polynesia", "PF" }, { "Gabon", "GA" }, { "Gambia", "GM" }, { "Gaza Strip", "/" },
		{ "Georgia", "GE" }, { "Germany", "DE" }, { "Ghana", "GH" }, { "Gibraltar", "GI" }, { "Greece", "GR" },
		{ "Greenland", "GL" }, { "Grenada", "GD" }, { "Guam", "GU" }, { "Guatemala", "GT" }, { "Guinea", "GN" },
		{ "Guinea-Bissau", "GW" }, { "Guyana", "GY" }, { "Haiti", "HT" }, { "Honduras", "HN" },
		{ "Hong Kong", "HK" }, { "Hungary", "HU" }, { "Iceland", "IS" }, { "India", "IN" }, { "Indonesia", "ID" },
		{ "Iran", "IR" }, { "Iraq", "IQ" }, { "Ireland", "IE" }, { "Isle of Man", "IM" }, { "Israel", "IL" },
		{ "Italy", "IT" }, { "Ivory Coast", "CI" }, { "Jamaica", "JM" }, { "Japan", "JP" }, { "Jersey", "JE" },
		{ "Jordan", "JO" }, { "Kazakhstan", "KZ" }, { "Kenya", "KE" }, { "Kiribati", "KI" }, { "Kuwait", "KW" },
		{ "Kyrgyzstan", "KG" }, { "Laos", "LA" }, { "Latvia", "LV" }, { "Lebanon", "LB" }, { "Lesotho", "LS" },
		{ "Liberia", "LR" }, { "Libya", "LY" }, { "Liechtenstein", "LI" }, { "Lithuania", "LT" },
		{ "Luxembourg", "LU" }, { "Macau", "MO" }, { "Macedonia", "MK" }, { "Madagascar", "MG" },
		{ "Malawi", "MW" }, { "Malaysia", "MY" }, { "Maldives", "MV" }, { "Mali", "ML" }, { "Malta", "MT" },
		{ "Marshall Islands", "MH" }, { "Mauritania", "MR" }, { "Mauritius", "MU" }, { "Mayotte", "YT" },
		{ "Mexico", "MX" }, { "Micronesia", "FM" }, { "Moldova", "MD" }, { "Monaco", "MC" }, { "Mongolia", "MN" },
		{ "Montenegro", "ME" }, { "Montserrat", "MS" }, { "Morocco", "MA" }, { "Mozambique", "MZ" },
		{ "Namibia", "NA" }, { "Nauru", "NR" }, { "Nepal", "NP" }, { "Netherlands", "NL" },
		{ "Netherlands Antilles", "AN" }, { "New Caledonia", "NC" }, { "New Zealand", "NZ" },
		{ "Nicaragua", "NI" }, { "Niger", "NE" }, { "Nigeria", "NG" }, { "Niue", "NU" },
		{ "Norfolk Island", "/ NFK" }, { "Northern Mariana Islands", "MP" }, { "North Korea", "KP" },
		{ "Norway", "NO" }, { "Oman", "OM" }, { "Pakistan", "PK" }, { "Palau", "PW" }, { "Panama", "PA" },
		{ "Papua New Guinea", "PG" }, { "Paraguay", "PY" }, { "Peru", "PE" }, { "Philippines", "PH" },
		{ "Pitcairn Islands", "PN" }, { "Poland", "PL" }, { "Portugal", "PT" }, { "Puerto Rico", "PR" },
		{ "Qatar", "QA" }, { "Romania", "RO" }, { "Russia", "RU" }, { "Rwanda", "RW" },
		{ "Saint Barthelemy", "BL" }, { "Samoa", "WS" }, { "San Marino", "SM" }, { "Sao Tome and Principe", "ST" },
		{ "Saudi Arabia", "SA" }, { "Senegal", "SN" }, { "Serbia", "RS" }, { "Seychelles", "SC" },
		{ "Sierra Leone", "SL" }, { "Singapore", "SG" }, { "Slovakia", "SK" }, { "Slovenia", "SI" },
		{ "Solomon Islands", "SB" }, { "Somalia", "SO" }, { "South Africa", "ZA" }, { "South Korea", "KR" },
		{ "Spain", "ES" }, { "Sri Lanka", "LK" }, { "Saint Helena", "SH" }, { "Saint Kitts and Nevis", "KN" },
		{ "Saint Lucia", "LC" }, { "Saint Martin", "MF" }, { "Saint Pierre and Miquelon", "PM" },
		{ "Saint Vincent and the Grenadines", "VC" }, { "Sudan", "SD" }, { "Suriname", "SR" },
		{ "Svalbard", "SJ" }, { "Swaziland", "SZ" }, { "Sweden", "SE" }, { "Switzerland", "CH" },
		{ "Syria", "SY" }, { "Taiwan", "TW" }, { "Tajikistan", "TJ" }, { "Tanzania", "TZ" }, { "Thailand", "TH" },
		{ "Togo", "TG" }, { "Tokelau", "TK" }, { "Tonga", "TO" }, { "Trinidad and Tobago", "TT" },
		{ "Tunisia", "TN" }, { "Turkmenistan", "TM" }, { "Turks and Caicos Islands", "TC" }, { "Tuvalu", "TV" },
		{ "United Arab Emirates", "AE" }, { "Uganda", "UG" }, { "United Kingdom", "GB" }, { "Ukraine", "UA" },
		{ "Uruguay", "UY" }, { "United States", "US" }, { "Uzbekistan", "UZ" }, { "Vanuatu", "VU" },
		{ "Holy See (Vatican City)", "VA" }, { "Venezuela", "VE" }, { "Vietnam", "VN" },
		{ "US Virgin Islands", "VI" }, { "Wallis and Futuna", "WF" }, { "Western Sahara", "EH" },
		{ "Yemen", "YE" }, { "Zambia", "ZM" }, { "Zimbabwe", "ZW" } };

	
}
