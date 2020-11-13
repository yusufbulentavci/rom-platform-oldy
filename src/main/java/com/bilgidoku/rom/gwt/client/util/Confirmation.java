package com.bilgidoku.rom.gwt.client.util;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.util.AsyncMethodNoParam;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public final class Confirmation extends ActionBarDlg {

	private HTML lblText;
	private AsyncMethodNoParam ret;

	/*
	 * create a confirm dialog
	 */
	public Confirmation(final String message, final AsyncMethodNoParam ret) {
		super("", null, "Evet");
		this.ret = ret;

		lblText = new HTML(message);
		
		run();
		center();
		show();
	}

	@Override
	public Widget ui() {

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(8);
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		Image image = new Image("/_public/images/question.png");
		lblText = new HTML("Are you sure?");
		lblText.setWidth("");
		
		horizontalPanel.add(lblText);
		horizontalPanel.add(image);
		

		return horizontalPanel;
	}

	@Override
	public void cancel() {
		try {
			ret.error();
		} catch (Exception e) {
			Sistem.printStackTrace(e, "Confirmation error error");
		}

		
	}

	@Override
	public void ok() {
		try {
			ret.run();
		} catch (Exception e) {
			Sistem.printStackTrace(e, "Confirmation run error");
		}

		
	}

}