package com.bilgidoku.rom.site.yerel.admin;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DlgNewCertificate extends ActionBarDlg {

	private String alias;
	final FormPanel form = new FormPanel();

	public DlgNewCertificate(String alias) {
		super("Upload Certificate", null, null);
		this.alias = alias;
		run();
	}

	@Override
	public Widget ui() {
		final Hidden hdnalias = new Hidden();
		hdnalias.setName("alias");
		hdnalias.setValue(alias);		
		
		form.setAction("/_admin/addCert.rom?outform=json");
		form.setMethod(FormPanel.METHOD_POST);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		
		FileUpload certFile = new FileUpload();
		certFile.setName("cert");

		
		Button btn = new Button("OK");
		btn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				form.submit();				
			}
		});
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(certFile);
		hp.add(hdnalias);
		hp.add(btn);

		form.add(hp);
		return form;
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ok() {
		
	}

}
