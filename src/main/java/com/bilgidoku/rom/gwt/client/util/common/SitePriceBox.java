package com.bilgidoku.rom.gwt.client.util.common;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

public class SitePriceBox extends Composite{

	private final SiteIntegerBox whole = new SiteIntegerBox();
	private final SiteIntegerBox frac = new SiteIntegerBox();
	private final ListBox curr = new ListBox();
	
	public SitePriceBox() {
		
		curr.addItem("$", "USD");
		curr.addItem("TL", "TRY");
		curr.addItem("Euro", "EUR");		
		
		curr.setSelectedIndex(1);
		curr.setWidth("48px");
		frac.setWidth("20px");
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(whole);
		hp.add(new Label("."));
		hp.add(frac);
		
		hp.add(curr);

		initWidget(hp);
		
		

	}

	public JSONArray getPrice() {

		JSONArray ja = new JSONArray();
		ja.set(0, new JSONNumber(whole.getIntVal() * 100 + frac.getIntVal()));
		ja.set(1, new JSONString(curr.getValue(curr.getSelectedIndex())));
		
		return ja;

	}

	public void setPrice(JSONValue jval) {
		if (jval == null || jval.isArray() == null)
			return;
		
		//TODO RomCurency den 1000 mu 1000 mi alınmalı
		
		JSONArray ja = jval.isArray();
		
		Double pr = ja.get(0).isNumber().doubleValue();
		int ipr = pr.intValue();
		
		whole.setValue((ipr / 100) + "");
		
		int fr = ipr % 100;		
		
		frac.setValue(fr < 10 ? "0"+fr :  fr + "");
		
		String munit = ja.get(1).isString().stringValue();
		
		ClientUtil.findAndSelect(curr, munit);
		
	}

	public void reset() {
		whole.setValue(null);		
		curr.setSelectedIndex(1);
		frac.setValue(null);
	}
	
	

}

