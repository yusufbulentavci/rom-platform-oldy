package com.bilgidoku.rom.site.yerel.writings;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class DlgDelete extends ActionBarDlg {

	private String[] langs;
	String selected = null;
	ListBox lb = new ListBox();

	public DlgDelete(String[] langs) {
		super(Ctrl.trans.confirmDelete(), null, Ctrl.trans.ok());
		this.langs = langs;
		run();
	}

	@Override
	public Widget ui() {
		lb.addItem("Sayfayı tamamen sil", "");
		for (int i = 0; i < langs.length; i++) {
			lb.addItem(ClientUtil.findLangMatch(langs[i]) + " içeriği sil", langs[i]);
		}
		
		return lb;
	}

	@Override
	public void cancel() {
		selected = null;
		
	}

	@Override
	public void ok() {
		if (lb.getSelectedIndex() <= 0)
			selected = null;
		else
			selected = lb.getSelectedValue();		
		
	}

}
