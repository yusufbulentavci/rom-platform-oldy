
package com.bilgidoku.rom.site.kamu.graph.client.ui.dlgs;

import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.ui.ChangeCallback;
import com.bilgidoku.rom.site.kamu.graph.client.ui.forms.search.SearchPanel;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DlgImageSearch extends ActionBarDlg {
	private ChangeCallback caller;
	private String userDir;
	private SearchPanel pnlSearch;

	public DlgImageSearch(final ChangeCallback caller, String userDir) {		
		super(GraphicEditor.trans.searchImageonInternet(), null, null);
		this.caller = caller;
		this.userDir = userDir;

		run();
		this.setGlassEnabled(true);
		this.show();
		this.center();
	}

	@Override
	public Widget ui() {
		pnlSearch = new SearchPanel(caller, userDir, false, this); 
		VerticalPanel vp = new VerticalPanel();
		vp.setStyleName("site-form");		
		vp.add(pnlSearch);
		return vp;
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ok() {

		
	}

}
