package com.bilgidoku.rom.gwt.client.util.browse.image.search;

import com.bilgidoku.rom.gwt.client.util.common.DialogBase;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class DlgImageDownload extends DialogBase {
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	public DlgImageDownload(String title, String okTitle) {
		super(trans.selectAContainer(), trans.ok());
	}

	@Override
	public Widget ui() {
		
		return null;
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
