package com.bilgidoku.rom.site.yerel.admin;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class NavDomainToolbar extends FlowPanel {

	private SiteToolbarButton btnNew = new SiteToolbarButton("user_add","Add", "User add");
	private SiteToolbarButton btnRemove = new SiteToolbarButton("user_delete","Delete", "");
	private SiteToolbarButton btnEdit = new SiteToolbarButton("user_edit", "Edit", "");
	// changepassword
	private DomainActions wa;

	public NavDomainToolbar(DomainActions wa) {
		this.wa = wa;
		forNew();
		forRemove();
		forEdit();
		
		btnEdit.setEnabled(false);
		btnRemove.setEnabled(false);
		Widget[] btns = { btnNew, btnEdit, btnRemove };
		this.add(ClientUtil.getToolbar(btns, 3));
	}

	private void forNew() {
		btnNew.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				wa.newLeaf();
			}
		});
	}

	private void forRemove() {
		btnRemove.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				wa.removeLeaf();
			}
		});
	}

	private void forEdit() {
		btnEdit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				wa.openLeaf();
			}
		});
	}

}
