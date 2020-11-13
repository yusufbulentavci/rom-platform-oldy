package com.bilgidoku.rom.site.yerel.common;

import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemDelete;
import com.bilgidoku.rom.gwt.client.util.common.TreeItemEdit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class NavTreeItem extends SiteTreeItem {

	public NavTreeItem(String text, String icon, Image hoverImage, String dragData, boolean justShow, boolean isEdit,
			boolean isDelete) {
		super(text, icon, dragData);
		overImage = hoverImage;

		if (justShow)
			return;

		forOver(isEdit, isDelete);
		forOut();
		if (isDelete)
			forDelete();
		if (isEdit) {
			forEdit();
		}
	}

	private void forDelete() {
		btnDelete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				lblItem.fireEvent(new TreeItemDelete());
				btnEdit.setVisible(false);
				btnDelete.setVisible(false);

			}
		});
	}

	private void forEdit() {
		btnEdit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				lblItem.fireEvent(new TreeItemEdit());
				btnEdit.setVisible(false);
				btnDelete.setVisible(false);
			}
		});
	}

	private void forOut() {
		this.holder.addDomHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				btnEdit.setVisible(false);
				btnDelete.setVisible(false);
				hidePopUp();
			}
		}, MouseOutEvent.getType());

	}

	protected void hidePopUp() {
		if (simplePopup != null) {
			simplePopup.hide();
			simplePopup = null;
		}
	}

	private void forOver(final boolean isEdit, final boolean isDelete) {
		this.holder.addDomHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				if (isDelete)
					btnDelete.setVisible(true);

				if (isEdit)
					btnEdit.setVisible(true);

				if (overImage != null) {
					Widget source = (Widget) event.getSource();
					showPopUp(overImage, source);
				}

			}
		}, MouseOverEvent.getType());
	}
}
