package com.bilgidoku.rom.site.kamu.tutor.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;

public class OptionView extends FlexTable implements Locatable{

	private final SelectView selectView;

	public OptionView(SelectView selectView) {
		this.selectView=selectView;
	}

	public void show(Option o, Button b) {
	}

	@Override
	public void locate(int x, int y) {
	}

}
