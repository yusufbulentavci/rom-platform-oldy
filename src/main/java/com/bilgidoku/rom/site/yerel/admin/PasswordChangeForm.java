package com.bilgidoku.rom.site.yerel.admin;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.InitialsDao;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class PasswordChangeForm extends FlexTable{
	private final PasswordTextBox rctPass = new PasswordTextBox();
	private final PasswordTextBox newPass = new PasswordTextBox();
	private final PasswordTextBox newPass2 = new PasswordTextBox();
	private final Button btnSavePassword = new Button("Save");
	private final Button btnCancel = new Button("Cancel");
	private String user;

	public PasswordChangeForm(String user) {
		this.user = user;
		forSavePass();
		forCancel();
		
		HorizontalPanel btns = new HorizontalPanel();
		btns.setSpacing(3);
		btns.add(btnSavePassword);
		btns.add(btnCancel);		
		
		this.setSize("100%", "50px");
		this.setHTML(1, 0, "New Password");
		this.setWidget(1, 1, newPass);
		this.setHTML(2, 0, "Confirm Password");
		this.setWidget(2, 1, newPass2);
		this.setWidget(3, 0, btns);
		this.getFlexCellFormatter().setColSpan(3, 0, 2);
		//this.addStyleName("site-panels");
		this.getFlexCellFormatter().setWidth(0, 0, "150px");
		
	}

	private void forCancel() {
		// TODO Auto-generated method stub
		
	}

	private void forSavePass() {
		btnSavePassword.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				if (newPass.getValue().isEmpty() || newPass2.getValue().isEmpty()) {
					Window.alert(Ctrl.trans.someValuesEmpty());
					newPass.setFocus(true);
					return;
				}
				if (!newPass.getValue().equals(newPass2.getValue())) {
					Window.alert(Ctrl.trans.samePasswords());
					newPass.setFocus(true);
					return;
				}
				
				InitialsDao.changepass(user, newPass.getValue(), "/_/_initials", new StringResponse() {
					@Override
					public void ready(String value) {
						// TODO Auto-generated method stub
						super.ready(value);
					}
					
				});				
			}
		});
		
	}

	public void resetForm() {		
		rctPass.setValue("");
		newPass.setValue("");
		newPass2.setValue("");		
	}

}
