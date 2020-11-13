package com.bilgidoku.rom.site.yerel.initial;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.constants.InitialConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;

public class MenuHandler {
	private final InitialConstants con = GWT.create(InitialConstants.class);

	public final static int MENU_MAIN = 0;
	public final static int MENU_FOOTER = 1;
	public final static int MENU_VITRINE = 2;

	private final ListBox list = new ListBox();
	private final Button add = new Button(ClientUtil.imageItemHTML("/_local/images/common/right.png", ""));
	private final Button up = new Button(ClientUtil.imageItemHTML("/_local/images/common/up.png", ""));
	private final Button down = new Button(ClientUtil.imageItemHTML("/_local/images/common/down.png", ""));
	private final Button delete = new Button(ClientUtil.imageItemHTML("/_local/images/common/cross_small.png", ""));
	private final int type;
	private final InitialPage initial;

	public MenuHandler(InitialPage initializationPage, final int type, final String listName, final String listTip) {
		this.initial = initializationPage;
		this.type = type;
		setInitialState();
		list.setVisibleItemCount(5);
		titles(listTip);
		forSelect();
		forDelete();
		forAdd();
		forUp();
		forDown();
	}

	private void setInitialState() {
		add.setEnabled(false);
		up.setEnabled(false);
		down.setEnabled(false);
		delete.setEnabled(false);
	}

	public void setState(boolean b) {
		//true => page item selected
		//false => menu item selected
		add.setEnabled(b);
		up.setEnabled(!b);
		down.setEnabled(!b);
		delete.setEnabled(!b);
	}

	private void forDown() {
		down.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int ind = list.getSelectedIndex();
				if (ind < 0 || ind == list.getItemCount()) {
					return;
				}
				String val = list.getValue(ind);
				list.removeItem(ind);
				list.insertItem(val, val, ind + 1);
				list.setSelectedIndex(ind + 1);
			}
		});
	}

	private void forUp() {
		up.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int ind = list.getSelectedIndex();
				if (ind < 1) {
					return;
				}
				String val = list.getValue(ind);
				list.removeItem(ind);
				list.insertItem(val, val, ind - 1);
				list.setSelectedIndex(ind - 1);
			}
		});
	}

	private void forAdd() {
		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Details det = initial.getSelectedDetail();
				if (det == null)
					return;
				String realUri = det.getRealUri();
				if (!addUri(realUri)) {
					return;
				}
				switch (type) {
				case MENU_MAIN:
					det.menu = true;
					break;
				case MENU_FOOTER:
					det.footer = true;
					break;
				case MENU_VITRINE:
					det.vitrine = true;
					break;
				}
			}
		});
	}

	private void forDelete() {
		delete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int ind = list.getSelectedIndex();
				if (ind < 0) {
					Window.alert("Select a item to remove");
					return;
				}

				String realUri = list.getItemText(ind);
				if (!removeUri(realUri)) {
					return;
				}
				Details det = initial.getDetail(realUri);
				switch (type) {
				case MENU_MAIN:
					det.menu = false;
					break;
				case MENU_FOOTER:
					det.footer = false;
					break;
				case MENU_VITRINE:
					det.vitrine = false;
					break;
				}
			}
		});
	}

	private void forSelect() {
		list.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				setState(false);
			}
		});

	}

	private void titles(final String listTip) {
		add.setTitle(con.addPageToMenu());
		delete.setTitle(con.deletePage());
		up.setTitle(con.moveUp());
		down.setTitle(con.moveDown());
		list.setTitle(listTip);
	}

	public ListBox getPageList() {
		return list;
	}

	public Button getAdd() {
		return add;
	}

	public Button getUp() {
		return up;
	}

	public Button getDown() {
		return down;
	}

	public Button getDelete() {
		return delete;
	}

	public boolean removeUri(String realUri) {
		for (int i = 0; i < list.getItemCount(); i++) {
			String txt = list.getItemText(i);
			if (txt.trim().equals(realUri.trim())) {
				list.removeItem(i);
				return true;
			}
		}
		return false;
	}

	public boolean changeUri(String oldRealUri, String realUri) {
		for (int i = 0; i < list.getItemCount(); i++) {
			if (list.getItemText(i).equals(oldRealUri)) {
				list.setItemText(i, realUri);
				list.setValue(i, realUri);
				return true;
			}
		}
		return false;
	}

	public boolean addUri(String realUri) {
		for (int i = 0; i < list.getItemCount(); i++) {
			if (list.getItemText(i).equals(realUri)) {
				return false;
			}
		}
		list.addItem(realUri, realUri);
		return true;
	}

}
