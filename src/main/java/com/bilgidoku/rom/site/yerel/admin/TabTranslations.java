package com.bilgidoku.rom.site.yerel.admin;

import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.StackLayoutPanel;

public class TabTranslations extends Composite {

	private final StackLayoutPanel stackPanel = new StackLayoutPanel(Unit.EM);
	private final TabDictionary dict;
	private final TabConstants cons;

	public TabTranslations() {

		dict = new TabDictionary();
		cons = new TabConstants();

		stackPanel.setPixelSize(650, 400);

		stackPanel.add(dict, new HTML(Ctrl.trans.translations()), 2);

		stackPanel.add(cons, new HTML(Ctrl.trans.constants()), 2);

		initWidget(stackPanel);
	}

	public String getSelectedWord() {
		if (stackPanel.getVisibleIndex() == 0) {
			return dict.getSelectedWord();
		} else {
			return cons.getSelectedWord();
		}
	}

}