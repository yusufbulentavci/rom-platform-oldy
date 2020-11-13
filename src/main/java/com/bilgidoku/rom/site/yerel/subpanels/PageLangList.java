package com.bilgidoku.rom.site.yerel.subpanels;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.content.HasContent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

public class PageLangList extends ListBox{
	
	public PageLangList(final HasContent caller) {
		this.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				caller.langChanged(getSelectedLang());
			}
		});

	}

	
	public String getSelectedLang() {
		return this.getValue(this.getSelectedIndex());
	}

	public static String getName(String lang) {
		String[][] langCodes = Data.LANG_CODES;		
		String found = "";				
		for (int i = 0; i < langCodes.length; i++) {
			String[] arr = langCodes[i];
			if (arr[0].equals(lang)) {
				found = arr[1];
				break;
			}
		}
		return found;
	}

	public void load(String[] pageLangs, String[] infoLangs) {
		this.clear();	
		for (int i = 0; i < infoLangs.length; i++) {
			String lang = infoLangs[i];
			if (contains(pageLangs, lang)) {
				this.addItem(getName(lang), lang);
			} else {
				this.addItem(Ctrl.trans.createInLanguage(getName(lang)), lang);
			}			
		}
	}
	
	public void loadLang(String currentLang, String[] pageLangs, String[] infoLangs) {
		this.load(pageLangs, infoLangs);
		this.selectLang(currentLang);

	}


	private boolean contains(String[] contentLangs, String lang) {
		if (contentLangs == null)
			return false;
		
		boolean exists = false;
		for (int i = 0; i < contentLangs.length; i++) {
			String iLang = contentLangs[i];
			if (lang.equals(iLang)) {
				exists = true;
				break;
			}
		}
		return exists;
		
	}

	public void selectLang(String value) {
		ClientUtil.findAndSelect(this, value);
		
	}

}
