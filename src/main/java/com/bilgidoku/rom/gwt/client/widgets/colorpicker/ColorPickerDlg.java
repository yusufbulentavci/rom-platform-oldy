package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.google.gwt.user.client.ui.Widget;

public class ColorPickerDlg extends ActionBarDlg {

	public ColorPickerDlg() {
		super("Color Picker", null, null);
		run();
		center();
	}

	@Override
	public Widget ui() {
		return new HSVColorPicker();
	}

	@Override
	public void cancel() {
	}

	@Override
	public void ok() {
	}
}
