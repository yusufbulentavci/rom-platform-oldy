package com.bilgidoku.rom.gwt.client.util.common;

import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.TreeItem;

public class DropOnTree extends GwtEvent<DropOnTreeHandler> {
	public TreeItem targetNode;
	public DropEvent baseEvent;
	public Integer where; 
	
	public DropOnTree() {
		super();
	}

	public static Type<DropOnTreeHandler> TYPE = new Type<DropOnTreeHandler>();

	public com.google.gwt.event.shared.GwtEvent.Type<DropOnTreeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DropOnTreeHandler handler) {
		handler.dropOnTree(this);		
	}

}
