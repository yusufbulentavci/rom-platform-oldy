package com.bilgidoku.rom.site.kamu.activate.client;

import com.bilgidoku.rom.site.kamu.activate.client.constants.activatetext;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class DomainBox extends Composite{
	private final activatetext trans = GWT.create(activatetext.class);
	TextBox domainBox = new TextBox();
	final static private String[] DOMAIN_EXTENSIONS = { "com", "org", "net" };
	private String extension = "com";

	private final ListBox extensionList = new ListBox(false);

	public DomainBox(DomainPanel caller) {
		for (String ext : DOMAIN_EXTENSIONS) {
			extensionList.addItem(ext);
		}
		

		domainBox.setWidth("150px");

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		hp.add(domainBox);
		hp.add(new Label("."));		
		hp.add(extensionList);
		
		initWidget(hp);

	}


	public String getDomainName() {
		return domainBox.getValue() + "." + extension;
	}

	public void setDomainName(String domain_name) {
		int ind = domain_name.indexOf('.');
		if (ind < 0) {
			Window.alert(trans.checkDomainName() + ":" + domain_name);
			return;
		}
		String domainName = domain_name.substring(0, ind);
		extension = domain_name.substring(ind + 1);// Plus for .

		domainBox.setValue(domainName);

		for (int i = 0; i < extensionList.getItemCount(); i++) {
			if (extensionList.getValue(i).equals(extension)) {
				extensionList.setSelectedIndex(i);
				return;
			}
		}
		Window.alert(trans.checkExtension() + ":" + extension);
	}

	public void removeOkStyles() {
		domainBox.removeStyleName("site-boxred");
		domainBox.removeStyleName("site-boxgreen");		
	}

	public void showOK() {
		domainBox.removeStyleName("site-boxred");
		domainBox.addStyleName("site-boxgreen");		
	}

	public void showNotOK() {
		domainBox.removeStyleName("site-boxgreen");
		domainBox.addStyleName("site-boxred");		
	}


}
