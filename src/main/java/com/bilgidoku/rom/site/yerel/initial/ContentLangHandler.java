package com.bilgidoku.rom.site.yerel.initial;

import com.bilgidoku.rom.site.yerel.constants.InitialConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;

public class ContentLangHandler {
	private final InitialConstants con = GWT.create(InitialConstants.class);
	private final ListBox langList = new ListBox();
	private String contentLang = null;
	private final InitialPage init;

	public ContentLangHandler(final InitialPage init) {
		this.init = init;
		forChange();
	}

	private void forChange() {
		getLangList().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String oldLang = contentLang;
				contentLang = getLangList().getValue(getLangList().getSelectedIndex());

				if (oldLang == null || oldLang.equals("")) {
					getLangList().removeItem(0);
				}

				if (oldLang == null || !contentLang.equals(oldLang)) {
					init.langChanged();
					init.step3();
				}
			}
		});
	}

	public ListBox getLangList() {
		return langList;
	}

	public String getLang() {
		return contentLang;
	}

	public void setLang(String site_lang) {
		for (int i = 0; i < langList.getItemCount(); i++) {
			if (langList.getValue(i).equals(site_lang)) {
				langList.setSelectedIndex(i);
				//langList.removeItem(0);
				contentLang = site_lang;
				return;
			}
		}
		Window.alert("Unexpected lang:" + site_lang);
	}


}
