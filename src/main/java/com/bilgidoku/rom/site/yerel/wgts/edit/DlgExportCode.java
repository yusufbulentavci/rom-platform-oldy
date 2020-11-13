package com.bilgidoku.rom.site.yerel.wgts.edit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextArea;

public class DlgExportCode extends DialogBox {
	private final TextArea ta = new TextArea();
	final Button btnCancel = new Button("Cancel");
	final Button btnOK = new Button("OK");
	public String code;

	public DlgExportCode(String code, String title) {
		btnCancel.setStyleName("site-closebutton");
		
		forCancel();
		forOK();

		ta.setVisibleLines(15);
		ta.setWidth("400px");

		ta.setValue(code);
		
		FlexTable holder = new FlexTable();
		holder.setWidget(0, 0, ta);
		holder.setWidget(1, 0, btnOK);
		holder.setWidget(2, 0, btnCancel);

		this.setWidget(holder);
		this.setText(title);
		
		setGlassEnabled(true);
		setAutoHideEnabled(true);
		center();
	}

	private void forOK() {
		btnOK.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				code = ta.getValue();
				DlgExportCode.this.hide(true);				
			}
		});
		
	}

	private void forCancel() {
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				code = null;
				DlgExportCode.this.hide(true);
			}
		});
	}

}