package com.bilgidoku.rom.site.yerel.exam;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class DlgNewQuestion extends ActionBarDlg {
	public String question = null;
	private TextArea txt = new TextArea();

	public DlgNewQuestion() {
		super("New Question", null, "OK");
		run();
	}

	@Override
	public Widget ui() {		
		return txt;
	}

	@Override
	public void cancel() {
		question = null;
	}

	@Override
	public void ok() {
		question = txt.getValue();

	}

}
