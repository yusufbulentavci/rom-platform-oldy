package com.bilgidoku.rom.site.yerel.common;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AccessDlg extends DialogBox {

	private AccessPanel accessPanel;

	public AccessDlg(String uri){
		super(false);
		VerticalPanel vp=new VerticalPanel();
		accessPanel=new AccessPanel(uri);
		vp.add(accessPanel);
		Button ok = new Button("save");
		Button cancel = new Button("cancel");
		vp.add(ok);
		vp.add(cancel);
		add(vp);
		center();
		show();
		ok.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				AccessDlg.this.hide();
				accessPanel.save();
			}
		});
		cancel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				AccessDlg.this.hide();
			}
		});
	}
}
