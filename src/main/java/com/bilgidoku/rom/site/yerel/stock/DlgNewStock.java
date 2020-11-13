package com.bilgidoku.rom.site.yerel.stock;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class DlgNewStock extends ActionBarDlg {
	CheckBox cbUnder = new CheckBox();
	TextBox tbTitle = new TextBox();

	public DlgNewStock(String firstStockTitle) {
		super("Yeni Stock", null, "Tamam");
		cbUnder.setVisible(false);
		cbUnder.setValue(false);
		
		if (firstStockTitle != null) {
			cbUnder.setText(firstStockTitle + " altında oluştur");
			cbUnder.setVisible(true);
		}
		
		run();
		show();
		center();
	}

	@Override
	public Widget ui() {
		
		FlexTable ft = new FlexTable();
		ft.setHTML(0, 0, "Stok Adı");
		ft.setWidget(0, 1, tbTitle);
		ft.setWidget(1, 1, cbUnder);
		
		return ft;
	}

	@Override
	public void cancel() {
		tbTitle.setValue(null);
	}

	@Override
	public void ok() {

	}
	
	public String getStockTitle() {
		return tbTitle.getValue();
	}
	
	public boolean isUnder() {
		return cbUnder.getValue();
	}

}
