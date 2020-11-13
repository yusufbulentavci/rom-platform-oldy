package com.bilgidoku.rom.gwt.client.util.common;

import com.google.gwt.event.shared.GwtEvent;

public class TreeItemDelete extends GwtEvent<TreeItemDeleteHandler> {
	public TreeItemDelete() {
		super();
	}

	public static Type<TreeItemDeleteHandler> TYPE = new Type<TreeItemDeleteHandler>();

	public com.google.gwt.event.shared.GwtEvent.Type<TreeItemDeleteHandler> getAssociatedType() {
		return TYPE;
	}
	@Override
	protected void dispatch(TreeItemDeleteHandler handler) {
		handler.deleteTreeItem(this);		
	}

}
