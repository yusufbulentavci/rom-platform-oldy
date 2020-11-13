package com.bilgidoku.rom.site.yerel.writings;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.user.client.ui.Widget;

public class DlgChooseLang extends ActionBarDlg {
	public String selectedLang = null;
	private final LangList langList = new LangList();

	public DlgChooseLang(String[] langCodes) {
		super(Ctrl.trans.select(Ctrl.trans.pageLang()) + "       ", null, Ctrl.trans.open());
		run();
		this.show();

	}

	@Override
	public Widget ui() {
		return langList;
	}

	@Override
	public void cancel() {
		selectedLang = null;

	}

	@Override
	public void ok() {
		selectedLang = langList.getSelectedLang();

	}

}