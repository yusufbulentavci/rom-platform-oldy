package com.bilgidoku.rom.gwt.client.util.com;

import com.bilgidoku.rom.gwt.client.util.common.DialogBase;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.ui.Widget;

public abstract class DlgFrame extends DialogBase implements RomFrameHandler {

	RomFrameImpl rf;

	public DlgFrame(UrlBuilder urlBuilder, final String initialText, String title, String okTitle) {
		super(title, okTitle);
		rf = new RomFrameImpl(urlBuilder, this, initialText);
		rf.setWidth("600px");
		rf.setHeight("400px");
		this.addStyleName("site-chatdlg");
		run();
	}

	@Override
	public Widget ui() {
		return rf;
	}

	@Override
	public void focusItem(String cls, String uri) {
	}

	@Override
	public void setItem(String cls, String uri) {
	}

	@Override
	public void ready() {
	}

	public String getText() {
		return rf.getText();
	}

	public void setFrameSize(int w, int h) {
		rf.setSize(w + "px", h + "px");
	}
	
}
