package com.bilgidoku.rom.site.yerel.contacts;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.site.yerel.mail.core.MailUtil;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class DlgAskName extends ActionBarDlg {
	private TextBox txtFirstName = new TextBox();
	private TextBox txtLastName = new TextBox();
	private TextBox txtEmail = new TextBox();
	public String firstName = null;
	public String lastName = null;
	public String email = null;
	
	public DlgAskName(String addr) {
		super("Contact Info", null, "OK");
		if (addr != null) {
			txtEmail.setValue(MailUtil.getAddrEmail(addr));
			txtFirstName.setValue(MailUtil.getAddrFirstName(addr));
			txtLastName.setValue(MailUtil.getAddrLastName(addr));
			
		}
		
		run();
		this.center();
	}
	


	@Override
	public Widget ui() {
		FlexTable f = new FlexTable();
		f.setHTML(0, 0, "First Name");
		f.setWidget(0, 1, txtFirstName);
		f.setHTML(1, 0, "Last Name");
		f.setWidget(1, 1, txtLastName);
		f.setHTML(2, 0, "Email");
		f.setWidget(2, 1, txtEmail);

		return f;
	}

	@Override
	public void cancel() {
		firstName = null;
		lastName = null;
		email = null;
		
	}

	@Override
	public void ok() {
		firstName = txtFirstName.getValue();
		lastName = txtLastName.getValue();
		email = txtEmail.getValue();
		
	}
}
