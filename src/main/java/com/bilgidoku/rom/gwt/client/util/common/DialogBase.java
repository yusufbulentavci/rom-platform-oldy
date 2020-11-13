package com.bilgidoku.rom.gwt.client.util.common;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public abstract class DialogBase extends DialogBox {

	final Button btnClose = new Button("Close");
	final Button btnOK = new Button("OK");
	public String selected = null;

	public DialogBase(String title, String okTitle) {
		btnOK.setStyleName("site-btn");
		if (okTitle == null)
			btnOK.setVisible(false);
		else
			btnOK.setText(okTitle);
		this.setText(title);
	}
	
	public void run(){
		btnClose.setStyleName("site-closebutton");		
		FlowPanel fp = new FlowPanel();
		fp.add(btnClose);
		fp.add(btnOK);
		
		VerticalPanel gp = new VerticalPanel();
		gp.setSpacing(2);
//		gp.setStyleName("site-chatdlgin");
		gp.add(ui());
		gp.add(fp);
		
		this.setWidget(gp);
		this.setAutoHideEnabled(false);
		this.setModal(true);
		this.hide();
		
		forClose();
		forOk();
		
	}


	public abstract Widget ui();
	


	private void forClose() {
		btnClose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				cancel();
				DialogBase.this.hide();
			}
		});

	}


	private void forOk() {
		btnOK.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ok();
				DialogBase.this.hide();
			}
		});
	}

	public abstract void cancel();
	public abstract void ok();

}
