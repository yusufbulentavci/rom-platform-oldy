package com.bilgidoku.rom.site.yerel.common;

import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.tags.DlgTagEditorForResource;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TreeItem;

public abstract class NavToolbarBase extends FlowPanel {

	public SiteToolbarButton btnNew = new SiteToolbarButton("/_local/images/common/add.png", "", Ctrl.trans.newItem(),
			"");
	public SiteToolbarButton btnEdit = new SiteToolbarButton("/_local/images/common/pencil.png", "",
			Ctrl.trans.editItem(), "");
	public SiteToolbarButton btnDelete = new SiteToolbarButton("/_local/images/common/bin.png", "",
			Ctrl.trans.deleteItem(), "");
	public SiteToolbarButton btnReload = new SiteToolbarButton("/_local/images/common/refresh.png", "",
			Ctrl.trans.reloadGroup(), "");
	public SiteToolbarButton btnCopyLink = new SiteToolbarButton("/_local/images/common/copy.png", "",
			Ctrl.trans.copy(Ctrl.trans.link()), "");

	public SiteToolbarButton btnNewContainer = new SiteToolbarButton("/_local/images/common/folder_add.png", "",
			Ctrl.trans.newFolder(), "");
	public SiteToolbarButton btnRename = new SiteToolbarButton("/_local/images/common/rename_16.png", "",
			Ctrl.trans.rename(), "");
	
	public SiteToolbarButton btnTags = new SiteToolbarButton("/_local/images/common/tag.png", "",
			Ctrl.trans.rename(), "");

	public NavToolbarBase() {

		forNew();
		forEdit();
		forDelete();
		forRename();
		forCopyLink();
		forNewContainer();
		forReloadContainer();
		forTags();
	}

	private void forCopyLink() {
		btnCopyLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String uri = getUri();
				clip(uri);
				Ctrl.setStatus(Ctrl.trans.copied());
			}
		});
	}

	public abstract TreeItem getSelectedItem();

	public abstract void deleteItem();

	public abstract void editSelectedItem();

	public abstract void editItem(String uri);

	public abstract void newItem();

	public abstract void renameItem();

	public abstract void newContainer();

	public abstract void reloadContainer(TreeItem item);

	private void forReloadContainer() {
		btnReload.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				reloadContainer(null);
			}
		});
	}

	private void forNewContainer() {
		btnNewContainer.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				newContainer();
			}
		});

	}

	private void forDelete() {
		btnDelete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deleteItem();
			}
		});
	}

	private void forRename() {
		btnRename.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				renameItem();
			}
		});
	}
	
	private void forTags() {
		btnTags.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tagsDlg();
			}
		});
	}

	private void forEdit() {
		btnEdit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				editSelectedItem();
			}
		});
	}

	private void forNew() {
		btnNew.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				newItem();
			}
		});
	}

	public void containerSelected() {
		buttonsStates(true);
	}

	public void itemSelected() {
		buttonsStates(false);
	}

	public void buttonsStates(boolean conSelected) {
		btnDelete.setEnabled(true);
		btnEdit.setEnabled(!conSelected);
		btnRename.setEnabled(!conSelected);

	}

	public void rootLevelSelected() {
		btnDelete.setEnabled(false);
		btnEdit.setEnabled(false);
	}
	
	public void tagsDlg() {
		String path=getUri();
		if(path==null)
			return;
		DlgTagEditorForResource dlg=new DlgTagEditorForResource(path);
		dlg.run();
		dlg.show();
		dlg.center();
	}

	public final native void clip(String text) /*-{

		var copyElement = document.createElement('input');
		copyElement.setAttribute('type', 'text');
		copyElement.setAttribute('value', text);
		copyElement = document.body.appendChild(copyElement);
		copyElement.select();
		try {
			if (!document.execCommand('copy'))
				throw 'Not allowed.';
		} catch (e) {
			copyElement.remove();
			console.log("document.execCommand('copy'); is not supported");
			prompt('Copy the text below. (ctrl c, enter)', text);
		} finally {
			if (typeof e == 'undefined') {
				copyElement.remove();
			}
		}

	}-*/;

	protected String getUri() {
		TreeItem selected = getSelectedItem();
		if (selected == null) {
			Window.alert(Ctrl.trans.selectAnItem());
			return null;
		}

		SiteTreeItemData data = (SiteTreeItemData) selected.getUserObject();
		String uri = data.getUri();
		return uri;
	}

}
