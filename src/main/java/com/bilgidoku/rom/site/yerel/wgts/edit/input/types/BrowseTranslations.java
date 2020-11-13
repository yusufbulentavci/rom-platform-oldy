package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.admin.TabTranslations;
import com.google.gwt.user.client.ui.Widget;

public class BrowseTranslations extends ActionBarDlg {

	final TabTranslations pnlTrans = new TabTranslations();
	public String selectedTranslation = null;

	public BrowseTranslations() {
		super(Ctrl.trans.writings(), null,Ctrl.trans.ok());
		run();
		this.center();
		this.show();

	}

	@Override
	public Widget ui() {
		return pnlTrans;
	}


	@Override
	public void cancel() {
				
	}


	@Override
	public void ok() {
		selectedTranslation = pnlTrans.getSelectedWord();
		
	}
}
