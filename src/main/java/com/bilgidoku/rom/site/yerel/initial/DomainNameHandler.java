package com.bilgidoku.rom.site.yerel.initial;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.siteinfo.subpanels.PanelAddress;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;

public class DomainNameHandler {

	final static private String[] DOMAIN_EXTENSIONS = { "com", "org", "net" };
	private String extension = "com";
	private String changedUri = null;

	private final ListBox extensionList = new ListBox(false);
	private final TextBox domainBox = new TextBox();
	private final  RadioButton rbCreate = new RadioButton("new", "create");
	private final  RadioButton rbTransfer = new RadioButton("new", "transfer");
	private final TextBox txtAuthInfo = new TextBox();	
	private final Button btnQuery = new Button("Query");
	private final Label uri = new Label("");
	private final Label uriLabel = new Label("Uri:");
	private final Label outInfo = new Label("Auth Info:");
	private final InitialPage init;
	private final DisclosurePanel addr = new DisclosurePanel(Ctrl.trans.address());
	private final PanelAddress panelAddr=null ;//=new PanelAddress();

	public DomainNameHandler(InitialPage initializationPage) {
		this.init = initializationPage;
		for (String ext : DOMAIN_EXTENSIONS) {
			extensionList.addItem(ext);
		}
		uri.setWidth("600px");
		rbCreate.setValue(true);
		setState(false);
		addr.setAnimationEnabled(true);
		addr.setContent(panelAddr);
		addr.setOpen(true);
		
		forExtensionSelect();
		forDomainChange();
		forTransferCheck();
		forCreateCheck();
	}

	private void forCreateCheck() {
		rbCreate.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
//				addr.setVisible(true);
				if (rbCreate.getValue()) 
					setState(false);
			}
		});
	}

	private void setState(boolean state) {
		txtAuthInfo.setVisible(state);		
		outInfo.setVisible(state);
	}
	
	private void forTransferCheck() {
		rbTransfer.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
//				addr.setVisible(false);
				if (rbTransfer.getValue()) 
					setState(true);
			}
		});
	}

	private void forDomainChange() {
		domainBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				init.domainNameChanged();
				uriChanged(changedUri);
				init.step2();
			}
		});

	}

	private void forExtensionSelect() {
		extensionList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				extension = extensionList.getItemText(extensionList.getSelectedIndex());
				init.domainNameChanged();
			}
		});
		
	}

	public ListBox getExtensionList() {
		return extensionList;
	}

	public TextBox getDomainBox() {
		return domainBox;
	}

	public String getUrl() {
		return "http://www." + domainBox.getValue() + "." + extension;
	}

	public String getDomainName() {
		return getDomainBox().getValue() + "." + extension;
	}

	public Label getUri() {
		return uri;
	}

	public Label getUriLabel() {
		return uriLabel;
	}

	public void uriChanged(String uri2) {
		if (uri2 == null)
			uri2 = "";
		if (uri2.endsWith("/")) {
			if (uri2.length() == 1) {
				this.uri.setText(getUrl());
			} else {
				this.uri.setText(getUrl() + uri2.substring(0, uri2.length() - 1));
			}
		} else {
			this.uri.setText(getUrl() + uri2);
		}
		changedUri = uri2;
	}

	public void noSelection() {
		this.uri.setText(getUrl());
	}

	public void setDomainName(String domain_name) {
		int ind = domain_name.indexOf('.');
		if (ind < 0) {
			Window.alert("Unexpected domain name:" + domain_name);
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
		Window.alert("Unexpected extension:" + extension);
	}

	public FlexTable getUi() {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		hp.add(new Label("www."));
		hp.add(domainBox);
		hp.add(new Label("."));
		hp.add(extensionList);
		
		domainBox.setSize("170px", "16px");
		txtAuthInfo.setSize("170px", "16px");
		FlexTable domainEntry = new FlexTable();
		domainEntry.setHeight("60px");
		domainEntry.setWidget(0, 0, hp);
		domainEntry.setWidget(0, 1, rbCreate);
		domainEntry.setWidget(0, 2, btnQuery);
		domainEntry.setWidget(1, 1, rbTransfer);
		domainEntry.setWidget(1, 3, outInfo);
		domainEntry.setWidget(1, 4, txtAuthInfo);
		

		domainEntry.setWidget(2, 0, addr);
		
		domainEntry.getCellFormatter().setVerticalAlignment(1, 3, HasVerticalAlignment.ALIGN_BOTTOM);
		domainEntry.getCellFormatter().setVerticalAlignment(1, 4, HasVerticalAlignment.ALIGN_BOTTOM);
		domainEntry.getFlexCellFormatter().setColSpan(2, 0, 5);
		return domainEntry;
	}

	public Boolean getIsTransfer() {
		if (rbTransfer.getValue()) 
			return new Boolean(true);
		return new Boolean(false);
	}

	public String getAuthInfo() {
		return txtAuthInfo.getValue();
	}

	public void setContact(Json contact) {
		if(contact == null || contact.getValue()==null || contact.getValue().isNull()!=null)
			return;
		panelAddr.loadData(contact.getValue().isObject());
	}

	public JSONObject getContact() {
		return panelAddr.getAddressObj();
	}

	public void closeAddressPanel() {
		addr.setOpen(false);		
	}

}
