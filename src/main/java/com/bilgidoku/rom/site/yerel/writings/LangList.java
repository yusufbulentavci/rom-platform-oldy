package com.bilgidoku.rom.site.yerel.writings;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.boxer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

public class LangList extends Composite {
	private ListBox lb = new ListBox();
	public LangList() {
		loadLangs(Ctrl.info().langcodes);
		initWidget(lb);
	}
	
	private void loadLangs(String[] langcodes) {
		for (int i = 0; i < langcodes.length; i++) {
			
			if (langcodes[i].equals(boxer.pageLang))
				continue;
			
			lb.addItem(ClientUtil.findLangMatch(langcodes[i]), langcodes[i]);
		}
	}

	public String getSelectedLang() {
		return lb.getSelectedValue();
	}

	
	
}
