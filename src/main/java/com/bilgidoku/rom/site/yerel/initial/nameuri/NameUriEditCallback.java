package com.bilgidoku.rom.site.yerel.initial.nameuri;

import com.google.gwt.user.client.ui.TreeItem;


public interface NameUriEditCallback {
	String getContentLang();
	void nameChanged(String name, String uri, boolean menu, boolean footer, boolean vitrine, String app);
	boolean addNewPage(String name, String uri, boolean menu, boolean footer, boolean vitrine, String app, String group);
	TreeItem addGetPageGroup(String name, String uri, boolean menu, boolean footer, boolean vitrine, String app);
}
