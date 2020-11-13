package com.bilgidoku.rom.gwt.client.widgets.dragdrop;

import com.google.gwt.user.client.ui.TreeItem;

public interface DropListener {
	public void dropped(TreeItem item, Integer where, String text, String kind); 
}
