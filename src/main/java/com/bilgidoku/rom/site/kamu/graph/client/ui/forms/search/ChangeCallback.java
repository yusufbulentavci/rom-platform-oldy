package com.bilgidoku.rom.site.kamu.graph.client.ui.forms.search;

import com.bilgidoku.rom.site.kamu.graph.client.change.Change;

public interface ChangeCallback {
	
	void textChanged(Change change);	
	void newImage(String imgUri);
	void imageChanged(String imgUrl);
	void setStatus(String text);
	
}
