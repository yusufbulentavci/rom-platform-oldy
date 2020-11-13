package com.bilgidoku.rom.gwt.client.util.common;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AskForValueDlg extends ActionBarDlg {

	private final TextBoxBase value;
	private String warning;	
	
	public AskForValueDlg(String title, String warning) {
		this(title, "", 1, 0, "OK", warning);
	}
	
	public AskForValueDlg(String title, String firstVal, int rows, int cols, String okButonText) {
		this(title, firstVal, rows, cols, okButonText, null);
	}
	
	public AskForValueDlg(String title, String firstVal, int rows, int cols, String okButonText, String warning) {
		super(title, null, okButonText);
		this.warning = warning;
		if (rows == 1)
			value = new TextBox();
		else {
			value = new TextArea();
			value.getElement().setAttribute("rows", rows +  "");
			value.getElement().setAttribute("cols", cols +  "");		
		}
		
		value.setValue(firstVal);
		value.setWidth("300px");
		
		run();		
		this.center();
		focusFirst();

	}

	public void setValue(String val) {
		value.setValue(val);
	}

	public String getValue() {
		return value.getValue();
	}

	@Override
	public Widget ui() {
		HTML warn = new HTML(warning);
		warn.setStyleName("site-warning");
		
		VerticalPanel ft = new VerticalPanel();
		ft.add(value);
		ft.add(warn);

		return ft;
	}

	@Override
	public void cancel() {
		setValue("");
		
	}

	@Override
	public void ok() {
	}

	public void focusFirst() {
		value.setFocus(true);
		
	}
}