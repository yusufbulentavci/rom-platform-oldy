package com.bilgidoku.rom.site.yerel.common;

import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemEdit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class MailBoxTreeItem extends SiteTreeItem {

	public MailBoxTreeItem(String text, String res) {
		super(text, res);
		lblItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				lblItem.fireEvent(new TreeItemEdit());
			}
		});
	}

}
