package com.bilgidoku.rom.site.yerel.common;

import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.user.client.ui.ListBox;

public class SiteLangList extends ListBox{
	
	public SiteLangList(String[] infoLangs, String siteLang) {
		loadContentLangs(infoLangs, siteLang);
	}
	
	public SiteLangList() {
	}

	public void loadContentLangs(String[] contentLangs, String currentLang) {
		this.clear();
		for (int i = 0; i < contentLangs.length; i++) {
			String lang = contentLangs[i];
			this.addItem(searchArray(Data.LANG_CODES, lang), lang);
			if (currentLang.equals(lang)) {
				this.setSelectedIndex(this.getItemCount() - 1);
			}
		}

		this.addItem("-----", "-");
		for (int i = 0; i < Data.LANG_CODES.length; i++) {
			String[] arr = Data.LANG_CODES[i];
			String key = arr[0];
			String value = arr[1];
			boolean writtenBefore = false;
			for (int j = 0; j < contentLangs.length; j++) {
				if (key.equals(contentLangs[j])) {
					writtenBefore = true;
					break;
				}
			}
			if (!writtenBefore) {
				this.addItem(Ctrl.trans.createInLanguage(value), key);
			}
			
		}
	}
	
	public String getSelectedLang() {
		return this.getValue(this.getSelectedIndex());
	}

	public void setSelectedLang(String[]  arr, String lang) {
		loadContentLangs(arr, lang);
	}

	public static String searchArray(String[][] langCodes, String lang) {
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

}
