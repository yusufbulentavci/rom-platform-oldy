package com.bilgidoku.rom.site.yerel.issues;

import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ResolveIssueDlg extends DialogBox {

	private final TextArea desc = new TextArea();
	public boolean fixed = false;

	public ResolveIssueDlg(String resolveDesc) {
		desc.setSize("450px", "200px");
		if (resolveDesc != null) {
			desc.setValue(resolveDesc);
		}		
		
		Button btnCancel = new Button("Cancel");
		btnCancel.setStyleName("site-closebutton");
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ResolveIssueDlg.this.setDesc("");
				fixed = false;
				ResolveIssueDlg.this.hide(true);
			}
		});

		Button ok = new Button("OK");
		ok.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				fixed = true;
				hide();				
			}
		});
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(new Label(Ctrl.trans.solution() + ":"));
		vp.add(desc);
		vp.add(ok);
		vp.add(btnCancel);
		
		this.setWidget(vp);
		this.setGlassEnabled(true);
		this.setAutoHideEnabled(true);
		this.setText(Ctrl.trans.resolve());
		this.center();
	}



	public void setDesc(String val) {
		desc.setValue(val);
	}

	public String getDesc() {
		return desc.getValue();
	}
}