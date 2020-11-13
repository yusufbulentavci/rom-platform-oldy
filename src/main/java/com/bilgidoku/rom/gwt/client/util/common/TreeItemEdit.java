package com.bilgidoku.rom.gwt.client.util.common;

import com.google.gwt.event.shared.GwtEvent;

public class TreeItemEdit extends GwtEvent<TreeItemEditHandler> {
	public TreeItemEdit() {
		super();
	}

	public static Type<TreeItemEditHandler> TYPE = new Type<TreeItemEditHandler>();

	public com.google.gwt.event.shared.GwtEvent.Type<TreeItemEditHandler> getAssociatedType() {
		return TYPE;
	}
	@Override
	protected void dispatch(TreeItemEditHandler handler) {
		handler.editTreeItem(this);		
	}

}
