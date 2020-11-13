package com.bilgidoku.rom.gwt.client.util.browse.image;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.google.gwt.user.client.ui.Widget;

public class BrowseImageDlg extends ActionBarDlg {
	public String selectedImage = null;

	private Browser br;
	
	public BrowseImageDlg() {
		super("Browse Images", null, "OK");
		br = new Browser(true, null);
		run();
		this.show();
		this.center();
	}

	@Override
	public Widget ui() {
		return br;
	}

	@Override
	public void cancel() {
		selectedImage  = null;
		
	}

	@Override
	public void ok() {
		selectedImage  = br.getSelected();
		
	}

	public void setResource(String path) {
		br.setResource(path);
		
	}

}
