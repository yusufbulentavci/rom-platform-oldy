package com.bilgidoku.rom.gwt.client.util.browse.image;

import com.bilgidoku.rom.gwt.client.util.browse.image.search.PnlImageSearch;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.google.gwt.user.client.ui.Widget;

public class SearchDlg extends ActionBarDlg {

	private String resourceUri;

	public SearchDlg() {
		super("Search Image", null, null);
		run();
		this.show();
		this.center();
	}

	public SearchDlg(String resourceUri) {		
		super("Search Image", null, null);
		this.resourceUri = resourceUri;
		run();
		this.show();
		this.center();
		
	}

	@Override
	public Widget ui() {
		PnlImageSearch sr = new PnlImageSearch(true, resourceUri);
		sr.addHandler(new DownloadCompletedHandler() {
			@Override
			public void done(DownloadCompleted event) {
				SearchDlg.this.fireEvent(new DownloadCompleted(event.selectedUri));
			}
		}, DownloadCompleted.TYPE);

		return sr;
	}

	@Override
	public void cancel() {

	}

	@Override
	public void ok() {
		// TODO Auto-generated method stub

	}

}
