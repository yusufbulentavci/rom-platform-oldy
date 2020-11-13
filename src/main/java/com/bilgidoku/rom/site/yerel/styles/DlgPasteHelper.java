package com.bilgidoku.rom.site.yerel.styles;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class DlgPasteHelper extends ActionBarDlg {

	public String widgetName = null;
	public String tagName = null;
	
	private TextBox txtWidget = new TextBox();
	private TextBox txtTag = new TextBox();
	
	public DlgPasteHelper() {
		super("Target Widget / Tag", null, "OK");
		run();
		show();
		center();

	}

	@Override
	public Widget ui() {
		FlexTable ft = new FlexTable();
		ft.setHTML(0, 0, "Widget Name");
		ft.setWidget(0, 1, txtWidget);

		ft.setHTML(1, 0, "Tag");
		ft.setWidget(1, 1, txtTag);

		return ft;
	}

	@Override
	public void cancel() {
		widgetName = null;
		tagName = null;
		
	}

	@Override
	public void ok() {
		widgetName = txtWidget.getValue();
		tagName = txtTag.getValue();		
	}

}
