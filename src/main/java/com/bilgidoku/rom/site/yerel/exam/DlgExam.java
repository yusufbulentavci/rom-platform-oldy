package com.bilgidoku.rom.site.yerel.exam;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class DlgExam extends ActionBarDlg {
	public String question = null;
	public int duration;
	
	private TextArea txtName = new TextArea();
	private TextArea txtDuration = new TextArea();

	public DlgExam() {
		super("Exam", null, "OK");
		txtName.setValue("");
		txtDuration.setValue("10");
		run();
	}

	@Override
	public Widget ui() {	
		FlexTable ft = new FlexTable();
		ft.setHTML(0, 0, "Name of Exam");
		ft.setWidget(0, 1, txtName);
		ft.setHTML(1, 0, "Duration");
		ft.setWidget(1, 1, txtDuration);
		ft.setHTML(1, 2, "in minutes");
		
		return ft;
	}

	@Override
	public void cancel() {
		question = null;
	}

	@Override
	public void ok() {
		question = txtName.getValue();
		duration = Integer.parseInt(txtDuration.getValue());
	}

}
