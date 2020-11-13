package com.bilgidoku.rom.gwt.client.util.common;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class SiteMessage extends ActionBarDlg {
	private String message;

	public SiteMessage(String message, String title) {
		super(title, null, null);
		this.message = message;
		run();
		show();
		center();
	}

	@Override
	public Widget ui() {
		return new HTML(message);
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ok() {
		// TODO Auto-generated method stub
		
	}
}
