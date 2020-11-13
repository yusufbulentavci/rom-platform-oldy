package com.bilgidoku.rom.site.kamu.tshirt.client;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class DlgImage extends ActionBarDlg {
	private String imgText;

	public DlgImage(String imgText, String canvas) {
		super("Önizleme (Baskı yapılacak imaj):" + canvas, null, null);
		this.imgText = imgText;
		run();
		this.setAutoHideEnabled(false);
		show();
		center();		

	}

	@Override
	public Widget ui() {
		Image img = new Image(imgText);
		return img;
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
