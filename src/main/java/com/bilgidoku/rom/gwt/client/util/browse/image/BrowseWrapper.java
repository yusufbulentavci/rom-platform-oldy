package com.bilgidoku.rom.gwt.client.util.browse.image;

import com.bilgidoku.rom.gwt.client.util.com.DlgFrame;
import com.google.gwt.http.client.UrlBuilder;

public class BrowseWrapper extends DlgFrame {

	private ServerBrowseCb callback;

	public BrowseWrapper(String type, String initial, ServerBrowseCb callback) {
		super(new UrlBuilder().setParameter("type", type).setParameter("CKEditor", "true").setPath("/_local/browse.html"), initial, "Browse", "Ok");
		this.callback = callback;
		this.show();
	}

	@Override
	public void cancel() {
		this.hide();
		if (callback != null)
			callback.cancel();

	}

	@Override
	public void ok() {
//		selected = getText();
		String s = getText();
		this.hide();
		
		if (callback == null)
			return;
		if (s == null)
			callback.noImage();
		else
			callback.selected(s);
	}

}
