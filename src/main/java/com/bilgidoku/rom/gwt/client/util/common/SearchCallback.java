package com.bilgidoku.rom.gwt.client.util.common;

import com.bilgidoku.rom.gwt.client.util.browse.image.search.SearchParams;

public interface SearchCallback {
	void newSearch(SearchParams params);
	void setOffsetForward();
	void setOffsetBackward();
	void picked(String uri);
}
